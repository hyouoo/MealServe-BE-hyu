package com.example.mealserve.domain.order;

import com.example.mealserve.domain.order.entity.Order;
import com.example.mealserve.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " +
            "join fetch o.account " +
            "join fetch o.menu " +
            "where o.status = 'PREPARE' AND o.menu.store.id = :storeId " +
            "order by o.createdAt")
    List<Order> findAllByStoreIdAndStatus(Long storeId);

    @Query("select o from Order o " +
            "join fetch o.menu " +
            "where o.status = 'PREPARE' AND o.account.id = :accountId AND o.menu.store = :store")
    List<Order> findAllByAccountIdAndStoreAndStatus(Long accountId, Store store);
}
