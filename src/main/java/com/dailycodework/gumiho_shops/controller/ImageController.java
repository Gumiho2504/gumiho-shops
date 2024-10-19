package com.dailycodework.gumiho_shops.controller;

import java.util.List;

import com.dailycodework.gumiho_shops.dto.ImageDto;
import com.dailycodework.gumiho_shops.exception.ResourceNotFoundException;
import com.dailycodework.gumiho_shops.model.Image;
import com.dailycodework.gumiho_shops.response.ApiResponse;
import com.dailycodework.gumiho_shops.service.image.IImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    new ApiResponse("Upload failed!", e.getMessage()));
        }

    }

    @GetMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image == null) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(
                    image.getImage().getBytes(1, (int) image.getImage().length()));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);

        } catch (SQLException e) {
            // Log the error and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            // Catch other exceptions like NullPointerException or IOException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(
            @PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));

    }

    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        try {
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", null));
    }

}
