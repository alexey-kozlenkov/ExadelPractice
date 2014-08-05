INSERT INTO "USER" (name, email, login, password, role)
VALUES ('Julia Malyshko', 'julia@gmail.com', 'Julia Malyshko', 'pass', 'student');
INSERT INTO "USER" (name, email, login, password, role)
VALUES ('Alexey Kozlenkov', 'k@gmail.com', 'Alexey Kozlenkov', 'pass', 'student');
INSERT INTO "USER" (name, email, login, password, role)
VALUES ('Alexey Stefanovich', 'alan.lonoi@yandex.ru', 'Alexey Stefanovich', 'pass', 'student');
INSERT INTO "USER" (name, email, login, password, role)
VALUES ('Vasiliy Ermakov', 'vasia-94@tut.by', 'Vasiliy Ermakov', 'pass', 'student');

INSERT INTO "STUDENT" (id, state, hire_date, university, faculty, course, s_group, graduation_date, working_hours, role_current_project, techs_current_project, english_level)
VALUES (1, 'training', '01-01-2014', 'BSU', 'FAMCS', 3, 7, '01-01-2018', 4, 'front-end', 'JS, CSS, JSON, JQUERY',
        'Intermediate');
INSERT INTO "STUDENT" (id, state, hire_date, university, faculty, course, s_group, graduation_date, working_hours, role_current_project, techs_current_project, english_level)
VALUES (2, 'training', '01-01-2014', 'BSU', 'FAMCS', 3, 5, '01-01-2018', 4, 'back-end', 'Spring, Hibernate, SQL',
        'Intermediate');
INSERT INTO "STUDENT" (id, state, hire_date, university, faculty, course, s_group, graduation_date, working_hours, role_current_project, techs_current_project, english_level)
VALUES (3, 'training', '01-01-2014', 'BSU', 'FAMCS', 3, 8, '01-01-2018', 8, 'front-end',
        'JS, CSS, JSON, JQUERY, JAVA, C++, C, Python, Ruby, SQL, Scala, Assembly, C#, .NET, Pascal, Perl, JBOSS, ActionScript',
        'Intermediate');
INSERT INTO "STUDENT" (id, state, hire_date, university, faculty, course, s_group, graduation_date, working_hours, role_current_project, techs_current_project, english_level)
VALUES (4, 'training', '01-01-2014', 'BSU', 'FAMCS', 3, 3, '01-01-2018', 4, 'back-end', 'Spring, Hibernate, SQL',
        'Intermediate');

