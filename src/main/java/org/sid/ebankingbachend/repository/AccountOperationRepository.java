package org.sid.ebankingbachend.repository;

import org.sid.ebankingbachend.dtos.AccountOperationDTO;
import org.sid.ebankingbachend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    public List<AccountOperation> findByBankAccountId(String accountId);
    public Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);

}
