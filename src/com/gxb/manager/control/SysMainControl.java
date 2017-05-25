package com.gxb.manager.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.control.GeneralControl;
import com.gxb.manager.dao.RoleMenus;
import com.gxb.manager.service.SysLoginService;
import com.gxb.model.SysLoginInfo;
import com.util.StrUtil;

@Controller
@RequestMapping("/manager")
public class SysMainControl extends GeneralControl{
	@Resource
	private SysLoginService loginService;
	/**
	 *摘要：
	 *@说明：去首页
	 *@创建：作者:yxy		创建时间：2013-4-8
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-8)<修改说明>
	 */
	@RequestMapping("/index")
	public String toMainIndex(Model model,HttpServletRequest request){
		//根据登录的角色获取所有菜单
		List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
		SysLoginInfo info = this.getLoginInfo(request);
		List<Integer> roles = info.getUsrRoleIds();
		for (Integer integer : roles) {
			List<Map<String,Object>> temps = RoleMenus.roleMenus.get(integer);
			menus.addAll(temps);
		}
		//父级菜单
		List<Map<String,Object>> pmenus = new ArrayList<Map<String,Object>>();
		if(menus.size()>0){
			for (Map<String,Object> map : menus) {
				int p_id = StrUtil.convertInt(map.get("p_id"));
				if(p_id==0){
					pmenus.add(map);
				}
			}
			if(pmenus.size()>0){
				model.addAttribute("pmenus", pmenus);
			}
		}
		return "main/index";
	}
	/**
	 *摘要：
	 *@说明：获取下级菜单
	 *@创建：作者:yxy		创建时间：2013-5-6
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-5-6)<修改说明>
	 */
	@RequestMapping("/nextmenu")
	public String nextmenu(Model model,int id,HttpServletRequest request){
		//根据登录的角色获取所有菜单
		List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
		SysLoginInfo info = this.getLoginInfo(request);
		List<Integer> roles = info.getUsrRoleIds();
		for (Integer integer : roles) {
			List<Map<String,Object>> temps = RoleMenus.roleMenus.get(integer);
			menus.addAll(temps);
		}
		//父级菜单
		List<Map<String,Object>> nextmenus = new ArrayList<Map<String,Object>>();
		if(menus.size()>0){
			for (Map<String,Object> map : menus) {
				int p_id = StrUtil.convertInt(map.get("p_id"));
				String menuTp = StrUtil.convertStr(map.get("menu_tp"));
				if(p_id==id && "0".equals(menuTp)){
					nextmenus.add(map);
				}
			}
			if(nextmenus.size()>0){
				model.addAttribute("menus", nextmenus);
			}
		}
		return "main/menu";
	}
	/**
	 *摘要：
	 *@说明：首页
	 *@创建：作者:yxy		创建时间：2013-4-8
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-8)<修改说明>
	 */
	@RequestMapping("/main")
	public String toMain(){
		return "main/main";
	}
	/**
	 *摘要：
	 *@说明：获取当前登录人的菜单
	 *@创建：作者:yxy		创建时间：2013-4-8 
	 *@修改历史：
	 *		[序号](yxy	2013-4-8)<修改说明>
	 */
	@RequestMapping("/menus")
	public void queryMyMenus(HttpServletResponse response,String id,HttpServletRequest request){
		int pid = 0;
		if(!StrUtil.isNull(id)){
			pid = StrUtil.convertInt(id);
		}
		List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
		SysLoginInfo info = this.getLoginInfo(request);
		List<Integer> roles = info.getUsrRoleIds();
		for (Integer integer : roles) {
			List<Map<String,Object>> temps = RoleMenus.roleMenus.get(integer);
			menus.addAll(temps);
		}
		StringBuilder str = new StringBuilder();
		if(null!=menus&&menus.size()>0){
			str.append("[");
			String sp="";
			for (Map<String,Object> map : menus) {
				String menuTp = StrUtil.convertStr(map.get("menu_tp"));
				if("0".equals(menuTp)){
					Integer p_id = StrUtil.convertInt(map.get("p_id"));
					if(pid==p_id){
						Integer menuId = StrUtil.convertInt(map.get("id_key"));
						String menuLeaf = StrUtil.convertStr(map.get("menu_leaf"));
						String menuUrl = StrUtil.convertStr2(map.get("menu_url"));
						String state = "open";
						if("0".equals(menuLeaf)){
							state = "closed";
						}
						String menuNm = StrUtil.convertStr(map.get("menu_nm"));
						str.append(sp).append("{\"id\":").append(menuId).append(",\"text\":\"")
						.append(menuNm).append("\",\"state\":\"").append(state).append("\",\"attributes\":{\"url\":\"")
						.append(menuUrl).append("\"}}");
						sp=",";
					}
				}
			}
			str.append("]");
			this.sendJsonResponse(response, str.toString());
		}
	}
	/**
	 *摘要：
	 *@说明：修改密码
	 *@创建：作者:yxy		创建时间：2013-4-14
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-14)<修改说明>
	 */
	@RequestMapping("/toupdateusrpwd")
	public String toupdateusrpwd(){
		return "main/updatepwd";
	}
	/**
	 * 
	 *摘要：
	 *@说明：修改密码
	 *@创建：作者:yxy		创建时间：2013-4-14
	 *@param usrPwd
	 *@param newPwd
	 *@param confirmPwd
	 *@param request 
	 *@修改历史：
	 *		[序号](yxy	2013-4-14)<修改说明>
	 */
	@RequestMapping("/updateusrpwd")
	public void updateusrpwd(String usrPwd,String newPwd,String confirmPwd,HttpServletRequest request,HttpServletResponse response){
		try {
			SysLoginInfo info = this.getLoginInfo(request);
			String oldPwd = this.loginService.queryPwdById(info.getIdKey());
			if(!oldPwd.equals(usrPwd)){
				this.sendHtmlResponse(response, "13");
			}else{
				this.loginService.updatePwd(info.getIdKey(), newPwd);
				this.sendHtmlResponse(response, "14");
			}
		} catch (Exception e) {
			log.error("修改密码出错："+e);
			this.sendHtmlResponse(response, "4");
		}
	}
}
