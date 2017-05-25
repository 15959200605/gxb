package com.gxb.manager.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.control.GeneralControl;
import com.gxb.manager.service.SysVersionService;
import com.gxb.model.SysLoginInfo;
import com.gxb.model.SysVersion;
import com.util.Page;
import com.util.StrUtil;

@Controller
@RequestMapping("/manager")
public class SysVersionControl extends GeneralControl {
	@Resource
	private SysVersionService versionService;
	
	/**
	  *摘要：到客户端版本管理页面
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @return 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	@RequestMapping("queryVersion")
	public String toVersionPage(){
		return "/system/version/versionPage";
	}
	
	/**
	  *摘要：查询版本
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param request
	  *@param @param response
	  *@param @param version
	  *@param @param page
	  *@param @param rows 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	@RequestMapping("queryVersionPage")
	public void queryVersionPage(HttpServletRequest request,HttpServletResponse response,SysVersion version,Integer page,Integer rows){
		try{
			Page p = this.versionService.queryVersion(version,page,rows);
			JSONObject json = new JSONObject();
			json.put("total", p.getTotal());
			json.put("rows", p.getRows());
			this.sendJsonResponse(response, json.toString());
			p=null;
		}catch (Exception e) {
			log.error("查询版本失败"+e);
		}
	}
	
	/**
	  *摘要：到添加客户端版本页面
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @return 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	@RequestMapping("toOperVersion")
	public String toOperVersion(HttpServletRequest request,Model model,Integer id){
		if(!StrUtil.isNull(id)){
			SysVersion version = this.versionService.queryVersionById(id);
			model.addAttribute("version", version);
		}
		return "/system/version/versionOper";
	}

	/**
	  *摘要：删除客户端版本
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param request
	  *@param @param response
	  *@param @param id 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	@RequestMapping("delVersion")
	public void delVersion(HttpServletRequest request,HttpServletResponse response,Integer id){
		try{
			this.versionService.deleteVersionById(id);
			this.sendHtmlResponse(response, "3");
		}catch (Exception e) {
			log.error("删除客户端版本失败"+e);
			this.sendHtmlResponse(response, "6");
		}
	}
	/**
	  *摘要：保存客户端版本号
	  *@说明：
	  *@创建：作者:YYP		创建时间：2014-7-14
	  *@param @param response
	  *@param @param version 
	  *@修改历史：
	  *		[序号](YYP	2014-7-14)<修改说明>
	 */
	@RequestMapping("saveVersion")
	public void saveVersion(HttpServletRequest request,HttpServletResponse response,SysVersion version,
			@RequestParam("applycationUrl") MultipartFile applycationUrl){
		InputStream is2 = null;
		InputStream is = null;
		FileOutputStream fos2 = null;
		FileOutputStream fos = null;
		try{ 
			//转码（中文乱码问题）
			String versionName = new String(version.getVersionName().getBytes("ISO-8859-1"),"utf-8") ;
			String versionContent = new String(version.getVersionContent().getBytes("ISO-8859-1"),"utf-8") ;
			version.setVersionName(versionName);
			version.setVersionContent(versionContent);
			if(!applycationUrl.isEmpty()){
				 String dirPath = request.getSession().getServletContext().getRealPath("upload");  //获取保存路径
		            File dirFile = new File(dirPath+"/app/");  //转成文件路径
		            if (!dirFile.exists()) {  //不存在则创建
		               dirFile.mkdirs();  
		           }
		           if(!applycationUrl.isEmpty()){
		        	   String str=applycationUrl.getOriginalFilename().substring(applycationUrl.getOriginalFilename().indexOf("."));//截取后缀名
		        	   File applycationUrlFile = new File(dirFile, "stoneApp"+str); 
		        	   is = applycationUrl.getInputStream();
		        	   fos = new FileOutputStream(applycationUrlFile);
		        	   byte[] tmp = new byte[1024];  
			           int len = -1;  
		        	   while ((len = is.read(tmp)) != -1) {  
			                fos.write(tmp, 0, len);  //内容写入
			           }  
		           }
			}
			SysLoginInfo info = getLoginInfo(request);
			version.setVersionTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			version.setVersionUser(info.getIdKey());
			if(StrUtil.isNull(version.getId())){//添加
				this.versionService.addVersion(version);
			}else{//修改
				this.versionService.updateVersion(version);
			}
			this.sendHtmlResponse(response, "1");
		}catch (Exception e) {
			System.out.println(e);
			log.error("保存客户端版本失败"+e);
			this.sendHtmlResponse(response, "-1");
		}finally{//关闭流
			try{
			   if(is!=null){
				   is.close();  
			   }
			   if(fos!=null){
		           fos.flush();  
		           fos.close(); 
			   }
	           if(!StrUtil.isNull(is2)){
		           is2.close();  
	           }
	           if(!StrUtil.isNull(fos2)){
		           fos2.flush();  
		           fos2.close();
	           }
			}catch (IOException e) {
				log.error("关闭流失败"+e);
			}
		}
	}
	
}
