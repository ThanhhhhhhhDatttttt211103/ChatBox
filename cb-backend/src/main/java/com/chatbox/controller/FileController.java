package com.chatbox.controller;

import com.chatbox.miniochatbox.service.MinIOservice;
import com.chatbox.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@CrossOrigin("*")
@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
    private MinIOservice minIOservice;

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadFileT(@RequestParam("file") MultipartFile file, @RequestParam("code") String code) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String type = getContentType(fileExtension);
        String fileName = code + MessageUtil.SLASH + type + MessageUtil.SLASH + file.getOriginalFilename();
        minIOservice.uploadFile(fileName, file.getContentType(), file.getInputStream());
    }

    @PostMapping("/getFile")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam("fileName") String fileName) {
        try {
            InputStream inputStream = minIOservice.getObject(fileName);
            InputStreamResource resource = new InputStreamResource(inputStream);

            String fileExtension = getFileExtension(fileName);
            String contentType = getContentTypeFromExtension(fileExtension);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private String getContentTypeFromExtension(String extension) {
        return switch (extension.toLowerCase()) {
            case "png" -> "image/png";
            case "jpg" -> "image/jpg";
            case "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }

    private String getContentType(String extension) {
        return switch (extension.toLowerCase()) {
            case "png", "jpg", "jpeg" -> "images";
            default -> "files";
        };
    }
}