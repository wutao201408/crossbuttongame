package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Coordinate;
import com.iooisoft.game.crossbuttongame.game.position.Position;

public class BtnNoLine extends PositionState {

	public BtnNoLine(Position position) {
		super(position);
		this.stateValue = StateValue.CL_BTN_NOLINE;
	}

	public boolean selectButton() {
		this.position.setState(new FSBtnNoLine(this.position));
		return true;
	}
	public void linedFromLeft() {
		//this.position.setState(new FocusButtonWithLeftLine(this.position));
	}
	public void linedFromRight() {
		//this.position.setState(new FocusButtonWithRightLine(this.position));
	}
	public void linedFromTop() {
		//this.position.setState(new FocusButtonWithTopLine(this.position));
	}
	public void linedFromBelow() {
		//this.position.setState(new FocusButtonWithBelowLine(this.position));
	}
	public boolean canLinkBy( Position source) {
	    Coordinate srcCoord = source.getCoordinate();
	    Coordinate.RelativePosition srcRelative = 
            this.position.getCoordinate().getRelativePosition(srcCoord);
        if( (srcRelative != Coordinate.RelativePosition.NOT_VALID)) {
            return true;
        } 
        return false;
	}
	
    public boolean clear() {
    	return false;
    }
    public void linkBy( Position source) {
        Coordinate srcCoord = source.getCoordinate();
        Coordinate.RelativePosition srcRelative = 
            this.position.getCoordinate().getRelativePosition(srcCoord);
        PositionState state = null;
        switch(srcRelative) {
        	case LEFT:
        		state = new FSBtnWithLine(this.position);
        		state.setStateValue(StateValue.FS_BTN_LF_LINE);
        		this.position.setState(state);
        		//linedFromLeft();
        		break;
        	case RIGHT:
        		state = new FSBtnWithLine(this.position);
        		state.setStateValue(StateValue.FS_BTN_RT_LINE);
        		this.position.setState(state);
        		//linedFromRight();
        		break;
        	case TOP:
        		state = new FSBtnWithLine(this.position);
        		state.setStateValue(StateValue.FS_BTN_TP_LINE);
        		this.position.setState(state);
        		//linedFromTop();
        		break;
        	case BELOW:
        		state = new FSBtnWithLine(this.position);
        		state.setStateValue(StateValue.FS_BTN_BL_LINE);
        		this.position.setState(state);
        		//linedFromBelow();
        		break;
        	default:
        		break;
        }
    }
    
    public boolean isLinkedButton() {
        return false;
    }
	public boolean canCrossByVerLine() {
	    return true;
	}
	public boolean canCrossByHorLine() {
	    return true;
	}
	public void verlineCross() {
		PositionState state = new BtnWithLine(this.position);
		state.setStateValue(StateValue.CL_BTN_VER_LINE);
		this.position.setState(state);
	}
	public void horlineCross() {
		PositionState state = new BtnWithLine(this.position);
		state.setStateValue(StateValue.CL_BTN_HOR_LINE);
		this.position.setState(state);
	}
}
