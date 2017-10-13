package com.parkspace.agent.model;

import java.io.Serializable;

/**
 * @Title: SocketDataModel.java
 * @Package com.parkspace.model
 * <p>Description:数据传输model</p>
 * @author sunld
 * @version V1.0.0 
 * <p>CreateDate:2017年10月12日 下午4:19:41</p>
*/

public class SocketDataModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	//车牌号
	private String carno;
	//是否成功
	private boolean isSuccess;
	//车位编号,形如3-101
	private String spaceno;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getSpaceno() {
		return spaceno;
	}
	public void setSpaceno(String spaceno) {
		this.spaceno = spaceno;
	}
	@Override
	public String toString() {
		return "SocketDataModel [userId=" + userId + ", carno=" + carno + ", isSuccess=" + isSuccess + ", spaceno="
				+ spaceno + "]";
	}
	

}
