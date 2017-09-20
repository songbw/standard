package com.jiannei.bean;

public class PageBean {
	private Object list =null;
	private int total = 0;
	private int pages=0;
	private int pageNo=0;
	private int pageSize=0;

	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static Integer getPages(Integer total, Integer pageSize){
		Integer a = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		return a;
	}
	public static Integer getOffset(Integer pageNo,Integer pageSize){
		Integer a = (pageNo - 1) * pageSize;
		return a;
	}
	
}
