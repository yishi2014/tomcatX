//package com.yishi.code.general.controller;
//
//import com.yishi.code.general.annotation_.RequestMapping;
//import com.yishi.code.general.annotation_.RequestParam;
//import com.yishi.code.general.annotation_.ResponseBody;
//import com.yishi.code.general.dto.unalterable.Result;
//import com.yishi.code.general.model.CodeDatasource;
//import com.yishi.code.general.service.CodeDatasourceService;
//import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestParam;
////import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//
//@Controller
//@RequestMapping("/codeDatasource")
////数据源
//public  class  CodeDatasourceController {
//
//    @Resource
//    private CodeDatasourceService codeDatasourceService;
//
//    @RequestMapping("/query")
//    @ResponseBody
//    public List<CodeDatasource> query(@RequestParam Map pageMap){
//
//        return codeDatasourceService.query(pageMap);
//    }
//
//    @RequestMapping("/queryByPage")
//    @ResponseBody
//    public Object queryByPage(@RequestParam Map pageMap){
//
//        return codeDatasourceService.queryByPage(pageMap);
//    }
//    @RequestMapping("/queryByTree")
//    @ResponseBody
//    public List queryByTree(@RequestParam Map pageMap){
//
//        return codeDatasourceService.queryByTree(pageMap);
//    }
//
//    @RequestMapping("/queryById")
//    @ResponseBody
//    public Object queryById(String id){
//        return codeDatasourceService.queryById(id);
//    }
//
//
//
//
//    @RequestMapping("/queryWithoutPass")
//    @ResponseBody
//    public Object queryWithoutPass(){
//        return codeDatasourceService.queryWithoutPass();
//    }
//
//    @ResponseBody
//    @RequestMapping("/save")
//    public Result save(CodeDatasource t){
//        return codeDatasourceService.save(t);
//
//    }
//
//    @RequestMapping("/update")
//    @ResponseBody
//    public Result update(CodeDatasource t){
//
//        return codeDatasourceService.update(t);
//
//    }
//
//    @RequestMapping("/delete")
//    @ResponseBody
//    public Result delete(CodeDatasource t){
//
//        return codeDatasourceService.delete(t);
//
//    }
//
//}
