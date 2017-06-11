package org.ledgerty.common.database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Héctor on 11/06/2017.
 */
public class Query {
    private String queryString;
    Map<String, Object> queryParameters = new HashMap<String, Object>();
    private int pageSize;
    private int pageNumber;

    public Query() {
    }

    public Query(String queryString) {
        this.queryString = queryString;
    }

    public Query(String queryString, int pageSize, int pageNumber) {
        this.queryString = queryString;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    public boolean hasQueryString() {
        return this.queryString != null && !this.queryString.isEmpty();
    }

    public boolean hasPaginationInfo() {
        return this.pageNumber > 0 && this.pageSize > 0;
    }

    public Query addParameter(String parameterName, Object parameterValue) {
        queryParameters.put(parameterName, parameterValue);
        return this;
    }
}
