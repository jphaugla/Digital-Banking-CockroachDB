package com.jphaugla.service.impl;

import com.jphaugla.domain.Phone;
import com.jphaugla.exception.ResourceNotFoundException;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PhoneServiceImpl implements PhoneService {
 
    @Autowired
    private PhoneRepository phoneRepository;


    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        super();
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Phone savePhone(Phone phone) {
        log.info("phoneService.savePhone");
        return phoneRepository.save(phone);
    }

    @Override
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone getPhoneById(String phone) {
//		Optional<Phone> phone = phoneRepository.findById(id);
//		if(phone.isPresent()) {
//			return phone.get();
//		}else {
//			throw new ResourceNotFoundException("Phone", "Id", id);
//		}
        return phoneRepository.findById(phone).orElseThrow(() ->
                new ResourceNotFoundException("Phone", "Id", phone));

    }


    @Override
    public void deletePhone(String id) {

        // check whether a phone exist in a DB or not
        phoneRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Phone", "Id", id));
        phoneRepository.deleteById(id);
    }

    @Override
    public List<Phone> getAllCustomerPhones(UUID customerId) {
        List <Phone> customerPhones = phoneRepository.findByCustomerId(customerId);
        return customerPhones;
    }
    @Override
    public void deleteCustomerPhones(UUID customerId) {
        List <Phone> customerPhones = getAllCustomerPhones(customerId);
        for (Phone phone : customerPhones) {
            deletePhone(phone.getNumber());
        }
    }

}
