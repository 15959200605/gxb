package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.util.StrUtil;


public class TaskSyncUtil {

	private static TaskSyncUtil syncUtil;
	public static ApplicationContext applicationContext;
	//public static BasicExpenseService basicExpenseService;
	//public static SysUserService sysUserService;
	
	public static TaskSyncUtil getInstance(){
		if(syncUtil==null){
			syncUtil = new TaskSyncUtil();
		}
		return syncUtil;
	}
	
	public void sync(){
//		if (null == applicationContext) {
//			applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		}
//		if (null == basicExpenseService) {
//			basicExpenseService = (BasicExpenseService) applicationContext.getBean("basicExpenseService");
//		}
//		if (null == sysUserService) {
//			sysUserService = (SysUserService) applicationContext.getBean("sysUserService");
//		}
//		String dqtime1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		String dqtime2=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//		String dqtime3=new SimpleDateFormat("MM-dd").format(new Date());
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		//int month = cal.get(Calendar.MONTH) + 1;
//		int count=basicExpenseService.queryStatisticBycount(dqtime2);
//		if(count<1){
//			List<BasicExpense> list=null;
//			if(dqtime3.equals("01-01")){
//				basicExpenseService.addStatisticjl(dqtime2, dqtime1);
//				list=basicExpenseService.queryexpenseBymLs(year-1+"-10-01", year-1+"-12-31");
//			}
//			if(dqtime3.equals("04-01")){
//				basicExpenseService.addStatisticjl(dqtime2, dqtime1);
//				list=basicExpenseService.queryexpenseBymLs(year+"-01-01", year+"-03-31");
//			}
//			if(dqtime3.equals("07-01")){
//				basicExpenseService.addStatisticjl(dqtime2, dqtime1);
//				list=basicExpenseService.queryexpenseBymLs(year+"-04-01", year+"-06-30");
//			}
//			if(dqtime3.equals("10-01")){
//				basicExpenseService.addStatisticjl(dqtime2, dqtime1);
//				list=basicExpenseService.queryexpenseBymLs(year+"-07-01", year+"-09-30");
//			}
//			if(list!=null){
//				for(int i=0;i<list.size();i++){
//					SysUser usr=sysUserService.queryUserById(list.get(i).getUsrId());
//					double etotals=list.get(i).getEtotals();
//					double syb=0.00;
//					if(etotals<1000){
//						syb=usr.getGoldPiece()*(0/100);
//					}
//					if(etotals>=1000&&etotals<3000){
//						syb=usr.getGoldPiece()*(1/100);
//					}
//					if(etotals>=3000&&etotals<7000){
//						syb=usr.getGoldPiece()*(1.5/100);
//					}
//					if(etotals>=7000&&etotals<15000){
//						syb=usr.getGoldPiece()*(2/100);
//					}
//					if(etotals>=15000&&etotals<30000){
//						syb=usr.getGoldPiece()*(2.5/100);
//					}
//					if(etotals>=30000){
//						syb=usr.getGoldPiece()*(3/100);
//					}
//					sysUserService.updateUserSyGoldPiece(usr.getSygoldPiece()+Integer.parseInt(new java.text.DecimalFormat("0").format(syb)), list.get(i).getUsrId());
//				}
//			  System.out.println("统计成功："+dqtime1);
//			}
//		}
	}
	
}
