package com.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;



public class SmsClient {
	protected String smsSvcUrl = "http://218.207.201.87:8860";	   //服务器URL 地址
	protected String cust_code = "530048";									 //账号
	protected String password = "hzzh530048";		 								//密码
	protected String sp_code = "1234";                  //接入码（扩展码）
	
	public void sendSms(String mobiles, String content) throws IOException {
		sendSms(mobiles, content, sp_code, 0);
	}

	public void sendSms(String mobiles, String content, long task_id)
			throws IOException {
		sendSms(mobiles, content, sp_code, task_id);
	}

	public void sendSms(String mobiles, String content, String sp_code)
			throws IOException {
		sendSms(mobiles, content, sp_code, 0);
	}

	public void sendSms(String mobiles, String content, String sp_code,
			long task_id) throws IOException {
		String urlencContent = URLEncoder.encode(content,"utf-8");
		//String sign = MD5.getMD5((urlencContent + password).getBytes());
        String sign=MD5.sign(urlencContent, password, "utf-8");
		String postData = "content=" + urlencContent + "&destMobiles="
				+ mobiles + "&sign=" + sign + "&cust_code=" + cust_code
				+ "&sp_code=" + sp_code + "&task_id=" + task_id;
		//System.err.println(postData);
		URL myurl = new URL(smsSvcUrl);
		URLConnection urlc = myurl.openConnection();
		urlc.setReadTimeout(1000 * 30);
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setAllowUserInteraction(false);

		DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
		//System.out.println("发送数据=" + postData);

		server.write(postData.getBytes("utf-8"));
		server.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlc.getInputStream(), "utf-8"));
		String resXml = "", s = "";
		while ((s = in.readLine()) != null)
			resXml = resXml + s + "\r\n";
		in.close();
		System.out.println("接收数据=" + URLDecoder.decode(resXml,"utf-8"));
	}
	
	public static void main(String[] args) {
		SmsClient client = new SmsClient();
		try {
			client.sendSms("18850569878", "你好这是测试数据");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
