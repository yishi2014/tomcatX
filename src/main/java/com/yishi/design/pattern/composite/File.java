package com.yishi.design.pattern.composite;

import java.util.List;
import java.util.function.Consumer;

public interface File {
   void remove(File file);
   void add(File file);
   List<File> getChild();
   void print();
   void execute(Consumer<File> consumer);
   String getPath();
   File getParent();
   void setParent(File file);
   void setName(String name);
   String getName();

}
