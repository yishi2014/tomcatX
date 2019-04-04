package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.XPackage;

public class CommonXPackage implements XPackage {

    private static final String CONTENT="package ";
    private String remark;
    private String pkgName;
    public CommonXPackage(String pkgName){
        this.pkgName=pkgName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String render() {
        return CONTENT+getPkgName()+";\n";
    }

    @Override
    public String _getName() {
        return this.getPkgName();
    }

    @Override
    public String _getRemark() {
        return this.getRemark();
    }
}
