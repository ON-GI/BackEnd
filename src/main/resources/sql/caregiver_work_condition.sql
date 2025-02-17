-- 근무 조건 설정
INSERT INTO caregiver_work_condition (caregiver_id, pay_type, pay_amount, negotiable)
VALUES
    (1, 'HOURLY', 13500, true),
    (2, 'HOURLY', 14000, true),
    (3, 'HOURLY', 15000, false),
    (4, 'HOURLY', 13000, true),
    (5, 'HOURLY', 14500, false),
    (6, 'HOURLY', 15500, true),
    (7, 'HOURLY', 14000, true),
    (8, 'HOURLY', 13500, false),
    (9, 'HOURLY', 14000, false),
    (10, 'HOURLY', 14500, true),
    (11, 'HOURLY', 13800, true),
    (12, 'HOURLY', 14200, true),
    (13, 'HOURLY', 14800, false),
    (14, 'HOURLY', 13200, true),
    (15, 'HOURLY', 14700, false),
    (16, 'HOURLY', 15600, true),
    (17, 'HOURLY', 13900, true),
    (18, 'HOURLY', 13400, false),
    (19, 'HOURLY', 14100, false),
    (20, 'HOURLY', 14400, true),
    (21, 'HOURLY', 13700, true),
    (22, 'HOURLY', 14300, true),
    (23, 'HOURLY', 14900, false),
    (24, 'HOURLY', 13100, true),
    (25, 'HOURLY', 14600, false),
    (26, 'HOURLY', 15400, true),
    (27, 'HOURLY', 13900, true),
    (28, 'HOURLY', 13300, false),
    (29, 'HOURLY', 14000, false),
    (30, 'HOURLY', 14300, true);

-- 근무 시간 설정 (다양한 요일 및 시간)
-- 근무 시간 설정 (다양한 요일 및 시간 고려)
INSERT INTO caregiver_work_time (work_condition_id, day_of_week, start_time, end_time)
VALUES
    -- ✅ 주 2~3일 근무자 (파트타임)
    (1, 'MONDAY', '08:00:00', '12:00:00'),
    (1, 'WEDNESDAY', '08:00:00', '12:00:00'),
    (2, 'TUESDAY', '09:00:00', '15:00:00'),
    (2, 'THURSDAY', '09:00:00', '15:00:00'),
    (3, 'FRIDAY', '10:00:00', '16:00:00'),
    (3, 'SUNDAY', '12:00:00', '18:00:00'),
    -- ✅ 주 4~5일 근무자 (일반적인 풀타임 근무)
    (4, 'MONDAY', '08:00:00', '17:00:00'),
    (4, 'TUESDAY', '08:00:00', '17:00:00'),
    (4, 'THURSDAY', '08:00:00', '17:00:00'),
    (4, 'FRIDAY', '08:00:00', '17:00:00'),
    (5, 'MONDAY', '09:00:00', '18:00:00'),
    (5, 'TUESDAY', '09:00:00', '18:00:00'),
    (5, 'WEDNESDAY', '09:00:00', '18:00:00'),
    (5, 'FRIDAY', '09:00:00', '18:00:00'),
    (6, 'MONDAY', '07:00:00', '16:00:00'),
    (6, 'WEDNESDAY', '07:00:00', '16:00:00'),
    (6, 'THURSDAY', '07:00:00', '16:00:00'),
    (6, 'SATURDAY', '07:00:00', '16:00:00'),
    (7, 'MONDAY', '09:00:00', '18:00:00'),
    (7, 'TUESDAY', '09:00:00', '18:00:00'),
    (7, 'WEDNESDAY', '09:00:00', '18:00:00'),
    (7, 'THURSDAY', '09:00:00', '18:00:00'),
    (7, 'FRIDAY', '09:00:00', '18:00:00'),
    (7, 'SATURDAY', '09:00:00', '18:00:00'),
    (8, 'MONDAY', '08:00:00', '16:00:00'),
    (8, 'TUESDAY', '08:00:00', '16:00:00'),
    (8, 'WEDNESDAY', '08:00:00', '16:00:00'),
    (8, 'THURSDAY', '08:00:00', '16:00:00'),
    (8, 'FRIDAY', '08:00:00', '16:00:00'),
    (8, 'SATURDAY', '08:00:00', '16:00:00'),
    (8, 'SUNDAY', '08:00:00', '16:00:00'),
    (9, 'MONDAY', '08:00:00', '12:00:00'),
    (9, 'THURSDAY', '08:00:00', '12:00:00'),
    (10, 'TUESDAY', '09:00:00', '14:00:00'),
    (10, 'FRIDAY', '09:00:00', '14:00:00'),
    (11, 'WEDNESDAY', '10:00:00', '16:00:00'),
    (11, 'SATURDAY', '12:00:00', '18:00:00'),
    (12, 'MONDAY', '08:00:00', '17:00:00'),
    (12, 'TUESDAY', '08:00:00', '17:00:00'),
    (12, 'WEDNESDAY', '08:00:00', '17:00:00'),
    (12, 'FRIDAY', '08:00:00', '17:00:00'),
    (13, 'TUESDAY', '09:00:00', '18:00:00'),
    (13, 'THURSDAY', '09:00:00', '18:00:00'),
    (13, 'SATURDAY', '09:00:00', '18:00:00'),
    (14, 'MONDAY', '07:00:00', '16:00:00'),
    (14, 'WEDNESDAY', '07:00:00', '16:00:00'),
    (14, 'THURSDAY', '07:00:00', '16:00:00'),
    (15, 'MONDAY', '06:00:00', '15:00:00'),
    (15, 'TUESDAY', '06:00:00', '15:00:00'),
    (15, 'WEDNESDAY', '06:00:00', '15:00:00'),
    (15, 'THURSDAY', '06:00:00', '15:00:00'),
    (15, 'FRIDAY', '06:00:00', '15:00:00')
    (16, 'MONDAY', '22:00:00', '06:00:00'),
    (16, 'TUESDAY', '22:00:00', '06:00:00'),
    (16, 'FRIDAY', '22:00:00', '06:00:00'),
    (17, 'WEDNESDAY', '23:00:00', '07:00:00'),
    (17, 'THURSDAY', '23:00:00', '07:00:00'),
    (17, 'SATURDAY', '23:00:00', '07:00:00'),
    (18, 'MONDAY', '21:00:00', '05:00:00'),
    (18, 'TUESDAY', '21:00:00', '05:00:00'),
    (18, 'THURSDAY', '21:00:00', '05:00:00'),
    (18, 'SATURDAY', '21:00:00', '05:00:00'),
    (19, 'MONDAY', '08:00:00', '17:00:00'),
    (19, 'TUESDAY', '08:00:00', '17:00:00'),
    (19, 'WEDNESDAY', '08:00:00', '17:00:00'),
    (19, 'THURSDAY', '08:00:00', '17:00:00'),
    (20, 'TUESDAY', '09:00:00', '18:00:00'),
    (20, 'WEDNESDAY', '09:00:00', '18:00:00'),
    (20, 'THURSDAY', '09:00:00', '18:00:00'),
    (20, 'FRIDAY', '09:00:00', '18:00:00'),
    (21, 'MONDAY', '07:00:00', '15:00:00'),
    (21, 'TUESDAY', '07:00:00', '15:00:00'),
    (21, 'THURSDAY', '07:00:00', '15:00:00'),
    (21, 'SATURDAY', '07:00:00', '15:00:00'),
    (22, 'MONDAY', '06:00:00', '14:00:00'),
    (22, 'TUESDAY', '06:00:00', '14:00:00'),
    (22, 'THURSDAY', '06:00:00', '14:00:00'),
    (22, 'FRIDAY', '06:00:00', '14:00:00'),
    (23, 'MONDAY', '08:00:00', '17:00:00'),
    (23, 'TUESDAY', '08:00:00', '17:00:00'),
    (23, 'WEDNESDAY', '08:00:00', '17:00:00'),
    (23, 'THURSDAY', '08:00:00', '17:00:00'),
    (23, 'FRIDAY', '08:00:00', '17:00:00'),
    (23, 'SATURDAY', '08:00:00', '17:00:00'),
    (23, 'SUNDAY', '08:00:00', '17:00:00'),
    (24, 'MONDAY', '09:00:00', '18:00:00'),
    (24, 'TUESDAY', '09:00:00', '18:00:00'),
    (24, 'WEDNESDAY', '09:00:00', '18:00:00'),
    (24, 'THURSDAY', '09:00:00', '18:00:00'),
    (24, 'FRIDAY', '09:00:00', '18:00:00'),
    (24, 'SATURDAY', '09:00:00', '18:00:00'),
    (25, 'MONDAY', '09:00:00', '18:00:00'),
    (25, 'TUESDAY', '09:00:00', '18:00:00'),
    (25, 'WEDNESDAY', '09:00:00', '18:00:00'),
    (25, 'THURSDAY', '09:00:00', '18:00:00'),
    (25, 'FRIDAY', '09:00:00', '18:00:00'),
    (26, 'MONDAY', '07:00:00', '16:00:00'),
    (26, 'TUESDAY', '07:00:00', '16:00:00'),
    (26, 'WEDNESDAY', '07:00:00', '16:00:00'),
    (26, 'THURSDAY', '07:00:00', '16:00:00'),
    (26, 'FRIDAY', '07:00:00', '16:00:00'),
    (26, 'SATURDAY', '07:00:00', '16:00:00');
    (27, 'MONDAY', '06:00:00', '12:00:00'),
    (27, 'TUESDAY', '12:00:00', '18:00:00'),
    (27, 'THURSDAY', '08:00:00', '16:00:00'),
    (28, 'WEDNESDAY', '14:00:00', '22:00:00'),
    (28, 'FRIDAY', '09:00:00', '17:00:00'),
    (28, 'SUNDAY', '12:00:00', '20:00:00'),
    (29, 'MONDAY', '10:00:00', '16:00:00'),
    (29, 'TUESDAY', '14:00:00', '22:00:00'),
    (29, 'THURSDAY', '09:00:00', '18:00:00'),
    (30, 'FRIDAY', '07:00:00', '12:00:00'),
    (30, 'SATURDAY', '14:00:00', '22:00:00'),
    (30, 'SUNDAY', '09:00:00', '15:00:00');

-- 📍 근무 지역 설정 (균형 있게 배치)
INSERT INTO caregiver_work_region (work_condition_id, city, district, town)
VALUES
    -- ✅ 특정 구 전체 담당자 (서울 특정 구)
    (1, '서울특별시', '강남구', NULL),
    (2, '서울특별시', '서초구', NULL),
    (3, '서울특별시', '송파구', NULL),
    (4, '서울특별시', '광진구', NULL),

    -- ✅ 특정 구 내 3~4개 동 담당자
    (5, '서울특별시', '강남구', '대치동'),
    (5, '서울특별시', '강남구', '삼성동'),
    (5, '서울특별시', '강남구', '논현동'),

    (6, '서울특별시', '서초구', '반포동'),
    (6, '서울특별시', '서초구', '방배동'),
    (6, '서울특별시', '서초구', '양재동'),

    (7, '서울특별시', '송파구', '잠실동'),
    (7, '서울특별시', '송파구', '가락동'),
    (7, '서울특별시', '송파구', '풍납동'),

    (8, '서울특별시', '광진구', '구의동'),
    (8, '서울특별시', '광진구', '화양동'),
    (8, '서울특별시', '광진구', '자양동'),

    -- ✅ 두 개 구 전체 담당자
    (9, '서울특별시', '강남구', NULL),
    (9, '서울특별시', '서초구', NULL),

    (10, '서울특별시', '송파구', NULL),
    (10, '서울특별시', '광진구', NULL),

    -- ✅ 3개 구 전체 담당자
    (11, '서울특별시', '강남구', NULL),
    (11, '서울특별시', '서초구', NULL),
    (11, '서울특별시', '송파구', NULL),

    -- ✅ 4개 구 전체 담당자 (최대 영역)
    (12, '서울특별시', '강남구', NULL),
    (12, '서울특별시', '서초구', NULL),
    (12, '서울특별시', '송파구', NULL),
    (12, '서울특별시', '광진구', NULL)

-- ✅ 랜덤하게 배치 (특정 동 4~5개 담당)
    (13, '서울특별시', '강남구', '대치동'),
    (13, '서울특별시', '강남구', '역삼동'),
    (13, '서울특별시', '강남구', '삼성동'),
    (13, '서울특별시', '강남구', '논현동'),

    (14, '서울특별시', '서초구', '반포동'),
    (14, '서울특별시', '서초구', '방배동'),
    (14, '서울특별시', '서초구', '양재동'),
    (14, '서울특별시', '서초구', '우면동'),

    (15, '서울특별시', '송파구', '잠실동'),
    (15, '서울특별시', '송파구', '문정동'),
    (15, '서울특별시', '송파구', '거여동'),
    (15, '서울특별시', '송파구', '마천동'),
    (16, '서울특별시', '강남구', '대치동'),
    (16, '서울특별시', '강남구', '삼성동'),

    (17, '서울특별시', '서초구', '반포동'),
    (17, '서울특별시', '서초구', '방배동'),

    (18, '서울특별시', '송파구', '잠실동'),
    (18, '서울특별시', '송파구', '가락동'),

    (19, '서울특별시', '광진구', '구의동'),
    (19, '서울특별시', '광진구', '화양동'),

    -- ✅ 랜덤하게 여러 구 담당 (21~30번)
    (21, '서울특별시', '강남구', NULL),
    (21, '서울특별시', '송파구', NULL),

    (22, '서울특별시', '서초구', NULL),
    (22, '서울특별시', '광진구', NULL),

    (23, '서울특별시', '강남구', NULL),
    (23, '서울특별시', '서초구', NULL),
    (23, '서울특별시', '송파구', NULL),

    (24, '서울특별시', '강남구', NULL),
    (24, '서울특별시', '서초구', NULL),
    (24, '서울특별시', '송파구', NULL),
    (24, '서울특별시', '광진구', NULL)
    (25, '서울특별시', '강남구', '도곡동'),
    (25, '서울특별시', '강남구', '역삼동'),
    (25, '서울특별시', '강남구', '청담동'),

    (26, '서울특별시', '서초구', '양재동'),
    (26, '서울특별시', '서초구', '우면동'),
    (26, '서울특별시', '서초구', '반포동'),

    (27, '서울특별시', '송파구', '장지동'),
    (27, '서울특별시', '송파구', '삼전동'),
    (27, '서울특별시', '송파구', '문정동'),

    -- ✅ 여러 구 전체 담당자 (28~30번)
    (28, '서울특별시', '강남구', NULL),
    (28, '서울특별시', '서초구', NULL),

    (29, '서울특별시', '송파구', NULL),
    (29, '서울특별시', '광진구', NULL),

    (30, '서울특별시', '강남구', NULL),
    (30, '서울특별시', '서초구', NULL),
    (30, '서울특별시', '송파구', NULL),
    (30, '서울특별시', '광진구', NULL)