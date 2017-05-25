package com.gxb.model;

import com.util.TableAnnotation;

/**
 * 
 * @author YYP
 * 客户端版本管理实体类
 *
 */ 
public class SysVersion {

	private Integer id;                  //客户端版本id
	private String versionName;          //客户端版本名称
	private String versionTime;          //版本更新时间
	private Integer versionUser;          //版本更新发布人
	private String versionType;           //版本系统类型：0:android 1:ios
	private String versionContent;        //版本更新信息
	private String versionUrl;            //下载地址
	//==============不在数据库===============//
	private String memberNm;
	
	@TableAnnotation(insertAble=false,updateAble=false)
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVersionContent() {
		return versionContent;
	}
	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getVersionType() {
		return versionType;
	}
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	public String getVersionUrl() {
		return versionUrl;
	}
	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}
	public Integer getVersionUser() {
		return versionUser;
	}
	public void setVersionUser(Integer versionUser) {
		this.versionUser = versionUser;
	}
	public SysVersion() {
		super();
	}
	public SysVersion(Integer id, String versionName, String versionTime, Integer versionUser, String versionType, String versionContent) {
		super();
		this.id = id;
		this.versionName = versionName;
		this.versionTime = versionTime;
		this.versionUser = versionUser;
		this.versionType = versionType;
		this.versionContent = versionContent;
	}
	
}
