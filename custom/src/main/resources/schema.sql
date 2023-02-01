CREATE TABLE `user` (
    `user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '유저 아이디',
    `email` VARCHAR(50) NOT NULL COMMENT '이메일',
    `password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `user_role` (
    `user_id` INT(11) NOT NULL COMMENT '유저 아이디',
    `authorities` VARCHAR(50) NOT NULL COMMENT '권한 목록',
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `access` (
    `access_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '접근 아이디',
    `target_url` VARCHAR(50) NOT NULL COMMENT '대상 URL',
    `roles` VARCHAR(50) NOT NULL COMMENT '대상 권한 목록',
    PRIMARY KEY (`access_id`)
);

CREATE TABLE `role` (
    `role_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '권한 번호',
    `code` VARCHAR(50) NOT NULL COMMENT '코드',
    `name` VARCHAR(50) NULL DEFAULT NULL COMMENT '권한명',
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `token` (
    `username` varchar(64) DEFAULT NULL,
    `series` varchar(64) NOT NULL,
    `token` varchar(64) DEFAULT NULL,
    `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`series`)
);

CREATE TABLE `student` (
    `student_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '학생 번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    `grade` INT(11) NULL DEFAULT NULL COMMENT '학년',
    `age` INT(11) NULL DEFAULT NULL COMMENT '나이',
    PRIMARY KEY (`student_id`)
);
