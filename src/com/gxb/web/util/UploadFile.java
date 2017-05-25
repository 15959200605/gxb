package com.gxb.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.framework.commons.core.encode.EncryptUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.control.GeneralControl;
import com.util.ImageUtil;
import com.util.ImgCoordinate;
import com.util.StrUtil;

public class UploadFile extends GeneralControl{
	
	/**
	 * 保存聊天的图片和录音
	 * @param request
	 * @param response
	 * @return
	 */
	public static String savePhotoOrVoice(HttpServletRequest request,HttpServletResponse response,String database){
		MultipartHttpServletRequest imgRequest = (MultipartHttpServletRequest)request;
		try {
			MultipartFile file = imgRequest.getFile("file");
			String photoType = StrUtil.returnFileType(file);
			if(!StrUtil.isNull(photoType)){
				String path = request.getSession().getServletContext().getRealPath("/upload");
				String photoName = System.currentTimeMillis()+photoType;
				StrUtil.upLoadFile(file, path+"/"+database+"/chat",photoName);
				return database+"/chat/"+photoName;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 上传一组图片
	 * @param request
	 * @param response
	 * @return
	 */
	public static Map<String, Object> updatePhotos(HttpServletRequest request,HttpServletResponse response,String folder,Integer type){
		MultipartHttpServletRequest imgRequest = (MultipartHttpServletRequest)request;
		try {
			List<String> fileNames = new ArrayList<String>();
			List<String> smallFile = new ArrayList<String>();
			List<String> spicFile = new ArrayList<String>();
			List<String> simageFile = new ArrayList<String>();
			Iterator<String> list = imgRequest.getFileNames();
			List<MultipartFile> files = new ArrayList<MultipartFile>();
			while (list.hasNext()) {
				MultipartFile file = imgRequest.getFile(list.next());
				files.add(file);
			}
			for (int i = 0; i < files.size(); i++) {
				String path="";
				String photoName="";
				MultipartFile file = files.get(i);
				String photoType = StrUtil.returnFileType(file);
				if(!StrUtil.isNull(photoType)){
					path = request.getSession().getServletContext().getRealPath("/upload/");
					photoName = System.currentTimeMillis()+i+photoType;
					StrUtil.upLoadFile(file, path+"/"+folder,photoName);
					fileNames.add(folder+"/"+photoName);
					//进行图片压缩
					ImageUtil.compressImsg(path+"/"+folder+"/"+photoName,path+"/"+folder+"/"+"/small_"+photoName,300,0);
					String smallName = "small_"+photoName;
					smallFile.add(folder+"/"+smallName);
				}
				if(i==0){//第一张图进行切图
					ImageUtil.copyFile(path+"/"+folder+"/"+"/small_"+photoName, path+"/"+folder+"/"+"/cut_"+photoName);
					//裁剪图片
					ImgCoordinate imgCoordinate = new ImgCoordinate();
					imgCoordinate.setSrcpath(path+"/"+folder+"/"+"/cut_"+photoName);
					imgCoordinate.setFolder(path+"/"+folder+"/");
					imgCoordinate.setImgNm("cut_"+EncryptUtils.Encrypt(String.valueOf(System.currentTimeMillis()), "MD5"));
					imgCoordinate.setWidth(300);
					imgCoordinate.setHeight(200);
					String cutName = ImageUtil.tailor(imgCoordinate,path+"/"+folder+"/","cut_"+photoName);
					spicFile.add(folder+"/"+cutName);
					//进行图片压缩
					ImageUtil.compressImsg(path+"/"+folder+"/"+photoName,path+"/"+folder+"/"+"/simage_"+photoName,100,100);
					String simage = "simage_"+photoName;
					simageFile.add(folder+"/"+simage);
				}else{
					spicFile.add("");
					simageFile.add("");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fileNames",fileNames);
			map.put("smallFile",smallFile);
			map.put("spicFile",spicFile);
			map.put("simageFile", simageFile);
			return map;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 上传群图片
	 * @param request
	 * @param response
	 * @return
	 */
	public static Map<String, Object>  updatePhoto(HttpServletRequest request,HttpServletResponse response,Integer type,String groupHead,String groupBg,String database){
		MultipartHttpServletRequest imgRequest = (MultipartHttpServletRequest)request;
		try {
			Map<String,MultipartFile> flies = new HashMap<String,MultipartFile>();
			MultipartFile file = null;
			Iterator<String> filedS = imgRequest.getFileNames();
			int i =0;
			while (filedS.hasNext()) {
				i++;
				String temp = filedS.next();
				if(temp.equals("groupHead")){
					file = imgRequest.getFile(temp);
					flies.put(groupHead,file);
				}
				if(temp.equals("groupBg")){
					file = imgRequest.getFile(temp);
					flies.put(groupBg,file);
				}
				if(i>2){
					break;
				}
			}
			
			if (type!=null) {
				file = imgRequest.getFile("file");
				flies.put("file",file);
			}
			String photoType = StrUtil.returnFileType(file);
			Map<String, Object> map = new HashMap<String, Object>();
			if(!StrUtil.isNull(photoType)){
				String path = request.getSession().getServletContext().getRealPath("/upload");
				for (String	fileName : flies.keySet()) {
					i++;
					String photoName = System.currentTimeMillis()+i+photoType;
					StrUtil.upLoadFile(flies.get(fileName), path+"/"+database+"/group",photoName);
					map.put(fileName, database+"/group/"+photoName);
				}
				return map;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
