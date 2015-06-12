package com.rimi.ctibet.common.util;

import java.util.ArrayList;
import java.util.List;

public class Pager {
	private int pageSize = 20;
	private int totalCount;
	private int totalPage;
	private int currentPage = 1;
	private List dataList;

	public Pager() {
	}

	//
	public Pager(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public Pager(int pageSize) {
		this.pageSize = pageSize;
	}

	/********************************************
	 * Setter Getter
	 ********************************************/
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		return pageSize * (currentPage - 1);
	}

	public int getEndIndex() {
		return pageSize * currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		// //System.out.println("totalPage"+totalPage);
		// //System.out.println("totalCount"+totalCount);
		return (totalPage > 0 && totalCount == 0) ? totalPage
				: totalCount == 0 ? 1 : (int) Math.ceil(new Double(totalCount)
						/ pageSize);

	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List getDataList() {
		if (dataList == null) {
			dataList = new ArrayList();
		}

		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
		totalPage = getTotalPage();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
