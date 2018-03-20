package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3918552629159611515L;
    @Id
    protected String id;
    protected Long version;
    protected String projectId;

    public BaseEntity() {
        super();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}