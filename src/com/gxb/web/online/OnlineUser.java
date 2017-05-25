package com.gxb.web.online;

import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户内存 类.
 * 
 * @author lhq
 * @since JDK 1.6
 * @see
 */
public class OnlineUser implements Serializable {

	private static final long serialVersionUID = 1418954919330809213L;

	/**
	 * 当前用户访问唯一标识.
	 */
	private String token;
	/**
	 * 用户id
	 */
	private Integer usrId;
	/**
	 * 账号
	 */
	private String usrNo;
	/**
	 * 用户名
	 */
	private String usrNm;
	/**
	 * 手机号码
	 */
	private String tel;
	/**
	 * 用户头像
	 */
	private String userHead;
	/**
	 * 访问时间.
	 */
	private Date accessTime;
	
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getUsrId() {
		return usrId;
	}
	public void setUsrId(Integer usrId) {
		this.usrId = usrId;
	}
	public String getUsrNo() {
		return usrNo;
	}
	public void setUsrNo(String usrNo) {
		this.usrNo = usrNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	public OnlineUser(Integer usrId, String usrNo,
			String tel,String usrNm,String userHead) {
		this.usrId = usrId;
		this.usrNo = usrNo;
		this.tel = tel;
		this.usrNm = usrNm;
		this.userHead = userHead;
		
	}
	public OnlineUser(String tel){
		this.tel = tel;
	}
	public OnlineUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
