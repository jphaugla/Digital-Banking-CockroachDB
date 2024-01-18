package com.jphaugla.service.impl;

import com.jphaugla.domain.TransactionReturn;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.TransactionReturnRepository;
import com.jphaugla.service.TransactionReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jphaugla.util.Constants.ERR_TRANSACTION_NOT_FOUND;
import static com.jphaugla.util.Constants.ERR_TRANSACTION_RETURN_NOT_FOUND;

@Slf4j
@Service
public class TransactionReturnServiceImpl implements TransactionReturnService {
 
    @Autowired
    private TransactionReturnRepository transactionReturnRepository;


    public TransactionReturnServiceImpl(TransactionReturnRepository transactionReturnRepository) {
        super();
        this.transactionReturnRepository = transactionReturnRepository;
    }

    @Override
    public TransactionReturn saveTransactionReturn(TransactionReturn transactionReturn) {
        log.info("transactionReturnService.saveTransactionReturn");
        return transactionReturnRepository.save(transactionReturn);
    }

    @Override
    public List<TransactionReturn> getAllTransactionReturns() {
        return transactionReturnRepository.findAll();
    }

    @Override
    public TransactionReturn getTransactionReturnById(String transactionReturn) throws NotFoundException {
//		Optional<TransactionReturn> transactionReturn = transactionReturnRepository.findById(id);
//		if(transactionReturn.isPresent()) {
//			return transactionReturn.get();
//		}else {
//			throw new ResourceNotFoundException("TransactionReturn", "Id", id);
//		}
        return transactionReturnRepository.findById(transactionReturn).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_RETURN_NOT_FOUND, transactionReturn)));

    }


    @Override
    public void deleteTransactionReturn(String transactionReturn) throws NotFoundException {

        // check whether a transactionReturn exist in a DB or not
        transactionReturnRepository.findById(transactionReturn).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_RETURN_NOT_FOUND, transactionReturn)));
        transactionReturnRepository.deleteById(transactionReturn);
    }


}
