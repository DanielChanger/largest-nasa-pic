package com.example;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PicturesApiInfo {
    @Value("${nasa.api-key}")
    private String apiKey;
    @Value("${nasa.pictures.uri}")
    private String picturesUri;
    @Value("${nasa.pictures.param.sol.name}")
    private String solParamName;
    @Value("${nasa.pictures.param.api-key.name}")
    private String apiKeyParamName;
    @Value("${nasa.pictures.api.response.img-uri.name}")
    private String imgSrcNodeName;
}
