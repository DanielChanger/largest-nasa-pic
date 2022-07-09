package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PictureService {
    private final RestTemplate restTemplate;
    private final PicturesApiInfo picturesApiInfo;

    public PictureService(RestTemplate restTemplate, PicturesApiInfo picturesApiInfo) {
        this.restTemplate = restTemplate;
        this.picturesApiInfo = picturesApiInfo;
    }

    public Optional<URI> getLargestPictureUri(int sol) {
        String requestUri = String.format("%s?%s={key}&%s={sol}",
                picturesApiInfo.getPicturesUri(),
                picturesApiInfo.getApiKeyParamName(),
                picturesApiInfo.getSolParamName()
        );
        JsonNode json = restTemplate.getForObject(requestUri, JsonNode.class, picturesApiInfo.getApiKey(), sol);
        if (json == null) {
            return Optional.empty();
        }
        return json.findValuesAsText(picturesApiInfo.getImgSrcNodeName())
                   .parallelStream()
                   .map(URI::create)
                   .map(uri -> new AbstractMap.SimpleEntry<>(uri, getPictureLength(uri)))
                   .max(Map.Entry.comparingByValue())
                   .map(Map.Entry::getKey);
    }

    private long getPictureLength(URI uri) {
        HttpHeaders httpHeaders = restTemplate.headForHeaders(uri);
        URI location = httpHeaders.getLocation();

        while (location != null) {
            httpHeaders = restTemplate.headForHeaders(location.toString());
            location = httpHeaders.getLocation();
        }

        return httpHeaders.getContentLength();
    }
}
