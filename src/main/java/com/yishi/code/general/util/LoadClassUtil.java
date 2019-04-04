package com.yishi.code.general.util;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class LoadClassUtil extends URLClassLoader {
        Map<String, byte[]> classBytes = new HashMap<String, byte[]>();
        public LoadClassUtil(Map<String, byte[]> classBytes) {
            super(new URL[0], LoadClassUtil.class.getClassLoader());
            this.classBytes.putAll(classBytes);
        }
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] buf = classBytes.get(name);
            if (buf == null) {
                return super.findClass(name);
            }
            classBytes.remove(name);
            return defineClass(name, buf, 0, buf.length);
        }

    public static void main(String[] args) {
//        new URLClassLoader().findResource("")
    }

}
