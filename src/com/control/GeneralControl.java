package com.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gxb.model.SysLoginInfo;
import com.util.StrUtil;


/**
 * 说明：
 * 
 * @创建：作者:yxy 创建时间：2013-4-8
 * @修改历史： [序号](yxy 2013-4-8)<修改说明>
 */
public class GeneralControl {
	
	public Logger log = Logger.getLogger(GeneralControl.class.getName());
	
	/**
	 * 摘要：
	 * 
	 * @说明：获取登录信息
	 * @创建：作者:yxy 创建时间：2013-4-11
	 * @param request
	 * @return
	 * @修改历史： [序号](yxy 2013-4-11)<修改说明>
	 */
	public SysLoginInfo getLoginInfo(HttpServletRequest request) {
		return (SysLoginInfo) request.getSession().getAttribute("usr");
	}
	// 向页面发送信息HTML格式
	public void sendHtmlResponse(HttpServletResponse response, String html) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(html);
			pw.flush();
			pw.close();
		} catch (IOException e) {
		}
	}

	// 向页面发送信息JSON格式
	public void sendJsonResponse(HttpServletResponse response, String str) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 上传图片保存
	 * @param request
	 * @param savepath文件保存路径
	 * @return List上传的所有图片的路径
	 * @throws Exception 
	 */
	public List<Map<String,String>> uploadFile(HttpServletRequest request,String savepath,String time) throws Exception {
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/**
		 * 先检查保存的目录是否存在
		 */
        File dirPath = new File(savepath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        List<Map<String,String>> pictures = new ArrayList<Map<String,String>>();
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			Map<String,String> map = new HashMap<String, String>();
			String key = (String) it.next();
			MultipartFile orderFile = multipartRequest.getFile(key);
			String realname = orderFile.getOriginalFilename();
			if (realname.length() > 0) {
				String ext = realname.substring(realname.indexOf("."));
				String filename = System.currentTimeMillis()+StrUtil.generateRandomString(5, 2)+ext;
				String desc = dirPath+File.separator+filename;
				File destFile = new File(desc);
				orderFile.transferTo(destFile);
				map.put("name", realname);
				map.put("path", time+"/"+filename);
				pictures.add(map);
			}
		}
		return pictures;
	}
	/**
	 * 删除图片
	 */
	public void delFile(String path){
		File file = new File(path);
		if(file != null){
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	/**
	 * 说明：获取ip地址
	 * 作者：yxy       日期：2014-3-12
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	public String getPathTemporary(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/temporary/");
	}
	public String getPathUpload(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/upload/");
	}
	
	public SysLoginInfo getInfo(HttpServletRequest request){
		return (SysLoginInfo)request.getSession().getAttribute("usr");
	}
	public String getDate(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	public String getFilePath(){
		return "attachment";
	}
}
