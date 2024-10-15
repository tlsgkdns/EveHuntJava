package com.evehunt.evehuntjava.domain.image.api;

import com.evehunt.evehuntjava.domain.image.dto.ImageRequest;
import com.evehunt.evehuntjava.domain.image.dto.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Value("${org.eveHunt.upload.path}")
    String uploadPath;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<ImageResponse> uploadImage(ImageRequest imageRequest)
    {
        if(imageRequest.file() == null) return null;
        MultipartFile file = imageRequest.file();
        List<ImageResponse> list = new ArrayList<ImageResponse>();
        String originName = file.getOriginalFilename();
        String[] names = originName.split("_");
        if(names.length > 1)
        {
            StringBuilder newName = new StringBuilder();
            for(int i = 0; i < names.length; i++)
            {
                newName.append(names[i]);
                if(i != names.length - 1) newName.append("-");
            }
            originName = newName.toString();
        }
        String uuid = UUID.randomUUID().toString();
        Path savePath = Paths.get(uploadPath, uuid+ "_" + originName);
        boolean image = false;
        try
        {
            file.transferTo(savePath);
            if(Files.probeContentType(savePath).startsWith("image"))
            {
                image = true;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        list.add(new ImageResponse(uuid, originName));
        return list;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName)
    {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();
        try
        {
            headers.add("Content-type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @DeleteMapping("/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable String fileName) throws IOException
    {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;
        removed = resource.getFile().delete();
        resultMap.put("result", removed);
        return resultMap;
    }
}
