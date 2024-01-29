package com.jphaugla.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING)
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
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
    private Timestamp initialDate;
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
    private Timestamp settlementDate;
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
    private Timestamp postingDate;
    //  this is authorized, posted, settled
    private String status;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private UUID disputeId;
    private String transactionReturn;
    private String location;
    @JsonFormat(shape=JsonFormat.Shape.ARRAY)
    @Type(ListArrayType.class)
    @Column(
            name = "transaction_tags",
            columnDefinition = "text[]"
    )
    private List<String> transactionTags;

}
