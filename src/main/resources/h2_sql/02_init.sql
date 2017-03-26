/* Пароль 123 для всех пользователей*/
INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('pharus@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Konstantin Bobrov', '1983-06-07', '2017-03-01', 'ADMIN');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Вася Перов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia1@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Петя нестеров', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia2@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Никита Хрустов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia3@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Лена абетаева', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia4@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Станислав Соколов', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia5@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Вася Васечкин', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO users(login, password, full_name, birth_date, register_date, role)
VALUES ('vasia6@mail.ru', '601f1889667efaebb33b8c12572835da3f027f78', 'Илья Васечкин', '1991-04-12', '2017-03-01', 'USER');

INSERT INTO friends(id_user, id_friend) VALUES (1,2);
INSERT INTO friends(id_user, id_friend) VALUES (1,3);
INSERT INTO friends(id_user, id_friend) VALUES (1,4);
INSERT INTO friends(id_user, id_friend) VALUES (1,5);
INSERT INTO friends(id_user, id_friend) VALUES (1,6);
INSERT INTO friends(id_user, id_friend) VALUES (1,7);
INSERT INTO friends(id_user, id_friend) VALUES (1,8);
INSERT INTO friends(id_user, id_friend) VALUES (2,1);
INSERT INTO friends(id_user, id_friend) VALUES (2,3);
INSERT INTO friends(id_user, id_friend) VALUES (2,4);
INSERT INTO friends(id_user, id_friend) VALUES (3,2);

INSERT INTO posts(user_id, title, post)
VALUES (1, '', 'Сдулась шина? <b>Берем палки и ветки - </b> разжигаем огонь - сжигаем ее. Profit!!!');


INSERT INTO posts(user_id, title, post)
VALUES (1, '', '<b>Вариант номер два - </b> поджигаем машину и бежим подальше. Profit!!!');

INSERT INTO posts(user_id, title, post)
VALUES (1, '', 'какой сегодня день?');

INSERT INTO posts(user_id, title, post)
VALUES (1, 'Делать было нечего', 'ну и как это назвать бли бла бла блаб бла блаб бла');

INSERT INTO posts(user_id, title, post)
VALUES (2, '', 'Сегодня залез в движек, а он коптит как печка чабуречка');
INSERT INTO posts(user_id, title, post)
VALUES (4, '', '<b>Коля любит мамбу, Толя любит мамбу, и Сережа тоже!</b>');

INSERT INTO messages(from_user_id, to_user_id, message, post_time)
VALUES (1,2,'Привет друг как у тебя дела?','2017-03-26');
INSERT INTO messages(from_user_id, to_user_id, message, post_time)
VALUES (2,1,'Приветствую, у меня нормально! как ты?','2017-03-26');
INSERT INTO messages(from_user_id, to_user_id, message, post_time)
VALUES (1,2,'Норм :)','2017-03-26');
INSERT INTO messages(from_user_id, to_user_id, message, post_time)
VALUES (5,1,'Тук тук, есть кто живой, машину продаете?','2017-03-26');

INSERT INTO models(name) VALUES ('Nissan Qashqai');
INSERT INTO models(name) VALUES ('Nissan Juke');
INSERT INTO models(name) VALUES ('Nissan Almera');
INSERT INTO models(name) VALUES ('BMW X3');
INSERT INTO models(name) VALUES ('BMW X5');
INSERT INTO models(name) VALUES ('BMW e38 750i');
INSERT INTO models(name) VALUES ('Mercedes Vito');
INSERT INTO models(name) VALUES ('Mercedes CLS');
INSERT INTO models(name) VALUES ('Mercedes W212i');

INSERT INTO cars(user_id, model_id, car_year, car_number)
VALUES (1, 1, 2011, 'Н609ХУ178');
INSERT INTO cars(user_id, model_id, car_year, car_number)
VALUES (1, 2, 2015, 'В334ВК78');
INSERT INTO cars(user_id, model_id, car_year, car_number)
VALUES (2, 3, 2014, 'В334ВК78');
INSERT INTO cars(user_id, model_id, car_year, car_number)
VALUES (3, 5, 2014, 'В189Н789');
