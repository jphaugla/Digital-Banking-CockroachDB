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
@Table(name = "account", schema = "public")
public class Account extends BaseEntity{
    private String accountNo;
    private UUID customerId;
    private String accountType;
    private String accountOriginSystem;
    private String accountStatus;
    private String cardNum;
    private Timestamp openDatetime;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private String createdBy;
    private Timestamp createdDatetime;
	public void setCurrentTime (String region) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        set_source(region);
        setCreatedDatetime(currentTimestamp);
        setOpenDatetime(currentTimestamp);
        setLastUpdated(currentTimestamp);
        if (getId() == null) {
            generateSetID();
        }
    }
}

