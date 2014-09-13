package com.iooisoft.game.crossbuttongame;

import javax.swing.SwingUtilities;

import com.iooisoft.game.crossbuttongame.common.CommonConst;
import com.iooisoft.game.crossbuttongame.game.CrossButtonGame;
import com.iooisoft.game.crossbuttongame.gui.GameFrame;

public class Main {
	public static void main(String[] args) {
		// 生成一个具有固定行数和固定列数的游戏棋盘类实例
		final CrossButtonGame game = new CrossButtonGame(CommonConst.ROWS,
				CommonConst.COLS,CommonConst.MAX_BUTTON_NUM,CommonConst.MAX_INTERVAL);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameFrame gameFrame = new GameFrame(game);		
				gameFrame.setVisible(true);
			}
		});
	}
}
