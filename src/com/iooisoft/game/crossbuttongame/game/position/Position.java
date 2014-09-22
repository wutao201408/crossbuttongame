package com.iooisoft.game.crossbuttongame.game.position;

import com.iooisoft.game.crossbuttongame.game.position.state.PositionState;

public class Position {
	private PositionState state;
	private Coordinate coordinate;
	
	public Coordinate getCoordinate() {
	    return this.coordinate;
	}
	public void setCoordinate(Coordinate coord) {
	    this.coordinate = coord;
	}
	public void setState(PositionState state) {
		this.state = state;
	}
	public PositionState getstate() {
		return this.state;
	}
	public boolean selected() {
        return this.state.selectButton();
    }
	public void crossBy(PositionState.StateValue lineType) {
		this.state.crossBy(lineType);
	}
	public boolean linkBy(Position src) {
		return this.state.linkBy(src);
	}
	public void linkTo(Position dst) {
		this.state.linkTo(dst);
	}
	public boolean canCrossBy(PositionState.StateValue lineType) {
	    return this.state.canCrossBy(lineType);
	}
	public boolean clear() {
		return this.state.clear();
	}
	public void undo() {
		this.state.undo();
	}
	public void addOneButton() {
        this.state.addButton();
    }
	public boolean isLinkedButton() {
	    return this.state.isLinkedButton();
	}
	public boolean isLine() {
	    return this.state.isLine();
	}
}
