package com.gxb.model;

import java.util.Date;

import com.util.TableAnnotation;

/**
 * 用户信息
 * 
 * @author LHC
 * 
 */
public class SysUser {
	private Integer idKey;// 用户id

	private String userNo;// 用户帐号

	private String userNm;// 用户名称

	private String userPwd;// 用户密码

	private String tel;// 手机号

	private String longitude;// 经度

	private String latitude;// 纬度

	private Date regtime;// 注册时间

	private Integer isSj;// 用户类型（1后台用户；2商家）

	private Integer sortId;// 自定义排序号

	/*************** 不在数据库***************/
	private String regtimeStr;// 注册时间

	public Integer getIdKey() {
		return idKey;
	}

	public void setIdKey(Integer idKey) {
		this.idKey = idKey;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public Integer getIsSj() {
		return isSj;
	}

	public void setIsSj(Integer isSj) {
		this.isSj = isSj;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@TableAnnotation(insertAble=false,updateAble=false)
	public String getRegtimeStr() {
		return regtimeStr;
	}

	public void setRegtimeStr(String regtimeStr) {
		this.regtimeStr = regtimeStr;
	}

}
