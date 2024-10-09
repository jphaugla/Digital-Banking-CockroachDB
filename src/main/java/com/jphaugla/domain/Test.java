package com.jphaugla.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
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
