package com.yishi.code.general.service;

//import com.yishi.code.general.model.CodeDatasource;
//import com.datanew.dto.Pages;
//import com.datanew.dto.unalterable.Result;

import com.yishi.code.general.dto.Pages;
import com.yishi.code.general.dto.unalterable.Result;
import com.yishi.code.general.model.CodeDatasource;

import java.util.List;
import java.util.Map;

//数据源
public  interface  CodeDatasourceService {

    List<CodeDatasource> queryById(String id);

    List<CodeDatasource> queryWithoutPass();

    public List<CodeDatasource> query(Map<String, String> pageMap);

    public Pages<CodeDatasource> queryByPage(Map<String, String> pageMap);

    public Result save(CodeDatasource t);

    public com.yishi.code.general.dto.unalterable.Result update(CodeDatasource t);

    public com.yishi.code.general.dto.unalterable.Result delete(CodeDatasource t);

    List queryByTree(Map pageMap);
}
