package com.gxb.manager.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.control.GeneralControl;
import com.gxb.manager.service.SysInstructionsService;
import com.gxb.model.SysInstructions;
import com.util.Page;
import com.util.StrUtil;

@Controller
@RequestMapping("/manager")
public class SysInstructionsControl extends GeneralControl{
	@Resource
	private SysInstructionsService instructionsService;
	
	/**
	 * 
	  *摘要：
	  *@说明：关于我们主页
	  *@创建：作者:llp		创建时间：2015-8-22
	  *@修改历史：
	  *		[序号](llp	2015-8-22)<修改说明>
	 */
	@RequestMapping("/queryinstructions")
	public String queryinstructions(Model model){
		return "/system/instructions/instructions";
	}

	/**
	 * 
	  *摘要：
	  *@说明：分页查询我们主页
	  *@创建：作者:llp		创建时间：2015-8-22
	  *@修改历史：
	  *		[序号](llp	2015-8-22)<修改说明>
	 */
	@RequestMapping("/instructionsPage")
	public void instructionsPage(HttpServletRequest request,HttpServletResponse response,SysInstructions instructions,Integer page,Integer rows){
		try{
			Page p = this.instructionsService.queryInstructions(instructions, page, rows);
			JSONObject json = new JSONObject();
			json.put("total", p.getTotal());
			json.put("rows", p.getRows());
			this.sendJsonResponse(response, json.toString());
			p = null;
		}catch (Exception e) {
			log.error("分页查询我们主页出错"+e);
		}
	}
	/**
	 * 
	  *摘要：
	  *@说明：添加/修改我们主页页面
	  *@创建：作者:llp		创建时间：2015-8-22
	  *@修改历史：
	  *		[序号](llp	2015-8-22)<修改说明>
	 */
	@RequestMapping("/tooperinstructions")
	public String tooperinstructions(Model model,Integer Id){
		if(!StrUtil.isNull(Id)){
			SysInstructions instructions=this.instructionsService.queryInstructionsById(Id);
			model.addAttribute("instructions", instructions);
		}
		return "/system/instructions/instructionsoper";
	}
	/**
	 * 
	  *摘要：
	  *@说明：添加/修改我们主页
	  *@创建：作者:llp		创建时间：2015-8-22
	  *@修改历史：
	  *		[序号](llp	2015-8-22)<修改说明>
	 */
	@RequestMapping("/operinstructions")
	public void operinstructions(HttpServletRequest request,HttpServletResponse response,SysInstructions instructions){
		try{
            if(StrUtil.isNull(instructions.getId())){
				
                this.sendHtmlResponse(response, "1");
			}else{
			    this.instructionsService.updateInstructions(instructions);
                this.sendHtmlResponse(response, "2");
			}
		} catch (Exception e) {
			log.error("添加/修改我们主页出错："+e);
		}
	}
}
