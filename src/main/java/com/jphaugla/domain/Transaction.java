package com.jphaugla.domain;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
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
    private String status;
    private UUID disputeId;
    private String transactionReturn;
    private String location;
    @Type(ListArrayType.class)
    @Column(
            name = "transaction_tags",
            columnDefinition = "text[]"
    )
    private List<String> transactionTags;


}
