package com.yishi.code.general.dto.unalterable;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Tree {
    String ID();
    String PID();
    String ISLEAF();
    String NAME();
    boolean OPEN();
//    private String id;
//    private String pid;
//    private String ISLEAF;
//    private String name;
//    private boolean open;
//
//    public Tree(String id, String pid, String ISLEAF, String name, boolean open) {
//        this.id = id;
//        this.pid = pid;
//        this.ISLEAF = ISLEAF;
//        this.name = name;
//        this.open = open;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//    @JsonProperty("pId")
//    public String getPid() {
//        return pid;
//    }
//
//    public void setPid(String pid) {
//        this.pid = pid;
//    }
//    @JsonProperty("ISLEAF")
//    public String getISLEAF() {
//        return ISLEAF;
//    }
//
//    public void setISLEAF(String ISLEAF) {
//        this.ISLEAF = ISLEAF;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean isOpen() {
//        return open;
//    }
//
//    public void setOpen(boolean open) {
//        this.open = open;
//    }
}
