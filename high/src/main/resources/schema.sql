CREATE TABLE `user` (
    `user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '유저 번호',
    `email` VARCHAR(50) NOT NULL COMMENT '이메일',
    `password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    `authorities` VARCHAR(50) NULL DEFAULT NULL COMMENT '권한',
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `student` (
    `student_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '학생 번호',
    `name` VARCHAR(50) NOT NULL COMMENT '이름',
    `grade` INT(11) NULL DEFAULT NULL COMMENT '학년',
    `age` INT(11) NULL DEFAULT NULL COMMENT '나이',
    PRIMARY KEY (`student_id`)
);
