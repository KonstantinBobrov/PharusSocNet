INSERT INTO models(name) VALUES ('Nissan Qashqai');
INSERT INTO models(name) VALUES ('Nissan Juke');
INSERT INTO models(name) VALUES ('Nissan Almera');
INSERT INTO models(name) VALUES ('BMW X3');
INSERT INTO models(name) VALUES ('BMW X5');
INSERT INTO models(name) VALUES ('BMW e38 750i');
INSERT INTO models(name) VALUES ('Mercedes Vito');
INSERT INTO models(name) VALUES ('Mercedes CLS');
INSERT INTO models(name) VALUES ('Mercedes W212i');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('pharus@mail.ru', '0000', 'Konstantin Bobrov', '1983-06-07', '2017-03-01', 'ADMIN');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia@mail.ru', '0000', 'Вася Перов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia5@mail.ru', '0000', 'Петя нестеров', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia6@mail.ru', '0000', 'Никита Хрустов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia7@mail.ru', '0000', 'Лена абетаева', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia8@mail.ru', '0000', 'Станислав Соколов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia9@mail.ru', '0000', 'Вася Васечкин', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia10@mail.ru', '0000', 'Илья Васечкин', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO friends(id_user, id_friend) VALUES (1,2);
INSERT INTO friends(id_user, id_friend) VALUES (1,3);
INSERT INTO friends(id_user, id_friend) VALUES (1,4);
INSERT INTO friends(id_user, id_friend) VALUES (1,5);
INSERT INTO friends(id_user, id_friend) VALUES (1,6);
INSERT INTO friends(id_user, id_friend) VALUES (1,7);
INSERT INTO friends(id_user, id_friend) VALUES (1,8);
INSERT INTO friends(id_user, id_friend) VALUES (2,1);
INSERT INTO friends(id_user, id_friend) VALUES (2,3);
INSERT INTO friends(id_user, id_friend) VALUES (3,2);

INSERT INTO posts(user_id, title, post)
VALUES (1, 'Как розжечь огонь из палок и веток', '<b>Берем палки и ветки - </b> разжигаем огонь. Profit!!!');


INSERT INTO posts(user_id, title, post)
VALUES (1, 'Готовим уху на походном костре', '<b>Вариант номер два - </b> ловим рыбу, заливаем водой, ставим на огонь. Profit!!!');