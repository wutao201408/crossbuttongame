package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Coordinate;
import com.iooisoft.game.crossbuttongame.game.position.Position;

public class FSBtnWithLine extends Clearable{

	public FSBtnWithLine(Position position) {
		super(position);
	}

    public void linkTo( Position dst) {
        Coordinate dstCoord = dst.getCoordinate();
        Coordinate.RelativePosition targetRelative = 
            this.position.getCoordinate().getRelativePosition(dstCoord);
        
        //改变当前纽扣的状态为连接左上线的纽扣
        if( (targetRelative == Coordinate.RelativePosition.LEFT &&
        	this.stateValue == StateValue.FS_BTN_TP_LINE) ||
        	(targetRelative == Coordinate.RelativePosition.TOP &&
        	this.stateValue == StateValue.FS_BTN_LF_LINE)) {
    		PositionState state = new BtnWithLine(this.position);
    		state.setStateValue(StateValue.CL_BTN_TPLF_LINE);
    		this.position.setState(state);
        } 
        //改变当前纽扣的状态为连接右上线的纽扣
        else if( (targetRelative == Coordinate.RelativePosition.RIGHT &&
            	this.stateValue == StateValue.FS_BTN_TP_LINE) ||
            	(targetRelative == Coordinate.RelativePosition.TOP &&
            	this.stateValue == StateValue.FS_BTN_RT_LINE)) {
    		PositionState state = new BtnWithLine(this.position);
    		state.setStateValue(StateValue.CL_BTN_TPRT_LINE);
    		this.position.setState(state);
        } 
        //改变当前纽扣的状态为连接右下线的纽扣
        else if( (targetRelative == Coordinate.RelativePosition.RIGHT &&
            	this.stateValue == StateValue.FS_BTN_BL_LINE) ||
            	(targetRelative == Coordinate.RelativePosition.BELOW &&
            	this.stateValue == StateValue.FS_BTN_RT_LINE)) {
    		PositionState state = new BtnWithLine(this.position);
    		state.setStateValue(StateValue.CL_BTN_BLRT_LINE);
    		this.position.setState(state);
        } 
        //改变当前纽扣的状态为连接左下线的纽扣
        else if( (targetRelative == Coordinate.RelativePosition.BELOW &&
            	this.stateValue == StateValue.FS_BTN_LF_LINE) ||
            	(targetRelative == Coordinate.RelativePosition.LEFT &&
            	this.stateValue == StateValue.FS_BTN_BL_LINE)) {
    		PositionState state = new BtnWithLine(this.position);
    		state.setStateValue(StateValue.CL_BTN_BLLF_LINE);
    		this.position.setState(state);
        }
        //改变当前纽扣的状态为连接水平线的纽扣
        else if( (targetRelative == Coordinate.RelativePosition.LEFT &&
            	this.stateValue == StateValue.FS_BTN_RT_LINE) ||
            	(targetRelative == Coordinate.RelativePosition.RIGHT &&
            	this.stateValue == StateValue.FS_BTN_LF_LINE)) {
    		PositionState state = new BtnWithLine(this.position);
    		state.setStateValue(StateValue.CL_BTN_VER_LINE);
    		this.position.setState(state);
        }
        //改变当前纽扣的状态为连接垂直线的纽扣
        else if( (targetRelative == Coordinate.RelativePosition.BELOW &&
            	this.stateValue == StateValue.FS_BTN_TP_LINE) ||
            	(targetRelative == Coordinate.RelativePosition.TOP &&
            	this.stateValue == StateValue.FS_BTN_BL_LINE)) {
    		PositionState state = new BtnWithLine(this.position);
    		state.setStateValue(StateValue.CL_BTN_HOR_LINE);
    		this.position.setState(state);
        }
    }
    
	public void undo() {
		this.position.setState(new BtnNoLine(this.position));
	}
}
