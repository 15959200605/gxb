package com.gxb.manager.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.control.GeneralControl;
import com.gxb.manager.service.SysFeedbackService;
import com.gxb.model.SysFeedback;
import com.util.Page;

/**
 * 意见反馈控制器
 * @author dzr
 *
 */
@Controller
@RequestMapping("/manager")
public class SysFeedbackControl extends GeneralControl{

	@Resource
	private SysFeedbackService sysFeedbackService;
	
	/**
	 * 
	  *摘要：
	  *@说明：到意见反馈主页
	  *@创建：作者:dzr		创建时间：2015-9-29
	  *@修改历史：
	  *		[序号]()<修改说明>
	 */
	@RequestMapping("/queryfeedback")
	public String queryfeedback(){
		return "/system/feedback/feedback";
	}

	/**
	 * 
	  *摘要：
	  *@说明：分页查询意见
	  *@创建：作者:dzr		创建时间：2015-9-29
	  *@修改历史：
	  *		[序号]()<修改说明>
	 */
	@RequestMapping("/feedbackPage")
	public void feedbackPage(HttpServletRequest request,HttpServletResponse response,SysFeedback feedback,Integer page,Integer rows){
		try{
			Page p = this.sysFeedbackService.queryFeedback(feedback, page, rows);
			JSONObject json = new JSONObject();
			json.put("total", p.getTotal());
			json.put("rows", p.getRows());
			this.sendJsonResponse(response, json.toString());
			p = null;
		}catch (Exception e) {
			log.error("分页查询意见出错"+e);
		}
	}
	/**
	 * 
	  *摘要：
	  *@说明：批量删除反馈意见
	  *@创建：作者:dzr		创建时间：2015-9-29
	  *@修改历史：
	  *		[序号]()<修改说明>
	 */
	@RequestMapping("/delFeedback")
	public void delFeedback(Integer[] id,HttpServletResponse response,HttpServletRequest request){
		try{
			this.sysFeedbackService.deleteFeedback(id);
			this.sendHtmlResponse(response, "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除意见出错:"+e);
			this.sendHtmlResponse(response, "-1");
		}
	}
}
