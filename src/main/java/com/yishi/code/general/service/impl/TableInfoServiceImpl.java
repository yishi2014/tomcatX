package com.yishi.code.general.service.impl;

import com.alibaba.fastjson.JSON;
import com.yishi.code.general.annotation_.Transactional;
import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.Result;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.example.EntityContainer;
import com.yishi.code.general.model.CodeForm;
import com.yishi.code.general.service.TableInfoService;
import com.yishi.code.general.util.UpdateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TableInfoServiceImpl implements TableInfoService {

    @Resource
    private BaseDao baseDao;

    @Override
    public List getTableInfoAll(String formTable,String formType,String fromStatus) {
        String hql = "from CodeForm where ";
        Boolean f1 = false;
        Boolean f2 = false;
        if(!formTable.equals("")){
            f1 = true;
            hql += " formTable='"+formTable+"'";
        }
        if(!formType.equals("")){
            f2 = true;
            if(f1){
                hql += " and formType='"+formType+"'";
            }else{
                hql += " formType='"+formType+"'";
            }
        }
        if(!fromStatus.equals("")){
            if(f2){
                hql += " and fromStatus='"+fromStatus+"'";
            }else{
                hql += " fromStatus='"+fromStatus+"'";
            }
        }
        if(formTable.equals("")&&formType.equals("")&&fromStatus.equals("")){
            hql += " 1=1";
        }
        hql +=" order by id desc ";
        return baseDao.selectByHql(hql);
    }

    @Override
    public CodeForm getTableInfoByName(CodeForm codeForm) {
        String hql = "from CodeForm where formTable='"+codeForm.getFormTable()+"'";
        return (CodeForm)baseDao.selectByHql(hql).get(0) ;
    }

    @Override
    public void saveTableInfo(Result result, CodeForm codeForm) {
        CodeForm cform = (CodeForm)baseDao.save(codeForm);
        result.setSuccess(true);
        result.setObj(cform);
    }

    @Override
    public void updateTableInfo(Result result,CodeForm codeForm) {
        String hql = "from CodeForm where guid='"+codeForm.getGuid()+"'";
        List tf = baseDao.selectByHql(hql);
        if(tf!=null&&tf.size()>0){
            TableMeta old= JSON.parseObject(((CodeForm) tf.get(0)).getFormJson(),TableMeta.class);

            if(EntityContainer.contains(old.getEntityName())){
                TableMeta newForm= JSON.parseObject((codeForm).getFormJson(),TableMeta.class);
/*
                try {
                    String rootPath=Thread.currentThread().getContextClassLoader().getResource("/").getPath();
//                    new LoadFunction(old,rootPath).unload();
//                    new LoadFunction(newForm,rootPath).run();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


            }


        }
        Boolean tag = false;
        try {
            tag = UpdateUtil.updateNotNullField(tf.get(0), codeForm);
        }catch (Exception e){
            System.out.println("更新出错!");
        }
        if(tag){
            baseDao.saveOrUpdate(tf.get(0));
            result.setSuccess(true);
        }
    }

    @Override
    public void deleteTableInfo(Result result,CodeForm codeForm) {
        baseDao.delete(codeForm);
        result.setSuccess(true);
    }

    @Override
    public List getDBTable(HttpServletRequest request) {
        String tableName = request.getParameter("tableName");
        String sql = "select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
        List<String> list = baseDao.selectBySql(sql);
        List createList = baseDao.selectBySql("select FORM_TABLE from CODE_FORM where 1=1");
        list.removeAll(createList);

        List result = new ArrayList();
        List<String> index = new ArrayList<String>();
        if (tableName!=null && !tableName.equals("")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).contains(tableName))
                    index.add(list.get(i));
            }
            for (int j=0;j<index.size();j++){
                Map map = new HashMap();
                map.put("tableName",index.get(j));
                result.add(map);
            }
        }else{
            for (int k=0;k<list.size();k++){
                Map map = new HashMap();
                map.put("tableName",list.get(k));
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public CodeForm getTableInfoById(String id) {
        List param=new ArrayList();
        param.add(id);
        return (CodeForm) baseDao.loadByHql("from CodeForm where id=?",param);
    }
}
