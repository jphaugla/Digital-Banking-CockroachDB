package com.jphaugla.domain;
import lombok.*;

import jakarta.persistence.*;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "phone", schema = "public", indexes = @Index(columnList = "customerId"))
public class Phone {
        @Id
        private String number;
        private String label;
        private UUID customerId;
}
