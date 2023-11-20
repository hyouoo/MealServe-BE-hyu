package com.example.mealserve.domain.store;

import com.example.mealserve.domain.store.dto.StoreRequestDto;
import com.example.mealserve.domain.store.dto.StoreResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;


    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public StoreResponseDto registerStore(@RequestBody StoreRequestDto storeRequestDto){
        return storeService.registerStore(storeRequestDto);
    }

    @PutMapping("/{storeId}")//임의
    public StoreResponseDto updateStore(@PathVariable Long storeId, @RequestBody StoreRequestDto storeRequestDto){
        return storeService.updateStore(storeId, storeRequestDto);
    }

    @DeleteMapping("/{storeId}")//임의
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    @GetMapping("/{storeId}")
    public StoreResponseDto getStore(@PathVariable Long storeId){
        return storeService.getStore(storeId);
    }

    @GetMapping
    public Page<StoreResponseDto> getAllStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return storeService.getAllStores(pageable);
    }

}
