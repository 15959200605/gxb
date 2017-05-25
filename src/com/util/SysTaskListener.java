package com.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.util.TaskSyncUtil;


/**
 * 任务定时器
 * @author Administrator
 *
 */
public class SysTaskListener implements ServletContextListener{
    
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		
		new Timer().scheduleAtFixedRate(new TimerTask(){
		   	public void run(){
		   		TaskSyncUtil dataSyncUtil = TaskSyncUtil.getInstance();
				dataSyncUtil.sync();
			}
		},new Date(), 60*60*1000);
	}

}
