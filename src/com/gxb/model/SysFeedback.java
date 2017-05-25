package com.gxb.model;

import com.util.TableAnnotation;

/**
 * 意见反馈
 * @author dzr
 *
 */
public class SysFeedback {

	private Integer fbId;//反馈id
	private Integer memberId;//用户id
	private String fbTime;//反馈时间
	private String fbContent;//反馈内容
	/////////不在数据库////////////
	private String memberNo;//帐号名称
	public Integer getFbId() {
		return fbId;
	}
	public void setFbId(Integer fbId) {
		this.fbId = fbId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getFbTime() {
		return fbTime;
	}
	public void setFbTime(String fbTime) {
		this.fbTime = fbTime;
	}
	public String getFbContent() {
		return fbContent;
	}
	public void setFbContent(String fbContent) {
		this.fbContent = fbContent;
	}
	@TableAnnotation(insertAble=false,updateAble=false)
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
}
