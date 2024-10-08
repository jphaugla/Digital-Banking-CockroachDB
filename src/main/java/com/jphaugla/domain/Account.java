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
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "account", schema = "public")
public class Account extends BaseEntity{
    private String accountNo;
    private UUID customerId;
    private String accountType;
    private String accountOriginSystem;
    private String accountStatus;
    private String cardNum;
    private Timestamp openDatetime;
    private Timestamp _timestamp;
    private String lastUpdatedBy;
    private String createdBy;
    private Timestamp createdDatetime;
	public void setCurrentTime (String region) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        set_source(region);
        setCreatedDatetime(currentTimestamp);
        setOpenDatetime(currentTimestamp);
        set_timestamp(currentTimestamp);
        if (getId() == null) {
            generateSetID();
        }
    }
}

