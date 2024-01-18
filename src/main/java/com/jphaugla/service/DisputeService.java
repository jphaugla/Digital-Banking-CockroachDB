package com.jphaugla.service;

import com.jphaugla.domain.Dispute;
import com.jphaugla.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface DisputeService {
    UUID saveDispute (Dispute  dispute );
    Dispute  getDisputeById(UUID id) throws NotFoundException;
    void deleteDispute(UUID id) throws NotFoundException;
    List<Dispute> getAllDisputes();

    Dispute updatedispute(Dispute dispute, UUID id) throws NotFoundException;

    Dispute putDisputeChargeBackReason(UUID disputeId, String reasonCode) throws NotFoundException;
    Dispute acceptDisputeChargeBackReason(UUID disputeId) throws NotFoundException;

    Dispute resolvedDispute(UUID disputeId) throws NotFoundException;

    String postDispute(Dispute dispute) throws NotFoundException;
}
