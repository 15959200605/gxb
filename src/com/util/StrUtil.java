package com.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 类说明：字符串，消息处理工具类
 * 
 */

final public class StrUtil {
	final static String[] chineseNumber = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	final static String[] chineseIntBit = { "元", "拾", "佰", "仟" };
	final static String[] chineseLongBit = { "万", "亿" };
	final static String[] chineseDecBit = { "角", "分" };
	final static String chineseZero = "整";
	final static char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static String weburl = "http://192.168.0.156:8080/scb/web/";
	//public static String weburl = "http://192.168.0.89:8092/scb/web/";
    //public static String weburl = "http://www.scbstone.com:8080/web/";
	/**
	 * 不允许实例化
	 * 
	 */
	private StrUtil() {
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 空返回 true,非空返回false
	 */
	public static boolean isNull(Object str) {
		if (str == null) {
			return true;
		} else if (str.toString().trim().length() == 0) {
			return true;
		} else if (str.toString().trim() == "null") {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 摘要：
	 * 
	 * @说明：根据对象转化为字符串
	 * @创建：作者:yxy 创建时间：2012-1-12
	 * @param obj
	 * @return
	 * @修改历史： [序号](yxy 2012-1-12)<修改说明>
	 */
	public static String transStrByObj(Object obj) {
		return null == obj ? "" : obj.toString();
	}

	/**
	 * 判断string是否是数字 return boolean
	 */
	public static Boolean isStrNum(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
		Matcher mc = pattern.matcher(str);
		return mc.matches();
	}

	/**
	 * 设置字符串为空
	 * 
	 * @param strText
	 * @return
	 */
	public static String setNull(String strText) {
		strText = "null";
		return strText;
	}

	/**
	 * 格式化字符串，数量字段专用，返回##0.0000.
	 * 
	 * @param sQty
	 * @return
	 */
	public static String formatQty(String sQty) {
		if (sQty == null || sQty.equals("")) {
			sQty = "0";
		}
		Double dQty = new Double(sQty);
		DecimalFormat df = new DecimalFormat("##0.000");
		sQty = df.format(dQty);
		return sQty;
	}

	/**
	 * 格式化字符串，数量字段专用，当f=null时默认返回###,##0.0000，并自动去除小数点后的0 其它按格式返回
	 * 
	 * @param sQty
	 * @param f
	 * @return
	 */
	public static String formatQty(String sQty, String f) {
		if (sQty == null || sQty.equals(""))
			sQty = "0";

		Double dQty = new Double(sQty);

		DecimalFormat df = new DecimalFormat("##0.0000");
		if (f != null) {
			df = new DecimalFormat(f);
		}
		sQty = df.format(dQty);
		return sQty;
	}

	/**
	 * 格式化字符串，数量字段专用，返回######.0000，并自动去除小数点后的0
	 * 
	 * @param dQty
	 * @return
	 */
	public static String formatQty(double dQty) {
		DecimalFormat df = new DecimalFormat("##0.0000");

		return df.format(dQty);
	}

	// 两位小数
	public static String formatQty2(double dQty) {
		DecimalFormat df = new DecimalFormat("##0.00");

		return df.format(dQty);
	}

	// 三位小数
	public static String formatQty3(double dQty) {
		DecimalFormat df = new DecimalFormat("##0.000");

		return df.format(dQty);
	}

	/**
	 * 格式化字符串，数量字段专用，当f=null时默认返回###,###.0000，并自动去除小数点后的0 其它按格式返回
	 * 
	 * @param dQty
	 * @param f
	 * @return
	 */
	public static String formatQty(double dQty, String f) {
		DecimalFormat df = new DecimalFormat("##0.0000");
		if (f != null) {
			df = new DecimalFormat(f);
		}
		String sQty = null;
		sQty = df.format(dQty);
		return sQty;
	}

	/**
	 * 格式化字符串，单价字段专用，返回######.000000，并自动去除小数点后的0
	 * 
	 * @param sQty
	 * @return
	 */
	public static String formatPrice(String sPrice) {
		if (sPrice == null || sPrice.equals(""))
			sPrice = "0";

		Double dPrice = new Double(sPrice);
		DecimalFormat df = new DecimalFormat("##0.000");
		return df.format(dPrice);
	}

	/**
	 * 格式化字符串，单价字段专用，当f=null时默认返回###,###.000000，并自动去除小数点后的0 其它按格式返回
	 * 
	 * @param sPrice
	 * @param f
	 * @return
	 */
	public static String formatPrice(String sPrice, String f) {
		if (sPrice == null || sPrice.equals(""))
			sPrice = "0";
		Double dPrice = new Double(sPrice);
		DecimalFormat df = new DecimalFormat("##0.000000");
		if (f != null) {
			df = new DecimalFormat(f);
		}
		sPrice = df.format(dPrice);
		return sPrice;
	}

	/**
	 * 格式化字符串，单价字段专用，返回######.000000，并自动去除小数点后的0
	 * 
	 * @param dPrice
	 * @return
	 */
	public static String formatPrice(double dPrice) {
		DecimalFormat df = new DecimalFormat("##0.000000");
		return df.format(dPrice);
	}

	// 两位小数
	public static String formatPrice2(double dPrice) {
		DecimalFormat df = new DecimalFormat("##0.000");
		return df.format(dPrice);
	}

	/**
	 * 格式化字符串，单价字段专用，当f=null时默认返回###,###.000000，并自动去除小数点后的0 其它按格式返回
	 * 
	 * @param dPrice
	 * @param f
	 * @return
	 */
	public static String formatPrice(double dPrice, String f) {
		DecimalFormat df = new DecimalFormat("##0.000000");
		if (f != null) {
			df = new DecimalFormat(f);
		}
		String sPrice = df.format(dPrice);
		return sPrice;
	}

	/**
	 * 格式化字符串，金额字段专用，返回######.000000，并自动去除小数点后的0
	 * 
	 * @param sQty
	 * @return
	 */
	public static String formatMny(String sMny) {
		if (sMny == null || sMny.equals(""))
			sMny = "0";

		Double dMny = new Double(sMny);
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(dMny);
	}

	/**
	 * 格式化字符串，金额字段专用，当f=null时默认返回###,###.000000，并自动去除小数点后的0 其它按格式返回
	 * 
	 * @param sMny
	 * @param f
	 * @return
	 */
	public static String formatMny(String sMny, String f) {
		if (sMny == null || sMny.equals(""))
			sMny = "0";

		Double dMny = new Double(sMny);
		DecimalFormat df = new DecimalFormat("##0.000000");
		if (f != null) {
			df = new DecimalFormat(f);
		}
		sMny = df.format(dMny);
		return sMny;
	}

	/**
	 * 格式化字符串，金额字段专用，返回######.000000，并自动去除小数点后的0
	 * 
	 * @param dMny
	 * @return
	 */
	public static String formatMny(double dMny) {
		DecimalFormat df = new DecimalFormat("##0.000000");
		return df.format(dMny);
	}

	// 两位小数
	public static String formatMny2(double dMny) {
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(dMny);
	}

	/**
	 * 格式化字符串，金额字段专用，当f=null时默认返回###,###.000000，并自动去除小数点后的0 其它按格式返回
	 * 
	 * @param dMny
	 * @param f
	 * @return
	 */
	public static String formatMny(double dMny, String f) {
		DecimalFormat df = new DecimalFormat("##0.000000");
		if (f != null) {
			df = new DecimalFormat(f);
		}
		String sMny = null;
		sMny = df.format(dMny);
		return sMny;
	}

	/**
	 * 摘要：数字格式化字符串
	 * 
	 * @说明：如果为0，显示为空
	 * @创建：作者:lj 创建时间：2007-12-13
	 * @param dMny
	 *            格式化数字
	 * @param f
	 *            格式字符串
	 * @param isShowZero
	 *            true-显示0值，false-不显示0值
	 * @return
	 * @修改历史： [序号](lj 2007-12-13)<修改说明>
	 */
	public static String formatMum(double dMny, String f, boolean isShowZero) {
		String sMny = null;

		if (isShowZero == false && dMny == 0) {
			sMny = "";
		} else {
			DecimalFormat df;
			if (f == null) {
				df = new DecimalFormat("##0.000000");
			} else {
				df = new DecimalFormat(f);
			}

			sMny = df.format(dMny);
		}

		return sMny;
	}

	/**
	 * 格式化税率
	 * 
	 * @param sRate
	 * @param nType
	 *            当nType==1时，sRate输入的值应该为17%;当nType!=1时，sRate输入的值应该为0.17;
	 * @return nType 当nType==1时，返回0.17;当nType!=1时，返回17%
	 */
	public static String formatRate(String sRate, int nType) {

		if (nType == 1) {
			Double dRate = Double.valueOf(sRate.substring(0, sRate.length() - 1));

			return new Double(dRate / 100).toString();
		}

		double dRate = new Double(sRate).doubleValue() * 100;

		return formatMny(dRate, "##0.00") + "%";
	}

	/**
	 * 阿拉伯数字转换为中文大写数字
	 * 
	 * @param sNum
	 * @return
	 */
	public static StringBuffer convertNumberToChinese(String sNum) {
		String sNumber = formatMny(sNum, "#####0.00");
		// 查询小数点位置
		int nDecPos = 0;
		for (int i = 0; i < sNumber.length(); i++) {
			if (sNumber.charAt(i) == '.') {
				nDecPos = i;
				break;
			}
		}
		// 整数位
		StringBuffer sbNumber = new StringBuffer();
		// 小数位
		StringBuffer sbDecimal = new StringBuffer();
		// 最终转换结果
		StringBuffer sbChinese = new StringBuffer();
		if (nDecPos > 0) {
			sbNumber.append(sNumber.substring(0, nDecPos)).reverse();
			sbDecimal.append(sNumber.substring(nDecPos + 1));
		} else {
			sbNumber.append(sNumber).reverse();
		}
		int nLen = sbNumber.length();
		for (int i = 0; i < nLen; i++) {
			String strNum = String.valueOf(sbNumber.charAt(i));
			int nNum = Integer.parseInt(strNum);
			int nPos = i % 4;
			int nZero = i / 4;
			if (nZero > 0) {
				if (nPos > 0) {
					if (nNum > 0)
						sbChinese.append(chineseIntBit[nPos]);

					sbChinese.append(chineseNumber[nNum]);
				} else {
					if (nNum > 0)
						sbChinese.append(chineseLongBit[nZero - 1]);
					sbChinese.append(chineseNumber[nNum]);
				}
			} else {
				if (nNum > 0)
					sbChinese.append(chineseIntBit[nPos]);

				if (i <= (nLen - 1) && nNum != 0)
					sbChinese.append(chineseNumber[nNum]);
			}
		}
		sbChinese.reverse();
		if (nDecPos > 0) {
			String decimals = sbDecimal.toString();
			if (decimals.equals("")) {
				sbChinese.append("整");
			} else {
				Double tempdb = Double.parseDouble(decimals);
				if (tempdb == 0) {
					sbChinese.append("整");
				} else {
					for (int i = 0; i < sbDecimal.length(); i++) {
						String strNum = String.valueOf(sbDecimal.charAt(i));
						int nNum = Integer.parseInt(strNum);
						int nPos = i % 4;
						sbChinese.append(chineseNumber[nNum]);
						sbChinese.append(chineseDecBit[nPos]);
					}
				}
			}
		} else {
			sbChinese.append(chineseZero);
		}
		return sbChinese;
	}

	/**
	 * 阿拉伯数字转换为中文大写数字
	 * 
	 * @param dNum
	 * @return
	 */
	public static StringBuffer convertNumberToChinese(double dNum) {
		String sNum = String.valueOf(dNum);

		return convertNumberToChinese(sNum);
	}

	/**
	 * 阿拉伯数字转换为中文大写数字
	 * 
	 * @param nNum
	 * @return
	 */
	public static StringBuffer convertNumberToChinese(long nNum) {
		String sNum = String.valueOf(nNum);

		return convertNumberToChinese(sNum);
	}

	/**
	 * 阿拉伯数字转换为中文大写数字
	 * 
	 * @param nNum
	 * @return
	 */
	public static StringBuffer convertNumberToChinese(int nNum) {
		String sNum = String.valueOf(nNum);

		return convertNumberToChinese(sNum);
	}

	public static String convertCharacter(String ss)
	/*
	 * 处理从页面获得的字符参数乱码问题
	 */
	{
		if (ss != null) {
			try {
				String temp_p = ss;
				byte[] temp_t = temp_p.getBytes("ISO8859-1");
				ss = new String(temp_t);
			} catch (Exception e) {
				System.err.println("toChinese exception:" + e.getMessage());
				System.err.println("The String is:" + ss);
			}
		}
		return ss;
	}

	public static String formatQty1(double dQty) {
		DecimalFormat df = new DecimalFormat("##0.00");

		return df.format(dQty);
	}

	public static long getArrayMaxValue(long[] lCompareArray) {
		if (lCompareArray.length <= 0) {
			return 0;
		} else {
			long lRtn = 0;
			for (int i = 0; i < lCompareArray.length; i++) {
				if (lCompareArray[i] > lRtn) {
					lRtn = lCompareArray[i];
				}
			}
			return lRtn;
		}
	}

	/**
	 * 摘要：
	 * 
	 * @说明：
	 * @创建：作者:whj 创建时间：2007-12-19
	 * @param sParam
	 * @return
	 * @修改历史： [序号](whj 2007-12-19)<修改说明>
	 */
	public static String formatResult(String sParam) {
		if (sParam == null || sParam.equals("0")) {
			return "";
		} else {
			String sResult = "";
			DecimalFormat df = new DecimalFormat("###,###.00");
			sResult = df.format(Double.valueOf(sParam));
			return sResult;
		}
	}

	/**
	 * 摘要：获得指定字符串的长度
	 * 
	 * @说明：
	 * @创建：作者:YQH 创建时间：2008-07-19
	 * @param sStr
	 *            指定字符串
	 * @return
	 * @修改历史： [序号](whj 2007-12-19)<修改说明>
	 */
	public static int valieFiledLength(String sStr) {
		String regex = "^\\w+$";
		String regex1 = "&@!#$%^)(-+=_\\'?><,~`!/ {}";
		int lengthg = 0;
		int j = 0;
		String srr = "";

		for (int i = 0; i < sStr.length(); i++) {
			if (i < sStr.length())
				j = i + 1;
			else
				j = i;
			srr = sStr.substring(i, j);
			if (!srr.matches(regex) && regex1.indexOf(srr) == -1) {
				lengthg = lengthg + 2;
			} else {
				lengthg = lengthg + 1;
			}

		}
		return lengthg;
	}

	public static String getFiledValue(String sStr, int length) {
		String regex = "^\\w+$";
		String regex1 = "&@!#$%^)(-+=_\\'?><,~`!/ {}";
		int lengthg = 0;
		int j = 0;
		String srr = "";
		String sRtn = "";
		for (int i = 0; i < sStr.length(); i++) {
			if (lengthg >= length)
				break;
			if (i < sStr.length())
				j = i + 1;
			else
				j = i;
			srr = sStr.substring(i, j);
			if (!srr.matches(regex) && regex1.indexOf(srr) == -1) {
				lengthg = lengthg + 2;

			} else {
				lengthg = lengthg + 1;

			}
			sRtn = sRtn + srr;

		}
		return sRtn;
	}

	/**
	 * 摘要：密码加密
	 * 
	 * @说明：
	 * @创建：作者:Administrator 创建时间：Apr 9, 2009
	 * @param password
	 * @return
	 * @修改历史： [序号](Administrator Apr 9, 2009)<修改说明>
	 */
	public static String encrypt(String password) {
		String result = null;
		String password1 = "";
		if (password != null) {
			try {
				MessageDigest ca = MessageDigest.getInstance("SHA");
				result = "";
				char pass[] = password.toCharArray();
				for (int i = 0; i < pass.length; i++) {
					password1 = (String) password1 + pass[i] + "&^./&";
				}
				byte mess[] = password1.getBytes();
				ca.reset();
				byte[] hash = ca.digest(mess);
				result = byte2hex(hash);
			} catch (Exception err) {

			}
		}
		return result;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * Transform the specified byte into a Hex String form.
	 * 
	 * @param bcd
	 * @return
	 */
	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);
		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * Transform the specified Hex String into a byte array.
	 * 
	 * @param
	 * @return
	 */
	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	/**
	 * 随机生成n位数字字母组合的str字符
	 * 
	 * @return type=1 数字字母组合。 type=2 数字only.
	 */
	public static String generateRandomString(int n, int type) {
		int length = n;
		if (length < 1)
			throw new IllegalArgumentException();

		char[] randomChar = new char[length];
		int index = 0;

		while (index < length) {
			double rand = Math.random() * 74;
			int num = (int) (rand + '0');

			if (type == 1) {
				if (num >= 48 && num <= 57 || num >= 65 && num <= 90 || num >= 97 && num <= 122) // /ascii
																									// things.
				{
					randomChar[index] = (char) num;
					index++;
				}
			} else if (type == 2) {
				if (num >= 48 && num <= 57) // /numbers.
				{
					randomChar[index] = (char) num;
					index++;
				}
			} else {
				return "";
			}

		}
		return new String(randomChar).toLowerCase();
	}

	// 对象转化成Str
	public static String convertStr(Object obj) {
		if (null == obj) {
			return null;
		} else {
			return obj.toString();
		}
	}

	// 对象转化成Str
	public static String convertStr2(Object obj) {
		if (null == obj) {
			return "";
		} else {
			return obj.toString();
		}
	}

	// 对象转化成int
	public static Integer convertInt(Object obj) {
		if (null == obj) {
			return null;
		} else {
			if (obj.toString().equals("")) {
				return null;
			}
			return Integer.parseInt(obj.toString());
		}
	}

	// 对象转化成Long
	public static Long convertLong(Object obj) {
		if (null == obj) {
			return null;
		} else {
			if (obj.toString().equals("")) {
				return null;
			}
			return Long.parseLong(obj.toString());
		}
	}

	// 对象转化成int
	public static Integer convertDbToInt(Object obj) {
		if (null == obj) {
			return null;
		} else {
			if (obj.toString().equals("")) {
				return null;
			}
			return Double.valueOf(obj.toString()).intValue();
		}
	}

	// 对象转化成Long
	public static Long convertDbToLong(Object obj) {
		if (null == obj) {
			return null;
		} else {
			if (obj.toString().equals("")) {
				return null;
			}
			return Double.valueOf(obj.toString()).longValue();
		}
	}

	// 对象转化成BigDecimal
	public static BigDecimal convertBigDecimal(Object obj) {
		if (null == obj) {
			return null;
		} else {
			if (obj.toString().equals("")) {
				return null;
			}
			return new BigDecimal(obj.toString());
		}
	}

	// 对象转化成Double
	public static Double convertDouble(Object obj) {
		if (null == obj) {
			return null;
		} else {
			if (obj.toString().equals("")) {
				return null;
			}
			return Double.parseDouble(obj.toString());
		}
	}

	// 对象转化成Date
	public static Date convertDate(Object obj, String formate) {
		if (null == obj) {
			return null;
		} else {
			try {
				return DateTimeUtil.getStrToDate(obj.toString(), formate);
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 对象转化成T
//	@SuppressWarnings("unchecked")
//	public static <T> T convertT(Object obj, Class tp) {
//		if (tp == Integer.class) {
//			return (T) convertInt(obj);
//		} else if (tp == Long.class) {
//			return (T) convertLong(obj);
//		} else if (tp == Double.class) {
//			return (T) convertDouble(obj);
//		} else if (tp == String.class) {
//			return (T) convertStr(obj);
//		} else if (tp == Date.class) {
//			return (T) convertDate(obj, "yyyy-MM-dd HH:mm:ss");
//		} else if (tp == Short.class) {
//			if (null == obj || obj.toString().length() == 0) {
//				return null;
//			}
//			Short s = Short.parseShort(obj.toString());
//			return (T) s;
//		} else if (tp == Byte.class) {
//			if (null == obj || obj.toString().length() == 0) {
//				return null;
//			}
//			Byte b = Byte.parseByte(obj.toString());
//			return (T) b;
//		} else {
//			return null;
//		}
//	}

	/**
	 * @说明: 根据个数获取序号
	 * @param count
	 *            值 2
	 * @param bits
	 *            位数 5
	 * @return 00002
	 */
	public static String getCdByCount(String finalCd, String count, int bits) {
		StringBuffer suffix = new StringBuffer(finalCd);
		int len = bits - count.length();
		if (len > 0) {
			for (int i = 0; i < len; i++)
				suffix.append("0");
		}
		suffix.append(count);
		return suffix.toString();
	}
	//对象转化成T
	@SuppressWarnings("unchecked")
	public static <T>T convertT(Object obj,String tp){
		if(tp.equals("Integer")||tp.equals("int")){
			return (T)convertInt(obj);
		}else if(tp.equals("Long")||tp.equals("long")){
			return (T)convertLong(obj);
		}else if(tp.equals("Double")||tp.equals("double")){
			return (T)convertDouble(obj);
		}else if(tp.equals("String")){
			return (T)convertStr(obj);
		}else if(tp.equals("Date")){
			return (T)convertDate(obj,"yyyy-MM-dd HH:mm:ss");
		}else{
			return null;
		}
	}
	/**
	 * 摘要：
	 * 
	 * @说明：格式字符串用于和数据库字段对应
	 * @创建：作者:yxy 创建时间：2013-3-13
	 * @param field
	 * @return
	 * @修改历史：
	 * 
	 */
	public static String convertField(String field) {
		StringBuffer f = new StringBuffer();
		for (int i = 0; i < field.length(); i++) {
			char c = field.charAt(i);
			if (Character.isUpperCase(c)) {
				char temp = Character.toLowerCase(c);
				if (i > 0) {
					f.append("_");
				}
				f.append(temp);
			} else {
				f.append(c);
			}
		}
		return f.toString();
	}

	/**
	 * 
	 * 摘要：
	 * @说明：上传
	 * @创建：作者:yxy 创建时间：2012-9-18
	 * @param source
	 * @param target
	 * @修改历史： [序号](yxy 2012-9-18)<修改说明>
	 * @throws IOException 
	 */
	public static String upLoadFile(MultipartFile source,String basePath,String oldName) throws IOException {
		if(null!=source){
			String fileName = source.getOriginalFilename();
			if(StrUtil.isNull(fileName)){
				return null;
			}
			if(isNull(oldName)){
				String flagType = fileName.substring(fileName.lastIndexOf("."));
				fileName = System.currentTimeMillis()+flagType;
			}else{
				fileName = oldName;
			}
			File file = new File(basePath);
			if(!file.exists()){
				file.mkdirs();
			}
			File target = new File((basePath+"/"+fileName));
			FileCopyUtils.copy(source.getBytes(), target);
			return fileName;
		}
		return null;
	}
	/**
	 * 
	 *摘要：
	 *@说明：移动文件
	 *@创建：作者:yxy		创建时间：2013-4-20
	 *@param path 文件总路径
	 *@param tempName 临时文件名称
	 *@param folderPath 正式文件夹
	 *@param fileName  正式文件
	 *@修改历史：
	 *		[序号](yxy	2013-4-20)<修改说明>
	 */
	public static void renameFile(String path,String tempName,String folderPath,String fileName){
		//原图片
		File f = new File(path+"/"+tempName);
		//正式图片目录
		File folder = new File(folderPath);
		//目录不存在创建
		if(!folder.exists()){
			folder.mkdir();
		}
		//获取正式文件
		File renameFile = new File(folderPath+"/"+fileName);
		//已存在删除
		if(renameFile.exists()){
			renameFile.delete();
		}
		//移动文件
		f.renameTo(renameFile);
	}
	/**
	 * 
	 *摘要：
	 *@说明：MD5加密
	 *@创建：作者:yxy		创建时间：2012-6-19
	 *@param str
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-6-19)<修改说明>
	 */
//	public static String md5Pwd(String str){
//		return encodePwd(str, "MD5");
//	}
//	/**
//	 * 
//	 *摘要：
//	 *@说明：密码加密
//	 *@创建：作者:yxy		创建时间：2012-6-19
//	 *@param str
//	 *@param formate
//	 *@return 
//	 *@修改历史：
//	 *		[序号](yxy	2012-6-19)<修改说明>
//	 */
//	public static String encodePwd(String str, String formate){
//		try {
//			MessageDigest md = MessageDigest.getInstance(formate);
//			md.update(str.getBytes());
//			byte ty[] = md.digest();
//			int i; 
//			StringBuffer buf = new StringBuffer(""); 
//			for (int offset = 0; offset < ty.length; offset++) { 
//				i = ty[offset]; 
//				if(i<0) i+= 256; 
//				if(i<16) 
//				buf.append("0"); 
//				buf.append(Integer.toHexString(i)); 
//			} 
//			//BASE64Encoder e = new BASE64Encoder();
//			//buf = e.encode(ty);
//			return buf.toString().toUpperCase();
//		} catch (Exception e) {
//			return null;
//		}
//	}
	 public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
    }
	/**
	 * 
	 * @说明：获取上传图片的类型
	 * @创建：作者:yxy 创建时间：2012-9-18
	 * @param source
	 * @修改历史： [序号](yxy 2012-9-18)<修改说明>
	 * @throws IOException 
	 */
	public static String returnFileType(MultipartFile source) throws IOException {
		if(null!=source){
			String sourceName = source.getOriginalFilename();
			if(StrUtil.isNull(sourceName)){
				return null;
			}
			return sourceName.substring(sourceName.lastIndexOf("."));
		}
		return null;
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String str){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = str.getBytes();
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	//随机生成六位验证码
	public static String stringyzm(){
		 String s = "";
		 while(s.length()<6){
		  s+=(int)(Math.random()*10);
		 }
		 return s;
	}
	public static void main(String[] args) {
		
	}
    //删除照片
	public static void deletepic(File file,String photo){  
		 if(file.isDirectory()){   
			 File[] files = file.listFiles();   
			 for (File file2 : files) {    
				 deletepic(file2,photo);   
			 }  
		  }else{   
			  String name=file.getName();   
			  if(photo.equals(name)){    
				  file.delete();   
			  }  
			   
		  } 
	}
}
