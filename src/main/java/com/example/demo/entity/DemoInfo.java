package com.example.demo.entity;import org.springframework.data.annotation.Id;import org.springframework.data.mongodb.core.mapping.Document;/** * * @author jensen  * @description    测试实体类 * @date 2018/3/15 14:37 * @param * @return  */@Document(collection="demoInfo")public class DemoInfo extends BaseEntity {    private String name;    private int age;    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public int getAge() {        return age;    }    public void setAge(int age) {        this.age = age;    }    @Override    public String toString() {        return "DemoInfo{" +                "name='" + name + '\'' +                ", age=" + age +                '}';    }}