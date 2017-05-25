package com.gxb.manager.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.exp.ServiceException;
import com.gxb.manager.dao.SysFeedbackDao;
import com.gxb.model.SysFeedback;
import com.util.Page;

@Service
public class SysFeedbackService {

	@Resource
	private SysFeedbackDao sysFeedbackDao;
	
	/**
	 * 
	  *摘要：
	  *@说明：分页查询意见
	  *@创建：作者:dzr		创建时间：2015-9-29
	  *@修改历史：
	  *		[序号]()<修改说明>
	 */
	 public Page queryFeedback(SysFeedback feedback,Integer page,Integer limit){
			try{
				return this.sysFeedbackDao.queryFeedback(feedback, page,limit);
			}catch(Exception ex){
				throw new ServiceException(ex);
			}
	 }
	 /**
		 * 
		  *摘要：
		  *@说明：批量删除意见
		  *@创建：作者:dzr		创建时间：2015-9-29
		  *@修改历史：
		  *		[序号]()<修改说明>
		 */
	 public void deleteFeedback(final Integer... ids) {
			try{
				this.sysFeedbackDao.deleteFeedback(ids);
			}catch(Exception ex){
				throw new ServiceException(ex);
			}
	}
}
