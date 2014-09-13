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

	public void horlineCross() {
		if(this.stateValue == StateValue.VER_LINE) {
			this.stateValue = StateValue.CROSS_LINE;
		}
	}
	
	public void verlineCross() {
		if(this.stateValue == StateValue.HOR_LINE) {
			this.stateValue = StateValue.CROSS_LINE;
		}
	}
	public boolean canCrossByVerLine() {
		if(this.stateValue == StateValue.HOR_LINE) {
			return true;
		}
		return false;
	}
	
	public boolean canCrossByHorLine() {
		if(this.stateValue == StateValue.VER_LINE) {
			return true;
		}
		return false;
	}
}
