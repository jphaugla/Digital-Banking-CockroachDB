package com.jphaugla.domain;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;


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
    private Timestamp _timestamp;
    private String _source;

    public Merchant(String issuer, String in_cat_code, String in_cat_desc, String in_state, String in_country, String in_source) {
        name = issuer;
        categoryCode = in_cat_code;
        categoryDescription=in_cat_desc;
        state=in_state;
        countryCode = in_country;
        setCurrentTime(in_source);
    }

    public void setCurrentTime(String in_source) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        set_timestamp(currentTimestamp);
        set_source(in_source);
    }

}
