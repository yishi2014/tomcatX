package com.yishi.html.CommonTableFillData;

import java.util.List;
import java.util.function.Consumer;

public interface JsonKey {
   void remove(JsonKey file);
   void add(JsonKey file);
   List<JsonKey> getChild();
   void print();
   void execute(Consumer<JsonKey> consumer);
   String getPath();
   JsonKey getParent();
   void setParent(JsonKey file);
   void setName(String name);
   String getName();

}
