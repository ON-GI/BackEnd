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
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
@DependsOn("centerDataInitializer")
public class SeniorDataInitializer {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initializeDatabase() {
        try {
            // ✅ senior 테이블에 데이터가 없으면 실행
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM senior", Integer.class);
            if (count != null && count > 0) {
                log.info("✅ senior 테이블에 데이터가 이미 존재하여 초기화 생략");
                return;
            }

            // ✅ 랜덤한 center_id 목록 생성
            Random random = new Random();
            List<Integer> centerIds = IntStream.range(0, 10)  // 10개 더미 데이터 생성
                    .mapToObj(i -> random.nextInt(30000) + 1)
                    .collect(Collectors.toList());

            // ✅ SQL 파일 로드 및 실행
            ClassPathResource resource = new ClassPathResource("sql/senior.sql");
            String sql = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);

            // ✅ `INSERT`문 중 `?`를 랜덤 center_id 값으로 대체
            String[] queries = sql.split(";");
            for (int i = 0; i < queries.length; i++) {
                if (!queries[i].trim().isEmpty()) {
                    String finalQuery = queries[i].replace("?", String.valueOf(centerIds.get(i % centerIds.size())));
                    jdbcTemplate.execute(finalQuery);
                }
            }

            log.info("✅ senior.sql 실행 완료!");
        } catch (Exception e) {
            log.error("❌ senior.sql 실행 중 오류 발생", e);
        }
    }
}
