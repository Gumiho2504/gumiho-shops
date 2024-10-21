package com.dailycodework.gumiho_shops.service.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.gumiho_shops.dto.ImageDto;
import com.dailycodework.gumiho_shops.exception.ResourceNotFoundException;
import com.dailycodework.gumiho_shops.model.Image;
import com.dailycodework.gumiho_shops.model.Product;
import com.dailycodework.gumiho_shops.repository.ImageRepository;
import com.dailycodework.gumiho_shops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService iProductService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("No image found with id: " + id);
                });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = iProductService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/image/images/download";

                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image saveImage = imageRepository.save(image);
                saveImage.setDownloadUrl(buildDownloadUrl + saveImage.getId());
                imageRepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(saveImage.getId());
                imageDto.setFileName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                imageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
