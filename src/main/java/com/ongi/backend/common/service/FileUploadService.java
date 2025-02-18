package com.ongi.backend.common.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.exception.FileUploadErrorCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * S3에 파일을 업로드하고, 업로드된 파일의 URL을 반환.
     *
     * @param file 업로드할 파일 (MultipartFile 형식)
     * @return 업로드된 파일의 S3 URL
     * @throws ApplicationException 파일 업로드 실패 시 예외 발생
     */
    public String uploadFileToS3(MultipartFile file) {
        String fileName = createFileName(file);
        String fileUrl = getFileUrl(fileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new ApplicationException(FileUploadErrorCase.FILE_UPLOAD_FAILED);
        }

        log.info("file url : " + fileUrl + " has upload to S3");
        return fileUrl;
    }

    /**
     * S3에서 특정 파일을 삭제.
     *
     * @param imageUrl 삭제할 파일의 S3 URL
     */
    public void deleteImage(String imageUrl) {
        String splitStr = ".com/";
        String fileName = imageUrl.substring(imageUrl.lastIndexOf(splitStr) + splitStr.length());

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    /**
     * 업로드할 파일의 고유한 이름을 생성. (날짜 기반 + 랜덤 UUID)
     *
     * @param file 업로드할 파일 (MultipartFile 형식)
     * @return S3에 저장될 고유한 파일 경로
     */
    private String createFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 확장자 추출

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); // 날짜 기반 폴더
        String randomFileName = UUID.randomUUID().toString(); // 랜덤 UUID

        // 예: image/2025/02/12/랜덤값.png
        return "image/" + datePath + "/" + randomFileName + extension;
    }

    /**
     * S3에서 해당 파일의 접근 가능한 URL을 생성.
     *
     * @param fileName S3에 저장된 파일의 경로
     * @return 해당 파일의 S3 URL
     */
    private String getFileUrl(String fileName) {
        return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
    }
}
