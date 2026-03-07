package org.unibl.etf.services.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.services.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {


    @Value("${app.images.dir}")
    private String imagesDir;

    @Override
    public String save(MultipartFile file) throws IOException {
        if(file == null)
            return null;
        String[] fileNameSplit = file.getOriginalFilename().split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        Path uploadPath = Paths.get(imagesDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String imageName = System.currentTimeMillis() + "." + extension;
        Path imagePath = uploadPath.resolve(imageName);
        Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imageName;
    }


    @Override
    public Resource getImage(String name) {
        Path filePath = Paths.get(imagesDir).resolve(name).normalize();
        try{
            return new UrlResource(filePath.toUri());
        }catch (Exception ex){
            return null;
        }
    }
}
