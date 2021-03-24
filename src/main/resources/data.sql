
insert into subjects( subject)
values
('Matematyka'),
('Fizyka'),
('Angielski');

insert into day ( day)
VALUES ('Poniedziałek'),
       ('Wtorek'),
       ('Środa'),
       ('Czwartek'),
       ('Piątek');

insert into teacher (teacher_id, email, login, password, regisration_date)
VALUES (1,'admin@op.pl','admin','admin','2008-01-01 00:00:01')
/*spring.datasource.initialization-mode=always

 */