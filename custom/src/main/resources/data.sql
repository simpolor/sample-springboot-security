INSERT INTO user(user_id, email, password, name)
    VALUES (1, 'admin@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '관리자');
INSERT INTO user(user_id, email, password, name)
    VALUES (2, 'user@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '유저');

INSERT INTO user_role(user_id, authorities)
    VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_role(user_id, authorities)
    VALUES (2, 'ROLE_USER');

INSERT INTO access(access_id, target_url, roles)
    VALUES (1, '/student/register', 'ROLE_USER');
INSERT INTO access(access_id, target_url, roles)
    VALUES (2, '/student/detail/**', 'ROLE_ADMIN,ROLE_USER');
INSERT INTO access(access_id, target_url, roles)
    VALUES (3, '/student/register', 'ROLE_ADMIN');
INSERT INTO access(access_id, target_url, roles)
    VALUES (4, '/student/modify/**', 'ROLE_ADMIN');
INSERT INTO access(access_id, target_url, roles)
    VALUES (5, '/student/delete/**', 'ROLE_ADMIN');

INSERT INTO role(role_id, code, name)
    VALUES (1, 'ROLE_ADMIN', '관리자');
INSERT INTO role(role_id, code, name)
    VALUES (2, 'ROLE_USER', '사용자');

INSERT INTO student(student_id, name, grade, age)
     VALUES (1, '하니', 1, 17);
INSERT INTO student(student_id, name, grade, age)
     VALUES (2, '홍길동', 3, 19);

