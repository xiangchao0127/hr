package com.handge.hr.common.utils;

import java.util.List;

/**
 * 分页
 * Created by DaLu Guo on 2018/5/17.
 */
public class PageResults<T> {
    /**
     * 下一页页码
     */
    private int nextPageNo;

    /**
     * 当前页页码
     */
    private int currentPage;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 记录
     */
    private List<T> results;

    public PageResults() {
    }

    public PageResults(int nextPageNo, int currentPage, int pageSize, int totalCount, int pageCount, List<T> results) {
        this.nextPageNo = nextPageNo;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.results = results;
    }

    public PageResults(int nextPageNo, int currentPage, int pageSize, Long totalCount, int pageCount, List<T> results) {
        this.nextPageNo = nextPageNo;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount.intValue();
        this.pageCount = pageCount;
        this.results = results;
    }

    public int getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PageResults{" +
                "nextPageNo=" + nextPageNo +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", pageCount=" + pageCount +
                ", results=" + results +
                '}';
    }
}
