package com.example.mealserve.domain.menu;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.menu.dto.MenuRequestDto;
import com.example.mealserve.domain.menu.dto.MenuResponseDto;
import com.example.mealserve.domain.store.entity.Store;
import com.example.mealserve.global.exception.CustomException;
import com.example.mealserve.global.exception.ErrorCode;
import com.example.mealserve.global.tool.LoginAccount;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@MultipartConfig
@RequiredArgsConstructor
@RequestMapping("/api/stores/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('OWNER')")
    public ResponseEntity<MenuResponseDto> addMenu(@RequestPart("menu") @Validated MenuRequestDto menuRequestDto,
                                                   @RequestPart("image") MultipartFile image,
                                                   @LoginAccount Account account) throws IOException {

        Store store = account.getStore();
        if (store == null) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }
        MenuResponseDto responseBody = menuService.addMenu(menuRequestDto, store.getId(), image);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long menuId) {
        MenuResponseDto responseBody = menuService.getMenu(menuId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PatchMapping("/{menuId}")
    @PreAuthorize("hasAnyRole('OWNER')")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable Long menuId,
                                                      @ModelAttribute MenuRequestDto menuRequestDto,
                                                      @RequestParam(value = "image", required = false)
                                                          MultipartFile image) throws IOException {
        MenuResponseDto responseBody = menuService.updateMenu(menuId, menuRequestDto, image);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasAnyRole('OWNER')")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        Long storeId = menuService.deleteMenu(menuId);
        URI storeUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/stores/{storeId}")
            .buildAndExpand(storeId)
            .toUri();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(storeUri).build();
    }
}
