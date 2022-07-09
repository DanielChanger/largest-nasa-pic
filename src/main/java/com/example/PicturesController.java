package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/pictures")
@RequiredArgsConstructor
public class PicturesController {
    private final PictureService pictureService;

    @GetMapping("/{sol}/largest")
    public ResponseEntity<String> getLargestPictureUrl(@PathVariable int sol) {
        Optional<URI> url = pictureService.getLargestPictureUri(sol);
        if (url.isEmpty()) {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(url.get())
                             .build();
    }
}
