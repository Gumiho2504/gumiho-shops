package com.dailycodework.gumiho_shops.service.image;

import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.gumiho_shops.dto.ImageDto;
import com.dailycodework.gumiho_shops.model.Image;
import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);

}
