package com.gxb.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.exp.ServiceException;
import com.gxb.manager.dao.SysUserDao;
import com.gxb.model.SysUser;
import com.util.Page;

/**
 * 用户信息服务类
 * @author LHC
 *
 */
@Service
public class SysUserService {
	
	@Resource
	private SysUserDao userDao;
	
	/**
	 * 分页查询用户
	 * @param user
	 * @param page
	 * @param limit
	 * @param tp
	 * @return
	 */
	public Page queryUser (SysUser user,Integer page,Integer limit,String tp){
		try {
			return this.userDao.queryUser(user, page, limit,tp);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public SysUser queryUserById(Integer userId){
		try {
			return this.userDao.queryUserById(userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据企业账号判断是否存在
	 * @param merchantNo
	 * @return
	 */
	public int queryUserCount(String userNo){
		try {
			return this.userDao.queryUserCount(userNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 添加企业信息
	 * @param merchant
	 * @param merchantDetailLs
	 * @param merchantLogos
	 */
	public void addUser(SysUser user,List<Object> merchantDetailLs,List<Object> merchantLogos) {
		try {
			this.userDao.addUser(user);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据企业id修改企业信息
	 * @param merchant
	 * @param merchantDetailLs
	 * @param merchantLogos
	 */
	public void updateUser(SysUser user,List<Object> merchantDetailLs,List<Object> merchantLogos) {
		try {
			this.userDao.updateUser(user);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据用户id删除用户
	 * @param id
	 */
	public void deleteUser(Integer id){
		try {
			 this.userDao.deleteUser(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据企业账号修改企业密码
	 * @param userNo
	 * @param userPwd
	 * @return
	 */
	public int updatePwdByUno(String userNo,String userPwd){
		try {
			return this.userDao.updatePwdByUno(userNo, userPwd);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
