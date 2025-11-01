CREATE TABLE `base_address` (
                                `base_address_id`   BIGINT NOT NULL AUTO_INCREMENT,
                                `base_address_name` VARCHAR(255) NOT NULL COMMENT '주소명 (예: 서울특별시 강남구)',
                                PRIMARY KEY (`base_address_id`)
);

CREATE TABLE `food` (
                        `food_id`    BIGINT NOT NULL AUTO_INCREMENT,
                        `food_name`  ENUM('한식', '일식', '중식', '양식', '치킨', '분식', '고기/구이', '도시락', '야식(족발,보쌈)', '패스트 푸드', '디저트', '아시안푸드') NOT NULL,
                        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`food_id`)
);

CREATE TABLE `term` (
                        `term_id`    BIGINT NOT NULL AUTO_INCREMENT,
                        `name`       ENUM('AGE', 'SERVICE', 'PRIVACY', 'LOCATION', 'MARKETING') NOT NULL,
                        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`term_id`)
);

CREATE TABLE `member` (
                          `member_id`       BIGINT NOT NULL AUTO_INCREMENT,
                          `social_uid`      VARCHAR(255) NULL,
                          `social_type`     ENUM('KAKAO', 'NAVER', 'APPLE', 'GOOGLE') NULL,
                          `member_name`     VARCHAR(100) NOT NULL,
                          `gender`          ENUM('MALE', 'FEMALE', 'NONE') NOT NULL DEFAULT 'NONE',
                          `email`           VARCHAR(255) NULL,
                          `phone_number`    VARCHAR(20) NULL,
                          `birth_date`      DATE NOT NULL,
                          `member_point`    INTEGER NOT NULL,
                          `base_address_id` BIGINT NOT NULL,
                          `detail_address`  VARCHAR(255) NOT NULL,
                          `created_at`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `updated_at`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `deleted_at`      TIMESTAMP NULL DEFAULT NULL,
                          PRIMARY KEY (`member_id`),
                          FOREIGN KEY (`base_address_id`) REFERENCES `base_address` (`base_address_id`)
);

CREATE TABLE `restaurant` (
                              `restaurant_id`             BIGINT NOT NULL AUTO_INCREMENT,
                              `restaurant_name`           VARCHAR(255) NOT NULL,
                              `manager_number`            BIGINT NOT NULL,
                              `restaurant_detail_address` VARCHAR(255) NOT NULL,
                              `created_at`                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_at`                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `base_address_id`           BIGINT NOT NULL,
                              PRIMARY KEY (`restaurant_id`),
                              FOREIGN KEY (`base_address_id`) REFERENCES `base_address` (`base_address_id`)
);

CREATE TABLE `mission` (
                           `mission_id`        BIGINT NOT NULL AUTO_INCREMENT,
                           `deadline`          TIMESTAMP NOT NULL,
                           `mission_condition` VARCHAR(255) NOT NULL,
                           `mission_point`     INTEGER NOT NULL,
                           `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `restaurant_id`     BIGINT NOT NULL,
                           PRIMARY KEY (`mission_id`),
                           FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
);

CREATE TABLE `review` (
                          `review_id`       BIGINT NOT NULL AUTO_INCREMENT,
                          `review_content`  TEXT NOT NULL,
                          `star_rating`     DECIMAL(2, 1) NOT NULL COMMENT '소수점 한자리까지 정확히 ex) 4.5 -> 정확히 4.5로 저장되게 설계',
                          `created_at`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `updated_at`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `restaurant_id`   BIGINT NOT NULL,
                          `member_id`       BIGINT NOT NULL,
                          PRIMARY KEY (`review_id`),
                          FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
                          FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
);

CREATE TABLE `member_favorite_food` (
                                        `user_favorite_food_id` BIGINT NOT NULL AUTO_INCREMENT,
                                        `member_id`             BIGINT NOT NULL,
                                        `food_id`               BIGINT NOT NULL,
                                        `created_at`            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `updated_at`            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`user_favorite_food_id`),
                                        FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
                                        FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`)
);

CREATE TABLE `member_mission` (
                                  `member_mission_id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `is_complete`       BOOLEAN NOT NULL DEFAULT FALSE,
                                  `created_at`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `member_id`         BIGINT NOT NULL,
                                  `mission_id`        BIGINT NOT NULL,
                                  PRIMARY KEY (`member_mission_id`),
                                  FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
                                  FOREIGN KEY (`mission_id`) REFERENCES `mission` (`mission_id`)
);

CREATE TABLE `member_term` (
                               `member_term_id` BIGINT NOT NULL AUTO_INCREMENT,
                               `member_id`      BIGINT NOT NULL,
                               `term_id`        BIGINT NOT NULL,
                               `created_at`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_at`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`member_term_id`),
                               FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
                               FOREIGN KEY (`term_id`) REFERENCES `term` (`term_id`)
);

CREATE TABLE `review_photo` (
                                `review_photo_id`  BIGINT NOT NULL AUTO_INCREMENT,
                                `review_photo_url` TEXT NOT NULL,
                                `created_at`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `updated_at`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                `review_id`        BIGINT NOT NULL,
                                PRIMARY KEY (`review_photo_id`),
                                FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
);

CREATE TABLE `review_reply` (
                                `review_reply_id`      BIGINT NOT NULL AUTO_INCREMENT,
                                `review_reply_content` TEXT NOT NULL,
                                `review_id`            BIGINT NOT NULL,
                                `created_at`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `updated_at`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`review_reply_id`),
                                FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
);