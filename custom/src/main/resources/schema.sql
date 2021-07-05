CREATE TABLE `user` (
    `seq` INT(11) NOT NULL AUTO_INCREMENT COMMENT '번호',
    `email` VARCHAR(50) NOT NULL COMMENT '이메일',
    `password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    PRIMARY KEY (`seq`)
);

CREATE TABLE `user_role` (
    `seq` INT(11) NOT NULL COMMENT '번호',
    `authorities` VARCHAR(50) NOT NULL COMMENT '권한',
    PRIMARY KEY (`seq`)
);

CREATE TABLE `access` (
    `access_seq` INT(11) NOT NULL AUTO_INCREMENT COMMENT '접근번호',
    `access_url` VARCHAR(50) NOT NULL COMMENT '접근 URL',
    `access_roles` VARCHAR(50) NOT NULL COMMENT '접근 권한코드',
    PRIMARY KEY (`access_seq`)
);

CREATE TABLE `role` (
    `role_seq` INT(11) NOT NULL AUTO_INCREMENT COMMENT '권한번호',
    `role_code` VARCHAR(50) NOT NULL COMMENT '권한코드',
    `role_name` INT(11) NULL DEFAULT NULL COMMENT '권한명',
    PRIMARY KEY (`role_seq`)
);

CREATE TABLE `token` (
    `username` varchar(64) DEFAULT NULL,
    `series` varchar(64) NOT NULL,
    `token` varchar(64) DEFAULT NULL,
    `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`series`)
);

CREATE TABLE `student` (
    `seq` INT(11) NOT NULL AUTO_INCREMENT COMMENT '번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    `grade` INT(11) NULL DEFAULT NULL COMMENT '학년',
    `age` INT(11) NULL DEFAULT NULL COMMENT '나이',
    `hobby` VARCHAR(50) NULL DEFAULT NULL COMMENT '취미',
    PRIMARY KEY (`seq`)
);
