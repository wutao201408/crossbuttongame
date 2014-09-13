package com.iooisoft.game.crossbuttongame.gui;  
      
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.iooisoft.game.crossbuttongame.common.CommonConst;
import com.iooisoft.game.crossbuttongame.game.GameIF;
import com.iooisoft.game.crossbuttongame.game.position.state.PositionState;
import com.iooisoft.game.crossbuttongame.game.position.state.PositionState.StateValue;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
         
	private Map<String,Image> positionImg = new HashMap<String,Image>();
	private PositionState.StateValue[][] boardStates;
	private GameIF game;
	
	class MouseAction implements MouseListener{
		
		public void mouseClicked(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {

		}

		public void mousePressed(MouseEvent e){  
			
			if( game.getGameStatus().getGameState() != GameIF.GameState.RUNNING ) {
				return;
			}
			
			if(e.getButton() == MouseEvent.BUTTON1) {
				//将鼠标点击的坐标位置转换成网格索引  
				int row=(e.getY()-CommonConst.MARGIN)/CommonConst.GRID_SPAN;
				int col=(e.getX()-CommonConst.MARGIN)/CommonConst.GRID_SPAN;
				game.SelectOnePosition(row, col);
			} else if( e.getButton() == MouseEvent.BUTTON3 ) {
				game.undoOperation();
			}
		}  

		public void mouseReleased(MouseEvent arg0) {
		}
		
	}
	
	synchronized public void setBoardStates(StateValue[][] states) {
		this.boardStates = states;
	}
	
    public BoardPanel(GameIF game){  
    	
    	this.game = game;
    	
    	// 将棋盘上所有元素的图片读入到内存中
        for( StateValue value : StateValue.values()) {
        	Image img = new ImageIcon(CommonConst.IMAGE_FILE_PATH+value.toString()+".png").getImage(); 
        	this.positionImg.put(value.toString(), img);
        }
        this.boardStates = game.getGameStatus().getPosState();
        this.addMouseListener(new MouseAction());
    }
         
    synchronized public void paintComponent(Graphics g){
        super.paintComponent(g);
		for( int row=0;row<this.boardStates.length;row++ ) {
			StateValue[] rowState = this.boardStates[row];
			for( int col=0;col<rowState.length;col++ ) {
	     		   g.drawImage(this.positionImg.get(rowState[col].toString()),
	     				  col*CommonConst.GRID_SPAN+CommonConst.MARGIN, 
	     				 row*CommonConst.GRID_SPAN+CommonConst.MARGIN, null);
			}
		}
    }
}  