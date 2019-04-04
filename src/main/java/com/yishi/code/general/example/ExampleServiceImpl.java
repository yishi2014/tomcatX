package com.yishi.code.general.example;

import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.Pages;
import com.yishi.code.general.dto.unalterable.Result;
import com.yishi.code.general.page.SqlGen;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class ExampleServiceImpl {

    @Resource
    private BaseDao baseDao;



    public List<Map> query(String unique___Name,Map var1) {
        try{
            Object[] sql_param=SqlGen.querySql(EntityContainer.getMapping(unique___Name),var1);
            List<Map> rawList= this.baseDao.selectMapsBySQL((String) sql_param[0], ((List) sql_param[1]));
            return SqlGen.convertMap(rawList,EntityContainer.getMapping(unique___Name));
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public Pages<Map> queryByPage(String unique___Name, Map var2) {
        Object offsetObj=var2.get("offset");
        Object limitObj=var2.get("limit");
        Pages pages=new Pages(Integer.parseInt(limitObj==null?"30":String.valueOf(limitObj)),Integer.parseInt(offsetObj==null?"1":String.valueOf(offsetObj)));
        try{
            Object[] sql_param=SqlGen.querySql(EntityContainer.getMapping(unique___Name),var2);
            List list= this.baseDao.selectMapsBySQL((String) sql_param[0], ((List) sql_param[1]),pages.getOffset(), pages.getLimit());
            List processedList=SqlGen.convertMap(list,EntityContainer.getMapping(unique___Name));
            Long count=baseDao.getCountBySQL("select count(*) from ("+sql_param[0]+") t ", (List) sql_param[1]);
            pages.setRows(processedList);
            pages.setTotal(count.intValue());
            return pages;
        }catch (Exception e){
            pages.setRows(new ArrayList());
            pages.setTotal(0);
            return pages;
        }
//        ArrayList var3 = new ArrayList();
//        List var4 = this.baseDao.selectByHql(HqlGen.hql(var2, Map.class, var3, var1), var3, (var1.getCurrent() - 1) * var1.getPageSize(), var1.getPageSize());
//        long var5 = this.baseDao.getCountByHQL(var1.getCountHql(), var3);
//        var1.setTotal(var5);
//        return new PageData(var1, var4);
    }

    public Result save(String unique___Name,Map var1) {
        try {
            Object[] sql_param=SqlGen.saveSql(EntityContainer.getMapping(unique___Name),var1);
            System.out.println("==============save======================");
            System.out.println((String) sql_param[0]);
            System.out.println((List) sql_param[1]);
            this.baseDao.executeBySql((String) sql_param[0], ((List) sql_param[1]));
        } catch (Exception var3) {
            var3.printStackTrace();
            return Result.fail();
        }

        return Result.success();
    }

    public Result update(String unique___Name, Map var1) {
        try {
            Object[] sql_param=SqlGen.updateSqlnew(EntityContainer.getMapping(unique___Name),var1);
            System.out.println("=============update=======================");
            System.out.println((String) sql_param[0]);
            System.out.println((List) sql_param[1]);
            this.baseDao.executeBySql((String) sql_param[0], ((List) sql_param[1]));
        } catch (Exception var3) {
            var3.printStackTrace();
            return Result.fail();
        }

        return Result.success();
    }

    public Result delete(String unique___Name,Map var1) {
        try {
            Object[] sql_param=SqlGen.deleteSql(EntityContainer.getMapping(unique___Name),var1);
            System.out.println("================delete====================");
            System.out.println((String) sql_param[0]);
            System.out.println((List) sql_param[1]);
            this.baseDao.executeBySql((String) sql_param[0], ((List) sql_param[1]));
        } catch (Exception var3) {
            var3.printStackTrace();
            return Result.fail();
        }

        return Result.success();
    }
}
