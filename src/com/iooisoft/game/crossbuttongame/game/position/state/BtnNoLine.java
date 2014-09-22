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
    public boolean clear() {
    	return false;
    }
    public boolean linkBy( Position source) {
        Coordinate srcCoord = source.getCoordinate();
        Coordinate.RelativePosition srcRelative = 
            this.position.getCoordinate().getRelativePosition(srcCoord);
        PositionState state = null;
        boolean canLink = true;
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
        		canLink = false;
        		break;
        }
        
        return canLink;
        
    }
    
    public boolean isLinkedButton() {
        return false;
    }
    public boolean canCrossBy(StateValue lineType) {
        return true;
    }
	public void crossBy(StateValue lineType) {
		PositionState state = new BtnWithLine(this.position);
		if(lineType == StateValue.VER_LINE) {
			state.setStateValue(StateValue.CL_BTN_VER_LINE);
		} else {
			state.setStateValue(StateValue.CL_BTN_HOR_LINE);
		}
		this.position.setState(state);
	}
}
