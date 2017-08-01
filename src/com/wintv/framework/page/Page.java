package com.wintv.framework.page;

import java.util.ArrayList;

public class Page {
	private int start;

    private int pageSize = 10;

    private Object data;
 
    private int totalCount;

    public Page() {
        this(0, 0, 10, new ArrayList());
    }

    public Page(int start, int totalSize, int pageSize, Object data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalSize;
        this.data = data;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public int getTotalPageCount() {
    	if(totalCount % pageSize == 0) 
    		return totalCount / pageSize; 
    	else 
    		return totalCount / pageSize + 1 ; 
    }

    public int getPageSize() {
        return pageSize;
    }

    public Object getResult() {
        return data;
    }


    public int getCurrentPageNo() {
        return (start / pageSize) + 1;
    }


    public boolean hasNextPage() {
        return (this.getCurrentPageNo() < this.getTotalPageCount() - 1);
    }

    public boolean hasPreviousPage() {
        return (this.getCurrentPageNo() > 1);
    }


    public static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, 10);
    }


    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
}
