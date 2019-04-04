package com.yishi.code.general.service.impl;

import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.Pages;
import com.yishi.code.general.dto.unalterable.Result;
import com.yishi.code.general.model.CodeDatasource;
import com.yishi.code.general.service.CodeDatasourceService;
//import com.datanew.dao.BaseDao;
//import com.datanew.dto.Pages;
//import com.datanew.dto.unalterable.Result;
//import com.datanew.util.DesUtil;
//import com.datanew.util.StringUtil;
//import com.datanew.util.unalterable.HqlGen;
import com.yishi.code.general.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

@Service
//数据源
public  class  CodeDatasourceServiceImpl implements CodeDatasourceService {

    @Resource
    private BaseDao baseDao;

    @Override
    public List<CodeDatasource> queryById(String id) {
        String sql = "from CodeDatasource where id='"+id+"'";
        return baseDao.selectByHql(sql);
    }

    @Override
    public List<CodeDatasource> queryWithoutPass() {
        String sql = "select new CodeDatasource(datasourcename,username,id,driverclassname,url) from CodeDatasource where 1=1 order by id desc ";
        return baseDao.selectByHql(sql);
    }

    public List<CodeDatasource> query(Map<String,String> pageMap){

        List param=new ArrayList();
        String sql="from CodeDatasource ";
        String condition="";
        condition=condition.replaceAll("^\\s*and","where");
        sql+=condition;
        return baseDao.selectByHql(sql,param);
    }

    public Pages<CodeDatasource> queryByPage(Map<String,String> pageMap){

        List param=new ArrayList();
        String sql="from CodeDatasource ";
        String condition="";
        if(!StringUtil.isblank(pageMap.get("datasourcename"))){
            condition += " and datasourcename like '%"+pageMap.get("datasourcename")+"%' ";
        }
        if(!StringUtil.isblank(pageMap.get("url"))){
            condition += " and url = ? ";
            param.add(pageMap.get("url"));
        }

        condition=condition.replaceAll("^\\s*and","where");
        sql+=condition;
        Object offsetObj=pageMap.get("offset");
        Object limitObj=pageMap.get("limit");
        Pages<CodeDatasource> page=new Pages<>(Integer.parseInt(limitObj==null?"30":String.valueOf(limitObj)),Integer.parseInt(offsetObj==null?"1":String.valueOf(offsetObj)));
        List list = baseDao.selectByHql(sql+" order by id desc  ", param,page.getOffset(),page.getLimit());
        Long count=baseDao.getCountByHQL("select count(*) from CodeDatasource "+condition,param);
        page.setTotal(count.intValue());
        page.setRows(list);
        return page;
    }

    public Result save(CodeDatasource t){
        Result result = new Result(false,"保存失败");
        t.setAddtime(new Date());
        baseDao.save(t);
        result.setSuccess(true);
        result.setMsg("保存成功");
        return result;
    }

    public Result update(CodeDatasource t){
        Result result = new Result(false,"更新失败");
        CodeDatasource cd = (CodeDatasource)baseDao.load(CodeDatasource.class,t.getId());
        cd.setUsername(t.getUsername());
        cd.setPassword(t.getPassword());
        cd.setDatasourcename(t.getDatasourcename());
        cd.setDriverclassname(t.getDriverclassname());
        cd.setUrl(t.getUrl());
        baseDao.update(cd);
        result.setSuccess(true);
        result.setMsg("更新成功");
        return result;
    }

    public Result delete(CodeDatasource t){
        Result result = new Result(false,"删除失败");
        baseDao.delete(t);
        result.setSuccess(true);
        result.setMsg("删除成功");
        return result;
    }

    @Override
    public List  queryByTree(Map pageMap) {
        List param=new ArrayList();
        String sql="select new map(c.id as id ,c.datasourcename||'__'||c.url as text) from CodeDatasource c ";
        return baseDao.selectByHql(sql,param);
    }

}
