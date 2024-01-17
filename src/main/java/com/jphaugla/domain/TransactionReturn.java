package com.jphaugla.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_return", schema = "public")
public class TransactionReturn  {

    @Id
    private String reasonCode;
    private String reasonDescription;
}
