CREATE TABLE `user` (
    `seq` INT(11) NOT NULL AUTO_INCREMENT COMMENT '번호',
    `email` VARCHAR(50) NOT NULL COMMENT '이메일',
    `password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    `authorities` VARCHAR(50) NULL DEFAULT NULL COMMENT '권한',
    PRIMARY KEY (`seq`)
);