package com.iooisoft.game.crossbuttongame.game;

public interface GameEventListener {
	public void gameDataUpdate(Object parameter );
	public void gameOver(Object parameter);
	public void changeLevel(Object parameter);
}
