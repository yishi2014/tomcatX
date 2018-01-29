package com.yishi.design.pattern.interpreter;

import java.util.List;
import java.util.function.Consumer;

public interface Expression {
   void remove(Expression file);
   void add(Expression file);
   List<Expression> getChild();
   void print();
   void execute(Consumer<Expression> consumer);
   String getPath();
   Expression getParent();
   void setParent(Expression file);
   void setName(String name);
   String getName();
   int getVal();
}
