package com.example.mealserve.domain.menu.dto;

import com.example.mealserve.domain.menu.validation.FileExtension;
import com.example.mealserve.domain.menu.validation.FileSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuRequestDto {

    @NotBlank(message = "메뉴 이름을 입력해주세요")
    private String name;

    @NotNull(message = "가격을 등록해주세요")
    @Min(value = 0, message = "올바른 가격을 입력해주세요")
    private Integer price;

    @FileSize(max = 1048576, message = "이미지 파일은 1MB 이하이어야 합니다")
    @FileExtension(ext = "png,jpg,jpeg", message = "이미지 파일은 png, jpg, jpeg 형식이어야 합니다")
    private MultipartFile image;
}
