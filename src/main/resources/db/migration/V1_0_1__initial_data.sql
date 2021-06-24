SET search_path TO student_record;

INSERT INTO status(id, name) VALUES(NEXTVAL('status_seq'), 'ACTIVE');
INSERT INTO status(id, name) VALUES(NEXTVAL('status_seq'), 'INACTIVE');

INSERT INTO technology_group(id, name) VALUES(NEXTVAL('status_seq'), 'Java');
INSERT INTO technology_group(id, name) VALUES(NEXTVAL('status_seq'), 'JavaScript');
INSERT INTO technology_group(id, name) VALUES(NEXTVAL('status_seq'), 'QA');
INSERT INTO technology_group(id, name) VALUES(NEXTVAL('status_seq'), 'Mobile (Android)');

INSERT INTO student_record.user(id, login, first_name, last_name, image, status_id) VALUES(NEXTVAL('user_seq'), 'AnnaNowak', 'Anna', 'Nowak', null, (SELECT id FROM status WHERE name = 'ACTIVE'));
INSERT INTO student_record.user(id, login, first_name, last_name, image, status_id) VALUES(NEXTVAL('user_seq'), 'JanKowalski', 'Jan', 'Kowalski', null, (SELECT id FROM status WHERE name = 'ACTIVE'));

INSERT INTO stage(id, name, description, year, technology_group_id) VALUES(NEXTVAL('user_seq'), 'Etap I', 'Etap pierwszy patronatu 2021 Java.', 2021, (SELECT id FROM technology_group WHERE name = 'Java'));
INSERT INTO stage(id, name, description, year, technology_group_id) VALUES(NEXTVAL('user_seq'), 'Etap II', 'Etap drugi patronatu 2021 Java.', 2021, (SELECT id FROM technology_group WHERE name = 'Java'));
INSERT INTO stage(id, name, description, year, technology_group_id) VALUES(NEXTVAL('user_seq'), 'Etap I', 'Etap pierwszy patronatu 2020 QA', 2020, (SELECT id FROM technology_group WHERE name = 'QA'));
INSERT INTO stage(id, name, description, year, technology_group_id) VALUES(NEXTVAL('user_seq'), 'Etap II', 'Etap drugi patronatu 2020 QA', 2020, (SELECT id FROM technology_group WHERE name = 'QA'));

INSERT INTO user_stage(user_id, stage_id, mark, reason) VALUES((SELECT id FROM student_record.user WHERE login = 'AnnaNowak'), (SELECT id FROM stage WHERE name = 'Etap I' and year = 2020), 5, 'Wszystko wykonane poprawnie.');
INSERT INTO user_stage(user_id, stage_id, mark, reason) VALUES((SELECT id FROM student_record.user WHERE login = 'AnnaNowak'), (SELECT id FROM stage WHERE name = 'Etap II' and year = 2021), 3, 'Nie wszystko wykonane poprawnie.');
INSERT INTO user_stage(user_id, stage_id, mark, reason) VALUES((SELECT id FROM student_record.user WHERE login = 'JanKowalski'), (SELECT id FROM stage WHERE name = 'Etap I' and year = 2021), 0, 'Nie oddał zadania.');
INSERT INTO user_stage(user_id, stage_id, mark, reason) VALUES((SELECT id FROM student_record.user WHERE login = 'JanKowalski'), (SELECT id FROM stage WHERE name = 'Etap II' and year = 2020), null, 'Jeszcze robi.');