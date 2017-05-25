package com.gxb.manager.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.control.GeneralControl;
import com.gxb.manager.service.SysRoleService;
import com.gxb.model.SysLoginInfo;
import com.gxb.model.SysMenu;
import com.gxb.model.SysRole;
import com.util.DateTimeUtil;
import com.util.Page;

/**
 *说明：角色Control
 *@创建：作者:yxy		创建时间：2013-4-7
 *@修改历史：
 *		[序号](yxy	2013-4-7)<修改说明>
 */
@Controller
@RequestMapping("/manager")
public class SysRoleControl extends GeneralControl{
	@Resource
	private SysRoleService roleService;
	/**
	 * 
	 *摘要：
	 *@说明：角色主页
	 *@创建：作者:yxy		创建时间：2013-4-11
	 *@return
	 *@修改历史：
	 *
	 */
	@RequestMapping("/queryrole")
	public String queryrole(){
		return "system/role/role";
	}
	/**
	 * 
	 *摘要：
	 *@说明：角色分页
	 *@创建：作者:yxy		创建时间：2013-4-11
	 *@修改历史：
	 *
	 */
	@RequestMapping("/rolepages")
	public void rolepages(HttpServletResponse response,HttpServletRequest request,SysRole role,Integer page,Integer rows){
		try {
			Page p = this.roleService.queryRole(role, page, rows);
			JSONObject json = new JSONObject();
			json.put("total", p.getTotal());
			json.put("rows", p.getRows());
			this.sendJsonResponse(response, json.toString());
			p=null;
		} catch (Exception e) {
			log.error("角色分页出错："+e);
		}
	}
	/**
	 *摘要：
	 *@说明：操作角色
	 *@创建：作者:yxy		创建时间：2013-4-12 
	 *@修改历史：
	 *		[序号](yxy	2013-4-12)<修改说明>
	 */
	@RequestMapping("/operrole")
	public void operrole(SysRole role,HttpServletResponse response,HttpServletRequest request){
		if(null!=role){
			try {
				if(null==role.getIdKey()){
					String createDt = DateTimeUtil.getDateToStr(new Date(), "yyyy-MM-dd");
					SysLoginInfo info=this.getLoginInfo(request);
					role.setCreateDt(createDt);
					role.setCreateId(info.getIdKey());
					this.roleService.addRole(role);
					this.sendHtmlResponse(response, "1");
				}else{
					this.roleService.updateRole(role);
					this.sendHtmlResponse(response, "2");
				}
			} catch (Exception e) {
				log.error("操作角色出错："+e);
				this.sendHtmlResponse(response, "4");
			}
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：获取角色对象
	 *@创建：作者:yxy		创建时间：2013-4-12
	 *@param id
	 *@param response 
	 *@修改历史：
	 *		[序号](yxy	2013-4-12)<修改说明>
	 */
	@RequestMapping("/getrole")
	public void getrole(Integer id,HttpServletResponse response){
		try {
			SysRole role = this.roleService.queryRoleById(id);
			JSONObject json = new JSONObject(role);
			this.sendJsonResponse(response, json.toString());
		} catch (Exception e) {
			log.error("获取角色对象出错："+e);
		}
	}
	/**
	 *摘要：
	 *@说明：删除角色
	 *@创建：作者:yxy		创建时间：2013-4-12
	 *@param id
	 *@param response 
	 *@修改历史：
	 *		[序号](yxy	2013-4-12)<修改说明>
	 */
	@RequestMapping("/delrole")
	public void delrole(Integer id,HttpServletResponse response){
		try{
			this.roleService.deleteRoles(id);
			this.sendHtmlResponse(response, "3");
		} catch (Exception e) {
			log.error("删除角色对象出错："+e);
			this.sendHtmlResponse(response, "6");
		}
	}
	/**
	 *摘要：
	 *@说明：查询权限树
	 *@创建：作者:yxy		创建时间：2013-4-13
	 *@param id
	 *@param response 
	 *@修改历史：
	 *		[序号](yxy	2013-4-13)<修改说明>
	 */
	@RequestMapping("/menutree")
	public void menutree(Integer id,HttpServletResponse response){
		try{
			List<SysMenu> menus = this.roleService.queryMenuByPidForRole(id);
			JSONArray json = new JSONArray(menus);
			this.sendJsonResponse(response, json.toString());
			menus=null;
		}catch(Exception ex){
			log.error("查询权限树出错:"+ex);
		}
	}
	/**
	 * 
	 *摘要：添加角色与用户权限
	 *@说明：
	 *@创建：作者:yxy 	创建时间：2011-6-13
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2011-6-13)<修改说明>
	 */
	@RequestMapping("/saverolemenu")
	public void saverolemenu(Integer roleid,Integer[] menuid,HttpServletResponse response){
		try{
			this.roleService.updateRoleMenu(menuid, roleid);
			this.sendHtmlResponse(response, "2");
		}catch(Exception ex){
			log.error("添加角色与菜单对应表出错:"+ex);
			this.sendHtmlResponse(response, "4");
		}
	}
	/**
	 *摘要：
	 *@说明：查询用户树
	 *@创建：作者:yxy		创建时间：2013-4-13
	 *@param id
	 *@param response 
	 *@修改历史：
	 *		[序号](yxy	2013-4-13)<修改说明>
	 */
	@RequestMapping("/usrtree")
	public void usrtree(Integer id,HttpServletResponse response){
		try {
			List<Map<String,Object>> usrs = this.roleService.queryUsrForRole(id);
			StringBuilder str = new StringBuilder();
			if(null!=usrs&&usrs.size()>0){
				str.append("[");
				String sp="";
				for (Map<String, Object> map : usrs) {
					str.append(sp).append("{\"usrid\":").append(map.get("id_key"));
					str.append(",\"usrnm\":\"").append(map.get("user_nm"));
					str.append("\",\"isuse\":").append(map.get("role_use")).append("}");
					sp=",";
				}
				str.append("]");
				this.sendJsonResponse(response, str.toString());
			}
		} catch (Exception e) {
			log.error("查询用户树出错:"+e);
		}
	}
	/**
	 * 
	 *摘要：添加角色与用户对应表
	 *@说明：
	 *@创建：作者:yxy 	创建时间：2011-6-13
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2011-6-13)<修改说明>
	 */
	@RequestMapping("/saveroleusr")
	public void saveroleusr(Integer roleid,Integer[] usrid,HttpServletResponse response){
		try{
			this.roleService.updateRoleUsr(usrid, roleid);
			this.sendHtmlResponse(response, "2");
		}catch(Exception ex){
			log.error("添加角色与用户对应表出错:"+ex);
			this.sendHtmlResponse(response, "5");
		}
	}
}

