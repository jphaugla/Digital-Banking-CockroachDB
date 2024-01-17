package com.jphaugla.service;

import com.jphaugla.domain.Dispute;

import java.util.List;
import java.util.UUID;

public interface DisputeService {
    UUID saveDispute (Dispute  dispute );
    Dispute  getDisputeById(UUID id);
    void deleteDispute(UUID id);
    List<Dispute> getAllDisputes();

    Dispute updatedispute(Dispute dispute, UUID id);

    Dispute putDisputeChargeBackReason(UUID disputeId, String reasonCode);
    Dispute acceptDisputeChargeBackReason(UUID disputeId);

    Dispute resolvedDispute(UUID disputeId);

    String postDispute(Dispute dispute);
}
