package com.viveksinghspringsecurity.repository;

import com.viveksinghspringsecurity.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@PreAuthorize("hasRole('ROOT')")
public interface LoanRepository extends CrudRepository<Loans,Long> {
    List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);
}
