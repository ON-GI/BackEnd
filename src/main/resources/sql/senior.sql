-- ✅ senior 테이블 (어르신 10명 유지 + 요양보호사 근무조건 고려하여 최적화)
INSERT INTO senior (id, name, birth_date, age, gender, grade_type, city, district, town, residence_type, profile_image_url, center_id, created_at, updated_at)
VALUES
    (1, '한정자', '1944-05-20', 81, 'FEMALE', 3, '서울특별시', '강남구', '역삼동', 'ALONE', NULL, 1, NOW(), NOW()),
    (2, '박명호', '1940-08-15', 85, 'MALE', 2, '서울특별시', '서초구', '반포동', 'WITH_SPOUSE_AWAY', NULL, 1, NOW(), NOW()),
    (3, '김순자', '1947-03-10', 78, 'FEMALE', 4, '서울특별시', '송파구', '잠실동', 'WITH_FAMILY_HOME', NULL, 1, NOW(), NOW()),
    (4, '이대철', '1942-11-25', 83, 'MALE', 1, '서울특별시', '광진구', '구의동', 'ALONE', NULL, 1, NOW(), NOW()),
    (5, '정영희', '1950-07-01', 75, 'FEMALE', 3, '서울특별시', '강남구', '논현동', 'WITH_SPOUSE_AWAY', NULL, 1, NOW(), NOW()),
    (6, '최병철', '1938-12-05', 87, 'MALE', 2, '서울특별시', '서초구', '양재동', 'WITH_FAMILY_AWAY', NULL, 1, NOW(), NOW()),
    (7, '박정숙', '1945-09-15', 80, 'FEMALE', 3, '서울특별시', '송파구', '풍납동', 'ALONE', NULL, 1, NOW(), NOW()),
    (8, '이만수', '1941-06-20', 84, 'MALE', 2, '서울특별시', '광진구', '화양동', 'WITH_SPOUSE_HOME', NULL, 1, NOW(), NOW()),
    (9, '김미경', '1948-02-28', 77, 'FEMALE', 4, '서울특별시', '강남구', '도곡동', 'WITH_FAMILY_HOME', NULL, 1, NOW(), NOW()),
    (10, '강동원', '1937-11-11', 88, 'MALE', 1, '서울특별시', '서초구', '서초동', 'WITH_FAMILY_AWAY', NULL, 1, NOW(), NOW()),
    (11, '이영자', '1943-01-15', 82, 'FEMALE', 4, '서울특별시', '강남구', '대치동', 'WITH_FAMILY_AWAY', NULL, 1, NOW(), NOW()),
    (12, '윤재호', '1939-06-22', 86, 'MALE', 3, '서울특별시', '서초구', '방배동', 'WITH_SPOUSE_HOME', NULL, 1, NOW(), NOW()),
    (13, '최수정', '1946-12-10', 79, 'FEMALE', 2, '서울특별시', '송파구', '석촌동', 'ALONE', NULL, 1, NOW(), NOW()),
    (14, '강용수', '1945-07-07', 80, 'MALE', 1, '서울특별시', '광진구', '자양동', 'WITH_SPOUSE_AWAY', NULL, 1, NOW(), NOW()),
    (15, '문지영', '1944-03-20', 81, 'FEMALE', 4, '서울특별시', '강남구', '논현동', 'WITH_FAMILY_HOME', NULL, 1, NOW(), NOW()),
    (16, '박세훈', '1948-09-18', 77, 'MALE', 3, '서울특별시', '서초구', '양재동', 'WITH_FAMILY_AWAY', NULL, 1, NOW(), NOW()),
    (17, '김민희', '1949-11-25', 76, 'FEMALE', 2, '서울특별시', '송파구', '거여동', 'WITH_SPOUSE_HOME', NULL, 1, NOW(), NOW()),
    (18, '조윤호', '1941-05-30', 84, 'MALE', 1, '서울특별시', '광진구', '구의동', 'WITH_FAMILY_HOME', NULL, 1, NOW(), NOW()),
    (19, '한미숙', '1947-02-10', 78, 'FEMALE', 3, '서울특별시', '강남구', '삼성동', 'ALONE', NULL, 1, NOW(), NOW()),
    (20, '오세준', '1938-10-05', 87, 'MALE', 2, '서울특별시', '서초구', '서초동', 'WITH_SPOUSE_AWAY', NULL, 1, NOW(), NOW());

-- ✅ senior_care_condition 테이블 더미 데이터
INSERT INTO senior_care_condition (id, senior_id, created_at, updated_at)
VALUES
    (1, 1, NOW(), NOW()), (2, 2, NOW(), NOW()), (3, 3, NOW(), NOW()),
    (4, 4, NOW(), NOW()), (5, 5, NOW(), NOW()), (6, 6, NOW(), NOW()),
    (7, 7, NOW(), NOW()), (8, 8, NOW(), NOW()), (9, 9, NOW(), NOW()),
    (10, 10, NOW(), NOW()), (11, 11, NOW(), NOW()), (12, 12, NOW(), NOW()), (13, 13, NOW(), NOW()),
    (14, 14, NOW(), NOW()), (15, 15, NOW(), NOW()), (16, 16, NOW(), NOW()),
    (17, 17, NOW(), NOW()), (18, 18, NOW(), NOW()), (19, 19, NOW(), NOW()),
    (20, 20, NOW(), NOW());

-- ✅ senior_care_type_mapping 테이블 더미 데이터
INSERT INTO senior_care_type_mapping (id, senior_care_condition_id, senior_care_detail, created_at, updated_at)
VALUES
    (1, 1, 'DAILY_CLEANING', NOW(), NOW()), (2, 1, 'MOBILITY_ASSIST', NOW(), NOW()),
    (3, 2, 'FEEDING_ASSIST', NOW(), NOW()), (4, 2, 'TOILETING_ASSISTANCE', NOW(), NOW()),
    (5, 3, 'DAILY_BATHING', NOW(), NOW()), (6, 3, 'MOBILITY_WHEELCHAIR', NOW(), NOW()),
    (7, 4, 'DAILY_HOSPITAL', NOW(), NOW()), (8, 4, 'FEEDING_COOKING', NOW(), NOW()),
    (9, 5, 'TOILETING_BED_CARE', NOW(), NOW()), (10, 5, 'DAILY_COGNITIVE', NOW(), NOW()),
    (11, 6, 'DAILY_EXERCISE', NOW(), NOW()), (12, 6, 'DAILY_EMOTIONAL_SUPPORT', NOW(), NOW()),
    (13, 7, 'FEEDING_TUBE', NOW(), NOW()), (14, 7, 'MOBILITY_IMMOBILE', NOW(), NOW()),
    (15, 8, 'TOILETING_DEVICE', NOW(), NOW()), (16, 8, 'DAILY_CLEANING', NOW(), NOW()),
    (17, 9, 'DAILY_BATHING', NOW(), NOW()), (18, 9, 'MOBILITY_ASSIST', NOW(), NOW()),
    (19, 10, 'TOILETING_ASSISTANCE', NOW(), NOW()), (20, 10, 'FEEDING_COOKING', NOW(), NOW()),
    (21, 11, 'FEEDING_TUBE', NOW(), NOW()), (22, 11, 'MOBILITY_IMMOBILE', NOW(), NOW()),
    (23, 12, 'DAILY_CLEANING', NOW(), NOW()), (24, 12, 'TOILETING_BED_CARE', NOW(), NOW()),
    (25, 13, 'DAILY_EXERCISE', NOW(), NOW()), (26, 13, 'DAILY_EMOTIONAL_SUPPORT', NOW(), NOW()),
    (27, 14, 'FEEDING_TUBE', NOW(), NOW()), (28, 14, 'MOBILITY_ASSIST', NOW(), NOW()),
    (29, 15, 'DAILY_HOSPITAL', NOW(), NOW()), (30, 15, 'DAILY_BATHING', NOW(), NOW()),
    (31, 16, 'MOBILITY_WHEELCHAIR', NOW(), NOW()), (32, 16, 'DAILY_COGNITIVE', NOW(), NOW()),
    (33, 17, 'DAILY_HOSPITAL', NOW(), NOW()), (34, 17, 'TOILETING_DEVICE', NOW(), NOW()),
    (35, 18, 'MOBILITY_IMMOBILE', NOW(), NOW()), (36, 18, 'FEEDING_TUBE', NOW(), NOW()),
    (37, 19, 'DAILY_CLEANING', NOW(), NOW()), (38, 19, 'MOBILITY_ASSIST', NOW(), NOW()),
    (39, 20, 'DAILY_BATHING', NOW(), NOW()), (40, 20, 'TOILETING_ASSISTANCE', NOW(), NOW());

-- ✅ senior_care_time 테이블 더미 데이터
INSERT INTO senior_care_time (id, care_condition_id, day_of_week, start_time, end_time, created_at, updated_at)
VALUES
    -- 🔹 한정자: 주 5일, 하루 2회 서비스
    (1, 1, 'MONDAY', '09:00:00', '12:00:00', NOW(), NOW()), (2, 1, 'MONDAY', '15:00:00', '18:00:00', NOW(), NOW()),
    (3, 1, 'WEDNESDAY', '09:00:00', '12:00:00', NOW(), NOW()), (4, 1, 'WEDNESDAY', '15:00:00', '18:00:00', NOW(), NOW()),
    (5, 1, 'FRIDAY', '10:00:00', '13:00:00', NOW(), NOW()),

    -- 🔹 박명호: 주 4일, 오전 집중
    (6, 2, 'TUESDAY', '08:00:00', '11:00:00', NOW(), NOW()), (7, 2, 'THURSDAY', '08:00:00', '11:00:00', NOW(), NOW()),
    (8, 2, 'SATURDAY', '09:00:00', '12:00:00', NOW(), NOW()),

    -- 🔹 김순자: 주 6일, 하루 1회 서비스
    (9, 3, 'MONDAY', '13:00:00', '16:00:00', NOW(), NOW()), (10, 3, 'TUESDAY', '13:00:00', '16:00:00', NOW(), NOW()),
    (11, 3, 'WEDNESDAY', '13:00:00', '16:00:00', NOW(), NOW()), (12, 3, 'THURSDAY', '13:00:00', '16:00:00', NOW(), NOW()),
    (13, 3, 'FRIDAY', '13:00:00', '16:00:00', NOW(), NOW()), (14, 3, 'SATURDAY', '13:00:00', '16:00:00', NOW(), NOW()),

    -- 🔹 이대철: 주 3일, 오후 중심
    (15, 4, 'TUESDAY', '14:00:00', '17:00:00', NOW(), NOW()), (16, 4, 'THURSDAY', '14:00:00', '17:00:00', NOW(), NOW()),
    (17, 4, 'SUNDAY', '14:00:00', '17:00:00', NOW(), NOW()),

    -- 🔹 정영희: 주 5일, 하루 2회
    (18, 5, 'MONDAY', '09:00:00', '12:00:00', NOW(), NOW()), (19, 5, 'MONDAY', '18:00:00', '21:00:00', NOW(), NOW()),
    (20, 5, 'WEDNESDAY', '09:00:00', '12:00:00', NOW(), NOW()), (21, 5, 'WEDNESDAY', '18:00:00', '21:00:00', NOW(), NOW()),
    (22, 5, 'FRIDAY', '10:00:00', '13:00:00', NOW(), NOW()),

    -- 🔹 최병철: 주 3일, 오후 서비스
    (23, 6, 'TUESDAY', '15:00:00', '18:00:00', NOW(), NOW()), (24, 6, 'THURSDAY', '15:00:00', '18:00:00', NOW(), NOW()),
    (25, 6, 'SATURDAY', '15:00:00', '18:00:00', NOW(), NOW()),

    -- 🔹 박정숙: 주 4일, 저녁 시간
    (26, 7, 'MONDAY', '18:00:00', '21:00:00', NOW(), NOW()), (27, 7, 'WEDNESDAY', '18:00:00', '21:00:00', NOW(), NOW()),
    (28, 7, 'FRIDAY', '18:00:00', '21:00:00', NOW(), NOW()),

    -- 🔹 이만수: 주 5일, 오후-저녁
    (29, 8, 'TUESDAY', '14:00:00', '17:00:00', NOW(), NOW()), (30, 8, 'THURSDAY', '14:00:00', '17:00:00', NOW(), NOW()),
    (31, 8, 'FRIDAY', '14:00:00', '17:00:00', NOW(), NOW()), (32, 8, 'SUNDAY', '14:00:00', '17:00:00', NOW(), NOW()),

    -- 🔹 김미경: 주 6일, 오전-오후
    (33, 9, 'MONDAY', '09:00:00', '12:00:00', NOW(), NOW()), (34, 9, 'TUESDAY', '09:00:00', '12:00:00', NOW(), NOW()),
    (35, 9, 'WEDNESDAY', '09:00:00', '12:00:00', NOW(), NOW()), (36, 9, 'THURSDAY', '09:00:00', '12:00:00', NOW(), NOW()),
    (37, 9, 'FRIDAY', '09:00:00', '12:00:00', NOW(), NOW()), (38, 9, 'SATURDAY', '09:00:00', '12:00:00', NOW(), NOW()),
    (39, 11, 'MONDAY', '09:00:00', '12:00:00', NOW(), NOW()), (40, 11, 'MONDAY', '15:00:00', '18:00:00', NOW(), NOW()),
    (41, 11, 'WEDNESDAY', '10:00:00', '13:00:00', NOW(), NOW()), (42, 11, 'FRIDAY', '16:00:00', '19:00:00', NOW(), NOW()),

    -- 🔹 윤재호: 주 4일, 오전 집중
    (43, 12, 'TUESDAY', '08:00:00', '11:00:00', NOW(), NOW()), (44, 12, 'THURSDAY', '09:00:00', '12:00:00', NOW(), NOW()),
    (45, 12, 'SATURDAY', '09:00:00', '12:00:00', NOW(), NOW()),

    -- 🔹 최수정: 주 6일, 오후 서비스
    (46, 13, 'MONDAY', '14:00:00', '18:00:00', NOW(), NOW()), (47, 13, 'TUESDAY', '14:00:00', '18:00:00', NOW(), NOW()),
    (48, 13, 'WEDNESDAY', '14:00:00', '18:00:00', NOW(), NOW()), (49, 13, 'THURSDAY', '14:00:00', '18:00:00', NOW(), NOW()),
    (50, 13, 'FRIDAY', '14:00:00', '18:00:00', NOW(), NOW()), (51, 13, 'SATURDAY', '14:00:00', '18:00:00', NOW(), NOW()),

    -- 🔹 강용수: 주 3일, 오후 서비스
    (52, 14, 'TUESDAY', '15:00:00', '19:00:00', NOW(), NOW()), (53, 14, 'THURSDAY', '15:00:00', '19:00:00', NOW(), NOW()),
    (54, 14, 'SUNDAY', '15:00:00', '19:00:00', NOW(), NOW()),

    -- 🔹 한미숙: 주 5일, 하루 2회
    (55, 19, 'MONDAY', '09:00:00', '12:00:00', NOW(), NOW()), (56, 19, 'MONDAY', '18:00:00', '21:00:00', NOW(), NOW()),
    (57, 19, 'WEDNESDAY', '09:00:00', '12:00:00', NOW(), NOW()), (58, 19, 'WEDNESDAY', '18:00:00', '21:00:00', NOW(), NOW()),
    (59, 19, 'FRIDAY', '10:00:00', '13:00:00', NOW(), NOW()),

    -- 🔹 (10번) 강동원: 주 4일, 하루 2회 (오전 & 오후)
    (60, 10, 'MONDAY', '08:00:00', '12:00:00', NOW(), NOW()), (61, 10, 'MONDAY', '14:00:00', '18:00:00', NOW(), NOW()),
    (62, 10, 'WEDNESDAY', '09:00:00', '13:00:00', NOW(), NOW()), (63, 10, 'WEDNESDAY', '15:00:00', '19:00:00', NOW(), NOW()),

    -- 🔹 (15번) 문지영: 주 3일, 오후 집중
    (64, 15, 'TUESDAY', '13:00:00', '17:00:00', NOW(), NOW()), (65, 15, 'THURSDAY', '13:00:00', '17:00:00', NOW(), NOW()),
    (66, 15, 'SATURDAY', '14:00:00', '18:00:00', NOW(), NOW()),

    -- 🔹 (16번) 박세훈: 주 4일, 오전-오후 혼합
    (67, 16, 'MONDAY', '09:00:00', '12:00:00', NOW(), NOW()), (68, 16, 'MONDAY', '16:00:00', '20:00:00', NOW(), NOW()),
    (69, 16, 'WEDNESDAY', '09:00:00', '12:00:00', NOW(), NOW()), (70, 16, 'FRIDAY', '16:00:00', '20:00:00', NOW(), NOW()),

    -- 🔹 (17번) 김민희: 주 5일, 하루 2회 서비스
    (71, 17, 'MONDAY', '07:00:00', '11:00:00', NOW(), NOW()), (72, 17, 'MONDAY', '18:00:00', '22:00:00', NOW(), NOW()),
    (73, 17, 'TUESDAY', '08:00:00', '12:00:00', NOW(), NOW()), (74, 17, 'WEDNESDAY', '17:00:00', '21:00:00', NOW(), NOW()),
    (75, 17, 'FRIDAY', '08:00:00', '12:00:00', NOW(), NOW()),

    -- 🔹 (18번) 조윤호: 주 4일, 오전 집중
    (76, 18, 'MONDAY', '07:00:00', '11:00:00', NOW(), NOW()), (77, 18, 'WEDNESDAY', '08:00:00', '12:00:00', NOW(), NOW()),
    (78, 18, 'FRIDAY', '07:00:00', '11:00:00', NOW(), NOW()), (79, 18, 'SUNDAY', '09:00:00', '12:00:00', NOW(), NOW()),

    -- 🔹 (20번) 오세준: 주 3일, 하루 2회 (오전 & 저녁)
    (80, 20, 'TUESDAY', '06:00:00', '10:00:00', NOW(), NOW()), (81, 20, 'TUESDAY', '19:00:00', '22:00:00', NOW(), NOW()),
    (82, 20, 'THURSDAY', '07:00:00', '11:00:00', NOW(), NOW()), (83, 20, 'SATURDAY', '18:00:00', '22:00:00', NOW(), NOW());

-- ✅ senior_disease 테이블 더미 데이터 (각 어르신마다 1~2개 질환)
INSERT INTO senior_disease (id, senior_id, disease, additional_dementia_symptoms, created_at, updated_at)
VALUES
    (1, 1, 'DIABETES', 'MEMORY_LOSS', NOW(), NOW()), (2, 2, 'HYPERTENSION', NULL, NOW(), NOW()),
    (3, 3, 'ALZHEIMER', 'GETTING_LOST', NOW(), NOW()), (4, 4, 'PARKINSON', NULL, NOW(), NOW()),
    (5, 5, 'STROKE', 'DELUSIONS', NOW(), NOW()), (6, 6, 'DIABETES', 'WANDERING', NOW(), NOW()),
    (7, 7, 'ALZHEIMER', 'AGGRESSIVE_BEHAVIOR', NOW(), NOW()), (8, 8, 'HYPERTENSION', NULL, NOW(), NOW()),
    (9, 9, 'PARKINSON', 'CHILDISH_BEHAVIOR', NOW(), NOW()), (10, 10, 'STROKE', 'CANNOT_RECOGNIZE_FAMILY', NOW(), NOW()),
    (11, 11, 'ALZHEIMER', 'OTHERS', NOW(), NOW()), (12, 12, 'HYPERTENSION', NULL, NOW(), NOW()),
    (13, 13, 'STROKE', 'GETTING_LOST', NOW(), NOW()), (14, 14, 'DIABETES', 'CHILDISH_BEHAVIOR', NOW(), NOW()),
    (15, 15, 'PARKINSON', 'MEMORY_LOSS', NOW(), NOW()), (16, 16, 'STROKE', NULL, NOW(), NOW()),
    (17, 17, 'ALZHEIMER', 'AGGRESSIVE_BEHAVIOR', NOW(), NOW()), (18, 18, 'HYPERTENSION', 'DELUSIONS', NOW(), NOW()),
    (19, 19, 'DIABETES', 'WANDERING', NOW(), NOW()), (20, 20, 'STROKE', 'CANNOT_RECOGNIZE_FAMILY', NOW(), NOW());

-- ✅ disease_dementia_mapping 테이블 더미 데이터 (각 어르신마다 1~2개 치매 증상)
INSERT INTO disease_dementia_mapping (id, senior_disease_id, dementia_symptom)
VALUES
    (1, 1, 'MEMORY_LOSS'), (2, 3, 'GETTING_LOST'), (3, 5, 'DELUSIONS'), (4, 6, 'WANDERING'),
    (5, 7, 'AGGRESSIVE_BEHAVIOR'), (6, 9, 'CHILDISH_BEHAVIOR'), (7, 10, 'CANNOT_RECOGNIZE_FAMILY'),
    (8, 11, 'OTHERS'), (9, 13, 'GETTING_LOST'), (10, 14, 'CHILDISH_BEHAVIOR'),
    (11, 15, 'MEMORY_LOSS'), (12, 17, 'AGGRESSIVE_BEHAVIOR'), (13, 18, 'DELUSIONS'),
    (14, 19, 'WANDERING'), (15, 20, 'CANNOT_RECOGNIZE_FAMILY'), (16, 12, 'OTHERS'),
    (17, 16, 'MEMORY_LOSS'), (18, 15, 'GETTING_LOST'), (19, 14, 'WANDERING'),
    (20, 19, 'AGGRESSIVE_BEHAVIOR');
