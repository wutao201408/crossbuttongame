package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Coordinate;
import com.iooisoft.game.crossbuttongame.game.position.Position;

public class BtnWithLine extends Clearable {

	public BtnWithLine(Position position) {
		super(position);
	}
	
	// 参数为出发点的源纽扣Position对象，根据当前目的纽扣连接着的线的方向，以及
	// 源纽扣相对于当前纽扣的位置判断源纽扣到目的纽扣是否可以连接
    public boolean canLinkBy( Position src) {
    	
    	// 判断源纽扣相对于目的纽扣的方位
        Coordinate srcCoord = src.getCoordinate();
        Coordinate.RelativePosition srcRelative = 
            this.position.getCoordinate().getRelativePosition(srcCoord);
        
        // 当前纽扣连接着一跟上方的线，并且源纽扣相对于目的纽扣是左，右，下的情况下
        // 可以连接
        if(this.stateValue == StateValue.CL_BTN_TP_LINE&
        		(srcRelative == Coordinate.RelativePosition.BELOW || 
        				srcRelative == Coordinate.RelativePosition.RIGHT ||
        				srcRelative == Coordinate.RelativePosition.LEFT)) {
            return true;
        } 
        // 当前纽扣连接着一跟下方的线，并且源纽扣相对于目的纽扣是左，右，上的情况下
        // 可以连接
        else if(this.stateValue == StateValue.CL_BTN_BL_LINE&
        		(srcRelative == Coordinate.RelativePosition.TOP || 
				srcRelative == Coordinate.RelativePosition.RIGHT ||
				srcRelative == Coordinate.RelativePosition.LEFT)) {
        	return true;
        } 
        // 当前纽扣连接着一跟左侧的线，并且源纽扣相对于目的纽扣是上，下，右的情况下
        // 可以连接
        else if(this.stateValue == StateValue.CL_BTN_LF_LINE&
        		(srcRelative == Coordinate.RelativePosition.BELOW || 
				srcRelative == Coordinate.RelativePosition.RIGHT ||
				srcRelative == Coordinate.RelativePosition.TOP)) {
        	return true;
        } 
        // 当前纽扣连接着一跟右侧的线，并且源纽扣相对于目的纽扣是上，下，左的情况下
        // 可以连接
        else if(this.stateValue == StateValue.CL_BTN_RT_LINE&
        		(srcRelative == Coordinate.RelativePosition.BELOW || 
				srcRelative == Coordinate.RelativePosition.LEFT ||
				srcRelative == Coordinate.RelativePosition.TOP)) {
        	return true;
        }
        return false;
    }
    
	public void undo() {
		this.position.setState(new BtnNoLine(this.position));
	}
	
	public boolean isLinkedButton() {
	    return true;
	}
}
