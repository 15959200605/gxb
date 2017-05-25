package com.gxb.manager.control;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.control.GeneralControl;
import com.gxb.manager.service.SysLoginService;
import com.gxb.model.Constant;
import com.gxb.model.SysLoginInfo;
import com.util.ImageUtil;
import com.util.ImgCoordinate;
import com.util.Statics;
import com.util.StrUtil;

/**
 *说明：
 *@创建：作者:yxy		创建时间：2013-4-7
 *@修改历史：
 *		[序号](yxy	2013-4-7)<修改说明>
 */
@Controller
@RequestMapping("/manager")
public class SysLoginControl extends GeneralControl{
	@Resource
	private SysLoginService loginService;
	
     /**
	 *摘要：
	 *@说明：到登录页面
	 *@创建：作者:yxy		创建时间：2013-4-10
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-10)<修改说明>
	 */
	@RequestMapping("/login")
	public String toLogin(Model model,HttpServletRequest request){
		String usrNo = request.getParameter("usrNo");
		model.addAttribute("usrNo", usrNo);
		return "main/login";
	}
	/**
	 *摘要：
	 *@说明：去session过期登录
	 *@创建：作者:yxy		创建时间：2013-4-27
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-27)<修改说明>
	 */
	@RequestMapping("/tologin")
	public String gologin(){
		return "main/tologin";
	}
	/**
	 *摘要：
	 *@说明：退出
	 *@创建：作者:yxy		创建时间：2013-4-27
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-27)<修改说明>
	 */
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request){
		request.getSession().removeAttribute("usr");
		return "main/login";
	}
	/**
	 *摘要：
	 *@说明：登录
	 *@创建：作者:yxy		创建时间：2013-4-10
	 *@param request
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-10)<修改说明>
	 */
	@RequestMapping("/dologin")
	public String login(Model model,HttpServletRequest request){
		String usrNo = request.getParameter("usrNo");
		String showMsg="";
		try {
			String usrPwd = request.getParameter("usrPwd").toUpperCase();
			SysLoginInfo info = this.loginService.queryLoginInfo(usrNo);
			if(null==info){
				showMsg="11";//账号不存在
			}else{
				String memberPwd = info.getUsrPwd().toUpperCase();
				if(!usrPwd.equals(memberPwd)){
					showMsg="12";//密码输入不正确
				}else{
					//查询角色
					List<Integer> roleIds =new ArrayList<Integer>();
					if(info.getIsSj()!=Constant.USER_TYPE_PT){
						roleIds.add(2);
					}else{
						roleIds = this.loginService.queryRoleIdByUsr(info.getIdKey());
					}
					if(null==roleIds || roleIds.size()==0){
						showMsg="13";//没有权限
					}else{
//						if(!"admin".equals(usrNo)){
//							showMsg="19";
//						}
						info.setUsrRoleIds(roleIds);
						request.getSession().setAttribute("usr", info);
					}
				}
			}
		} catch (Exception e) {
			showMsg="15";
			log.error("登录出错："+e);
		}
		if(StrUtil.isNull(showMsg)){
			return "redirect:/manager/index";
		}
		model.addAttribute("usrNo", usrNo);
		model.addAttribute("showMsg", showMsg);
		return "main/login";
	}
	/**
	 * 
	 *摘要：
	 *@说明：去上传页面
	 *@创建：作者:yxy		创建时间：2013-4-18
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-18)<修改说明>
	 */
	@RequestMapping("/toupfile")
	public String toUpFile(Model model,int width,int height,String tp){
		model.addAttribute("width", width);
		model.addAttribute("height", height);
		model.addAttribute("tp", tp);
		return "/include/upFile";
	}
	/**
	 * 
	 *摘要：
	 *@说明：上传到临时文件夹
	 *@创建：作者:yxy		创建时间：2013-4-18 
	 *@修改历史：
	 *		[序号](yxy	2013-4-18)<修改说明>
	 */
	@RequestMapping("/uploadtemp")
	public void uploadTemp(HttpServletRequest request,HttpServletResponse response,int width,int height){
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
		MultipartFile upFile = multiRequest.getFile("upFile");
		//照片路径
		String path = request.getSession().getServletContext().getRealPath("/upload/temp");
		try {
			//上传图片
			String fileName = StrUtil.upLoadFile(upFile,path,null);
			BufferedImage sourceImg =ImageIO.read(new FileInputStream(path+"/"+fileName));
			int sourceWidth = sourceImg.getWidth();
			int sourceHeight = sourceImg.getHeight();
			if(sourceWidth<width || sourceHeight<height){
				this.sendHtmlResponse(response, "-1");
				return;
			}
			if(StrUtil.isNull(fileName)){
				this.sendHtmlResponse(response, "-2");
				return;
			}
			this.sendHtmlResponse(response, fileName);
		} catch (Exception e) {
			log.error("上传到临时文件夹出错："+e);
			this.sendHtmlResponse(response, "-2");
		}
	}
	/**
	  *@说明：上边上传图片窗口（正方）
	  *@创建：作者:llp		创建时间：2013-9-18
	  *@param @return 
	  *@修改历史：
	  *		[序号](llp	2013--9-18)<修改说明>
	 */
	@RequestMapping("/toImgCoord")
	public String toImgCoord(Model model,String imgsrc){
		model.addAttribute("imgsrc", imgsrc);
		return "/main/toImgCoordzf";
	}
	/**
	  *@说明：上边上传图片窗口（长方横）
	  *@创建：作者:llp		创建时间：2013-9-18
	  *@param @return 
	  *@修改历史：
	  *		[序号](llp	2013--9-18)<修改说明>
	 */
	@RequestMapping("/toImgCoordcf")
	public String toImgCoordcf(Model model,String imgsrc){
		model.addAttribute("imgsrc", imgsrc);
		return "/main/toImgCoordcf";
	}
	/**
	  *@说明：上边上传图片窗口（长方竖）
	  *@创建：作者:llp		创建时间：2013-9-18
	  *@param @return 
	  *@修改历史：
	  *		[序号](llp	2013--9-18)<修改说明>
	 */
	@RequestMapping("/toImgCoordcfs")
	public String toImgCoordcfs(Model model,String imgsrc){
		model.addAttribute("imgsrc", imgsrc);
		return "/main/toImgCoordcfs";
	}
	/**
	 * 说明：图片剪裁
	 * @创建：作者:llp 创建时间：2013-8-23
	 * @param x 开始(横坐标)
	 * @param y 开始(纵坐标)
	 * @param width  剪裁的宽度
	 * @param height 剪裁的高度
	 * @param folder 要保存的目录
	 * @param imgNm  要保存的文件名称
	 * @修改历史： [序号](llp 2013-8-23)<修改说明>
	 */
	@RequestMapping("/imgCoordinate")
	public void imgCoordinate(ImgCoordinate imgc,HttpServletResponse response,HttpServletRequest request){
		try {
			String path = request.getSession().getServletContext().getRealPath("/upload");
			String folder=path+"/temp";
			String fileName = imgc.getSrcpath();
			imgc.setSrcpath(folder+"/"+fileName);
			imgc.setFolder(folder);
			imgc.setImgNm("j"+System.currentTimeMillis());
			String srcPath = folder+"/s"+fileName;
			//图片缩放
			ImageUtil.scImgBySize(imgc.getSrcpath(), srcPath, imgc.getImgWidth(), imgc.getImgHeight());
			imgc.setSrcpath(srcPath);
			//图片剪裁
			String imgNm = ImageUtil.clipping(imgc);
			if(StrUtil.isNull(imgNm)){
				this.sendHtmlResponse(response, "-1");
			}else{
				this.sendHtmlResponse(response, imgNm);
			}
		} catch (Exception e) {
			log.error("截图出错："+e);
			this.sendHtmlResponse(response, "-1");
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：去上传页面
	 *@创建：作者:yxy		创建时间：2013-4-18
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-18)<修改说明>
	 */
	@RequestMapping("/toupfile2")
	public String toUpFile2(Model model,String str1,String str2,int width,int height){
		model.addAttribute("str1", str1);
		model.addAttribute("str2", str2);
		model.addAttribute("width", width);
		model.addAttribute("height", height);
		return "/include/upFile2";
	}
	@RequestMapping("/toImgCoord2")
	public String toImgCoord2(String imgsrc,Model model,String str2,String str1){
		model.addAttribute("imgsrc", imgsrc);
		model.addAttribute("str2", str2);
		model.addAttribute("str1", str1);
		return "/main/toImgCoordzf2";
	}
	@RequestMapping("/toImgCoordcf2")
	public String toImgCoordcf2(String imgsrc,Model model,String str2,String str1){
		model.addAttribute("imgsrc", imgsrc);
		model.addAttribute("str2", str2);
		model.addAttribute("str1", str1);
		return "/main/toImgCoordcf2";
	}
	/**
	 *摘要：
	 *@说明：到地区页面
	 *@创建：作者:yxy		创建时间：2013-4-30
	 *@param model
	 *@param request
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2013-4-30)<修改说明>
	 */
	@RequestMapping("/getmap")
	public String toMap(Model model,HttpServletRequest request){
        String city = request.getParameter("city");
		if(!StrUtil.isNull(city)){
			Double[] coordinates = Statics.getCoordinate(city);
			if(null!=coordinates && coordinates.length==2){
				model.addAttribute("cityLng", coordinates[0]);
				model.addAttribute("cityLat", coordinates[1]);
				model.addAttribute("cityNm", city);
			}
		}
		String oldLng = request.getParameter("oldLng");
		if(!StrUtil.isNull(oldLng)){
			model.addAttribute("oldLng", oldLng);
		}
		String oldLat = request.getParameter("oldLat");
		if(!StrUtil.isNull(oldLat)){
			model.addAttribute("oldLat", oldLat);
		}
		String zoom = request.getParameter("zoom");
		if(!StrUtil.isNull(zoom)){
			model.addAttribute("zoom", zoom);
		}
		String searchCondition = request.getParameter("searchCondition");
		if(!StrUtil.isNull(searchCondition)){
			model.addAttribute("searchCondition", searchCondition);
		}
		return "/main/map";
	}
}