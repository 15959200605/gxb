package com.gxb.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.exp.ServiceException;
import com.gxb.manager.dao.SysVersionDao;
import com.gxb.model.SysVersion;
import com.util.Page;


@Service
public class SysVersionService {
	@Resource
	private SysVersionDao versionDao;
	/**
	  *摘要：保存客户端版本号
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param version 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	public void addVersion(SysVersion version) {
		try{
			this.versionDao.saveVersion(version);
		}catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @说明：查询最新的版本更新
	 * @return
	 */
	public List<SysVersion> findLastVersion(Integer verSion){
		return this.versionDao.findLastVersion(verSion);
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
		try{
			return this.versionDao.queryVersion(version,page,rows);
		}catch (Exception e) {
			throw new ServiceException(e);
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
	public void deleteVersionById(Integer id) {
		try{
			this.versionDao.deleteVersionById(id);
		}catch (Exception e) {
			throw new ServiceException(e);
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
		try{
			return this.versionDao.queryVersionById(id);
		}catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	  *摘要：修改版本信息
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-18
	  *@param @param version 
	  *@修改历史：
	  *		[序号](YYP	2014-7-18)<修改说明>
	 */
	public void updateVersion(SysVersion version) {
		try{
			 this.versionDao.updateVersion(version);
		}catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
