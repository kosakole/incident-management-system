package org.unibl.etf.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String save(MultipartFile file) throws IOException;
    Resource getImage(String name);
}
