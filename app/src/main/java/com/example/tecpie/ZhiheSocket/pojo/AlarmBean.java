package com.example.tecpie.ZhiheSocket.pojo;


/**
 * Created by xsy35 on 2017/12/13.
 */

public class AlarmBean {
    private String alarmGroupUid;
    private String alarmMessage;
    private String alarmState;
    private String code;
    private String condMethod;
    private int conditon1;
    private int condition2;
    private int dataType;
    private Boolean deviceChanged;
    private Group group;
    private String lastRecoveredTime;
    private String lastTriggeredTime;
    private String memo;
    private int operand1;
    private int operand2;
    private String timeCreated;
    private String timeModified;
    private String uid;
    private String valueOnLastEvent;
    private PlcSrc src;
	public String getAlarmGroupUid() {
		return alarmGroupUid;
	}
	public void setAlarmGroupUid(String alarmGroupUid) {
		this.alarmGroupUid = alarmGroupUid;
	}
	public String getAlarmMessage() {
		return alarmMessage;
	}
	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}
	public String getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(String alarmState) {
		this.alarmState = alarmState;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCondMethod() {
		return condMethod;
	}
	public void setCondMethod(String condMethod) {
		this.condMethod = condMethod;
	}
	public int getConditon1() {
		return conditon1;
	}
	public void setConditon1(int conditon1) {
		this.conditon1 = conditon1;
	}
	public int getCondition2() {
		return condition2;
	}
	public void setCondition2(int condition2) {
		this.condition2 = condition2;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Boolean getDeviceChanged() {
		return deviceChanged;
	}
	public void setDeviceChanged(Boolean deviceChanged) {
		this.deviceChanged = deviceChanged;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getLastRecoveredTime() {
		return lastRecoveredTime;
	}
	public void setLastRecoveredTime(String lastRecoveredTime) {
		this.lastRecoveredTime = lastRecoveredTime;
	}
	public String getLastTriggeredTime() {
		return lastTriggeredTime;
	}
	public void setLastTriggeredTime(String lastTriggeredTime) {
		this.lastTriggeredTime = lastTriggeredTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getOperand1() {
		return operand1;
	}
	public void setOperand1(int operand1) {
		this.operand1 = operand1;
	}
	public int getOperand2() {
		return operand2;
	}
	public void setOperand2(int operand2) {
		this.operand2 = operand2;
	}
	public String getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getTimeModified() {
		return timeModified;
	}
	public void setTimeModified(String timeModified) {
		this.timeModified = timeModified;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getValueOnLastEvent() {
		return valueOnLastEvent;
	}
	public void setValueOnLastEvent(String valueOnLastEvent) {
		this.valueOnLastEvent = valueOnLastEvent;
	}
	public PlcSrc getSrc() {
		return src;
	}
	public void setSrc(PlcSrc src) {
		this.src = src;
	}
	@Override
	public String toString() {
		return "AlarmBean [alarmGroupUid=" + alarmGroupUid + ", alarmMessage="
				+ alarmMessage + ", alarmState=" + alarmState + ", code="
				+ code + ", condMethod=" + condMethod + ", condition2="
				+ condition2 + ", conditon1=" + conditon1 + ", dataType="
				+ dataType + ", deviceChanged=" + deviceChanged
				+ ", lastRecoveredTime=" + lastRecoveredTime
				+ ", lastTriggeredTime=" + lastTriggeredTime + ", memo=" + memo
				+ ", operand1=" + operand1 + ", operand2=" + operand2
				+ ", timeCreated=" + timeCreated + ", timeModified="
				+ timeModified + ", uid=" + uid + ", valueOnLastEvent="
				+ valueOnLastEvent + "]";
	}
    
    
    
 
	
    
	
    
}
