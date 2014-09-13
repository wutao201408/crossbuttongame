package com.iooisoft.game.crossbuttongame.game;

import com.iooisoft.game.crossbuttongame.game.position.state.PositionState;

public class GameStatus {
	
	private GameIF.GameState gameState;
	private int remainButtons;
	private long score;
	private int level;
	private int rank;
	private PositionState.StateValue[][] posState; 
	
	public GameStatus() {
	}
	
	public GameStatus(GameIF.GameState gameState,int remainButtons,
			long score,int level,
			PositionState.StateValue[][] posState) {
		this.gameState = gameState;
		this.remainButtons = remainButtons;
		this.score = score;
		this.level = level;
		this.posState = posState;
		this.rank = GameRank.NO_RANK;
	}
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public GameStatus(PositionState.StateValue[][] posState) {
		this(GameIF.GameState.STOP,0,0L,0,posState );
	}
	
	public GameIF.GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameIF.GameState gameState) {
		this.gameState = gameState;
	}

	public int getRemainButtons() {
		return remainButtons;
	}
	public void setRemainButtons(int remainButtons) {
		this.remainButtons = remainButtons;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public PositionState.StateValue[][] getPosState() {
		return posState;
	}
	public void setPosState(PositionState.StateValue[][] posState) {
		this.posState = posState;
	}
}
