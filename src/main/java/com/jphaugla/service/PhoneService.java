package com.jphaugla.service;

import com.jphaugla.domain.Phone;

import java.util.List;
import java.util.UUID;

public interface PhoneService {
    Phone savePhone (Phone  phone );
    List<Phone > getAllPhones();
    Phone  getPhoneById(String id);
    void deletePhone(String id);

    List<Phone> getAllCustomerPhones(UUID customerId);

    void deleteCustomerPhones(UUID customerId);
}
