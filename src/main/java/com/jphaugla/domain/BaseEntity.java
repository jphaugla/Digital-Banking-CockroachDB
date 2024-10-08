package com.jphaugla.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

// @Entity
@Getter
@Setter
// Choose your inheritance strategy:
//@Inheritance(strategy=InheritanceType.JOINED)
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class BaseEntity {

    @Id
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private UUID id;
    private String _source;
    public UUID generateSetID() {
        id = UUID.randomUUID();
        return id;
    }
}
