package org.sid.ebankingbachend.repository;

import org.sid.ebankingbachend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying
    @Query("DELETE from Customer c where c.id =: id")
    void deleteCustome(@Param("id") Long id);
}
