package com.yishi.code.general.basedata.construct;

import java.util.HashSet;
import java.util.Set;

public class JEnd implements ImportParser {
    @Override
    public Set<JClass> parseImport() {
        return new HashSet<>();
    }

    private static final String CONTENT="\n}";

    public static String getCONTENT() {
        return CONTENT;
    }

    @Override
    public String toString() {
        return CONTENT;
    }
}
