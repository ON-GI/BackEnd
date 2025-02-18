package com.ongi.backend.common.initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
@DependsOn("centerDataInitializer")
public class CaregiverDataInitializer {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initializeDatabase() {
        try {
            // ✅ senior 테이블에 데이터가 없으면 실행
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Caregiver", Integer.class);

            if (count != null && count > 0) {
                log.info("✅ Caregiver 테이블에 데이터가 이미 존재하여 초기화 생략");
                return;
            }

            // ✅ SQL 파일 로드 및 실행
            ClassPathResource caregiverResource = new ClassPathResource("sql/caregiver.sql");
            String caregiverSql = new String(FileCopyUtils.copyToByteArray(caregiverResource.getInputStream()), StandardCharsets.UTF_8);

            String[] caregiverSqlStatements = caregiverSql.split(";");
            jdbcTemplate.batchUpdate(caregiverSqlStatements);

            ClassPathResource workConditionResource = new ClassPathResource("sql/caregiver_work_condition.sql");
            String workConditionSql = new String(FileCopyUtils.copyToByteArray(workConditionResource.getInputStream()), StandardCharsets.UTF_8);

            String[] workConditionSqlStatements = workConditionSql.split(";");
            jdbcTemplate.batchUpdate(workConditionSqlStatements);

            log.info("✅ caregiver.sql 실행 완료!");
        } catch (Exception e) {
            log.error("❌ caregiver.sql 실행 중 오류 발생", e);
        }
    }
}
