package com.yishi.code.general.dao;

import com.yishi.code.general.model.CodeDatasource;

import java.util.List;
import java.util.Map;

public class BaseDao {
    public List selectByHql(String sql) {
        return null;
    }

    public void update(CodeDatasource cd) {
    }

    public Object load(Class<CodeDatasource> codeDatasourceClass, Integer id) {
        return null;
    }

    public Object save(Object t) {return null;
    }

    public Long getCountByHQL(String s, List param) {
        return null;
    }

    public List<CodeDatasource> selectByHql(String sql, List param) {
        return null;
    }

    public List selectByHql(String s, List param, Integer offset, Integer limit) {
        return null;
    }

    public void delete(Object t) {
    }

    public List<Map> selectMapsBySQL(String s, List list) {return null;
    }

    public List selectMapsBySQL(String s, List list, Integer offset, Integer limit) {return null;
    }

    public Long getCountBySQL(String s, List list) {return  null;
    }

    public void executeBySql(String s, List list) {
    }

    public void saveOrUpdate(Object o) {
    }

    public List selectBySql(String sql) {return null;
    }

    public Object loadByHql(String s, List param) {return  null;
    }
}
