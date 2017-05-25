package com.util;

import java.math.BigInteger;

/**
 *说明：
 *@创建：作者:yxy		创建时间：2013-9-29
 *@修改历史：
 *		[序号](yxy	2013-9-29)<修改说明>
 */
public class EncryptionUtil {
	//秘钥
	private static final byte[] KEY = "SIXUNCBP19830625".getBytes();
	public EncryptionUtil() {
	}
	//加密
	public static String encrypt(String str){
		byte[] buffer = str.getBytes();
		int i=0;
		int keylength = KEY.length;
		StringBuilder sbr = new StringBuilder();
		for (byte b : buffer) {
			//异或
			int t = (b^KEY[i]);
			//位移
			t = t<<2;
			//转二进制
			String tempStr = Integer.toBinaryString(t);
			//取反
			StringBuilder sb = new StringBuilder();
			for (int m=0;m<tempStr.length();m++) {
				String substr = tempStr.substring(m, m+1);
				if(m>0){
					if("0".equals(substr)){
						substr="1";
					}else{
						substr="0";
					}
				}
				sb.append(substr);
			}
			//二进制转数字
			BigInteger bi = new BigInteger(sb.toString(),2);
			//数字转byte
			t = Integer.parseInt(bi.toString());
			if((t>=65 && t<=90) || (t>=97 && t<=122)){
				sbr.append((char)t);
			}else{
				sbr.append((t+"").length());
				sbr.append(t);
			}
			i++;
			if(i==keylength){
				i=0;
			}
		}
		return sbr.toString();
	}
	//解密
	public static String decrypt(String str){
		byte[] bytes = new byte[1];
		int keylength = KEY.length;
		int j=0;
		int l=0;
		for (int i = 0; i < str.length(); i++) {
			//转byte
			String substr = str.substring(i, i+1);
			int b;
			if(StrUtil.isStrNum(substr)){
				i++;
				int num = Integer.parseInt(substr);
				b = Integer.parseInt(str.substring(i, (i+num)));
				i=(i+num-1);
			}else{
				b = substr.getBytes()[0];
			}
			//byte转二进制
			String tempStr = Integer.toBinaryString(b);
			//取反
			StringBuilder sb = new StringBuilder();
			for (int m=0;m<tempStr.length();m++) {
				String tstr = tempStr.substring(m, m+1);
				if(m>0){
					if("0".equals(tstr)){
						tstr="1";
					}else{
						tstr="0";
					}
				}
				sb.append(tstr);
			}
			//转int
			BigInteger bi = new BigInteger(sb.toString(),2);
			b = Integer.parseInt(bi.toString());
			//位移
			b = b>>2;
			//异或
			b = b^KEY[j];
			byte bt = (byte)b;
			if(l==0){
				bytes[l]=bt;
			}else{
				byte[] temps = bytes;
				bytes = new byte[l+1];
				for (int k = 0; k < temps.length; k++) {
					bytes[k]=temps[k];
				}
				bytes[l]=bt;
			}
			l++;
			j++;
			if(j==keylength){
				j=0;
			}
		}
		return new String(bytes);
	}
	public static void main(String[] args) {
		System.out.println(encrypt("SX00000001"));
	}
}

