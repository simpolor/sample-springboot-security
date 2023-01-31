INSERT INTO user(user_id, email, password, name, authorities)
    VALUES (1, 'admin@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '관리자', 'ROLE_ADMIN');

INSERT INTO user(user_id, email, password, name, authorities)
    VALUES (2, 'user@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '유저', 'ROLE_USER');

INSERT INTO student(student_id, name, grade, age)
     VALUES (1, '하니', 1, 17);

INSERT INTO student(student_id, name, grade, age)
     VALUES (2, '홍길동', 3, 19);

