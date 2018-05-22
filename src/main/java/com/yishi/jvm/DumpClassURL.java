//package com.yishi.jvm;
//
//import java.io.*;
//import java.util.*;
//import sun.jvm.hotspot.memory.*;
//import sun.jvm.hotspot.oops.*;
//import sun.jvm.hotspot.debugger.*;
//import sun.jvm.hotspot.runtime.*;
//import sun.jvm.hotspot.tools.*;
//
//public class DumpClassURL extends Tool {
//    public void run() {
//        // Ready to go with the database...
//        try {
//            final ArrayList<InstanceKlass> klasses = new ArrayList<InstanceKlass>(128);
//
//            SystemDictionary dict = VM.getVM().getSystemDictionary();
//            dict.classesDo(new SystemDictionary.ClassVisitor() {
//                public void visit(Klass k) {
//                    if (k instanceof InstanceKlass) {
//                        klasses.add((InstanceKlass) k);
//                    }
//                }
//            });
//
//            Collections.sort(klasses, new Comparator<InstanceKlass>() {
//                public int compare(InstanceKlass x, InstanceKlass y) {
//                    return x.getName().asString().compareTo(y.getName().asString());
//                }
//            });
//
//            System.out.println("class                path                loader");
//            System.out.println("-----------------------------------------------");
//            for (InstanceKlass k : klasses) {
//                System.out.printf("%s, %s, %s\n",
//                        getClassNameFrom(k),
//                        getPathFromInstanceKlass(k),
//                        getClassLoaderOopFrom(k));
//            }
//        } catch (AddressException e) {
//            System.err.println("Error accessing address 0x"
//                    + Long.toHexString(e.getAddress()));
//            e.printStackTrace();
//        }
//    }
//
//    private static String getClassNameFrom(InstanceKlass klass) {
//        return klass != null ? klass.getName().asString().replace('/', '.') : null;
//    }
//
//    private static String getPathFromInstanceKlass(InstanceKlass klass) {
//        Oop protectionDomain = klass.getProtectionDomain();
//        if (protectionDomain == null) return null;
//
//        Oop codesource = getOopFieldValueFrom(protectionDomain,
//                "codesource", "Ljava/security/CodeSource;");
//        if (codesource == null) return null;
//
//        Oop location = getOopFieldValueFrom(codesource, "location", "Ljava/net/URL;");
//        if (location == null) return null;
//
//        Oop path = getOopFieldValueFrom(location, "path", "Ljava/lang/String;");
//        if (path == null) return null;
//
//        return OopUtilities.stringOopToString(path);
//    }
//
//    private static String getClassLoaderOopFrom(InstanceKlass klass) {
//        Oop loader = klass.getClassLoader();
//        return loader != null
//                ? getClassNameFrom((InstanceKlass) loader.getKlass()) + " @ " + loader.getHandle()
//                : "<bootstrap>";
//    }
//
//    private static Oop getOopFieldValueFrom(Oop oop, String name, String sig) {
//        InstanceKlass klass = (InstanceKlass) oop.getKlass();
//        OopField field = (OopField) klass.findField(name, sig);
//        return field.getValue(oop);
//    }
//
//    public String getName() {
//        return "dumpClassURL";
//    }
//
//    public static void main(String[] args) {
//        DumpClassURL tool = new DumpClassURL();
//        tool.start(args);
//        tool.stop();
//    }
//}}