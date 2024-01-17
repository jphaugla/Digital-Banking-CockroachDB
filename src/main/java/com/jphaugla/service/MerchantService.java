package com.jphaugla.service;

import com.jphaugla.domain.Merchant;

import java.util.List;
import java.util.UUID;

public interface MerchantService {
    String saveMerchant (Merchant  merchant );

    void saveAll(List<Merchant> merchantList);

    List<Merchant > getAllMerchants();
    Merchant  getMerchantById(String id);
    void deleteMerchant(String id);
}
