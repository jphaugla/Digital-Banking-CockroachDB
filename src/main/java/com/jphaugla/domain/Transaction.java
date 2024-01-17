package com.jphaugla.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "transaction", schema = "public")
public class Transaction extends BaseEntity{

    private UUID accountId;
    // debit or credit
    private String amountType;
    private String merchant;
    private String referenceKeyType;
    private String referenceKeyValue;
    private double originalAmount;
    private double amount;
    private String tranCode;
    private String description;
    private Timestamp initialDate;
    private Timestamp settlementDate;
    private Timestamp postingDate;
    //  this is authorized, posted, settled
    private String status   ;
    private UUID disputeId;
    private String transactionReturn;
    private String location;
    private String transactionTags;

}
