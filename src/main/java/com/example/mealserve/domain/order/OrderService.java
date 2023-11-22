package com.example.mealserve.domain.order;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.menu.MenuRepository;
import com.example.mealserve.domain.menu.entity.Menu;
import com.example.mealserve.domain.order.dto.OrderDto;
import com.example.mealserve.domain.order.dto.OrderListResponseDto;
import com.example.mealserve.domain.order.dto.OrderRequestDto;
import com.example.mealserve.domain.order.dto.OrderResponseDto;
import com.example.mealserve.domain.order.entity.DeliverStatus;
import com.example.mealserve.domain.order.entity.Order;
import com.example.mealserve.domain.store.StoreRepository;
import com.example.mealserve.domain.store.entity.Store;
import com.example.mealserve.global.exception.CustomException;
import com.example.mealserve.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "OrderService")
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public OrderResponseDto orderIn(Long storeId, List<OrderRequestDto> requestDtoList, Account customer) {
        Store store = findStore(storeId);
        List<OrderDto> orderDtoList = new ArrayList<>();
        int totalPrice = 0;

        for (OrderRequestDto requestDto : requestDtoList) {
            Menu menu = menuRepository.findById(requestDto.getMenuId())
                    .orElseThrow(() ->
                            new CustomException(ErrorCode.MENU_NOT_FOUND));
            Order newOrder = Order.of(customer, menu, requestDto.getQuantity(), DeliverStatus.PREPARE);
            orderRepository.save(newOrder);

            orderDtoList.add(OrderDto.fromCustomer(newOrder));
            totalPrice += menu.getPrice() * newOrder.getQuantity();
        }

        if (customer.getPoint() < totalPrice)
            throw new CustomException(ErrorCode.INSUFFICIENT_POINT);

        return OrderResponseDto.of(orderDtoList, totalPrice);
    }

    @Transactional(readOnly = true)
    public List<OrderListResponseDto> getOrders(Account owner) {
        log.info("store 조회");
        Store store = owner.getStore();
        log.info("fetch join start");
        List<Order> orders = orderRepository.findAllByStoreId(store.getId());
        log.info("fetch join end");
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<OrderListResponseDto> orderListResponseDtos = new ArrayList<>();

        int i = 0, j = 0, totalPrice = 0;
        log.info("while start");
        while (true) {
            Account user = orders.get(i).getAccount();
            if (user == orders.get(i + j).getAccount()) {
                orderDtoList.add(OrderDto.fromOwner(orders.get(i + j)));
                totalPrice += orders.get(i + j).getMenu().getPrice() * orders.get(i + j).getQuantity();
                j++;
            } else {
                orderListResponseDtos.add(OrderListResponseDto.of(user, orderDtoList, totalPrice));
                i += j;
                j = 0;
                totalPrice = 0;
                orderDtoList.clear();
            }
            if (i + j == orders.size()) {
                orderListResponseDtos.add(OrderListResponseDto.of(user, orderDtoList, totalPrice));
                break;
            }
        }
        return orderListResponseDtos;
    }

    @Transactional
    public void completeOrders(Account owner, Long accountId) {
        List<Order> orders = orderRepository.findAllByAccountId(accountId);
        for (Order order : orders) {
            owner.earnPoint(order.getMenu().getPrice() * order.getQuantity());
            order.complete();
        }
    }

    private Store findStore(Long id) {
        return storeRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.STORE_NOT_FOUND));
    }
}
