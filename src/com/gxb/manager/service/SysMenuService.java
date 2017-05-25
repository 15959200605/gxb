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
import com.util.TreeMode;

@Service
public class SysMenuService {
	@Resource
	private SysMenuDao menuDao;
	@Resource
	private SysRoleDao roleDao;
	/**
	 * 
	 * @说明：查询功能菜单
	 * @创建：作者:yxy 创建时间：2011-4-13
	 * @param id		菜单id
	 * @param roleId 	角色id
	 * @param menuType	菜单类型
	 * @return 功能菜单集合
	 */
	public List<TreeMode> querySysMenu(Integer id,Integer roleId,String menuTp){
		try {
			return this.menuDao.querySysMenu(id, roleId, menuTp);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：添加菜单
	 *@创建：作者:yxy		创建时间：2012-3-21
	 *@param menu 
	 *@修改历史：
	 *		[序号](yxy	2012-3-21)<修改说明>
	 */
	public int addMenu(SysMenu menu){
		try {
			int count = menuDao.addMenu(menu);
			if(count==1){
				menu.setIdKey(this.menuDao.queryAutoId());
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据id获取菜单
	 *@创建：作者:yxy		创建时间：2012-3-21
	 *@param idKey
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-3-21)<修改说明>
	 */
	public SysMenu queryMenuById(Integer idKey){
		try{
			return menuDao.queryMenuById(idKey);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据id获取菜单
	 *@创建：作者:yxy		创建时间：2012-3-21
	 *@param idKey
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-3-21)<修改说明>
	 */
	public SysMenu queryMenuForDel(int pid){
		try{
			return menuDao.queryMenuForDel(pid);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：修改菜单
	 *@创建：作者:yxy		创建时间：2012-3-21
	 *@param menu 
	 *@修改历史：
	 *		[序号](yxy	2012-3-21)<修改说明>
	 */
	public int updateMenu(SysMenu menu){
		try {
			int count = this.menuDao.updateMenu(menu);
			if(count==1){
				installRoleMenus();
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException(e);
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
	/**
	 * 
	 *摘要：
	 *@说明：删除菜单
	 *@创建：作者:yxy		创建时间：2012-3-21
	 *@param idKey 
	 *@修改历史：
	 *		[序号](yxy	2012-3-21)<修改说明>
	 */
	public int deleteMenu(int idKey){
		try {
			//删除角色菜单
			int roleCount = menuDao.deleteRoleMenu(idKey);
			int count = menuDao.deleteMenu(idKey);
			if(count==1 || roleCount>0){
				this.installRoleMenus();
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据父id查询子菜单个数
	 *@创建：作者:yxy 	创建时间：2011-6-17
	 *@param pid
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2011-6-17)<修改说明>
	 */
	public Integer getMenuSizeByPid(int pid){
		try{
			return menuDao.getMenuSizeByPid(pid);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
	}
}
