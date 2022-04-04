package com.scalefocus.java.simeonyachev.domain.sqlite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stream_types")
public class StreamType {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}