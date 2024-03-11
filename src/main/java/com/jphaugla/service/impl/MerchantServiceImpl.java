package com.jphaugla.service.impl;

import com.jphaugla.domain.Merchant;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.MerchantRepository;
import com.jphaugla.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jphaugla.util.Constants.ERR_CUSTOMER_EMAIL_NOT_FOUND;
import static com.jphaugla.util.Constants.ERR_MERCHANT_NOT_FOUND;

@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {
 
    @Autowired
    private MerchantRepository merchantRepository;
    @Value("${app.region}")
    private String source_region;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        super();
        this.merchantRepository = merchantRepository;
    }

    @Override
    public String saveMerchant(Merchant merchant) {
        log.info("merchantService.saveMerchant");
        merchant.setCurrentTime(source_region);
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
    public Merchant getMerchantById(String merchant) throws NotFoundException {
//		Optional<Merchant> merchant = merchantRepository.findById(id);
//		if(merchant.isPresent()) {
//			return merchant.get();
//		}else {
//			throw new ResourceNotFoundException("Merchant", "Id", id);
//		}
        return merchantRepository.findById(merchant).orElseThrow(() ->
                new NotFoundException(String.format(ERR_MERCHANT_NOT_FOUND, merchant)));

    }


    @Override
    public void deleteMerchant(String merchant) throws NotFoundException {

        // check whether a merchant exist in a DB or not
        merchantRepository.findById(merchant).orElseThrow(() ->
                new NotFoundException(String.format(ERR_MERCHANT_NOT_FOUND, merchant)));
        merchantRepository.deleteById(merchant);
    }

}
