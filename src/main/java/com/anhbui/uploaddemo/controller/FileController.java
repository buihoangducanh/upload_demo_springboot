package com.anhbui.uploaddemo.controller;

import com.anhbui.uploaddemo.dto.FileUploadResponse;
import com.anhbui.uploaddemo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileController {

    @Value("${file.upload-path}")
    private String uploadPath;
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        try {
            FileUploadResponse fileUploadResponse = fileService.uploadFile(file);
            return new ResponseEntity<>(fileUploadResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("File upload unsuccessfully!");
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String imageName) throws IOException {

        File imageFile = new File(uploadPath+"/" + imageName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(Files.readAllBytes(imageFile.toPath()), headers, HttpStatus.OK);
    }
}
