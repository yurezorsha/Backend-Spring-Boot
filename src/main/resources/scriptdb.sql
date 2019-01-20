

CREATE TABLE users (
   id INT  NOT NULL IDENTITY,
   username VARCHAR (45),
   password VARCHAR (255),  
   PRIMARY KEY (id)
); 

CREATE TABLE roles (
   id INT  NOT NULL IDENTITY,
   name VARCHAR (45),
   PRIMARY KEY (id)
); 

CREATE TABLE user_roles (
   user_id INT  NOT NULL,
   role_id INT NOT NULL,
   PRIMARY KEY (user_id)
); 

CREATE TABLE activity (
   id INT  NOT NULL IDENTITY,
   distance float (20),
   run_date date,  
   run_time INT,
   id_user INT,
   FOREIGN KEY (id_user) REFERENCES users(id),
   PRIMARY KEY (id)
   
); 

INSERT INTO roles (id,name) VALUES (1,'ROLE_USER');
INSERT INTO roles (id,name) VALUES (2,'ROLE_ADMIN') ;

INSERT INTO users (id,username,password) values (1,'admin','$2a$10$VwCoHFg2uIfLfRvFCO7D4ePQasTaGb7k19imN44eViEZiwvZjr2y2');
INSERT INTO users (id,username,password) values (2,'user','$2a$10$.NOJmOt6y1Zbwg42ge4HautmqtAQb2yx0chvI0TQMNW9bZZG03bbW');

INSERT INTO user_roles (user_id, role_id) VALUES (1,2);
INSERT INTO user_roles (user_id, role_id) VALUES (2,1);

INSERT INTO activity (distance,run_date,run_time,id_user) VALUES (3.5, '2019-01-19', 30,2);
INSERT INTO activity (distance,run_date,run_time,id_user) VALUES (4, '2019-01-18', 35,2);

