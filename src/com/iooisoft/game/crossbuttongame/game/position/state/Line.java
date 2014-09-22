package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Position;

public class Line extends Clearable {

	public Line(Position position) {
		super(position);
		this.stateValue = StateValue.CROSS_LINE;
	}

	public void undo() {
		this.position.setState(new Blank(this.position));
	}
	
    public boolean isLinkedButton() {
        return false;
    }
    
	public boolean isLine() {
	    return true;
	}

	public void crossBy(StateValue lineType) {
		if(this.stateValue != lineType && this.stateValue != StateValue.CROSS_LINE) {
			this.stateValue = StateValue.CROSS_LINE;
		}
	}
	public boolean canCrossBy(StateValue lineType) {
		if(this.stateValue != lineType && this.stateValue != StateValue.CROSS_LINE) {
			return true;
		}
		return false;
	}
}
