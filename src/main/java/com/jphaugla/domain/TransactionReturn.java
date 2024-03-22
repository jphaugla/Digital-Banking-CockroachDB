package com.jphaugla.domain;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_return", schema = "public")
public class TransactionReturn  {
    @Id
    private String reasonCode;
    private String reasonDescription;
    private Timestamp lastUpdated;
    @Value("${app.region}")
    private String _source;

    public TransactionReturn(String in_code, String in_description, String in_source) {
        reasonCode = in_code;
        reasonDescription = in_description;
        setCurrentTime(in_source);
    }

    public void setCurrentTime(String in_source) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        setLastUpdated(currentTimestamp);
        set_source(in_source);
    }

}
