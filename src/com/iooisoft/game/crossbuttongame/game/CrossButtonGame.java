package com.iooisoft.game.crossbuttongame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.iooisoft.game.crossbuttongame.common.CommonConst;
import com.iooisoft.game.crossbuttongame.game.GameRank.RankInfo;
import com.iooisoft.game.crossbuttongame.game.position.state.PositionState;


public class CrossButtonGame implements GameIF{
	
	// 游戏包含一个棋盘
	private GameBoard gameBoard;
	
	private int maxRow,maxCol;
	
	// 游戏包含可以用来投入到棋盘的纽扣
	private int buttons;
	
	// 游戏管理自己的状态信息
	private GameState state;
	
	// 游戏包含一个定时器，间隔固定时间调用游戏类的方法
	private Timer addButtonTimer;
	
	private long score;
	
	private int level;
	
	private int rank;
	
	private GameRank gameRank;
	
	private List<GameEventListener> listeners;

	// 向棋盘添加纽扣的最大时间间隔。随着游戏级别的提升(level)，实际的添加纽扣的
	// 时间间隔通过 maxInterval/level算出
	private final int maxInterval;
	
	private final int maxButtonNum;

	// 包含一个定时器调用的内部类。采用内部类的原因是因为
	// 内部类里的run方法可以很方便的方位所有此游戏类的成员与方法
	class AddButtonTask extends TimerTask {
		public void run() {
			putButton();
		}
	}

	// 设置一个带参数的构造器，强制生成游戏实例的时候设置游戏棋盘
	public CrossButtonGame( int rows,int cols,int maxButtonNum,int maxInterval) {
		this.listeners = new ArrayList<GameEventListener>();
		this.maxRow = rows;
		this.maxCol = cols;
		this.gameBoard = new GameBoard(rows,cols);
		this.gameBoard.initGameBoard();
		this.maxInterval = maxInterval;
		this.maxButtonNum = maxButtonNum;
		this.level = 1;
		this.state = GameState.STOP;
		this.score = 0;
		this.rank = GameRank.NO_RANK;
		this.gameRank = new GameRank(10,CommonConst.RANK_FILE_PATH +"rank.data");
	}

	public void addGameEventListener(GameEventListener listener) {
		this.listeners.add(listener);
	}
	
	public synchronized void startGame() {
		this.state = GameState.RUNNING;
		this.buttons = this.maxButtonNum;
		this.rank = GameRank.NO_RANK;
		this.score = 0;
		if( this.addButtonTimer != null ) {
			this.addButtonTimer.cancel();
			this.addButtonTimer.purge();
		}
		this.gameBoard.initGameBoard();
		for( int cnt=0;cnt<CommonConst.INIT_BUTTONS;cnt++ ) {
			this.putButton();
		}

		this.addButtonTimer = new Timer();
		this.addButtonTimer.schedule(new AddButtonTask(), 
				0, this.maxInterval/level);
		for(GameEventListener listener:this.listeners) {
			listener.gameDataUpdate(this.getGameStatus());
		}
	}
	
	public synchronized void pauseGame() {
		this.state = GameState.PAUSE;
		this.addButtonTimer.cancel();
		this.addButtonTimer.purge();
	}
	
	public synchronized void stopGame() {
		this.state = GameState.STOP;
		this.addButtonTimer.cancel();
		this.addButtonTimer.purge();
	}
	
	public synchronized void resumeGame() {
		this.state = GameState.RUNNING;
		this.addButtonTimer = new Timer();
		this.addButtonTimer.schedule(new AddButtonTask(), 
				0, this.maxInterval/level);
	}
	
	private void gameOver() {
		this.stopGame();
		if(this.score>0) {
			this.rank = gameRank.computeRank(this.score);
		}
		for(GameEventListener listener:this.listeners) {
			listener.gameOver(this.getGameStatus());
		}
	}
	
	public synchronized void putButton() {
		
		// 没有纽扣可以添加到棋盘上，游戏结束
		if(this.buttons==0) {
			gameOver();
			return;
		}
		if(!this.gameBoard.putButton()) {
			gameOver();
			return;
		} else {
			this.buttons--;
		}
		for(GameEventListener listener:this.listeners) {
			listener.gameDataUpdate(this.getGameStatus());
		}
	}
	
	public synchronized void SelectOnePosition(int row,int col) {
		
		// 只要游戏不是在运行状态中，任何选择棋盘上的元素操作都被忽略
		if(this.state != GameState.RUNNING) {
			return;
		}
	
		if( row >= this.maxRow || col >=this.maxCol ) {
			return;
		}
		
		GameBoard.SelectResult result = this.gameBoard.selectPosition(row, col);
		this.buttons = this.buttons + result.getGetButtons();
		this.score = this.score + 5*result.getGetButtons()*result.getGetButtons();
		int newLevel  = this.computeLevel(this.score);
		if( newLevel != this.level ) {
			this.level = newLevel;
			this.pauseGame();
			this.resumeGame();
			for(GameEventListener listener:this.listeners) {
				listener.changeLevel(this.getGameStatus());
			}
		}
		if( result.isNeedRepaint() ) {
			//进行棋盘的重绘动作
			for(GameEventListener listener:this.listeners) {
				listener.gameDataUpdate(this.getGameStatus());
			}
		}
	}
	
	private int computeLevel(long score) {
		
		int cnt=0;
		for( ;cnt<CommonConst.LEVEL_MAPPING_SCORE.length;cnt++ ) {
			if( score<CommonConst.LEVEL_MAPPING_SCORE[cnt] ) {
				return cnt+1;
			}
		}
		
		return cnt+1;
	} 
	
	
	public synchronized void undoOperation() {
		this.gameBoard.undoOperation();
		for(GameEventListener listener:this.listeners) {
			listener.gameDataUpdate(this.getGameStatus());
		}
	}
	
	public synchronized GameStatus getGameStatus() {
		GameStatus gameData = new GameStatus();
		gameData.setGameState(this.state);
		gameData.setLevel(this.level);
		PositionState.StateValue posState[][] = this.gameBoard.getPositionState();
		gameData.setPosState(posState);
		gameData.setRemainButtons(this.buttons);
		gameData.setScore(this.score);
		gameData.setRank(this.rank);
		return gameData;
	}

	public void setGameLevel(int level) {
		this.level = level;
	}

	public int recordRank(String name, long score) {
		return this.gameRank.recordRank(name, score);
	}

	@Override
	public List<RankInfo> getRank() {
		return this.gameRank.getRankInfo();
	}
}
