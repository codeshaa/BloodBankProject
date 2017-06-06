/**
 * 
 */
package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean;

/**
 * @author JOEL
 *
 */
public class RequestBean {
	
	private Integer requestId;
	private UserBean donor;
	private UserBean requester;
	private Integer status;
	private String requesterMessage;
	
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public UserBean getDonor() {
		return donor;
	}
	public void setDonor(UserBean donor) {
		this.donor = donor;
	}
	public UserBean getRequester() {
		return requester;
	}
	public void setRequester(UserBean requester) {
		this.requester = requester;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRequesterMessage() {
		return requesterMessage;
	}
	public void setRequesterMessage(String requesterMessage) {
		this.requesterMessage = requesterMessage;
	}
	
	

}
