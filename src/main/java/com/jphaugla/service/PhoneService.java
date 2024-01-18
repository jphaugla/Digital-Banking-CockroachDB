package com.jphaugla.service;

import com.jphaugla.domain.Phone;
import com.jphaugla.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface PhoneService {
    Phone savePhone (Phone  phone );
    List<Phone > getAllPhones();
    Phone  getPhoneById(String id) throws NotFoundException;
    void deletePhone(String id) throws NotFoundException;

    List<Phone> getAllCustomerPhones(UUID customerId);

    void deleteCustomerPhones(UUID customerId) throws NotFoundException;
}
