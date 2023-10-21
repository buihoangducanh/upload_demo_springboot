package com.anhbui.uploaddemo.service;


import com.anhbui.uploaddemo.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileUploadResponse uploadFile(MultipartFile file);
}
