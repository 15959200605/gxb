package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 说明：时间工具类
 */
public class DateTimeUtil {
	private static Calendar calendar = GregorianCalendar.getInstance(Locale.CHINA);
	
	/**
	 * @说明 得到格式为 formate 的日期Str
	 * @param date 日期
	 * @param formate 转化格式
	 * @return String 
	 */
	public static String getDateToStr(Date date,String formate)throws Exception{
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
			return simpleDateFormat.format(date);
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	/**
	 * @说明 得到格式为 formate 的日期Str
	 * @param date 日期
	 * @param formate 转化格式
	 * @return String 
	 */
	public static String getDateToStr(String dateStr,String formate1,String formate2)throws Exception{
		try{
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(formate1, java.util.Locale.CHINA);
			Date date = simpleDateFormat1.parse(dateStr);
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(formate2, java.util.Locale.CHINA);
			return simpleDateFormat2.format(date);
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	/**
	 * @说明 得到格式为 formate 的日期
	 * @param str
	 * @param formate 转化格式
	 * @return Date
	 */
	public static Date getStrToDate(String str,String formate)throws Exception{
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
			return simpleDateFormat.parse(str);
		}catch(ParseException pe){
			throw new Exception(pe);
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	/**
	 * @说明 获取系统时间年份
	 * @return int
	 */
	public static int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @说明 根据指定的时间返回年份
	 * @param date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * @说明 返回系统时间月份
	 * @return int
	 */
	public static int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * @说明 根据指定的时间返回月份
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	/**
	 * @说明 返回当前系统时间的日期(天)
	 * @return int
	 */
	public static int getDay() {
		return calendar.get(Calendar.DATE);
	}

	/**
	 * @说明 根据指定的时间返回日期(天)
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		c.setTime(date);
		return c.get(Calendar.DATE);
	}
	/**
	 * @说明：获取月份有多少天
	 * @创建：作者:yxy	创建时间：2011-5-14
	 * @return
	 */
	public static int getDays(int year,int month){
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.YEAR,year); 
		cal.set(Calendar.MONTH, (month-1));//Java月份才0开始算 
		return cal.getActualMaximum(Calendar.DATE);
	}
	/**
	 * @说明：获取月份有多少天
	 * @创建：作者:yxy	创建时间：2011-5-14
	 * @return
	 */
	public static int getDays(String tgMonth){
		Calendar cal = Calendar.getInstance(); 
		int year=Integer.valueOf(tgMonth.substring(0, 4));
		int month=Integer.valueOf(tgMonth.substring(5,7));
		cal.set(Calendar.YEAR,year); 
		cal.set(Calendar.MONTH, (month-1));//Java月份才0开始算 
		return cal.getActualMaximum(Calendar.DATE);
	}
	/**
	 * @说明 返回自定义时间
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDateTime(int year, int month, int day) {
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		c.set(year, month, day);
		return c.getTime();
	}
	/**
	 * @说明 日期加减
	 * @param type 5--天  2--月 1--年
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dateTimeAdd(Date date,int type,int amount) {
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		c.setTime(date);
		c.add(type, amount);
		return c.getTime();
	}
	/**
	 * @说明 日期加减
	 * @param type 5--天  2--月 1--年
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date dateTimeAdd(String str,int type,int amount,String formate)throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		try {
			c.setTime(simpleDateFormat.parse(str));
			c.add(type, amount);
			return c.getTime();
		} catch (ParseException e) {
			throw new Exception(e);
		}
	}
	/**
	 * @说明 日期加减
	 * @param type 5--天  2--月 1--年
	 * @param amount
	 * @param day
	 * @return
	 */
	public static String dateTimeAddToStr(String str,int type,int amount,String formate)throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		try {
			c.setTime(simpleDateFormat.parse(str));
			c.add(type, amount);
			return simpleDateFormat.format(c.getTime());
		} catch (ParseException e) {
			throw new Exception(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据日期获取当前时间是周几
	 *@创建：作者:yxy 	创建时间：2011-8-18
	 *@param dte
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2011-8-18)<修改说明>
	 */
	public static Integer getWeekByDate(Date dte){
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		c.setTime(dte);
		return c.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据日期获取当前时间是周几
	 *@创建：作者:yxy 	创建时间：2011-8-18
	 *@param str
	 *@param formate
	 *@return
	 *@throws Exception 
	 *@修改历史：
	 *		[序号](yxy	2011-8-18)<修改说明>
	 */
	public static Integer getWeekByStr(String str,String formate){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
		Calendar c = GregorianCalendar.getInstance(Locale.CHINA);
		try {
			c.setTime(simpleDateFormat.parse(str));
			return c.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据两个日期字符串获取之间相差多少天
	 *@创建：作者:yxy 	创建时间：2011-8-18
	 *@param str
	 *@param formate
	 *@return
	 *@throws Exception 
	 *@修改历史：
	 *		[序号](yxy	2011-8-18)<修改说明>
	 */
	public static Integer getDaysDiff(String str1,String str2,String formate){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
		try {
			long d1 = simpleDateFormat.parse(str1).getTime();
			long d2 = simpleDateFormat.parse(str2).getTime();
			long t = (d2-d1)/1000;
			Long l = t/(3600*24);
			return l.intValue()+1;
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据两个日期字符串获取之间相差多少天
	 *@创建：作者:yxy 	创建时间：2011-8-18
	 *@param str
	 *@param formate
	 *@return
	 *@throws Exception 
	 *@修改历史：
	 *		[序号](yxy	2011-8-18)<修改说明>
	 */
	public static Integer getDaysDiff(Date dt1,Date dt2){
		try {
			long d1 = dt1.getTime();
			long d2 = dt2.getTime();
			long t = (d2-d1)/1000;
			Long l = t/(3600*24);
			return l.intValue()+1;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据时间字符串获取与当前相差多少
	 *@创建：作者:yxy 	创建时间：2011-8-18
	 *@param str
	 *@param formate
	 *@return
	 *@throws Exception 
	 *@修改历史：
	 *		[序号](yxy	2011-8-18)<修改说明>
	 */
	@SuppressWarnings("deprecation")
	public static String getDaysDiff(String str,String formate){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, java.util.Locale.CHINA);
		try {
			Date d1 = simpleDateFormat.parse(str);
			Date d2 = new Date();
			int year1 = d1.getYear();
			int year2 = d2.getYear();
			int month1 = d1.getMonth();
			int month2 = d2.getMonth();
			int day1 = d1.getDate();
			int day2 = d2.getDate();
			int hours1 = d1.getHours();
			int hours2 = d2.getHours();
			if((year1==year2&&month1==month2&&day1==day2&&hours1==hours2)){
				int temp = (d2.getMinutes()-d1.getMinutes());
				return (temp<=0?1:temp)+"分钟前";
			}else if((year1==year2&&month1==month2&&day1==day2&&(hours1+1)==hours2&&d1.getMinutes()>30&&d2.getMinutes()<30)){
				int temp = 60-d1.getMinutes()+d2.getMinutes();
				return temp+"分钟前";
			}else if(year1==year2&&month1==month2&&day1==day2){
				return (hours2-hours1)+"小时前";
			}else if((year1==year2&&month1==month2) || (year1==year2&&(month1+1)==month2)){
				long time1 = d1.getTime();
				long time2 = d2.getTime();
				long t = (time2-time1)/1000;
				Long l = t/(3600*24);
				return (l.intValue()+1)+"天前";
			}else if(year1==year2){
				return (month2-month1)+"个月前";
			}else{
				return (year2-year1)+"年前";
			}
		} catch (ParseException e) {
			return "";
		}
	}
    //获得一周前的日期
	public static String lastWeek(){
		Date date = new Date();
		int year=Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
	    int month=Integer.parseInt(new SimpleDateFormat("MM").format(date));
	    int day=Integer.parseInt(new SimpleDateFormat("dd").format(date))-6;
	  
	    if(day<1){
	     month-=1;
	     if(month==0){
	      year-=1;month=12;
	     }
	     if(month==4||month==6||month==9||month==11){
	      day=30+day;
	     }else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
	     {
	      day=31+day;
	     }else if(month==2){
	      if(year==0||(year %4==0&&year!=0))day=29+day;
	      else day=28+day;
	     }    
	    }
	    String y = year+"";String m ="";String d ="";
	    if(month<10) m = "0"+month;
	    else m=month+"";
	    if(day<10) d = "0"+day;
	    else d = day+"";
	    return y+"-"+m+"-"+d;
	}
    //获得几个月前的日期
	public static String lastMonth(int allMonth) {
          Date date = new Date();
             int year=Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
             int month=Integer.parseInt(new SimpleDateFormat("MM").format(date))-allMonth;
             int day=Integer.parseInt(new SimpleDateFormat("dd").format(date));
             if(month <= 0){
                int yearFlag = (month*(-1))/12 + 1;
                int monthFlag = (month *(-1))%12;
                year -= yearFlag;
                month=monthFlag*(-1) +12;
            }
            else if(day>28){
               if(month==2){
                    if(year%400==0||(year %4==0&&year%100!=0)){
                        day=29;
                    }else day=28;
                }else if((month==4||month==6||month==9||month==11)&&day==31){
                    day=30;
                }
            }
            String y = year+"";String m ="";String d ="";
            if(month<10) m = "0"+month;
           else m=month+"";
           if(day<10) d = "0"+day;
            else d = day+"";
           return y+"-"+m+"-"+d;
     }
	 //获得一年前的日期
	 public static String lastYear(){
	    Date date = new Date();
	    int year=Integer.parseInt(new SimpleDateFormat("yyyy").format(date))-1;
	    int month=Integer.parseInt(new SimpleDateFormat("MM").format(date));
	    int day=Integer.parseInt(new SimpleDateFormat("dd").format(date));
	    if(month==0){
	     year-=1;month=12;
	    }
	    else if(day>28){
	     if(month==2){
	      if(year==0||(year %4==0&&year!=0)){
	       day=29;
	      }else day=28;
	     }
	    }
	    String y = year+"";String m ="";String d ="";
	    if(month<10) m = "0"+month;
	    else m=month+"";
	    if(day<10) d = "0"+day;
	    else d = day+"";
	    return y+"-"+m+"-"+d;
	 }
	 
	/**
	 * 得到几天前的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	
	/**
	 * 得到几天后的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
}
