package com.jphaugla.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import java.sql.Timestamp;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "test", schema = "public")
public class Test {
        @Id
        private String col1;
        private String col2;
        private double amount;
        @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
        private Timestamp posting;
        @JsonFormat(shape=JsonFormat.Shape.STRING)
        private UUID customer;
        public void setCurrentTime () {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                setPosting(currentTimestamp);
        }
}
