package com.gxb.manager.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.dao.JdbcDaoTemplate;
import com.exp.DaoException;
import com.gxb.model.SysFeedback;
import com.util.Page;
import com.util.StrUtil;

@Repository
public class SysFeedbackDao {

	@Resource(name="daoTemplate")
	private JdbcDaoTemplate daoTemplate;
	
	/**
	 * 
	  *摘要：
	  *@说明：分页查询意见
	  *@创建：作者:dzr		创建时间：2015-9-29
	  *@修改历史：
	  *		[序号]()<修改说明>
	 */
	public Page queryFeedback(SysFeedback feedback, int page,int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT fb.*,m.member_no from sys_feedback fb LEFT JOIN sys_member m ON fb.member_id=m.member_id where 1=1 ");
		if(!StrUtil.isNull(feedback)){
		if(!StrUtil.isNull(feedback.getMemberNo())){
			sql.append(" and m.member_no like '%").append(feedback.getMemberNo()).append("%'");
		}
		}
		sql.append(" order by fb.fb_id desc ");
		try {
			return daoTemplate.queryForPageByMySql(sql.toString(), page,limit,SysFeedback.class);
		} catch (Exception ex) {
			throw new DaoException(ex);
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
	public int[] deleteFeedback(final Integer... ids) {
		String sql = "delete from sys_feedback where fb_id=? ";
		try {
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return ids.length;
				}

				public void setValues(PreparedStatement pre, int num)
						throws SQLException {
					pre.setInt(1, ids[num]);
				}
			};
			return daoTemplate.batchUpdate(sql.toUpperCase(), setter);
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
}
