package com.gxb.manager.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysRole;
import com.util.Page;
import com.util.StrUtil;

/**
 * 说明：角色数据库操作类
 * @创建：作者:yxy 创建时间：2012-3-19
 * @修改历史： [序号](yxy 2012-3-19)<修改说明>
 */
@Repository
public class SysRoleDao{
	@Resource(name="daoTemplate")
	private JdbcDaoTemplate daoTemplate;
	/**
	 * 摘要：
	 * 
	 * @说明：添加角色
	 * @创建：作者:yxy 创建时间：2012-3-19
	 * @param role
	 * @修改历史： [序号](yxy 2012-3-19)<修改说明>
	 */
	public int addRole(SysRole role) {
		try {
			return this.daoTemplate.addByObject("sys_role", role);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * 
	 * @说明：根据id获取角色
	 * @创建：作者:yxy 创建时间：2012-3-19
	 * @para0m idKey
	 * @return
	 * @修改历史： [序号](yxy 2012-3-19)<修改说明>
	 */
	public SysRole queryRoleById(Integer idKey) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from sys_role where id_key=").append(idKey);
		try {
			return daoTemplate.queryForObj(sql.toString(), SysRole.class);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：修改角色
	 * @创建：作者:yxy 创建时间：2012-3-19
	 * @param role
	 * @修改历史： [序号](yxy 2012-3-19)<修改说明>
	 */
	public int updateRole(SysRole role) {
		try {
			Map<String,Object> whereParam = new HashMap<String, Object>();
			whereParam.put("id_key", role.getIdKey());
			return this.daoTemplate.updateByObject("sys_role", role, whereParam, "id_key");
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：分页查询角色
	 * @创建：作者:yxy 创建时间：2012-3-19
	 * @param role
	 * @param page
	 * @return
	 * @修改历史： [序号](yxy 2012-3-19)<修改说明>
	 */
	public Page queryRole(SysRole role, int page,int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from sys_role where id_key!=2 ");
		if (null != role) {
			String roleNm = role.getRoleNm();
			if (!StrUtil.isNull(roleNm)) {
				sql.append(" and role_nm like '%").append(roleNm).append("%' ");
			}
		}
		sql.append(" order by id_key desc ");
		try {
			return daoTemplate.queryForPageByMySql(sql.toString(), page,limit,SysRole.class);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：批量删除角色
	 * @创建：作者:yxy 创建时间：2012-3-19
	 * @param roleIds
	 * @修改历史： [序号](yxy 2012-3-19)<修改说明>
	 */
	public int[] deleteRoles(final Integer... roleIds) {
		String sql = " delete from sys_role where id_key=? ";
		try {
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return roleIds.length;
				}

				public void setValues(PreparedStatement pre, int num)
						throws SQLException {
					pre.setInt(1, roleIds[num]);
				}
			};
			return daoTemplate.batchUpdate(sql.toUpperCase(), setter);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：查询用户用于分配用户
	 * @创建：作者:yxy 创建时间：2012-3-22
	 * @param roleId
	 * @return
	 * @修改历史： [序号](yxy 2012-3-22)<修改说明>
	 */
	public List<Map<String,Object>> queryUsrForRole(Integer roleId) {
		StringBuffer sql = new StringBuffer("select mem.id_key,mem.user_nm,(select count(1) from sys_role_user r");
		sql.append(" where mem.id_key=r.user_id and r.role_id=?");
		sql.append(" ) as role_use from sys_user mem");
		try {
			return daoTemplate.queryForList(sql.toString().toUpperCase(),roleId);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * 
	 * @说明：批量删除角色分配用户表
	 * @创建：作者:yxy 创建时间：2012-3-23
	 * @param roleId
	 * @修改历史： [序号](yxy 2012-3-23)<修改说明>
	 */
	public int[] deleteRoleUsrs(final Integer... roleIds) {
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from sys_role_user where role_id=? ");
		try {
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return roleIds.length;
				}

				public void setValues(PreparedStatement pre, int num)
						throws SQLException {
					pre.setInt(1, roleIds[num]);
				}
			};
			return daoTemplate.batchUpdate(sql.toString().toUpperCase(), setter);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：批量删除角色分配菜单
	 * @创建：作者:yxy 创建时间：2012-3-23
	 * @param roleId
	 * @修改历史： [序号](yxy 2012-3-23)<修改说明>
	 */
	public void deleteRoleMenus(final Integer... roleIds) {
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from sys_role_menu where role_id=? ");
		try {
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return roleIds.length;
				}

				public void setValues(PreparedStatement pre, int num)
						throws SQLException {
					pre.setInt(1, roleIds[num]);
				}
			};
			daoTemplate.batchUpdate(sql.toString().toUpperCase(), setter);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：批量添加角色分配人员表
	 * @创建：作者:yxy 创建时间：2012-3-22
	 * @param usrIds
	 * @param roleId
	 * @修改历史： [序号](yxy 2012-3-22)<修改说明>
	 */
	public int[] saveRoleUsr(final Integer[] usrIds, final Integer roleId) {
		String sql = " insert into sys_role_user(role_id,user_id) values(?,?) ";
		try {
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return usrIds.length;
				}

				public void setValues(PreparedStatement pre, int num)
						throws SQLException {
					pre.setInt(1, roleId);
					pre.setInt(2, usrIds[num]);
				}
			};
			return daoTemplate.batchUpdate(sql.toUpperCase(), setter);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DaoException(ex);
		}
	}
	/**
	  *摘要：
	  *@说明：添加权限
	  *@创建：作者:yxy		创建时间：2013-9-16
	  *@param @param usrId
	  *@param @param roleId
	  *@param @return 
	  *@修改历史：
	  *		[序号](yxy	2013-9-16)<修改说明>
	 */
	public int addRoleUsr(long usrId,int roleId){
		String sql = " insert into sys_role_user(role_id,user_id) values(?,?) ";
		try {
			return this.daoTemplate.update(sql,roleId,usrId);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	/**
	 * 
	 * 摘要：
	 * @说明：批量添加角色分配人员表
	 * @创建：作者:yxy 创建时间：2012-3-22
	 * @param usrIds
	 * @param roleId
	 * @修改历史： [序号](yxy 2012-3-22)<修改说明>
	 */
	public int[] saveRoleMenu(final Integer[] menuIds, final Integer roleId) {
		String sql = " insert into sys_role_menu(role_id,menu_id) values(?,?) ";
		try {
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return menuIds.length;
				}

				public void setValues(PreparedStatement pre, int num)
						throws SQLException {
					pre.setInt(1, roleId);
					pre.setInt(2, menuIds[num]);
				}
			};
			return daoTemplate.batchUpdate(sql.toUpperCase(), setter);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * 
	 * @说明：查询所有角色
	 * @创建：作者:yxy 创建时间：2012-3-24
	 * @return
	 * @修改历史： [序号](yxy 2012-3-24)<修改说明>
	 */
	public List<SysRole> queryRoleAll() {
		String sql = " select id_key,role_nm from sys_role order by id_key ";
		try {
			return daoTemplate.queryForLists(sql, SysRole.class);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
}
