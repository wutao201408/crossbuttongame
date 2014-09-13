package com.iooisoft.game.crossbuttongame.game.position;

public class Coordinate {
	
	public enum RelativePosition {
		LEFT,RIGHT,TOP,BELOW,NOT_VALID
	}
	
    private int row;
    private int col;
    
    public Coordinate(int row,int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getCol() {
        return this.col;
    }
    public int getRow() {
        return this.row;
    }
    
    public RelativePosition getRelativePosition(int row,int col) {
        if(  row == this.row && col > this.col ) {
            return RelativePosition.RIGHT;
        } else if( row == this.row && col < this.col ) {
            return RelativePosition.LEFT;
        } else if( col == this.col && row > this.row ) {
            return RelativePosition.BELOW;
        } else if( col == this.col && row < this.row ) {
            return RelativePosition.TOP;
        } else {
            return RelativePosition.NOT_VALID;
        }
    }
    public RelativePosition getRelativePosition(Coordinate coord) {
        return getRelativePosition(coord.getRow(),coord.getCol());
    }
}
