package com.gxb.manager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysVersion;
import com.util.Page;
import com.util.StrUtil;

@Repository
public class SysVersionDao {
	@Resource(name="daoTemplate")
	private JdbcDaoTemplate daoTemplate;
	
	/**
	  *摘要：保存客户端版本号
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param version 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	public void saveVersion(SysVersion version) {
		try{
			this.daoTemplate.addByObject("sys_version",version);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * @说明：查询最新的版本更新
	 * @return
	 */
	public List<SysVersion> findLastVersion(Integer verSion){
		try {
			StringBuffer str = new StringBuffer("select * from sys_version where version_type = ? order by version_time desc ");
			return this.daoTemplate.queryForLists(str.toString(), SysVersion.class,verSion);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	  *摘要：查询版本
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param version
	  *@param @param page
	  *@param @param rows
	  *@param @return 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	public Page queryVersion(SysVersion version, Integer page, Integer rows) {
		StringBuffer sql = new StringBuffer(" select *,(select user_no from sys_user where id_key=version_user )as memberNm from sys_version where 1=1 ");
		String versionName = version.getVersionName();
		if(!StrUtil.isNull(versionName)){
			sql.append(" and version_name like'%").append(versionName).append("%' ");
		}
		sql.append(" order by id desc");
		try{
			return this.daoTemplate.queryForPageByMySql(sql.toString(), page, rows, SysVersion.class);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	  *摘要：删除客户端版本
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param id 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	public int deleteVersionById(Integer id) {
		StringBuffer sql = new StringBuffer(" delete from sys_version where 1=1 and id=? ");
		try{
			return this.daoTemplate.update(sql.toString(),id);
		}catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	  *摘要：根据id查询版本信息
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-18
	  *@param @param id
	  *@param @return 
	  *@修改历史：
	  *		[序号](YYP	2014-7-18)<修改说明>
	 */
	public SysVersion queryVersionById(Integer id) {
		StringBuffer sql = new StringBuffer(" select * from sys_version where 1=1 and id=? ");
		try{
			return this.daoTemplate.queryForObj(sql.toString(), SysVersion.class, id);
		}catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	  *摘要：修改版本信息
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-18
	  *@param @param version
	  *@param @return 
	  *@修改历史：
	  *		[序号](YYP	2014-7-18)<修改说明>
	 */
	public Object updateVersion(SysVersion version) {
		try{
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("id", version.getId());
			return this.daoTemplate.updateByObject("sys_version", version, params, "id");
		}catch (Exception e) {
			throw new DaoException(e);
		}
	}
}
