package com.example.mealserve;

import com.example.mealserve.domain.account.AccountRepository;
import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.menu.MenuRepository;
import com.example.mealserve.domain.order.OrderRepository;
import com.example.mealserve.domain.order.OrderService;
import com.example.mealserve.domain.order.dto.OrderListResponseDto;
import com.example.mealserve.domain.order.dto.OrderRequestDto;
import com.example.mealserve.domain.order.dto.OrderResponseDto;
import com.example.mealserve.domain.order.entity.Order;
import com.example.mealserve.domain.store.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j(topic = "Test Code")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceIntegrationTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("메뉴 주문")
    void testOrderIn() {
        // given
        Long storeId = 2L;
        List<OrderRequestDto> requestDtoList = List.of(OrderRequestDto.of(1L, 2), OrderRequestDto.of(2L, 3));
        Account customer = accountRepository.findById(2L).orElse(null);

        // when
        menuRepository.findById(2L).ifPresent(menu -> System.out.println("menu = " + menu.getName()));
        OrderResponseDto orderResponseDto = orderService.orderIn(storeId, requestDtoList, customer);

        // then
        System.out.println("Total Price = " + orderResponseDto.getTotalPrice());
        assertEquals(65000, orderResponseDto.getTotalPrice());
        System.out.println("1st Menu name = " + orderResponseDto.getOrders().get(0).getName());
    }

    @Test
    @DisplayName("주문 확인")
    void testGetOrders() {
        // given
        log.info("owner account 조회");
        Account owner = accountRepository.findById(4L).orElse(null);

        // when
        assert owner != null;
        log.info("move to service");
        List<OrderListResponseDto> orderListResponseDtos = orderService.getOrders(owner);

        // then
        log.info("move to Test");
        for (OrderListResponseDto dto : orderListResponseDtos) {
            System.out.println("customer Id = " + dto.getAccountId());
        }
    }

    @Test
    @DisplayName("배달 완료")
    @Rollback(value = false)
    void testCompleteOrders() {
        // given
        Account owner = accountRepository.findById(4L).orElse(null);
        Long accountId = 5L;

        // when
        orderService.completeOrders(owner, accountId);

        // then
        List<Order> orders = orderRepository.findAllByAccountId(accountId);
        for (Order order : orders) {
            System.out.println("order.status = " + order.getStatus());
        }
    }
}
