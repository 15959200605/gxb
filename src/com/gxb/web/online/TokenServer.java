package com.gxb.web.online;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端Token服务类.
 * 
 * @author lhq
 * @since JDK 1.6
 * @see
 */
public class TokenServer {

	private static List<OnlineUser> onlineUserList = new ArrayList<OnlineUser>();

	private static List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();

	public static void tokenCreated(OnlineUser onlineMember) {
		onlineMember.setAccessTime(new Date());
		onlineUserList.add(onlineMember);
	}

	public static void tokenDestroyed(OnlineUser member) {
		onlineUserList.remove(member);
	}

	/*
	 * public static boolean checkLoginState(OnlineUser onlineUser){ if
	 * (onlineUserList != null && onlineUserList.size() > 0) { // 判断该用户是否已经登录
	 * for (OnlineUser onlineMember : onlineUserList) { if (onlineMember != null
	 * &&
	 * onlineMember.getId()!=null&&onlineMember.getId().intValue()==onlineUser
	 * .getId().intValue()){ return false; } } } return true; }
	 */

	public static OnlineMessage tokenCheck(String token) {
		OnlineUser temp = null;
		OnlineMessage onlineMessage = null;
		if (onlineUserList != null && onlineUserList.size() > 0) {
			// 判断该用户是否已经登录
			for (OnlineUser onlineMember : onlineUserList) {
				if (onlineMember != null && onlineMember.getToken() != null
						&& onlineMember.getToken().equals(token)) {
					onlineMessage = new OnlineMessage();
					Date nowTime = new Date();
					Date accessTime = onlineMember.getAccessTime();
					long time = nowTime.getTime() - accessTime.getTime();
					long minutes = time / 60000;
					if (minutes > WebServerConstants.APP_OVERTIME) {// 登录超时，重新登陆
						onlineMessage.setMessage("访问超时，请重新登陆.");
						onlineMessage.setSuccess(false);
						temp = onlineMember;
						// onlineUserList.remove(onlineMember);
					} else {
						onlineMember.setAccessTime(new Date());
						onlineMessage.setMessage("登录成功.");
						onlineMessage.setSuccess(true);
						onlineMessage.setOnlineMember(onlineMember);
						break;
					}
				}
			}
			if (null != temp) {
				onlineUserList.remove(temp);
			}
		}
		if (null == onlineMessage) {
			onlineMessage = new OnlineMessage();
			onlineMessage.setMessage("请先登录系统");
			onlineMessage.setSuccess(false);
		}
		return onlineMessage;
	}

	// 登录时判断是否有重复登录，重复删除前一个登录记录
	public static boolean checkLoginState(Integer usrId) {
		OnlineUser temp = null;
		if (onlineUserList != null && onlineUserList.size() > 0) {
			// 判断该用户是否已经登录
			for (OnlineUser onlineMember : onlineUserList) {
				if (onlineMember != null
						&& onlineMember.getUsrId() != null
						&& onlineMember.getUsrId().intValue() == usrId
								.intValue()) {
					if (null == onlineMember.getTel()) {
						temp = onlineMember;
						// onlineUserList.remove(onlineMember);
					} else {
						onlineMember.setTel(null);// 提示异地登录用
					}
				}
			}
			if (null != temp) {
				/**
				 * 在list遍历完成之后再进行删除，不然会出现线程并发问题java.util.
				 * ConcurrentModificationException
				 **/
				onlineUserList.remove(temp);
			}
		}
		return true;
	}

	public static void addCode(String code, String mobile) {
		for (int i = 0; i < codes.size(); i++) {
			if (mobile.equals(codes.get(i).get("mobile"))) {
				codes.remove(i);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("mobile", mobile);
		map.put("time", new Date());
		codes.add(map);
	}

	public static boolean checkCode(String code, String mobile) {
		boolean falg = false;
		Date startTime = new Date();
		if (codes != null && codes.size() > 0) {
			for (int i = 0; i < codes.size(); i++) {
				Date endTime = (Date) codes.get(i).get("time");
				long time = endTime.getTime() - startTime.getTime();
				long minutes = time / 60000;
				if (minutes > 10) {
					codes.remove(i);
				}
				if (code.equals(codes.get(i).get("code"))
						&& mobile.equals(codes.get(i).get("mobile"))) {
					falg = true;
				}
			}
		}
		return falg;
	}
}
