package com.scalefocus.java.simeonyachev.domain.sqlite;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stream_types")
public class StreamType {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public StreamType() {
    }

    public StreamType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StreamType)) {
            return false;
        }
        StreamType that = (StreamType) obj;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
