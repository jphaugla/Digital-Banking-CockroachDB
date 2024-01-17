package com.jphaugla.domain;

import lombok.*;
import jakarta.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "merchant", schema = "public")
public class Merchant  {
    @Id
    private String name;
    private String categoryCode;
    private String categoryDescription;
    private String state;
    private String countryCode;
}
