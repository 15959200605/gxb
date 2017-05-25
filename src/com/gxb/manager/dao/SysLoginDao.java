package com.gxb.manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysLoginInfo;
import com.util.StrUtil;


/**
 * 说明：系统登录dao
 * 
 * @创建：作者:yxy 创建时间：2012-1-12
 * @修改历史： [序号](yxy 2012-1-12)<修改说明>
 */
@Repository
public class SysLoginDao {
	@Resource(name = "daoTemplate")
	private JdbcDaoTemplate daoTemplate;

	/**
	 * 摘要：
	 * @说明：根据用户编号查询用户
	 * @创建：作者:yxy 创建时间：2012-1-12
	 * @param userNo
	 * @return
	 * @修改历史： [序号](yxy 2012-1-12)<修改说明>
	 */
	public SysLoginInfo queryLoginInfo(String userNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id_key,user_nm as usr_nm,user_no as usr_no,user_pwd as usr_pwd,is_sj");
		sql.append(" from sys_user where user_no=?");
		try {
			return daoTemplate.queryForObj(sql.toString(), SysLoginInfo.class,userNo);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * @说明：根据用户id查询该用户的角色
	 * @创建：作者:yxy 创建时间：2012-3-24
	 * @param userId
	 * @return
	 * @修改历史： [序号](yxy 2012-3-24)<修改说明>
	 */
	public List<Integer> queryRoleIdByUsr(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select role_id from sys_role_user where user_id=").append(userId);
		try {
			List<Map<String, Object>> temps = daoTemplate.queryForList(sql.toString().toUpperCase());
			if (null != temps && temps.size() > 0) {
				List<Integer> ls = new ArrayList<Integer>();
				for (Map<String, Object> map : temps) {
					Integer roleId = StrUtil.convertInt(map.get("role_id"));
					ls.add(roleId);
				}
				return ls;
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
	/**
	 * 
	 * 摘要：
	 * @说明：修改密码
	 * @创建：作者:yxy 创建时间：2012-3-24
	 * @param userNo
	 * @param userPwd
	 * @修改历史： [序号](yxy 2012-3-24)<修改说明>
	 */
	public int updatePwd(Integer idKey, String userPwd) {
		String sql = " update sys_user set user_pwd=? where id_key=? ";
		try {
			return daoTemplate.update(sql, new Object[] { userPwd, idKey });
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}

	/**
	 * 
	 * 摘要：
	 * @说明：查询用户密码
	 * @创建：作者:yxy 创建时间：2012-5-9
	 * @param idKey
	 * @return
	 * @修改历史： [序号](yxy 2012-5-9)<修改说明>
	 */
	public String queryPwdById(Integer idKey) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select user_pwd from sys_user where id_key=").append(idKey);
		try {
			Map<String, Object> map = daoTemplate.queryForMap(sql.toString());
			if (null != map && map.size() > 0) {
				return map.get("user_pwd").toString();
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new DaoException();
		}
	}
}
