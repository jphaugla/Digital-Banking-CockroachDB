package com.jphaugla.service.impl;

import com.jphaugla.domain.Dispute;
import com.jphaugla.domain.Transaction;

import com.jphaugla.exception.ResourceNotFoundException;
import com.jphaugla.repository.DisputeRepository;
import com.jphaugla.repository.TransactionRepository;

import com.jphaugla.service.DisputeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Dispute getDisputeById(UUID id) {
//		Optional<dispute> dispute = disputeRepository.findById(id);
//		if(dispute.isPresent()) {
//			return dispute.get();
//		}else {
//			throw new ResourceNotFoundException("dispute", "Id", id);
//		}
        return disputeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("dispute", "Id", id));

    }

    @Override
    public Dispute updatedispute(Dispute dispute, UUID id) {

        // we need to check whether dispute with given id is exist in DB or not
        Dispute existingDispute = disputeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("dispute", "Id", id));

        //existingdispute.setEmail(dispute.getEmail());
        // save existing dispute to DB
        disputeRepository.save(existingDispute);
        return existingDispute;
    }

    @Override
    public void deleteDispute(UUID id) {

        // check whether a dispute exist in a DB or not
        disputeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("dispute", "Id", id));
        disputeRepository.deleteById(id);
    }

    public String postDispute(Dispute dispute) {
        log.info("in bs.postDispute with Dispute =" + dispute);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        //  incoming is only a date and not a timestamp, change to a timestamp

        // dispute.setFilingDate(filingDateTime);
        dispute.setLastUpdateDate(currentTimestamp);
        Transaction transaction = transactionRepository.findById(dispute.getTranId()).orElseThrow(() ->
                new ResourceNotFoundException("Dispute", "tranId", dispute.getTranId().toString()));;
        dispute.setChargeBackAmount(transaction.getAmount());
        log.info("before create with Dispute =" + dispute);
        disputeRepository.save(dispute);
        transaction.setDisputeId(dispute.getId());
        transactionRepository.save(transaction);
        return dispute.getId().toString();
    }

    public Dispute putDisputeChargeBackReason(UUID disputeId, String reasonCode) {
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(() ->
                new ResourceNotFoundException("dispute", "Id", disputeId.toString()));
        dispute.setChargeBackReason(reasonCode);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        dispute.setLastUpdateDate(currentTimestamp);
        dispute.setReviewDate(currentTimestamp);
        dispute.setStatus("Investigate");
        disputeRepository.save(dispute);
        return dispute;
    }
    public Dispute acceptDisputeChargeBackReason(UUID disputeId) {
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(() ->
                new ResourceNotFoundException("dispute", "Id", disputeId.toString()));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        dispute.setLastUpdateDate(currentTimestamp);
        dispute.setAcceptanceChargeBackDate(currentTimestamp);
        dispute.setStatus("ChargedBack");
        disputeRepository.save(dispute);
        return dispute;
    }

    public Dispute resolvedDispute(UUID disputeId) {
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(() ->
                new ResourceNotFoundException("dispute", "Id", disputeId.toString()));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        dispute.setLastUpdateDate(currentTimestamp);
        dispute.setResolutionDate(currentTimestamp);
        dispute.setStatus("Resolved");
        return dispute;
    }
}
