package com.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.exp.DaoException;
import com.util.Page;
import com.util.StrUtil;
import com.util.TableAnnotation;


/**
 * 
 * Description: JdbcDaoTemplate
 * @Author:  xushh
 * @Version: 1.0
 */
public class JdbcDaoTemplate extends JdbcTemplate{
	/**
	 * 
	 *摘要：
	 *@说明：获取自增id用于MySql
	 *@创建：作者:yxy		创建时间：2012-6-13
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-6-13)<修改说明>
	 */
	public Integer getAutoIdForIntByMySql(){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT LAST_INSERT_ID();");
		try{
			return this.queryForInt(sql.toString());
		}catch(Exception ex){
			throw new DaoException(ex);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：获取自增id用于MySql
	 *@创建：作者:yxy		创建时间：2012-6-13
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-6-13)<修改说明>
	 */
	public Long getAutoIdForLongByMySql(){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT LAST_INSERT_ID();");
		try{
			return this.queryForLong(sql.toString());
		}catch(Exception ex){
			throw new DaoException(ex);
		}
	}
	/**
     * 
     *摘要：
     *@说明：根据查询语句分页查询用于oracle查询
     *@创建：作者:yxy 	创建时间：2012-1-12
     *@param sql
     *@param page
     *@return
     *@throws DBException 
     *@修改历史：
     *		[序号](yxy	2012-1-12)<修改说明>
     */
	public Page queryForPageByMySql(String sql,int page,int limit,Class cls){
		Page p = new Page();
		if(page==0 || limit==0){
			return p;
		}
		try{
			//开始数
			int startNum = ((page==0?1:page)-1)*limit;
			StringBuffer pageSql = new StringBuffer();
			pageSql.append(sql).append(" limit ").append(startNum).append(" , ").append(limit);
			p.setRows(this.queryForLists(pageSql.toString(), cls));
			//去掉sql的order by
			int order_index = sql.toLowerCase().lastIndexOf("order by");
			if(order_index>0){
				sql = sql.substring(0,order_index);
			}
			StringBuffer totalSql = new StringBuffer(" select count(1) from (").append(sql).append(") t");
			//设置总条数
			int total = this.queryForInt(totalSql.toString());
			//设置总条数
			p.setTotal(total);
			p.setPageSize(limit);
			return p;
		}catch(Exception ex){
			throw new DaoException("queryForPage分页查询出错："+ex);
		}
	}
	/**
     * 
     * 摘要：
     * @说明：关闭连接
     * @创建：作者:yxy 	创建时间：2012-1-11.
     * @param conn
     * @param stmt
     * @param rs
     * @throws DaoException
     */
    public void cleanUp(Connection conn,Statement stmt,ResultSet rs)throws DaoException
    {
        try
        {
            if(rs!=null)
            {
                rs.close();
            }
            if(stmt!=null)
            {
                stmt.close();
            }
            releaseConn(conn);
        }catch(Exception e)
        {
            throw new DaoException();
        }
    }
    
    /**
     * 
     * 摘要：
     * @说明：关闭连接
     * @创建：作者:yxy 	创建时间：2012-1-11.
     * @param conn
     * @param stmt
     * @param rs
     * @throws DaoException
     */
    public void cleanUp(Connection conn,PreparedStatement pst,ResultSet rs)throws DaoException
    {
        try
        {
            if(rs!=null)
            {
                rs.close();
            }
            if(pst!=null)
            {
                pst.close();
            }
            releaseConn(conn);
            
        }catch(Exception e)
        {
            throw new DaoException();
        }
    }
    
    /**
     * 
     *摘要：
     *@说明：释放连接
     *@创建：作者:yxy 	创建时间：2012-1-11
     *@param conn 
     *@修改历史：
     *		[序号](yxy	2012-1-11)<修改说明>
     */
    public void releaseConn(Connection conn){
        DataSourceUtils.releaseConnection(conn, getDataSource());
    }

    /**
     * 查询数据并封装到POJO
     * @author Lins
     * @param sql 数据库执行语句
     * @param clz POJO
     * @param params 查询条件（查询参数）
     * @param <T> 默认POJO
     * @return List<T>
     */
    public <T> List<T> queryForLists(String sql,Class clz,Object...params){
        List<T> list;
        Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
        try {
			conn = DataSourceUtils.getConnection(getDataSource());
			stmt = conn.prepareStatement(sql);
            if(params!=null){
                for(int d=0;d<params.length;d++){
                    stmt.setString(d+1,params[d].toString());
                }
            }
            list= RowMapper(stmt.executeQuery(), clz);
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			 cleanUp(conn, stmt, rs);
		}
        return list;
    }
    /**
     * 映射结果集到
     * @author Lins
     * @param resultSet
     * @param elementType
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> RowMapper(ResultSet resultSet,Class elementType) throws SQLException {
        List<T> list=null;
        Object obj=null;
        try{
            Method[] methods=elementType.getMethods();
            Map<String,String> map=new HashMap<String, String>();
            //遍历结果集并存储<列名，值>
            list=new ArrayList<T>();
            while (resultSet.next()){
                obj=elementType.newInstance();
                for(int c=1; c<=resultSet.getMetaData().getColumnCount();c++){
                    String str=resultSet.getString(c);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    map.put(metaData.getColumnLabel(c).replace("_","").toUpperCase(),str == null?"":str.trim());
                }
                for(Method method:methods){
                    if(method.getName().startsWith("set")){
                    	String pType = method.getParameterTypes()[0].getSimpleName();
                        String field=method.getName().substring(3);
                        if(map.containsKey(field.toUpperCase())){
                            method.invoke(obj,StrUtil.convertT(map.get(field.toUpperCase()), pType));
                        }
                    }
                }
                list.add((T)obj);
            }
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }
        return list;
    }
    /**
	 *@说明：根据查询语句返回一个Map对象(用于查询一条记录)
	 *@创建：作者:yxy 	创建时间：2012-1-11
	 * @param sql
	 * @param page
	 * @throws DaoException 
	 *@修改历史：
	 *		[序号](yxy	2012-1-11)<修改说明>
	 */
	@SuppressWarnings("unchecked")
	public <T>T queryForObj(String sql,Class cls,Object...params) throws DaoException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			stmt = conn.prepareStatement(sql.toUpperCase());
			if(params!=null){
                for(int d=0;d<params.length;d++){
                    stmt.setString(d+1,params[d].toString());
                }
            }
			rs = stmt.executeQuery();
			return (T)resultSetToObj(rs,cls);
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			 cleanUp(conn, stmt, rs);
		}
	}
	/**
	 *@说明：根据查询语句返回一个Map对象(用于查询一条记录)
	 *@创建：作者:yxy 	创建时间：2012-1-11
	 * @param sql
	 * @param page
	 * @throws DBException 
	 *@修改历史：
	 *		[序号](yxy	2012-1-11)<修改说明>
	 */
	public Map<String,Object> queryForMap(String sql){
		Map<String,Object> map = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			stmt = conn.createStatement();
			if (sql == null || sql.trim().equals(""))
				return null;
			rs = stmt.executeQuery(sql);
			map = resultSetToMap(rs);
			return map;
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			 cleanUp(conn, stmt, rs);
		}
	}
	
	/**
	 *@说明：根据查询语句返回一个List对象(用于查询所有记录)
	 *@创建：作者:yxy 	创建时间：2012-1-11
	 * @param sql
	 * @param page
	 * @throws DBException 
	 *@修改历史：
	 *		[序号](yxy	2012-1-11)<修改说明>
	 */
	public List<Map<String,Object>> queryForList(String sql){
		Map<String,Object> map = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		List<Map<String,Object>> list=null;
		ResultSetMetaData metaData = null;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			stmt = conn.createStatement();
			if (sql == null || sql.trim().equals(""))
				return null;
			rs = stmt.executeQuery(sql);
			//遍历结果集并存储<列名，值>
            list=new ArrayList<Map<String,Object>>();
            while (rs != null && rs.next()){
            	metaData = rs.getMetaData();
            	int columnCount = metaData.getColumnCount();
            	map = new HashMap<String, Object>();
            	for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i).toLowerCase();
					Object columnValue = mapResultSet(rs, i, metaData.getColumnType(i)); 
					map.put(columnName, columnValue);
				}
            	list.add(map);
            }
            return list;
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			 cleanUp(conn, stmt, rs);
		}
	}
	
	
	/**
	 *@说明：把查询返回的结果封装为Map
	 *@创建：作者:yxy 	创建时间：2012-1-11
	 * @param rs
	 * @throws RuntimeException 
	 *@修改历史：
	 *		[序号](yxy	2012-1-11)<修改说明>
	 */
	private Map<String,Object> resultSetToMap(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		ResultSetMetaData metaData = null;
		Map<String,Object> map = null;
		try {
			metaData = rs.getMetaData();
			map = new HashMap<String, Object>();
			int columnCount = metaData.getColumnCount();
			if(rs != null && rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i).toLowerCase();
					Object columnValue = mapResultSet(rs, i, metaData.getColumnType(i)); 
					map.put(columnName, columnValue);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			metaData = null;
		}
		return map;
	}
	/**
	 *@说明：进行数据库类型转化
	 *@创建：作者:yxy 	创建时间：2012-1-11
	 * @param rs
	 * @throws RuntimeException 
	 *@修改历史：
	 *		[序号](yxy	2012-1-11)<修改说明>
	 */
	private Object mapResultSet(ResultSet rs, int columnPosition,int SQLType) {
		try {
			if(SQLType==Types.INTEGER){
                return rs.getInt(columnPosition);
            }else if(SQLType==Types.VARCHAR){
                return rs.getString(columnPosition);
            }else if(SQLType==Types.CHAR){
                return rs.getString(columnPosition);
            }else if(SQLType==Types.DATE){
                return rs.getDate(columnPosition);
            }else if(SQLType==Types.DECIMAL){
            	Double db = rs.getDouble(columnPosition);
            	if(null!=db){
            		DecimalFormat df = new DecimalFormat("###.##");
            		return Double.parseDouble(df.format(db));
            	}
                return null; 
            }else if(SQLType==Types.NUMERIC){
            	Double d = rs.getDouble(columnPosition);
            	if(null!=d){
            		Integer i = d.intValue();
            		if((d-i)==0){
            			return rs.getInt(columnPosition);
            		}
            	}
            	return d;
            }else if(SQLType==Types.DOUBLE){
            	Double db = rs.getDouble(columnPosition);
            	if(null!=db){
            		DecimalFormat df = new DecimalFormat("###.##");
            		return Double.parseDouble(df.format(db));
            	}
                return null; 
            }else if(SQLType==Types.FLOAT){
                return rs.getFloat(columnPosition);
            }else if(SQLType==Types.REAL){
            	return rs.getFloat(columnPosition);
            }else if(SQLType==Types.TIMESTAMP){
                return rs.getTimestamp(columnPosition);
            }else if(SQLType==-5){
            	 return rs.getString(columnPosition);
            }else if(SQLType==-6){
            	 return rs.getInt(columnPosition);
            }else if(SQLType==5){
            	 return rs.getInt(columnPosition);
            }else {
				throw new RuntimeException("数据库结果集数据类型转化失败.");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：把查询返回的结果封装为Object
	 *@创建：作者:yxy		创建时间：2012-4-5
	 *@param rs 结果集
	 *@param elementType 对应的类
	 *@return
	 *@throws SQLException 
	 *@修改历史：
	 *		[序号](yxy	2012-4-5)<修改说明>
	 */
	@SuppressWarnings("unchecked")
	private <T>T resultSetToObj(ResultSet rs,Class elementType) throws SQLException{
		if (rs == null) {
			return null;
		}
		ResultSetMetaData metaData = null;
		try {
			Method[] methods=elementType.getMethods();
            Map<String,Object> tempMap=new HashMap<String, Object>();
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			if(rs != null && rs.next()) {
				Object obj=elementType.newInstance();
				for (int i = 1; i <= columnCount; i++) {
					String str=rs.getString(i);
					tempMap.put(metaData.getColumnLabel(i).replace("_","").toUpperCase(),str == null?"":str.trim());
				}
				for(Method method:methods){
                    if(method.getName().startsWith("set")){
                    	String pType = method.getParameterTypes()[0].getSimpleName();
                        String field=method.getName().substring(3);
                        if(tempMap.containsKey(field.toUpperCase())){
                            method.invoke(obj,StrUtil.convertT(tempMap.get(field.toUpperCase()), pType));
                        }
                    }
                }
				return (T)obj;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：批量删除用于int的数组
	 *@创建：作者:yxy		创建时间：2012-7-12
	 *@param sql
	 *@param its
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-7-12)<修改说明>
	 */
	public int[] deletes(String sql,final Integer... its){
		try{
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter(){
				public int getBatchSize() {
					return its.length;
				};
				public void setValues(PreparedStatement pre, int num) throws SQLException {
					pre.setInt(1, its[num]);
				};
			};
			return this.batchUpdate(sql.toUpperCase(),setter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：批量删除用于int的数组
	 *@创建：作者:yxy		创建时间：2012-7-12
	 *@param sql
	 *@param its
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-7-12)<修改说明>
	 */
	public int[] deletes(String sql,final Long... its){
		try{
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter(){
				public int getBatchSize() {
					return its.length;
				};
				public void setValues(PreparedStatement pre, int num) throws SQLException {
					pre.setLong(1, its[num]);
				};
			};
			return this.batchUpdate(sql.toUpperCase(),setter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：批量删除用于string的数组
	 *@创建：作者:yxy		创建时间：2012-7-12
	 *@param sql
	 *@param its
	 *@return 
	 *@修改历史：
	 *		[序号](yxy	2012-7-12)<修改说明>
	 */
	public int[] deletes(String sql,final String... its){
		try{
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter(){
				public int getBatchSize() {
					return its.length;
				};
				public void setValues(PreparedStatement pre, int num) throws SQLException {
					pre.setString(1, its[num]);
				};
			};
			return this.batchUpdate(sql.toUpperCase(),setter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据对象添加到指定表
	 *@创建：作者:yxy		创建时间：2013-4-7
	 *@param tableName
	 *@param obj
	 *@return
	 *@修改历史：
	 *
	 */
	public int addByObject(String tableName,Object obj){
		Method[] methods=obj.getClass().getMethods();
		StringBuilder condition = new StringBuilder();//条件存在
		List<Object> objs = new ArrayList<Object>();  //条件对应值存放
		StringBuilder sql = new StringBuilder(" insert into ");
		sql.append(tableName).append("(");
		String sp="";
		try {
			for(Method method:methods){
	            if(method.getName().startsWith("get")){
	                String field=method.getName().substring(3);
	                String lowerField = field.toLowerCase();
	                if(lowerField.equals("class")){
	                	continue;
	                }
	                TableAnnotation annotation = method.getAnnotation(TableAnnotation.class);
	                if(null!=annotation && !annotation.insertAble()){
	                	continue;
	                }
	                Object o = method.invoke(obj);
	                if(null!=o){
	                	lowerField = StrUtil.convertField(field);
	                	sql.append(sp).append(lowerField);
	                	condition.append(sp).append("?");
	                	objs.add(o);
	    				sp=",";
	                }
	            }
	        }
			sql.append(") values(").append(condition).append(") ");
			return this.update(sql.toString().toUpperCase(),objs.toArray());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	/**
	 * 
	 *摘要：
	 *@说明：根据对象修改指定表
	 *@创建：作者:yxy		创建时间：2013-4-7
	 *@param tableName
	 *@param obj
	 *@return
	 *@修改历史：
	 *
	 */
	public int updateByObject(String tableName,Object obj,Map<String,Object> whereParam,String autoId){
		Method[] methods=obj.getClass().getMethods();
		List<Object> objs = new ArrayList<Object>();  //条件对应值存放
		StringBuilder sql = new StringBuilder(" update ");
		sql.append(tableName).append(" set ");
		String sp="";
		try {
			for(Method method:methods){
	            if(method.getName().startsWith("get")){
	                String field=method.getName().substring(3);
	                String lowerField = field.toLowerCase();
	                if(lowerField.equals("class")){
	                	continue;
	                }
	                if(!StrUtil.isNull(autoId)){
	                	if(lowerField.equals(autoId.toLowerCase())){
		                	continue;
		                }
	                }
	                Object o = method.invoke(obj);
	                lowerField = StrUtil.convertField(field);
	                TableAnnotation annotation = method.getAnnotation(TableAnnotation.class);
	                if(null!=annotation && null==o && !annotation.nullToUpdate()){
	                	continue;
	                }
	                if(null==annotation || annotation.updateAble() ){
                		sql.append(sp).append(lowerField).append(" = ?");
	                	objs.add(o);
	    				sp=",";
                	}
	            }
	        }
			if(  whereParam!=null &&  whereParam.size()>0 ){
				Iterator<String> fields = whereParam.keySet().iterator();
				sp=" where ";
				while(fields.hasNext()){
					String field = fields.next();
					sql.append(sp).append(field).append(" = ?");
					sp=" and ";
					objs.add(whereParam.get(field));
				}
			}
			return this.update(sql.toString().toUpperCase(),objs.toArray());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
}
