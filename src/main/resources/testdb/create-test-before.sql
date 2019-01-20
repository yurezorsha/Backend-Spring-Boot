delete from activity;
delete from user_roles;
delete from users;
delete from roles;

INSERT INTO roles (id,name) VALUES (1,'ROLE_USER');
INSERT INTO roles (id,name) VALUES (2,'ROLE_ADMIN') ;

INSERT INTO users (id,username,password) values (1,'admin','$2a$10$VwCoHFg2uIfLfRvFCO7D4ePQasTaGb7k19imN44eViEZiwvZjr2y2');
INSERT INTO users (id,username,password) values (2,'valera','$2a$10$JjwW6xgxJJRmlbeotX0YPuMvItFWUxFHM.6kL1J2vUDfC7toYxAHm');

INSERT INTO user_roles (user_id, role_id) VALUES (1,2);
INSERT INTO user_roles (user_id, role_id) VALUES (2,1);

INSERT INTO ACTIVITY VALUES(1,3.5E0,'2019-01-18 00:00:00.000000',30,1);
INSERT INTO ACTIVITY VALUES(2,4.0E0,'2019-01-19 00:00:00.000000',35,1);
INSERT INTO ACTIVITY VALUES(3,5.0E0,'2019-01-20 00:00:00.000000',40,1);
INSERT INTO ACTIVITY VALUES(4,4.0E0,'2019-01-21 00:00:00.000000',40,1);
INSERT INTO ACTIVITY VALUES(5,6.0E0,'2019-01-22 00:00:00.000000',50,1);
INSERT INTO ACTIVITY VALUES(6,4.0E0,'2019-01-23 00:00:00.000000',38,1);
INSERT INTO ACTIVITY VALUES(7,3.0E0,'2019-01-24 00:00:00.000000',25,1);