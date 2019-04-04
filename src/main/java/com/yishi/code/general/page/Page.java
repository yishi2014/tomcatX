package com.yishi.code.general.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Lustin on 2017/11/21.
 */
public class Page {
    private Integer pageSize;
    private Integer current;
    private Long total;
    @JsonIgnore
    private String order;
    @JsonIgnore
    private String orderby;
    @JsonIgnore
    private String countHql;
    @JsonIgnore
    private String hql;


    public Page() {
    }

    public Page(Integer pageSize, Integer current, Long total, String countHql, String hql) {
        this.pageSize = pageSize;
        this.current = current;
        this.total = total;
        this.countHql = countHql;
        this.hql = hql;
    }

    public Integer getPageSize() {
        if(this.pageSize==null)
            return 30;
        return pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public void setPageSize(Integer pageSize) {

        this.pageSize = pageSize;
    }

    public Integer getCurrent() {
        if(this.current==null)
            return 1;
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String gethql(String hql) {
        if(this.orderby!=null&&!"".equals(this.orderby.trim())){
            hql+= ((" order by ")+this.orderby);
            if(this.order!=null)
                hql+=order;
        }
        return hql;
    }

    public String getcountHql() {
        return countHql;
    }

    public String getCountHql() {
        return countHql;
    }

    public void setCountHql(String countHql) {
        this.countHql = countHql;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }
}
