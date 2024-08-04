package com.tithe_system.tithe_management_system.utils.requests;

import java.io.Serializable;

public class DataTableRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int page;
    private int size;
    private String echo;
    private String searchValue;

    public DataTableRequest() {
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "DataTableRequest{" + "page=" + page + ", size=" + size + ", echo='" + echo + '\'' + ", searchValue='"
                + searchValue + '\'' + '}';
    }
}
