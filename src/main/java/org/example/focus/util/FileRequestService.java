package org.example.focus.util;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.request.ImageRequestDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class FileRequestService {

    private final RestTemplate restTemplate = new RestTemplate();
    public String sendBookCoverImageReqeust(ImageRequestDto request, MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("request", request);
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                EncryptUtil.imageSaveUrl,
                requestEntity,
                String.class);

        return response.getBody();
    }
}
