package com.gxb.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.exp.ServiceException;
import com.gxb.manager.dao.SysLoginDao;
import com.gxb.model.SysLoginInfo;

@Service
public class SysLoginService {
	@Resource
	private SysLoginDao loginDao;
	/**
	 * 摘要：
	 * @说明：根据用户编号查询用户
	 * @创建：作者:yxy 创建时间：2012-1-12
	 * @param userNo
	 * @return
	 * @修改历史： [序号](yxy 2012-1-12)<修改说明>
	 */
	public SysLoginInfo queryLoginInfo(String userNo) {
		try {
			return this.loginDao.queryLoginInfo(userNo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * @说明：根据用户id查询该用户的角色
	 * @创建：作者:yxy 创建时间：2012-3-24
	 * @param userId
	 * @return
	 * @修改历史： [序号](yxy 2012-3-24)<修改说明>
	 */
	public List<Integer> queryRoleIdByUsr(int userId) {
		try {
			return this.loginDao.queryRoleIdByUsr(userId);
		} catch (Exception ex) {
			throw new ServiceException(ex);
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
		try {
			return this.loginDao.updatePwd(idKey, userPwd);
		} catch (Exception ex) {
			throw new ServiceException(ex);
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
		try {
			return this.loginDao.queryPwdById(idKey);
		} catch (Exception ex) {
			throw new ServiceException();
		}
	}
}
