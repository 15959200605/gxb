package com.gxb.manager.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysUser;
import com.util.Page;
import com.util.StrUtil;

/**
 * 用户信息dao
 * @author LHC
 *
 */
@Repository
public class SysUserDao {
	@Resource(name="daoTemplate")
	private JdbcDaoTemplate daoTemplate;
	
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param page
	 * @param limit
	 * @param tp
	 * @return
	 */
	public Page queryUser (SysUser user,Integer page,Integer limit,String tp){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from sys_user where 1=1");
		if(null!=user){
			if(!StrUtil.isNull(user.getUserNm())){
				sql.append(" and (user_nm like '%"+user.getUserNm()+"%' or user_no like '%"+user.getUserNm()+"%')");
			}
			if(!StrUtil.isNull(user.getIsSj())){
				sql.append(" and is_sj="+user.getIsSj()+"");
			}
		}
		sql.append(" order by id_key desc ");
		try {
			return this.daoTemplate.queryForPageByMySql(sql.toString(), page, limit, SysUser.class);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public SysUser queryUserById(Integer userId){
		try{
			String sql = "select * from sys_user where id_key=? ";
			return this.daoTemplate.queryForObj(sql, SysUser.class,userId);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 */
	public int addUser(SysUser user) {
		try {
			return this.daoTemplate.addByObject("sys_user", user);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
	
	/**
	 * 根据用户id修改用户信息
	 * @param user
	 * @return
	 */
	public int updateUser(SysUser user) {
		String sql = "update sys_user set tel = ?,user_nm = ?,user_no = ?,user_pwd = ? where id_key = ?";
		try {			
			return this.daoTemplate.update(sql,user.getTel(),user.getUserNm(),user.getUserNo(),user.getUserPwd(),user.getIdKey());
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
	
	/**
	 *@说明：根据企业账号判断是否存在
	 *@创建：作者:llp		创建时间：2015-7-30
	 *@修改历史：
	 *		[序号](llp	2015-7-30)<修改说明>
	 */
	public int queryUserCount(String userNo){
		try {
			String sql=" select count(1) from sys_user where user_no=?";
			return this.daoTemplate.queryForInt(sql, userNo);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	 * 根据用户id删除用户
	 * @param id
	 */
	public void deleteUser(Integer id){
		String sql=" delete from sys_user where id_key=? ";
		try {
			daoTemplate.update(sql, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	 *@说明：根据企业账号修改企业密码
	 *@创建：作者:llp		创建时间：2015-7-30
	 *@修改历史：
	 *		[序号](llp	2015-7-30)<修改说明>
	 */
	public int updatePwdByUno(String userNo,String userPwd){
		String sql = "update sys_user set user_pwd=? where user_no=? ";
		try {
			return this.daoTemplate.update(sql,userPwd,userNo);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
}
