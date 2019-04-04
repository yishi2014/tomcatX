package com.yishi.code.general.basedata.construct;

import java.util.HashSet;
import java.util.Set;

public class JPackage implements ImportParser {
    private static final String CONTENT="package ";
    private String pkgName;
    public JPackage(String pkgName){
        this.setPkgName(pkgName);
    }
    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    @Override
    public Set<JClass> parseImport() {
        return new HashSet<>();
    }

    @Override
    public String toString() {
        return CONTENT+' '+pkgName+";\n\n";
    }
}
