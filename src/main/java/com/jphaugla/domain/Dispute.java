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
@Table(name = "dispute", schema = "public")
public class Dispute extends BaseEntity{
    private UUID tranId;
    private Timestamp filingDate;
    private Timestamp reviewDate;
    private String reasonCode;
    private Timestamp acceptanceChargeBackDate;
    private Timestamp resolutionDate;
    private Timestamp _timestamp;
    private String status;
    private double chargeBackAmount;
    public void setCurrentTime(String in_source) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        setFilingDate(currentTimestamp);
        set_timestamp(currentTimestamp);
        set_source(in_source);
        if (getId() == null) {
            generateSetID();
        }
    }
    public void setChargeBackReason(String chargeBackReason) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        _timestamp = currentTimestamp;
        reasonCode=chargeBackReason;
        reviewDate=currentTimestamp;
        status="Investigate";
    }

    public void acceptChargeBack() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        _timestamp=currentTimestamp;
        acceptanceChargeBackDate=currentTimestamp;
        status="ChargedBack";
    }
    public void resolved() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        _timestamp=currentTimestamp;
        resolutionDate=currentTimestamp;
        status="Resolved";
    }

}
