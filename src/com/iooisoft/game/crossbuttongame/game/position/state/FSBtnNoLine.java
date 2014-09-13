package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Coordinate;
import com.iooisoft.game.crossbuttongame.game.position.Position;

public class FSBtnNoLine extends PositionState {

	public FSBtnNoLine(Position position) {
		super(position);
		this.stateValue = StateValue.FS_BTN_NOLINE;
	}
	
    public boolean clear() {
    	return false;
    }
	
	public void undo() {
		this.position.setState(new BtnNoLine(this.position));
	}
    public void linkTo( Position dst) {
        Coordinate dstCoord = dst.getCoordinate();
        Coordinate.RelativePosition targetRelative = 
            this.position.getCoordinate().getRelativePosition(dstCoord);
        PositionState state = null;
        switch(targetRelative) {
        	case LEFT:
        		state = new BtnWithLine(this.position);
        		state.setStateValue(StateValue.CL_BTN_LF_LINE);
        		this.position.setState(state);
        		break;
        	case RIGHT:
        		state = new BtnWithLine(this.position);
        		state.setStateValue(StateValue.CL_BTN_RT_LINE);
        		this.position.setState(state);
        		break;
        	case TOP:
        		state = new BtnWithLine(this.position);
        		state.setStateValue(StateValue.CL_BTN_TP_LINE);
        		this.position.setState(state);
        		break;
        	case BELOW:
        		state = new BtnWithLine(this.position);
        		state.setStateValue(StateValue.CL_BTN_BL_LINE);
        		this.position.setState(state);
        		break;
        	default:
        		break;
        }
    }
    public boolean isLinkedButton() {
        return false;
    }
}
