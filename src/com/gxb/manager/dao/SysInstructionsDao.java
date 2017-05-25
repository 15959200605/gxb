package com.gxb.manager.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysInstructions;
import com.util.Page;

@Repository
public class SysInstructionsDao {
	@Resource(name="daoTemplate")
	private JdbcDaoTemplate daoTemplate;
	/**
	 *说明：分页查询关于我们
	 *@创建：作者:llp		创建时间：2015-8-22
	 *@修改历史：
	 *		[序号](llp	2015-8-22)<修改说明>
	 */
	public Page queryInstructions(SysInstructions instructions,Integer page,Integer limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from sys_instructions where 1=1 ");
		sql.append(" order by id desc ");
		try {
			return this.daoTemplate.queryForPageByMySql(sql.toString(), page, limit, SysInstructions.class);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	/**
	 *说明：获取关于我们
	 *@创建：作者:llp		创建时间：2015-8-22
	 *@修改历史：
	 *		[序号](llp	2015-8-22)<修改说明>
	 */
	public SysInstructions queryInstructionsById(Integer Id){
		try{
			String sql = "select * from sys_instructions where id=? ";
			return this.daoTemplate.queryForObj(sql, SysInstructions.class,Id);
		} catch (Exception e) {
			throw new DaoException(e);
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
			Map<String,Object> whereParam = new HashMap<String, Object>();
			whereParam.put("id", instructions.getId());
			return this.daoTemplate.updateByObject("sys_instructions", instructions, whereParam, "id");
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
}
