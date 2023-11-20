package com.example.mealserve.domain.store;

import com.example.mealserve.domain.menu.MenuRepository;
import com.example.mealserve.domain.menu.dto.MenuResponseDto;
import com.example.mealserve.domain.menu.entity.Menu;
import com.example.mealserve.domain.store.dto.StoreRequestDto;
import com.example.mealserve.domain.store.dto.StoreResponseDto;
import com.example.mealserve.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public StoreService(StoreRepository storeRepository, MenuRepository menuRepository) {
        this.storeRepository = storeRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public StoreResponseDto registerStore(StoreRequestDto storeRequestDto) {
        Store store = Store.builder()
                .name(storeRequestDto.getName())
                .address(storeRequestDto.getAddress())
                .tel(storeRequestDto.getTel())
                .build();

        storeRepository.save(store);

        return StoreResponseDto.from(store); // 메뉴 정보가 있다면 여기에 추가
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto storeRequestDto) {
        Store store = validateStore(storeId);

        store.update(storeRequestDto);

        storeRepository.save(store);

        return new StoreResponseDto(store);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        Store store = validateStore(storeId);
        storeRepository.delete(store);
    }

    @Transactional(readOnly = true)
    public StoreResponseDto getStore(Long storeId) {
        Store store = validateStore(storeId);

        List<Menu> menus = menuRepository.findByStoreId(storeId);
        List<MenuResponseDto> menuResponseDto = menus.stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());

        return new  StoreResponseDto(store, menuResponseDto);

    }
    @Transactional(readOnly = true)
    public Page<StoreResponseDto> getAllStores(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(StoreResponseDto::new);
    }

    private Store validateStore(Long storeId){
        return storeRepository.findById(storeId).orElseThrow(()
                -> new IllegalArgumentException("업장을 찾지 못했습니다."));
    }

}
