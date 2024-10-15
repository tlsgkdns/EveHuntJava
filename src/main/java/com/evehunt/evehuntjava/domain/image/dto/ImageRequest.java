package com.evehunt.evehuntjava.domain.image.dto;

import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(MultipartFile file) {

}
