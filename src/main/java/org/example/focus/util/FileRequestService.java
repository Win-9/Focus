package org.example.focus.util;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.request.ImageRequestDto;
import org.example.focus.exception.ErrorCode;
import org.example.focus.exception.notFound.FileBoundException;
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
    public String sendBookImageReqeust(ImageRequestDto request, MultipartFile file) {
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

        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is5xxServerError()) {
            throw new FileBoundException(ErrorCode.IMAGE_SAVE_EXCEPTION);
        }

        return response.getBody();
    }

    public String deleteBookImage(ImageRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<ImageRequestDto> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                EncryptUtil.imageDeleteUrl,
                requestEntity,
                String.class);
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is5xxServerError()) {
            throw new FileBoundException(ErrorCode.IMAGE_SAVE_EXCEPTION);
        }

        return response.getBody();
    }
}
