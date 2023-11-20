package com.example.mealserve.domain.menu;

import com.example.mealserve.domain.menu.dto.MenuRequestDto;
import com.example.mealserve.domain.menu.dto.MenuResponseDto;
import com.example.mealserve.domain.menu.entity.Menu;
import com.example.mealserve.domain.store.StoreRepository;
import com.example.mealserve.domain.store.entity.Store;
import com.example.mealserve.exception.CustomException;
import com.example.mealserve.exception.ErrorCode;
import com.example.mealserve.s3.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final S3Upload s3Upload;

    @Transactional
    public MenuResponseDto addMenu(MenuRequestDto menuRequestDto, Long storeId) throws IOException {
        Store store = validateStoreById(storeId);
        validateMenuByName(menuRequestDto.getName());
        String imageUrl = uploadImageToS3(menuRequestDto.getImage());
        Menu menu = Menu.of(
                menuRequestDto.getName(),
                menuRequestDto.getPrice(),
                imageUrl,
                store
        );
        menuRepository.save(menu);
        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = validateMenuByIdAndGetMenu(menuId);
        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto, MultipartFile image) throws IOException {
        Menu menu = validateMenuByIdAndGetMenu(menuId);
        String imageUrl = uploadImageToS3(image);
        menu.updateMenuDetail(menuRequestDto.getName(), menuRequestDto.getPrice(), imageUrl);
        Menu updateMenu = menuRepository.save(menu);
        return new MenuResponseDto(updateMenu);
    }

    @Transactional
    public Long deleteMenu(Long menuId) {
        Menu menu = validateMenuByIdAndGetMenu(menuId);
        Long storeId = menu.getStore().getId();
        menuRepository.delete(menu);
        return storeId; // 삭제된 메뉴의 상점 ID 반환
    }

    //이미지 업로드
    private String uploadImageToS3(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new CustomException(ErrorCode.ELEMENTS_IS_REQUIRED); // 혹은 기본 이미지 URL을 반환
        }
        return s3Upload.upload(image);
    }

    //메뉴 중복 검사
    private void validateMenuByName(String name) {
        Menu registeredMenu = menuRepository.findByName(name);
        if (registeredMenu != null) {
            throw new CustomException(ErrorCode.MENU_ALREADY_EXIST);
        }
    }

    //매뉴 중복 검사
    private Menu validateMenuByIdAndGetMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
    }

    private Store validateStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }
}
