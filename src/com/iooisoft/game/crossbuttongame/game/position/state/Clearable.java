package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Position;

public class Clearable extends PositionState {

	public Clearable(Position position) {
		super(position);
	}

	public boolean selectButton() {
		return false;
	}
	
	// 清空自己状态为空格子状态，如果是可清空棋盘元素（除了空格子和普通纽扣以外的元素）的话
	// 返回true,否则返回false
	public boolean clear() {
		this.position.setState(new Blank(this.position));
		this.stateValue = StateValue.BLANK;
		return true;
	}
}
