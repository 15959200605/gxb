package com.gxb.manager.control;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.control.GeneralControl;
import com.gxb.manager.service.SysUserService;
import com.gxb.model.SysLoginInfo;
import com.gxb.model.SysUser;
import com.util.DateTimeUtil;
import com.util.Page;
import com.util.StrUtil;

/**
 * 用户管理控制器
 * 
 * @author LHC
 * 
 */
@Controller
@RequestMapping("/manager")
public class SysUserControl extends GeneralControl {

	@Resource
	private SysUserService userService;

	/**
	 * 后台用户主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryuserht")
	public String queryuserht(Model model) {
		return "/system/user/userht";
	}

	/**
	 * 分页查询用户
	 * 
	 * @param request
	 * @param response
	 * @param merchant
	 * @param page
	 * @param rows
	 * @param tp
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/userPage")
	public void userPage(HttpServletRequest request,
			HttpServletResponse response, SysUser user, Integer page,
			Integer rows, String tp) {
		try {
			Page p = this.userService.queryUser(user, page, rows, tp);
			List<SysUser> list = p.getRows();
			for (SysUser sysUser : list) {
				sysUser.setRegtimeStr(DateTimeUtil.getDateToStr(sysUser.getRegtime(), "yyyy-MM-dd HH:mm:ss"));
			}
			JSONObject json = new JSONObject();
			json.put("total", p.getTotal());
			json.put("rows", p.getRows());
			this.sendJsonResponse(response, json.toString());
			p = null;
		} catch (Exception e) {
			log.error("分页查询企业出错" + e);
		}
	}

	/**
	 * 修改/添加后台用户页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/tooperuserht")
	public String tooperuserht(Model model, Integer id) {
		if (null != id) {
			try {
				SysUser user = this.userService.queryUserById(id);
				model.addAttribute("user", user);
			} catch (Exception e) {
				log.error("获取后台用户出错：" + e);
			}
		}
		return "/system/user/userhtoper";
	}

	/**
	 * 修改/添加后台用户
	 * 
	 * @param request
	 * @param response
	 * @param merchant
	 */
	@RequestMapping("/operuserht")
	public void operuserht(HttpServletRequest request,HttpServletResponse response, SysUser user) {
		try {
			int count = this.userService.queryUserCount(user.getUserNo());
			if (null == user.getIdKey()) {
				if (count >= 1) {
					this.sendHtmlResponse(response, "-2");
					return;
				}
				user.setUserPwd(StrUtil.string2MD5(user.getUserPwd()));
				user.setRegtime(new Date());
				// 添加
				this.userService.addUser(user, null, null);
				this.sendHtmlResponse(response, "1");
			} else {
				if (count >= 1) {
					SysUser user1 = this.userService.queryUserById(user.getIdKey());
					if (!user1.getUserNo().equals(user.getUserNo())) {
						this.sendHtmlResponse(response, "-2");
						return;
					}
				}
				// 修改
				this.userService.updateUser(user, null, null);
				this.sendHtmlResponse(response, "2");
			}

		} catch (Exception e) {
			log.error("修改/添加企业出错：" + e);
		}
	}

	/**
	 * 根据用户id删除
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/deleteUser")
	public void deleteMerchant(HttpServletRequest request,HttpServletResponse response,Integer[] ids){
		try{
			SysLoginInfo usr=(SysLoginInfo) request.getSession().getAttribute("usr");
			for(int j=0;j<ids.length;j++){
				if(ids[j].equals(usr.getIdKey()))
				{
					this.sendHtmlResponse(response, "2");
					return;
				}
			}
			for(int i=0;i<ids.length;i++){
				//SysUser user=this.userService.queryUserById(ids[i]);
				this.userService.deleteUser(ids[i]);
			}
			this.sendHtmlResponse(response, "1");
		} catch (Exception e) {
			log.error("操作失败："+e);
		}
	}
	
	/**
	 * 修改密码
	 * 
	 * @param response
	 * @param request
	 * @param oldpwd
	 * @param newpwd
	 * @throws JSONException
	 */
	@RequestMapping("/modifypwd")
	public void modifyPwd(HttpServletResponse response,
			HttpServletRequest request, String oldpwd, String newpwd)
			throws JSONException {
		JSONObject json = new JSONObject();
		try {
			if (oldpwd == null || newpwd == null) {
				json.put("status", "-1");
				json.put("msg", "非法的访问(参数不完整)!");
			} else {
				SysLoginInfo info = getLoginInfo(request);
				if (info == null) {
					json.put("status", "-1");
					json.put("msg", "您还没有登录!");
				} else {
					String pwd = info.getUsrPwd();
					if (pwd.equals(oldpwd)) {
						this.userService
								.updatePwdByUno(info.getUsrNo(), newpwd);
						json.put("status", "1");
						json.put("msg", "修改成功");
					} else {
						json.put("status", "-1");
						json.put("msg", "密码错误!不允许修改");
					}
				}
			}
			this.sendJsonResponse(response, json.toString());
		} catch (Exception e) {
			json.put("status", "-1");
			json.put("msg", "修改出错!");
			this.sendJsonResponse(response, json.toString());
			log.error("系统企业修改密码对象出错：" + e);
		}
	}
}
