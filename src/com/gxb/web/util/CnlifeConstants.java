package com.gxb.web.util;


import com.util.PropertiesUtils;

/**
 * 日报常量
 * @author YJP
 *
 */
public class CnlifeConstants {
	
//	 1--账号不存在 2--密码输入不正确 3--没有激活 4--后台出错 5--角色添加成功 6--角色修改成功 7--删除成功 8--权限分配成功 9--操作成功 
	// 10--角色激活成功
	/**
     * 短信验证码超时时间,默认2分钟. -14
     */
	public static long CODE_OVERTIME = 2;
	
	/**
     * 客户端用户超时时间,默认120分钟. -13
     */
    public static long APP_OVERTIME = 120;
	
	/**
	 *  账户不存在 -2
	 */
	public static String ACCOUNT_NOTEXIST="账户不存在";
	
	/**
	 *  登陆成功 3
	 */
	public static String LOGIN_SUCCESS = "登陆成功";
	
	/**
	 *  登陆失败 -3
	 */
	public static String LOGIN_FAIL = "登陆失败";
	
	/**
	 *  操作成功 1
	 */
	public static String SUCCESS="操作成功";
	
	/**
	 *  操作失败 -1
	 */
	public static String FAIL ="操作失败";
	
	/**
	 *  账号激活成功 4
	 */
	public static String ACTIVATE_SUCCESS = "账号激活成功";
	
	/**
	 *  账号已被激活过，请直接登录 -12
	 */
	public static String ACTIVATE_ISSUCCESS = "账号已被激活过，请直接登录";
	/**
	 *  账号激活失败 -4
	 */
	public static String ACTIVATE_FAIL = "账号激活失败";
	
	/**
	 * 账号未激活 -11
	 */
	public static String ACTIVATE_ISNULL = "账号未激活";
	
	/**
	 *  验证码验证成功 5
	 */
	public static String CODE_SUCCESS="验证码验证成功";
	
	/**
	 *  验证码验证失败 -5
	 */
	public static String CODE_FAIL="验证码验证失败";
	
	/**
	 *  密码重置成功 6
	 */
	public static String RESETPWD_SUCCESS="密码重置成功";
	/**
	 *  密码重置失败 -6
	 */
	public static String RESETPWD_FAIL="密码重置失败";
	
	
	/**
	 *  账号密码不能为空 -7
	 */
	public static String ACCOUNT_ISNULL="账号密码不能为空";
	
	/**
	 *  账号密码不正确 -8
	 */
	public static String PASSWORD_STATE="账号密码不正确";
	
	/**
	 *  手机号码格式不正确 -9
	 */
	public static String MOBILE_STATE ="手机号码格式不正确";
	
	/**
	 *  手机号码不能为空 -10
	 */
	public static String MOBILE_ISNULL ="手机号码不能为空";
	
	
	public static String LOGIN_OVERTIME = "访问超时，请重新登陆";
//	public static String ADDRESS = PropertiesUtils.readProperties("/system.properties", "ADDRESS");
	//public static String ADDRESS = "http://192.168.0.161:8989/";
//	public static String SERVELET_URL =  PropertiesUtils.readProperties("/system.properties", "SERVELET_URL");
	
	/**
	 * 得到项目跟路径
	 * @return
	 */
	public static String url() {
		String url = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();
		return url;
	}
	
	/**
	 * 主题分页每页显示数
	 */
	public static Integer pageSize = 5;
	/**
	 * 添加圈友列表分页显示数
	 */
	public static Integer pSize = 20;
}
