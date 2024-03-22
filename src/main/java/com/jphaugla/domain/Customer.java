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
@Table(name = "customer", schema = "public", indexes = {
        @Index(name = "customer_state_city", columnList = "stateAbbreviation, city"),
        @Index(name = "customer_zip_last", columnList = "zipcode, lastName"),
})
public class Customer extends BaseEntity{
    private  String addressLine1;
    private  String addressLine2;
    private  String addressType;
    private  String billPayEnrolled;
    private  String city;
    private  String countryCode;
    private  String createdBy;
    private  Timestamp createdDatetime;
    private  String customerOriginSystem;
    private  String customerStatus;
    private  String customerType;
    private  String dateOfBirth;
    private  String firstName;
    private  String fullName;
    private  String gender;
    private  String lastName;
    private  Timestamp _timestamp;
    private  String lastUpdatedBy;
    private  String middleName;
    private  String prefix;
    private  String queryHelperColumn;
    private  String stateAbbreviation;
    private  String zipcode;
    private  String zipcode4;


   public void setCurrentTime (String in_source) {
       Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
       setCreatedDatetime(currentTimestamp);
       set_timestamp(currentTimestamp);
       set_source(in_source);
       if (getId() == null) {
           generateSetID();
       }
   }

}
