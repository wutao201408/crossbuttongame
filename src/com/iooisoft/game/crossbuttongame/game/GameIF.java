package com.iooisoft.game.crossbuttongame.game;

import java.util.List;

import com.iooisoft.game.crossbuttongame.game.GameRank.RankInfo;

public interface GameIF {
	
	public enum GameState {
		PAUSE,
		STOP,
		RUNNING;
	}
	
	public void startGame();
	
	public void pauseGame();
	
	public void stopGame();
	
	public void resumeGame();
	
	public GameStatus getGameStatus();
	
	public void SelectOnePosition(int row,int col);
	
	public void undoOperation();
	
	public void addGameEventListener(GameEventListener listener);
	
	public void setGameLevel(int level);
	
	public int recordRank(String name,long score);
	
	public List<RankInfo> getRank();
}
