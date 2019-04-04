package com.yishi.code.general.example;

import com.yishi.code.general.dto.TableMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityContainer {
    private static Map<String,TableMeta> entityMapping=new ConcurrentHashMap<>();
    public static void addMapping(String name,TableMeta entity){
        entityMapping.put(name,entity);
    }
    public static TableMeta getMapping(String name){
        return entityMapping.get(name);
    }
    public static void removeMapping(String name){
        entityMapping.remove(name);
    }

    public static boolean contains(String entityName) {
        return entityMapping.containsKey(entityName);
    }
}
