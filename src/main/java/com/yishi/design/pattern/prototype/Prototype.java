package com.yishi.design.pattern.prototype;

import java.io.*;

public class Prototype implements Prototype_ {
    @Override
    public Prototype Clone() {

        try {/* 写入当前对象的二进制流 */
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

        /* 读出二进制流产生的新对象 */
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ((Prototype) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
