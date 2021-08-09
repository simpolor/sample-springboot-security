INSERT INTO user(email, password, name, authorities)
    VALUES ('user@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '유저', 'ROLE_USER');

INSERT INTO user(email, password, name, authorities)
    VALUES ('admin@gmail.com', '{bcrypt}$2a$10$lUNu67WtxjEmpe0foT3iR.LvunC.NDKeJbNgQmiJsAY6qcio9LTK6', '관리자', 'ROLE_ADMIN');

INSERT INTO student(name, grade, age, hobby)
     VALUES ('하니', 1, 17, '달리기');

INSERT INTO student(name, grade, age, hobby)
     VALUES ('홍길동', 3, 19, '반항');

