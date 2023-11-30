package com.example.mealserve.domain.account;

import com.example.mealserve.domain.account.entity.Account;
import java.util.Optional;

import com.example.mealserve.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select a from Account a " +
            "join fetch Store " +
            "where a.store = :store")
    Account findByStore(Store store);

}
