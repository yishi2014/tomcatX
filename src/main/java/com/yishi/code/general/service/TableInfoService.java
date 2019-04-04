package com.yishi.code.general.service;


import com.yishi.code.general.dto.Result;
import com.yishi.code.general.model.CodeForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TableInfoService {
    List getTableInfoAll(String formTable, String formType, String fromStatus);
    CodeForm getTableInfoByName(CodeForm codeForm);
    void saveTableInfo(Result result, CodeForm codeForm);
    void updateTableInfo(Result result, CodeForm codeForm);
    void deleteTableInfo(Result result, CodeForm codeForm);
    List getDBTable(HttpServletRequest request);
    CodeForm getTableInfoById(String id);
}
