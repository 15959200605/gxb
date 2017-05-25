package com.gxb.manager.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.exp.ServiceException;
import com.gxb.manager.dao.SysInstructionsDao;
import com.gxb.model.SysInstructions;
import com.util.Page;

@Service
public class SysInstructionsService {
	@Resource
	private SysInstructionsDao instructionsDao;
	/**
	 *说明：分页查询关于我们
	 *@创建：作者:llp		创建时间：2015-8-22
	 *@修改历史：
	 *		[序号](llp	2015-8-22)<修改说明>
	 */
	public Page queryInstructions(SysInstructions instructions,Integer page,Integer limit){
		try {
			return this.instructionsDao.queryInstructions(instructions, page, limit);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 *说明：获取关于我们
	 *@创建：作者:llp		创建时间：2015-8-22
	 *@修改历史：
	 *		[序号](llp	2015-8-22)<修改说明>
	 */
	public SysInstructions queryInstructionsById(Integer Id){
		try {
			return this.instructionsDao.queryInstructionsById(Id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 *@说明：修改关于我们
	 *@创建：作者:llp		创建时间：2015-8-22
	 *@修改历史：
	 *		[序号](llp	2015-8-22)<修改说明>
	 */
	public int updateInstructions(SysInstructions instructions) {
		try {
			return this.instructionsDao.updateInstructions(instructions);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
