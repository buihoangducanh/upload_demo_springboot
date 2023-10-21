package com.anhbui.uploaddemo.service.impl;

import com.anhbui.uploaddemo.dto.FileUploadResponse;
import com.anhbui.uploaddemo.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {
        //get original filename
        var originalFilename = file.getOriginalFilename();

        // extract file extension

        var fileExtension = originalFilename.substring(originalFilename.indexOf('.'));

        // create random file name

        var randomFilename = UUID.randomUUID().toString() + fileExtension;
        // save file

        // create target path
        var targetPath = uploadPath +'/' +randomFilename;


        // check if upload path directory exist or not
        try {
            if (!Files.exists(Path.of(uploadPath))) {
                Files.createDirectories(Path.of(uploadPath));
            }
            Files.copy(file.getInputStream(), Path.of(targetPath));

            return new FileUploadResponse(randomFilename, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
