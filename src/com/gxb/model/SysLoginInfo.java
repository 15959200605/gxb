package com.gxb.model;

import java.util.List;

/**
 *说明：
 *@创建：作者:llp		创建时间：2015-3-23
 *@修改历史：
 *		[序号](llp	2015-3-23)<修改说明>
 */
public class SysLoginInfo {
	private Integer idKey;				//用户id
	private String usrNm;				//用户名
	private String usrNo;				//账号
	private String usrPwd;				//密码
	private List<Integer> usrRoleIds;	//角色
	private Integer isSj;		    //是否平台管理员（1是；2否）
	
	public Integer getIdKey() {
		return idKey;
	}
	public void setIdKey(Integer idKey) {
		this.idKey = idKey;
	}
	public List<Integer> getUsrRoleIds() {
		return usrRoleIds;
	}
	public void setUsrRoleIds(List<Integer> usrRoleIds) {
		this.usrRoleIds = usrRoleIds;
	}
	public String getUsrPwd() {
		return usrPwd;
	}
	public void setUsrPwd(String usrPwd) {
		this.usrPwd = usrPwd;
	}
	public String getUsrNo() {
		return usrNo;
	}
	public void setUsrNo(String usrNo) {
		this.usrNo = usrNo;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public Integer getIsSj() {
		return isSj;
	}
	public void setIsSj(Integer isSj) {
		this.isSj = isSj;
	}
	
}