package com.example.mealserve.domain.menu;

import com.example.mealserve.domain.menu.dto.MenuRequestDto;
import com.example.mealserve.domain.menu.dto.MenuResponseDto;
import com.example.mealserve.domain.menu.entity.Menu;
import com.example.mealserve.domain.store.StoreRepository;
import com.example.mealserve.domain.store.entity.Store;
import com.example.mealserve.global.exception.CustomException;
import com.example.mealserve.global.exception.ErrorCode;
import com.example.mealserve.global.s3.S3Upload;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final S3Upload s3Upload;

    @Transactional
    public MenuResponseDto addMenu(MenuRequestDto menuRequestDto, Long storeId) throws IOException {
        Store store = validateStoreById(storeId);
        String imageUrl = uploadImageToS3(menuRequestDto.getImage());

        Menu menu = Menu.of(menuRequestDto, imageUrl, store);

        validateMenuByName(menuRequestDto.getName());

        menuRepository.save(menu);
        return MenuResponseDto.from(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = validateMenuByIdAndGetMenu(menuId);
        return MenuResponseDto.from(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto, MultipartFile image) throws IOException {
        Menu menu = validateMenuByIdAndGetMenu(menuId);
        String currentImageUrl = menu.getImage();
        String imageUrl = updateImageToS3(image, currentImageUrl);
        menu.updateMenuDetail(menuRequestDto.getName(), menuRequestDto.getPrice(), imageUrl);
        return MenuResponseDto.from(menu);
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
            throw new CustomException(ErrorCode.ELEMENTS_IS_REQUIRED);
        }
        return s3Upload.upload(image);
    }

    //이미지 수정
    private String updateImageToS3(MultipartFile image, String currentImageUrl) throws IOException {
        return Optional.ofNullable(image)
                .filter(imageFIle -> !imageFIle.isEmpty())
                .map(imageFIle -> {
                    try {
                        return s3Upload.upload(image);
                    } catch (IOException e) {
                        throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
                    }
                })
                .orElse(currentImageUrl); //기존에 등록되어있는 이미지 URL을 반환
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
