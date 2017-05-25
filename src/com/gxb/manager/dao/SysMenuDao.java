package com.gxb.manager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysMenu;
import com.util.TreeMode;

/**
 * @说明：功能菜单数据库操作类
 * @创建：作者:yxy		创建时间：2011-4-13
 * @修改历史：
 *		[序号](yxy	2011-4-13)<修改说明>
 */
@Repository
public class SysMenuDao {
	@Resource(name="daoTemplate")
	private JdbcDaoTemplate daoTemplate;
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
		StringBuffer sql = new StringBuffer();
		sql.append(" select id_key as tree_id,menu_nm as tree_nm,menu_tp as tree_leaf,menu_url as tree_url,p_id from sys_menu where 1=1 ");
		if(id!=null){
			sql.append(" and p_id=").append(id);
		}
		if(roleId!=null){
			//权限判断
			sql.append(" and is_use=1 ");
		}
		if(menuTp!=null){
			sql.append(" and menu_tp='").append(menuTp).append("' ");
		}
		sql.append(" order by menu_seq asc ");
		try{
			return daoTemplate.queryForLists(sql.toString(), TreeMode.class);
		}catch(Exception ex){
			throw new DaoException(ex);
		}
	}
	/**
	 * 
	 * @说明：根据角色id查询可用的菜单
	 * @创建：作者:yxy 创建时间：2011-4-13
	 * @param id		菜单id
	 * @param roleId 	角色id
	 * @param menuType	菜单类型
	 * @return 功能菜单集合
	 */
	public List<Map<String,Object>> querySysMenuByRoleId(List<Object[]> roles){
		StringBuffer sql = new StringBuffer();
		sql.append(" select m.id_key,m.menu_nm,m.menu_leaf,m.menu_url,m.p_id,m.menu_cd from sys_menu m,sys_role_menu p where p.role_id in (");
		if(null!=roles){
			int count = 0;
			for (Object[] objects : roles) {
				count++;
				if(count==roles.size()){
					sql.append(objects[0]);
				}else{
					sql.append(objects[0]+",");
				}
			}
		}else{
			return null;
		}
		sql.append(") and m.id_key=p.menu_id and is_use=1 order by m.menu_seq asc ");
		try{
			return daoTemplate.queryForList(sql.toString().toUpperCase());
		}catch(Exception ex){
			throw new DaoException(ex);
		}
	}
	/**
	 * 
	 * @说明：根据角色id查询可用的菜单
	 * @创建：作者:yxy 创建时间：2011-4-13
	 * @param id		菜单id
	 * @param roleId 	角色id
	 * @param menuType	菜单类型
	 * @return 功能菜单集合
	 */
	public List<Map<String,Object>> querySysMenuByRoleId(Integer roleId){
		StringBuffer sql = new StringBuffer();
		sql.append(" select m.id_key,m.menu_nm,m.menu_leaf,m.menu_url,m.p_id,m.menu_cd,m.menu_cls,m.menu_tp from sys_menu m,sys_role_menu p where m.id_key=p.menu_id and is_use=1 ");
		if(null!=roleId&&!roleId.equals("0")){
			sql.append(" and p.role_id=").append(roleId);
		}else{
			return null;
		}
		sql.append(" order by m.menu_seq asc,m.id_key asc ");
		try{
			return daoTemplate.queryForList(sql.toString().toUpperCase());
		}catch(Exception ex){
			throw new DaoException(ex);
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
		StringBuffer sql=new StringBuffer();
		sql.append(" select m.id_key,m.menu_nm,m.menu_tp,m.p_id,(select count(1) from sys_role_menu r where r.menu_id=m.id_key and r.role_id=")
		   .append(roleId).append(") as menu_seq from sys_menu m where m.is_use=1 order by m.menu_seq asc ");
		try{
			return daoTemplate.queryForLists(sql.toString(), SysMenu.class);
		}catch(Exception ex){
			throw new DaoException(ex);
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
			return daoTemplate.addByObject("sys_menu", menu);
		} catch (Exception e) {
			throw new DaoException(e);
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
		StringBuffer sql = new StringBuffer();
		if(null!=idKey){
			sql.append(" select * from sys_menu where id_key=").append(idKey);
		}else{
			sql.append(" select * from sys_menu where p_id=0 order by menu_seq asc limit 0,1 ");
		}
		try{
			return daoTemplate.queryForObj(sql.toString(), SysMenu.class);
		}catch(Exception ex){
			throw new DaoException(ex);
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
		StringBuffer sql = new StringBuffer();
		try{
			SysMenu menu = null;
			if(pid!=0){
				//查找父菜单下的第条记录
				sql.append(" select * from sys_menu where p_id=").append(pid).append(" order by menu_seq asc limit 0,1 ");
				menu = daoTemplate.queryForObj(sql.toString(), SysMenu.class);
				if(null==menu){
					sql.setLength(0);
					//父菜单下没有子菜单显示父菜单
					sql.append(" select * from sys_menu where id_key=").append(pid);
					menu = daoTemplate.queryForObj(sql.toString(), SysMenu.class);
				}
			}else{
				//查询第一级别的菜单
				sql.append(" select * from sys_menu where p_id=0 order by menu_seq asc limit 0,1 ");
				menu = daoTemplate.queryForObj(sql.toString(), SysMenu.class);
			}
			return menu;
		}catch(Exception ex){
			throw new DaoException(ex);
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
			Map<String, Object> whereParam = new HashMap<String, Object>();
			whereParam.put("id_key", menu.getIdKey());
			return this.daoTemplate.updateByObject("sys_menu", menu, whereParam, null);
		} catch (Exception e) {
			throw new DaoException(e);
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
		StringBuffer sql = new StringBuffer(" delete from sys_menu where id_key=").append(idKey);
		try {
			return daoTemplate.update(sql.toString());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	/**
	  *摘要：
	  *@说明：删除角色菜单
	  *@创建：作者:yxy		创建时间：2014-4-10
	  *@param @param idKey
	  *@param @return 
	  *@修改历史：
	  *		[序号](yxy	2014-4-10)<修改说明>
	 */
	public int deleteRoleMenu(int idKey){
		String sql = " delete from sys_role_menu where menu_id=? ";
		try {
			return this.daoTemplate.update(sql,idKey);
		} catch (Exception e) {
			throw new DaoException(e);
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
		StringBuffer sql = new StringBuffer(" select count(1) from sys_menu where p_id=").append(pid);
		try{
			return daoTemplate.queryForInt(sql.toString());
		}catch(Exception ex){
			throw new DaoException(ex);
		}
	}
	/**
	 * 说明：获取自增id
	 * 作者：yxy       日期：2014-3-18
	 * @return
	 */
	public int queryAutoId(){
		try {
			return daoTemplate.getAutoIdForIntByMySql();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
}

