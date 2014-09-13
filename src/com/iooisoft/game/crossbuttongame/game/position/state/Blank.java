package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Position;

public class Blank extends PositionState{

	public Blank(Position position) {
		super(position);
		this.stateValue = StateValue.BLANK;
	}

	public void addButton() {
		this.position.setState(new BtnNoLine(this.position));
	}
	
	public void verlineCross() {
		Line state = new Line(this.position);
		state.setStateValue(StateValue.VER_LINE);
		this.position.setState(state);
	}

	public void horlineCross() {
		Line state = new Line(this.position);
		state.setStateValue(StateValue.HOR_LINE);
		this.position.setState(state);
	}
    public boolean canCrossByVerLine() {
        return true;
    }
    public boolean canCrossByHorLine() {
        return true;
    }
    public boolean clear() {
        return false;
    }
    public boolean isLinkedButton() {
        return false;
    }
}
