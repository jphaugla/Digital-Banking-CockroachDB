package com.jphaugla.service.impl;

import com.jphaugla.domain.Dispute;
import com.jphaugla.domain.Transaction;

import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.DisputeRepository;
import com.jphaugla.repository.TransactionRepository;

import com.jphaugla.service.DisputeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Constants.ERR_DISPUTE_NOT_FOUND;
import static com.jphaugla.util.Constants.ERR_TRANSACTION_NOT_FOUND;

@Slf4j
@Service
public class DisputeServiceImpl implements DisputeService {
    @Autowired
    private DisputeRepository disputeRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public DisputeServiceImpl(DisputeRepository disputeRepository) {
        super();
        this.disputeRepository = disputeRepository;
    }

    @Override
    public UUID saveDispute(Dispute dispute) {
        log.info("disputeService.savedispute");
        disputeRepository.save(dispute);
        return dispute.getId();
    }

    @Override
    public List<Dispute> getAllDisputes() {
        return disputeRepository.findAll();
    }

    @Override
    public Dispute getDisputeById(UUID id) throws NotFoundException {
//		Optional<dispute> dispute = disputeRepository.findById(id);
//		if(dispute.isPresent()) {
//			return dispute.get();
//		}else {
//			throw new ResourceNotFoundException("dispute", "Id", id);
//		}
        return disputeRepository.findById(id).orElseThrow(() ->
                new NotFoundException (String.format(ERR_DISPUTE_NOT_FOUND, id)));

    }

    @Override
    public Dispute updatedispute(Dispute dispute, UUID id) throws NotFoundException {

        // we need to check whether dispute with given id is exist in DB or not
        Dispute existingDispute = disputeRepository.findById(id).orElseThrow(
                () -> new NotFoundException (String.format(ERR_DISPUTE_NOT_FOUND, id)));

        //existingdispute.setEmail(dispute.getEmail());
        // save existing dispute to DB
        disputeRepository.save(existingDispute);
        return existingDispute;
    }

    @Override
    public void deleteDispute(UUID id) throws NotFoundException {

        // check whether a dispute exist in a DB or not
        disputeRepository.findById(id).orElseThrow(() ->
                new NotFoundException (String.format(ERR_DISPUTE_NOT_FOUND, id)));
        disputeRepository.deleteById(id);
    }

    public String postDispute(Dispute dispute) throws NotFoundException {
        log.info("in bs.postDispute with Dispute =" + dispute);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        //  incoming is only a date and not a timestamp, change to a timestamp

        // dispute.setFilingDate(filingDateTime);
        dispute.setLastUpdateDate(currentTimestamp);
        Transaction transaction = transactionRepository.findById(dispute.getTranId()).orElseThrow(() ->
                new NotFoundException (String.format(ERR_TRANSACTION_NOT_FOUND, dispute.getTranId())));
        dispute.setChargeBackAmount(transaction.getAmount());
        log.info("before create with Dispute =" + dispute);
        disputeRepository.save(dispute);
        transaction.setDisputeId(dispute.getId());
        transactionRepository.save(transaction);
        return dispute.getId().toString();
    }

    public Dispute putDisputeChargeBackReason(UUID disputeId, String reasonCode) throws NotFoundException {
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(() ->
                new NotFoundException (String.format(ERR_DISPUTE_NOT_FOUND, disputeId)));
        dispute.setChargeBackReason(reasonCode);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        dispute.setLastUpdateDate(currentTimestamp);
        dispute.setReviewDate(currentTimestamp);
        dispute.setStatus("Investigate");
        disputeRepository.save(dispute);
        return dispute;
    }
    public Dispute acceptDisputeChargeBackReason(UUID disputeId) throws NotFoundException {
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(() ->
                new NotFoundException (String.format(ERR_DISPUTE_NOT_FOUND, disputeId)));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        dispute.setLastUpdateDate(currentTimestamp);
        dispute.setAcceptanceChargeBackDate(currentTimestamp);
        dispute.setStatus("ChargedBack");
        disputeRepository.save(dispute);
        return dispute;
    }

    public Dispute resolvedDispute(UUID disputeId) throws NotFoundException {
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(() ->
                new NotFoundException (String.format(ERR_DISPUTE_NOT_FOUND, disputeId)));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        dispute.setLastUpdateDate(currentTimestamp);
        dispute.setResolutionDate(currentTimestamp);
        dispute.setStatus("Resolved");
        return dispute;
    }
}
