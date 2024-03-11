package com.jphaugla.service.impl;

import com.jphaugla.domain.Phone;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Constants.ERR_EMAIL_NOT_FOUND;
import static com.jphaugla.util.Constants.ERR_PHONE_NOT_FOUND;

@Slf4j
@Service
public class PhoneServiceImpl implements PhoneService {
 
    @Autowired
    private PhoneRepository phoneRepository;
    @Value("${app.region}")
    private String source_region;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        super();
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Phone savePhone(Phone phone) {
        log.info("phoneService.savePhone");
        phone.setCurrentTime(source_region);
        return phoneRepository.save(phone);
    }

    @Override
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone getPhoneById(String phone) throws NotFoundException {
//		Optional<Phone> phone = phoneRepository.findById(id);
//		if(phone.isPresent()) {
//			return phone.get();
//		}else {
//			throw new ResourceNotFoundException("Phone", "Id", id);
//		}
        return phoneRepository.findById(phone).orElseThrow(() ->
                new NotFoundException(String.format(ERR_PHONE_NOT_FOUND, phone)));

    }


    @Override
    public void deletePhone(String phone) throws NotFoundException {

        // check whether a phone exist in a DB or not
        phoneRepository.findById(phone).orElseThrow(() ->
                new NotFoundException(String.format(ERR_PHONE_NOT_FOUND, phone)));
        phoneRepository.deleteById(phone);
    }

    @Override
    public List<Phone> getAllCustomerPhones(UUID customerId) {
        return phoneRepository.findByCustomerId(customerId);
    }
    @Override
    public void deleteCustomerPhones(UUID customerId) throws NotFoundException {
        List <Phone> customerPhones = getAllCustomerPhones(customerId);
        for (Phone phone : customerPhones) {
            deletePhone(phone.getNumber());
        }
    }

}
