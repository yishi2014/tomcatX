package com.yishi.code.general.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="CODE_DATASOURCE")
//数据源
public  class  CodeDatasource {

    //数据源名称
    private String datasourcename;

    //密码
    private String password;

    //用户名
    private String username;

    //主键
    private Integer id;

    //驱动类名
    private String driverclassname;

    //连接地址
    private String url;
    //新增时间
    private Date addtime;


    @Basic
    @Column(name="ADDTIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public CodeDatasource(){}

    public CodeDatasource(String datasourcename, String username, Integer id, String driverclassname, String url) {
        this.datasourcename = datasourcename;
        this.username = username;
        this.id = id;
        this.driverclassname = driverclassname;
        this.url = url;
    }

    public void setDatasourcename(String datasourcename){

        this.datasourcename=datasourcename;
    }

    @Basic
    @Column(name="DATASOURCENAME")
    public String getDatasourcename(){

        return this.datasourcename;
    }

    public void setPassword(String password){

        this.password=password;
    }

    @Basic
    @Column(name="PASSWORD")
    public String getPassword(){

        return this.password;
    }

    public void setUsername(String username){

        this.username=username;
    }

    @Basic
    @Column(name="USERNAME")
    public String getUsername(){

        return this.username;
    }

    public void setId(Integer id){

        this.id=id;
    }

    @Id
    @GeneratedValue(generator = "ID")
    @GenericGenerator(name = "ID", strategy = "native")
    @Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
    public Integer getId(){

        return this.id;
    }

    public void setDriverclassname(String driverclassname){

        this.driverclassname=driverclassname;
    }

    @Basic
    @Column(name="DRIVERCLASSNAME")
    public String getDriverclassname(){

        return this.driverclassname;
    }

    public void setUrl(String url){

        this.url=url;
    }

    @Basic
    @Column(name="URL")
    public String getUrl(){

        return this.url;
    }

    @Override
    public String toString() {
        return "CodeDatasource{" +
                "datasourcename='" + datasourcename + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", driverclassname='" + driverclassname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
