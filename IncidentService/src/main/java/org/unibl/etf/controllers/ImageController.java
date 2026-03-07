package org.unibl.etf.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.services.ImageService;

@RestController
@RequestMapping(value = "/api/incidents/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{image}")
    public ResponseEntity<Resource> getImage(@PathVariable(value = "image") String image){
        if(image == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(imageService.getImage(image));
    }
}
