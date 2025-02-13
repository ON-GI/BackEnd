package com.ongi.backend.domain.center.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.ongi.backend.domain.center.dto.request.CenterInitializerRequestDto;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.repository.CenterRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CenterDataInitializerService {

    private final AmazonS3 amazonS3;
    private final CenterRepository centerRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private static final String EXCEL_FILE_PATH = "excel/Center.xlsx"; // S3 경로
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostConstruct
    @Transactional
    public void loadCenterDataFromS3() {
        log.info("🔄 S3에서 Center.xlsx 데이터를 가져와 DB에 저장합니다.");

        if (centerRepository.count() > 0) {
            log.info("✅ Center 데이터가 이미 존재하여 초기화 과정을 생략합니다.");
            return;
        }

        try {
            // S3에서 파일 다운로드
            S3Object s3Object = amazonS3.getObject(bucketName, EXCEL_FILE_PATH);
            InputStream inputStream = s3Object.getObjectContent();

            // 엑셀 파싱
            List<Center> centers = parseExcel(inputStream);

            // DB 저장
            centerRepository.saveAll(centers);

            log.info("✅ 총 {}개의 센터 데이터가 저장되었습니다.", centers.size());
        } catch (Exception e) {
            log.error("센터 데이터 로드 중 오류 발생: {}", e.getMessage());
        }
    }

    private List<Center> parseExcel(InputStream inputStream) {
        List<Center> centers = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 헤더 스킵

                String centerCode = getCellValue(row.getCell(0)); // 장기요양기관코드
                String name = getCellValue(row.getCell(1)); // 장기요양기관이름
                String establishmentDateStr = getCellValue(row.getCell(7)); // 지정일자
                String address = getCellValue(row.getCell(9)); // 기관별 상세주소

                // 날짜 변환
                LocalDate establishmentDate = LocalDate.parse(establishmentDateStr, DATE_FORMATTER);
                CenterInitializerRequestDto centerRequestDto = CenterInitializerRequestDto.builder()
                        .name(name)
                        .centerCode(centerCode)
                        .address(address)
                        .establishmentDate(establishmentDate)
                        .build();

                centers.add(Center.from(centerRequestDto));
            }
        } catch (Exception e) {
            log.error("엑셀 파일 파싱 오류: {}", e.getMessage());
        }

        return centers;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default -> "";
        };
    }
}
