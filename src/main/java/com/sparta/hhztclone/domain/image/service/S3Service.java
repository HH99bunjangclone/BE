package com.sparta.hhztclone.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.hhztclone.domain.image.dto.ImageSaveDto;
import com.sparta.hhztclone.domain.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3Client amazonS3Client;

    @Transactional
    public List<String> saveImages(ImageSaveDto saveDto) {
        List<String> resultList = new ArrayList<>();

        for (MultipartFile multipartFile : saveDto.getImages()) {
            String value = saveImage(multipartFile);
            resultList.add(value);
        }
        return resultList;
    }

    @Transactional
    public String saveImage(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName);
        String filename = image.getStoreName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);
            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();

            image.setAccessUrl(accessUrl);
        } catch (IOException e) {

        }
        return image.getAccessUrl();
    }

    @Transactional
    public void deleteImage(String file) {
        String splitStr = ".com/";
        String filename = file.substring(file.lastIndexOf(splitStr) + splitStr.length());

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName,filename));
    }
}
