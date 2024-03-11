package com.jphaugla.domain;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "phone", schema = "public", indexes = @Index(columnList = "customerId"))
public class Phone  {
        @Id
        private String number;
        private String label;
        private UUID customerId;
        private Timestamp _timestamp;
        @Value("${app.region}")
        private String _source;

        public Phone(String in_number, String in_label, UUID in_customerId, String in_source) {
                number = in_number;
                label = in_label;
                customerId = in_customerId;
                setCurrentTime(in_source);
        }

        public void setCurrentTime(String in_source) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                set_timestamp(currentTimestamp);
                set_source(in_source);
        }

}
