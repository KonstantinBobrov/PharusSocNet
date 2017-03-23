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
VALUES ('vasia5@mail.ru', '0000', 'Вася5 Перов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO friends(id_user, id_friend) VALUES (1,2);
INSERT INTO friends(id_user, id_friend) VALUES (1,3);
INSERT INTO friends(id_user, id_friend) VALUES (2,1);
INSERT INTO friends(id_user, id_friend) VALUES (2,3);
INSERT INTO friends(id_user, id_friend) VALUES (3,2);

INSERT INTO posts(user_id, title, post)
VALUES (1, 'Как розжечь огонь из палок и веток', '<b>Берем палки и ветки - </b> разжигаем огонь. Profit!!!');


INSERT INTO posts(user_id, title, post)
VALUES (1, 'Готовим уху на походном костре', '<b>Вариант номер два - </b> ловим рыбу, заливаем водой, ставим на огонь. Profit!!!');