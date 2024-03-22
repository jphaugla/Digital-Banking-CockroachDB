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
@Table(name = "email", schema = "public", indexes = @Index(columnList = "customerId"))
public class Email  {

        @Id
        private String address;
        private String label;
        private UUID customerId;
        private Timestamp lastUpdated;
        private String _source;

        public Email(String in_address, String in_label, UUID cust, String in_source) {
                setCurrentTime(in_source);
                customerId = cust;
                label = in_label;
                address = in_address;
        }

        public void setCurrentTime(String in_source) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                _source = in_source;
                setLastUpdated(currentTimestamp);
        }
}
