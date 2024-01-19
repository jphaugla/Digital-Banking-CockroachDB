package com.jphaugla.repository;


import com.jphaugla.domain.Transaction;


import org.springframework.data.domain.Limit;
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
   @Query(value="select t.status as status, count(1) as count from Transaction as t group by t.status",
   nativeQuery = false)
   List<TransactionStatusInterface> countByStatusInterface();

    @Query(value = "select t.* from transaction t inner join merchant m on t.merchant=m.name " +
            "where m.category_code = :merchantCategory and t.account_id = :accountId and " +
            "t.posting_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Optional<List<Transaction>> findByMerchantCategoryAndAccountIdAndPostingDateBetween
            (@Param("merchantCategory") String merchantCategory, @Param("accountId") UUID accountId,
             @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "select t.* from transaction t inner join account a on t.account_id=a.id " +
            "where a.card_num = :creditCard and " +
            "t.posting_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Optional<List<Transaction>> findByCreditCardAndPostingDateBetween(String creditCard, Date startDate, Date endDate);

    List<Transaction> findByAccountIdAndPostingDateBetween(UUID accountId, Date startDate, Date endDate);
    @Query(value = "select * from transaction  where account_id = :accountId and posting_date > (CURRENT_DATE - 30)" +
            " order by posting_date limit 20", nativeQuery = true)
    List<Transaction> findByAccountIdAndPostingDateBeforeAndLimit(UUID accountId);

    @Query(value = "select * from transaction where account_id = :accountId and " +
             " :tag=ANY(transaction_tags)", nativeQuery = true)
    Optional<List<Transaction>> findByAccountIdAndTransactionTags(UUID accountId, String tag);

    List<Transaction> findByStatus(String statusToChange, Limit of);
}
