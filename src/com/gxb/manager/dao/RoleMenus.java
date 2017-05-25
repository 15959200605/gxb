package com.gxb.manager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gxb.model.SysRole;








/**
 *说明：
 *@创建：作者:yxy		创建时间：2011-6-28
 *@修改历史：
 *		[序号](yxy	2011-6-28)<修改说明>
 */
public class RoleMenus implements ServletContextListener{
	public static Map<Integer,List<Map<String,Object>>> roleMenus = new HashMap<Integer, List<Map<String,Object>>>();//角色和菜单map
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		SysRoleDao roleDao = (SysRoleDao)ac.getBean("sysRoleDao");
		SysMenuDao menusDao = (SysMenuDao)ac.getBean("sysMenuDao");
		List<SysRole> roles = roleDao.queryRoleAll();
		for (SysRole role : roles) {
			List<Map<String,Object>> menus = menusDao.querySysMenuByRoleId(role.getIdKey());
			roleMenus.put(role.getIdKey(), menus);
		}
	}
}

