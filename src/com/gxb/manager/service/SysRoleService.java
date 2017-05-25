package com.gxb.manager.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.exp.ServiceException;
import com.gxb.manager.dao.RoleMenus;
import com.gxb.manager.dao.SysMenuDao;
import com.gxb.manager.dao.SysRoleDao;
import com.gxb.model.SysMenu;
import com.gxb.model.SysRole;
import com.util.Page;

@Service
public class SysRoleService {
	@Resource
	private SysRoleDao roleDao;        //角色数据库操作类
	@Resource
	private SysMenuDao menuDao;        //菜单数据库操作类
	
	/**
	 * 
	 *摘要：
	 *@说明：添加角色
	 *@创建：作者:yxy		创建时间：2012-3-19
	 *@param role 
	 *@修改历史：
	 *		[序号](yxy	2012-3-19)<修改说明>
	 */
	public int addRole(SysRole role){
		try{
			int i = this.roleDao.addRole(role);
			if(i!=1){
				throw new ServiceException("添加出错");
			}
			return i;
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：修改角色
	 *@创建：作者:yxy		创建时间：2012-3-19
	 *@param role 
	 *@修改历史：
	 *		[序号](yxy	2012-3-19)<修改说明>
	 */
	public int updateRole(SysRole role){
		try{
			int i = this.roleDao.updateRole(role);
			if(i!=1){
				throw new ServiceException("添加出错");
			}
			return i;
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：分页查询角色
	 *@创建：作者:yxy		创建时间：2012-3-19
	 *@param role
	 *@param page
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-3-19)<修改说明>
	 */
	public Page queryRole(SysRole role,int page,int limit){
		try{
			return this.roleDao.queryRole(role, page,limit);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：批量删除角色
	 *@创建：作者:yxy		创建时间：2012-3-19
	 *@param roleIds 
	 *@修改历史：
	 *		[序号](yxy	2012-3-19)<修改说明>
	 */
	public void deleteRoles(Integer... roleIds){
		try{
			//删除角色对应用户
			this.roleDao.deleteRoleUsrs(roleIds);
			//删除角色对应菜单
			this.roleDao.deleteRoleMenus(roleIds);
			//删除角色
			this.roleDao.deleteRoles(roleIds);
			installRoleMenus();
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据id获取角色
	 *@创建：作者:yxy		创建时间：2012-3-19
	 *@param idKey
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-3-19)<修改说明>
	 */
	public SysRole queryRoleById(Integer idKey){
		try{
			return this.roleDao.queryRoleById(idKey);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：查询用户用于分配用户
	 *@创建：作者:yxy		创建时间：2012-3-22
	 *@param roleId
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-3-22)<修改说明>
	 */
	public List<Map<String,Object>> queryUsrForRole(Integer roleId){
		try{
			return this.roleDao.queryUsrForRole(roleId);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：批量添加角色分配人员表
	 *@创建：作者:yxy		创建时间：2012-3-22
	 *@param usrIds
	 *@param roleId 
	 *@修改历史：
	 *		[序号](yxy	2012-3-22)<修改说明>
	 */
	public void updateRoleUsr(Integer[] usrIds,Integer roleId){
		try{
			//删除角色分配用户表
			this.roleDao.deleteRoleUsrs(roleId);
			//批量添加角色分配人员表
			if(null!=usrIds&&usrIds.length>0){
				this.roleDao.saveRoleUsr(usrIds, roleId);
			}
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：批量添加角色分配权限
	 *@创建：作者:yxy		创建时间：2012-3-22
	 *@param usrIds
	 *@param roleId 
	 *@修改历史：
	 *		[序号](yxy	2012-3-22)<修改说明>
	 */
	public void updateRoleMenu(Integer[] menuIds,Integer roleId){
		try{
			//删除角色分配权限
			this.roleDao.deleteRoleMenus(roleId);
			//批量添加角色分配权限
			if(null!=menuIds&&menuIds.length>0){
				this.roleDao.saveRoleMenu(menuIds, roleId);
			}
			installRoleMenus();
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据父id查询菜单
	 *@创建：作者:yxy		创建时间：2012-3-23
	 *@param pId
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-3-23)<修改说明>
	 */
	public List<SysMenu> queryMenuByPidForRole(Integer roleId){
		try{
			return this.menuDao.queryMenuByPidForRole(roleId);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：设置角色权限
	 *@创建：作者:yxy 	创建时间：2011-6-29 
	 *@修改历史：
	 *		[序号](yxy	2011-6-29)<修改说明>
	 */
	private void installRoleMenus(){
		List<SysRole> roles = this.roleDao.queryRoleAll();
		RoleMenus.roleMenus.clear();
		for (SysRole role : roles) {
			List<Map<String,Object>> menus = this.menuDao.querySysMenuByRoleId(role.getIdKey());
			RoleMenus.roleMenus.put(role.getIdKey(), menus);
		}
	}
}
