package com.vaadin.example.sightseeing.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Place extends AbstractEntity {

    private String name;
    private Integer x;
    private Integer y;
    private String tags;
    private LocalDate updated;
    private Integer oid;
    private boolean enabled;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public LocalDate getUpdated() {
        return updated;
    }
    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
    public Integer getOid() {
        return oid;
    }
    public void setOid(Integer oid) {
        this.oid = oid;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
