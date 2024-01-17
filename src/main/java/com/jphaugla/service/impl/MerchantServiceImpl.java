package com.jphaugla.service.impl;

import com.jphaugla.domain.Merchant;
import com.jphaugla.exception.ResourceNotFoundException;
import com.jphaugla.repository.MerchantRepository;
import com.jphaugla.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {
 
    @Autowired
    private MerchantRepository merchantRepository;


    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        super();
        this.merchantRepository = merchantRepository;
    }

    @Override
    public String saveMerchant(Merchant merchant) {
        log.info("merchantService.saveMerchant");
        merchantRepository.save(merchant);
        return merchant.getName();
    }

    @Override
    public void saveAll(List<Merchant> merchantList) {
        log.info("merchantService.saveMerchant");
        merchantRepository.saveAll(merchantList);
    }

    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant getMerchantById(String merchant) {
//		Optional<Merchant> merchant = merchantRepository.findById(id);
//		if(merchant.isPresent()) {
//			return merchant.get();
//		}else {
//			throw new ResourceNotFoundException("Merchant", "Id", id);
//		}
        return merchantRepository.findById(merchant).orElseThrow(() ->
                new ResourceNotFoundException("Merchant", "Id", merchant));

    }


    @Override
    public void deleteMerchant(String id) {

        // check whether a merchant exist in a DB or not
        merchantRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Merchant", "Id", id));
        merchantRepository.deleteById(id);
    }

}
