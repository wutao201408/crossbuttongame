package com.iooisoft.game.crossbuttongame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iooisoft.game.crossbuttongame.game.position.Coordinate;
import com.iooisoft.game.crossbuttongame.game.position.Position;
import com.iooisoft.game.crossbuttongame.game.position.state.Blank;
import com.iooisoft.game.crossbuttongame.game.position.state.PositionState;

public class GameBoard {
	
	private Position[][] board;
	private Position lastPosition;
	private Position beginPosition;
	private List<Position> emptyPosList;
	private int rowNumber;
	private int colNumber;
	
	public GameBoard(int rowNumber,int colNumber) {
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
	}
	
	public Position[][] getBoard() {
		return this.board;
	}
	
	public void initGameBoard() {
		this.board = new Position[rowNumber][colNumber];
		this.emptyPosList = new ArrayList<Position>();
		for( int row=0;row<this.rowNumber;row++) {
			for( int col=0;col<this.colNumber;col++ ) {
				Position pos = new Position();
				PositionState posState = new Blank(pos);
				posState.setStateValue(PositionState.StateValue.BLANK);
				pos.setState(posState);
				pos.setCoordinate(new Coordinate(row,col));
				this.board[row][col] = pos;
				this.emptyPosList.add(pos);
			}
		}
		this.lastPosition = null;
		this.beginPosition = null;
	}
	
	private void linkFromLeftToRight( int row,int colFrom,int colTo ) {
		for( int col = colFrom+1;col<colTo;col++ ) {
	        Position temp = board[row][col];
	        temp.crossBy(PositionState.StateValue.VER_LINE);
	        if(emptyPosList.contains(temp)) {
	            this.emptyPosList.remove(temp);
	        }
		}
	}
	
	private void linkFromBlewToTop( int col,int rowFrom,int rowTo ) {
		for( int row = rowFrom+1;row<rowTo;row++ ) {
	        Position temp = board[row][col];
	        temp.crossBy(PositionState.StateValue.HOR_LINE);
	        if(emptyPosList.contains(temp)) {
	            this.emptyPosList.remove(temp);
	        }
		}
	}
	
	private void linkTwoPosition( Position src, Position dst ) {

		src.linkTo(dst);
		
	    int srcRow = src.getCoordinate().getRow();
	    int srcCol = src.getCoordinate().getCol();
	    int dstRow = dst.getCoordinate().getRow();
	    int dstCol = dst.getCoordinate().getCol();
	    
	    if( srcRow == dstRow ) {
	    	if( srcCol < dstCol ) {
	    		linkFromLeftToRight(srcRow,srcCol,dstCol);
	    	} else {
	    		linkFromLeftToRight(srcRow,dstCol,srcCol);
	    	}
	    } else {
	    	if( srcRow < dstRow ) {
	    		linkFromBlewToTop(srcCol,srcRow,dstRow);
	    	} else {
	    		linkFromBlewToTop(srcCol,dstRow,srcRow);
	    	}
	    }
	}
	
	private boolean judgeFromLeftToRight( int row,int colFrom,int colTo ) {
		for( int col = colFrom+1;col<colTo;col++ ) {
	        Position temp = board[row][col];
	        if(!temp.canCrossBy(PositionState.StateValue.VER_LINE)) {
	        	return false;
	        }
		}
		return true;
	}
	
	private boolean judgeFromBlewToTop( int col,int rowFrom,int rowTo ) {
		for( int row = rowFrom+1;row<rowTo;row++ ) {
	        Position temp = board[row][col];
	        if(!temp.canCrossBy(PositionState.StateValue.HOR_LINE)) {
	        	return false;
	        }
		}
		return true;
	}
	
	private boolean canRouteCross( Position src,Position dst ) {
	    int srcRow = src.getCoordinate().getRow();
	    int srcCol = src.getCoordinate().getCol();
	    int dstRow = dst.getCoordinate().getRow();
	    int dstCol = dst.getCoordinate().getCol();
	    
	    boolean result;
	    if( srcRow == dstRow ) {
	    	if( srcCol < dstCol ) {
	    		result = judgeFromLeftToRight(srcRow,srcCol,dstCol);
	    	} else {
	    		result = judgeFromLeftToRight(srcRow,dstCol,srcCol);
	    	}
	    } else {
	    	if( srcRow < dstRow ) {
	    		result = judgeFromBlewToTop(srcCol,srcRow,dstRow);
	    	} else {
	    		result = judgeFromBlewToTop(srcCol,dstRow,srcRow);
	    	}
	    }
	    
	    return result;
	}
	
	static class SelectResult {
		private boolean needRepaint;
		public boolean isNeedRepaint() {
			return needRepaint;
		}
		public void setNeedRepaint(boolean needRepaint) {
			this.needRepaint = needRepaint;
		}
		public int getGetButtons() {
			return getButtons;
		}
		public void setGetButtons(int getButtons) {
			this.getButtons = getButtons;
		}
		private int getButtons;
		SelectResult() {
			this.getButtons = 0;
			this.needRepaint = false;
		}
		SelectResult(boolean needRepaint,int getButtons) {
			this.needRepaint = needRepaint;
			this.getButtons = getButtons;
		}
	}
	
	// 此方法为当用户选中一个格子的时候所被调用的方法。
	// 参数是格子的[x,y]坐标
	// 返回值为棋盘是否需要进行重绘
	public SelectResult selectPosition(int row,int col) {
		
		// 获取此坐标的Position对象
		Position dst = board[row][col];
		
		// 如果初次选中一个纽扣的话，将初始纽扣的位置设置为
		// 此位置。
		if( lastPosition == null ) {
		    if( dst.selected()) {
		        lastPosition = dst;
		        beginPosition = dst;
		        return new SelectResult(true,0);
		    } else {
		    	// 如未选中纽扣则返回false;
		    	return new SelectResult();
		    }
		}
		
		// 继续判断源纽扣和目标纽扣的两个端点间的通路是否可以连通
		if( !this.canRouteCross(lastPosition, dst) ) {
			return new SelectResult();
		}
		
		// 已经在穿纽扣的过程中,判断目标的格子是否是一个可以连接
		// 的纽扣
		if( !dst.linkBy(lastPosition) ) {
			return new SelectResult();
		}
		
		// 连接两个端点并且设置路径上的格子为连接线
		this.linkTwoPosition(this.lastPosition, dst);
		lastPosition = dst;
		
		// 程序走到这里表明源纽扣与用户选择的目标坐标上的纽扣可以进行
		// 连接，此时需要判断如果目标纽扣与初始纽扣为同一纽扣的话
		// 则说明形成了一个闭路，此时清空路径上所有的元素为空格子
		// 如果不为同一纽扣的话说明还未形成闭路，此时将连接源纽扣
		// 和目标纽扣。
		if( beginPosition == dst ) {
			int buttonNum = 0;
			for( int x=0;x<rowNumber;x++) {
				for( int y=0;y<colNumber;y++) {
					Position pos = board[x][y];
					if( pos.isLinkedButton() ) {
						buttonNum++;
					}
					if(pos.clear()) {
						this.emptyPosList.add(pos);
					}		
				}
			}
			beginPosition = null;
			lastPosition = null;
			return new SelectResult(true,buttonNum);
		}
		
		return new SelectResult(true,0);
	}
	
	public void undoOperation() {
		for( int x=0;x<rowNumber;x++)
			for( int y=0;y<colNumber;y++) {
				Position pos = board[x][y];
				if( pos.isLine() ) {
				    this.emptyPosList.add(pos);
				}
				pos.undo();
			}
		beginPosition = null;
		lastPosition = null;
	}
	
	public boolean putButton() {
	    
	    // 游戏结束的两个条件，一是游戏界面里已经没有可以存放的
	    // 空格子了。二是所有的纽扣都已经被添加到棋盘上，没有多余
	    // 的纽扣可以被添加了。
	    int remainPosSize = this.emptyPosList.size();
		if( remainPosSize == 0 ) {
		    return false;
		}
		
		// 在剩余的空格子上随机选择一个然后添加纽扣
		Random rand = new Random();
		Position pos = this.emptyPosList.remove(rand.nextInt(remainPosSize));
		pos.addOneButton();
		return true;
	}
	
	public PositionState.StateValue[][] getPositionState() {
		PositionState.StateValue[][] posStates = 
			new PositionState.StateValue[this.rowNumber][this.colNumber];
		for( int row=0;row<this.rowNumber;row++) {
			for( int col=0;col<this.colNumber;col++ ) {
				posStates[row][col]=this.board[row][col].getstate().getStateValue();
			}
		}
		return posStates;
	}
}


