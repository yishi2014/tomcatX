package com.yishi.code.general.dto;

import com.yishi.code.general.x.XField;

public class ReferenceMeta {
    private ColumnMeta columnMeta;
    //text列所在的类型的fieldName
    private XField referenceObj;

    private XField textXfield;
    private TableMeta tableMeta;

    public TableMeta getTableMeta() {
        return tableMeta;
    }

    public void setTableMeta(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    public ReferenceMeta(ColumnMeta columnMeta) {
        this.columnMeta = columnMeta;
    }

    public ColumnMeta getColumnMeta() {
        return columnMeta;
    }

    public void setColumnMeta(ColumnMeta columnMeta) {
        this.columnMeta = columnMeta;
    }

    public XField getTextXfield() {
        return textXfield;
    }

    public void setTextXfield(XField textXfield) {
        this.textXfield = textXfield;
    }

    public XField getReferenceObj() {
        return referenceObj;
    }

    public void setReferenceObj(XField referenceObj) {
        this.referenceObj = referenceObj;
    }
}
