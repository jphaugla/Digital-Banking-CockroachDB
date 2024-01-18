package com.jphaugla.repository;


import com.jphaugla.domain.Transaction;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<List<Transaction>> findByMerchantAndAccountIdAndPostingDateBetween(String merchant, UUID accountId,
                                                                                Date startDate, Date EndDate);
   /* @Query("select t.id, t.accountId, t.amount, t.amountType, t.description, t.disputeId, t.initialDate," +
            " t.location, t.merchant, t.originalAmount, t.postingDate, t.referenceKeyType," +
            " t.referenceKeyValue, t.settlementDate, t.status, t.tranCode, t.transactionReturn, t.transactionTags " +
            "from transaction t inner join merchant m on t.merchant=m.name where m.categoryCode = :merchantCategory " +
            "and t.postingDate BETWEEN :startDate AND :endDate and accountId = :accountId")
    */

    @Query(value = " select t.* from transaction t inner join merchant m on t.merchant=m.name " +
            "where m.category_code = :merchantCategory and t.account_id = :accountId and " +
            "t.posting_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Optional<List<Transaction>> findByMerchantCategoryAndAccountIdAndPostingDateBetween
            (@Param("merchantCategory") String merchantCategory, @Param("accountId") UUID accountId,
             @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
