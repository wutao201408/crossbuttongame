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
	public void verticalCross() {
		this.state.verlineCross();
	}
	public void horizontalCross() {
		this.state.horlineCross();
	}
	public boolean canLinkBy(Position src) {
	    return this.state.canLinkBy(src);
	}
	public void linkBy(Position src) {
		this.state.linkBy(src);
	}
	public void linkTo(Position dst) {
		this.state.linkTo(dst);
	}
	public boolean canCrossByVerLine() {
	    return this.state.canCrossByVerLine();
	}
	public boolean canCrossByHorLine() {
	    return this.state.canCrossByHorLine();
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
