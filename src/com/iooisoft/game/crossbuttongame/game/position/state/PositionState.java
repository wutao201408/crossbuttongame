package com.iooisoft.game.crossbuttongame.game.position.state;

import com.iooisoft.game.crossbuttongame.game.position.Position;


/* 此类为状态模式中的各个状态子类所实现的接口类
 * 
 */
public class PositionState {
	
	public enum StateValue {
		BLANK,CROSS_LINE,VER_LINE,HOR_LINE,
		CL_BTN_NOLINE,CL_BTN_BLLF_LINE,CL_BTN_BL_LINE,
		CL_BTN_BLRT_LINE,CL_BTN_HOR_LINE,CL_BTN_LF_LINE,
		CL_BTN_RT_LINE,CL_BTN_TPLF_LINE,CL_BTN_TP_LINE,
		CL_BTN_TPRT_LINE,CL_BTN_VER_LINE,FS_BTN_NOLINE,
		FS_BTN_BL_LINE,FS_BTN_LF_LINE,FS_BTN_RT_LINE,
		FS_BTN_TP_LINE
	}
	
	// 保存一个Position的引用，此Position在状态模式中未Context类。
	protected Position position;
	
	protected StateValue stateValue;
	public PositionState(Position position){
		this.position = position;
	}
	public StateValue getStateValue() {
	    return this.stateValue;
	}
	public void setStateValue(StateValue value) {
	    this.stateValue = value;
	}
	public void addButton() {
		// Do nothing.
	}
	public boolean selectButton() {
		return false;
	}
	public void verlineCross() {
		// Do nothing.
	}
	public void horlineCross() {
		// Do nothing.
	}
	public boolean canLinkBy( Position src) {
	    return false;
	}
	public boolean canCrossByVerLine() {
	    return false;
	}
	public boolean canCrossByHorLine() {
	    return false;
	}
	public void linkBy(Position src) {
	}
	public void linkTo(Position dst) {
	}
	
	// 清空自己，如果是可清空元素（除了空格子和普通纽扣以外的元素）的话
	// 返回true,否则返回false
	public boolean clear() {
		return false;
	}
	public void undo() {
		// Do nothing.
	}
	public boolean isLinkedButton() {
	    return false;
	}
	public boolean isLine() {
	    return false;
	}
}

