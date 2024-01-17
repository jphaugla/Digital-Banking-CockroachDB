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
@Table(name = "email", schema = "public", indexes = @Index(columnList = "customerId"))
public class Email {
        @Id
        private String address;
        private String label;
        private UUID customerId;
}
