INSERT INTO user(seq, email, password, name)
    VALUES (1, 'user@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '유저');

INSERT INTO user(seq, email, password, name)
    VALUES (2, 'admin@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '관리자');

INSERT INTO user_role(seq, authorities)
    VALUES (1, 'ROLE_USER');

INSERT INTO user_role(seq, authorities)
    VALUES (2, 'ROLE_ADMIN');

INSERT INTO access(access_seq, access_url, access_roles)
    VALUES (1, '/student/register', 'ROLE_ADMIN');

INSERT INTO access(access_seq, access_url, access_roles)
    VALUES (2, '/student/detail/**', 'ROLE_ADMIN,ROLE_USER');

INSERT INTO access(access_seq, access_url, access_roles)
    VALUES (3, '/student/register', 'ROLE_ADMIN');

INSERT INTO access(access_seq, access_url, access_roles)
    VALUES (4, '/student/modify/**', 'ROLE_ADMIN');

INSERT INTO access(access_seq, access_url, access_roles)
    VALUES (5, '/student/delete/**', 'ROLE_ADMIN');

INSERT INTO student(name, grade, age, hobby)
     VALUES ('하니', 1, 17, '달리기');

INSERT INTO student(name, grade, age, hobby)
     VALUES ('홍길동', 3, 19, '반항');

