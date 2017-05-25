package com.util;

import java.util.List;

/**
 * 说明：分页的包装类
 */
public class Page {
	private int total = 0;		                  //总记录数
	private int curPage = 1;					  //当前页
	private int pageSize = 10;					  //每页大小
	private int totalPage = 0;                    //总页数
	private List rows;			                  //显示所有行的信息集合
	private Double tolprice;                      //金额统计
	public int getTotalPage() {
		if(total==0 || pageSize==0){
			totalPage = 0;
		}else{
			int temp = total%pageSize;
			totalPage = total/pageSize;
			if(temp!=0){
				totalPage++;
			}
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Double getTolprice() {
		if(null==tolprice){
			tolprice=0.0;
		}
		return tolprice;
	}
	public void setTolprice(Double tolprice) {
		this.tolprice = tolprice;
	}
	
}

