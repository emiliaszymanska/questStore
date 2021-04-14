create table user_types
(
    id   serial  not null
        constraint user_types_pk
            primary key,
    type varchar not null
);

create unique index user_types_user_type_id_uindex
    on user_types (id);

INSERT INTO user_types (id, type) VALUES (1, 'admin');
INSERT INTO user_types (id, type) VALUES (2, 'mentor');
INSERT INTO user_types (id, type) VALUES (3, 'student');

--
--

create table artifact_types
(
    id   serial not null
        constraint artifact_types_pk
            primary key,
    name varchar
);

create unique index artifact_types_artifact_type_id_uindex
    on artifact_types (id);

INSERT INTO artifact_types (id, name) VALUES (1, 'related to students');
INSERT INTO artifact_types (id, name) VALUES (2, 'related to mentors');
INSERT INTO artifact_types (id, name) VALUES (3, 'related to the teaching');


--
--


create table modules
(
    id   serial not null
        constraint modules_pk
            primary key,
    name varchar
);


create unique index modules_module_id_uindex
    on modules (id);

INSERT INTO modules (id, name) VALUES (3, 'web');
INSERT INTO modules (id, name) VALUES (4, 'advanced');
INSERT INTO modules (id, name) VALUES (2, 'java oop');
INSERT INTO modules (id, name) VALUES (1, 'programming basics');
INSERT INTO modules (id, name) VALUES (5, 'test module');

--
--

create table users
(
    id           serial  not null
        constraint users_pk
            primary key,
    first_name   varchar not null,
    last_name    varchar not null,
    user_type_id integer
        constraint users_user_types_user_type_id_fk
            references user_types,
    phone_number varchar,
    email        varchar not null,
    password     varchar not null,
    is_active    boolean,
    session_id   uuid,
    photo        varchar
);

create unique index users_email_uindex
    on users (email);

create unique index users_user_id_uindex
    on users (id);

INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (13, 'Natalie', 'Portman', 3, '525525555', 'natalie@gmail.com', 'asd', true, '3f229bfb-1258-4817-9cef-78f096b67d62', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (6, 'Adam', 'Sandler', 1, '524698352', 'adam@aol.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (3, 'Nicole', 'Kidman', 1, '678342750', 'nicole@aol.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (28, 'Jack', 'Nicholson', 2, '826895907', 'jack@yahoo.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (23, 'Brad', 'Pitt', 3, '821567345', 'brad@aol.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (22, 'Leonardo', 'DiCaprio', 3, '675123890', 'leo@yahoo.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (21, 'Colin', 'Firth', 1, '621357236', 'colin@gmail.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (20, 'Gal', 'Gadot', 3, '843423116', 'gal@gmail.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (19, 'Steve', 'Carell', 2, '632131147', 'steve@aol.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (17, 'Marion', 'Cotillard', 3, '654986146', 'marion@gmail.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (16, 'Rachel', 'Weisz', 2, '654765980', 'rachel@gmail.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (24, 'James', 'McAvoy', 3, '631215279', 'james@gmail.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (5, 'Harrison', 'Ford', 2, '671432876', 'harrison@yahoo.com', 'asd', true, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (30, 'Keanu', 'Reeves', 3, '531423611', 'keanu@gmail.com', 'asd', true, 'e035c6b1-b739-4380-8d04-3ec476009ede', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (15, 'Meryl', 'Streep', 2, '567876321', 'meryl@gmail.com', 'asd', true, 'd6f65db4-9158-48b0-b1b3-50aa8a948edd', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (7, 'Johnny', 'Depp', 3, '567890123', 'johnny@gmail.com', 'asd', true, '17db41e3-610a-471b-9357-ace51dc131c5', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (2, 'Will', 'Smith', 1, '685421856', 'will@gmail.com', 'asd', true, '92869e75-a175-43f3-b533-a22e5e8be588', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (1, 'George', 'Clooney', 2, '521456422', 'george@gmail.com', 'asd', true, 'ef6fb93f-d317-4c99-9ed9-39554a21b27c', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (29, 'Angelina', 'Jolie', 3, '721351154', 'angelina@aol.com', 'asd', false, '35e44d01-678d-429a-864d-a1eb2cfbea51', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (8, 'Uma', 'Thurman', 1, '543786129', 'uma@yahoo.com', 'asd', true, 'c37e2bc2-8fc4-42d5-9423-2592f634c2a6', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (4, 'Julia', 'Roberts', 2, '675331253', 'julia@gmail.com', 'asd', true, 'a2fee3d5-a562-47ee-b2e9-998a1002fde8', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (25, 'Jared', 'Leto', 3, '112112116', 'jared@gmail.com', 'asd', true, '872f79bb-726c-46aa-a4b7-c49a596f0102', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (10, 'Cara', 'Delevingne', 3, '713436723', 'cara@gmail.com', 'asd', false, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (18, 'Tom', 'Hanks', 2, '545678123', 'tom@gmail.com', 'asd', false, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (27, 'Jennifer', 'Aniston', 3, '731236483', 'jennifer@gmail.com', 'asd', false, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (9, 'Channing', 'Tatum', 3, '523232131', 'channing@aol.com', 'asd', false, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (11, 'Ben', 'Stiller', 3, '621524212', 'ben@yahoo.com', 'asd', false, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (14, 'Margot', 'Robbie', 3, '564871396', 'margot@aol.com', 'asd', false, null, null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (12, 'Charlize', 'Theron', 3, '721664119', 'charlize@gmail.com', 'asd', true, 'b0c64f66-455d-4399-a28f-eb8d157bab5d', null);
INSERT INTO users (id, first_name, last_name, user_type_id, phone_number, email, password, is_active, session_id, photo) VALUES (26, 'Cate', 'Blanchett', 2, '745093164', 'cate@yahoo.com', 'asd', false, '203a7a57-5f83-4645-a32c-7d3f04d3e93b', null);

--
--


create table students
(
    experience_level integer,
    balance          integer,
    module_id        integer
        constraint students_modules_module_id_fk
            references modules,
    user_id          integer
        constraint students_users_user_id_fk
            references users
);


create unique index students_user_id_uindex
    on students (user_id);

INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (2, 800, 2, 9);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (3, 1200, 3, 12);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (4, 1800, 4, 24);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (1, 300, 1, 27);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (1, 400, 1, 22);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (2, 700, 2, 23);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (1, 200, 1, 7);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (3, 1300, 3, 25);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (3, 1400, 3, 11);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (1, 200, 1, 14);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (5, 2000, 4, 17);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (5, 2100, 4, 10);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (3, 1100, 3, 20);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (4, 700, 4, 30);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (2, 1850, 3, 29);
INSERT INTO students (experience_level, balance, module_id, user_id) VALUES (2, 13600, 2, 13);

--
--

create table quest_types
(
    id   serial not null
        constraint quest_types_pk
            primary key,
    name varchar
);

create unique index quest_types_quest_type_id_uindex
    on quest_types (id);

INSERT INTO quest_types (id, name) VALUES (1, 'easy');
INSERT INTO quest_types (id, name) VALUES (2, 'moderate');
INSERT INTO quest_types (id, name) VALUES (3, 'difficult');


--
--

create table quests
(
    id            serial not null
        constraint quests_pk
            primary key,
    name          varchar,
    description   varchar,
    reward        varchar,
    experience    integer,
    quest_type_id integer
        constraint quests_quest_types_quest_type_id_fk
            references quest_types
);

create unique index quests_quest_id_uindex
    on quests (id);

INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (5, 'Tic Tac Toe', 'Make a simple Tic Tac Toe game', '300', 150, 2);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (12, 'Hangman', 'Create a game that simulates the classical game of Hangman', '200', 100, 1);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (11, 'Text Analyzer', 'Make a text analyzing tool for counting sentences, words etc.', '350', 150, 2);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (4, 'To-do List', 'Build a To-do List application', '400', 200, 2);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (2, 'Contact Book', 'Create a contact book app to save contact details', '200', 100, 1);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (9, 'Gift Recommendation App', 'Make an app that helps to predict what to buy a friend for their birthday', '150', 50, 1);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (6, 'Password Generator', 'Build a secure password generator', '250', 100, 1);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (3, 'Quiz App', 'The Quiz Application will present questions to the users and expect the right answers to those questions', '400', 200, 2);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (1, 'Palindrome Checker', 'Build a palindrome checker', '100', 50, 1);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (10, 'Online Shopping System', 'Build a shopping app with advanced features like geolocation', '600', 300, 3);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (8, 'Web Page', 'Build a simple web page', '250', 100, 2);
INSERT INTO quests (id, name, description, reward, experience, quest_type_id) VALUES (7, 'Course Management System', 'Create an online management software application designed for educational institutions', '550', 250, 3);

--
--

create table artifacts
(
    id               serial not null
        constraint artifacts_pk
            primary key,
    name             varchar,
    description      varchar,
    price            integer,
    artifact_type_id integer
        constraint artifacts_artifact_types_artifact_type_id_fk
            references artifact_types,
    is_group         boolean
);

create unique index artifacts_artifact_id_uindex
    on artifacts (id);

INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (1, 'Doom Inscriptions', 'All the students will focus on your coding problem until it''s solved', 1000, 1, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (5, 'Fortune''s Talisman', 'You can take your pet to school', 400, 1, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (9, 'Urn of Sleep', 'You can miss one day of the course and sleep longer', 1200, 1, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (3, 'Box of Eternal Patience', 'You can work from home for one day', 500, 3, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (6, 'Flame Book', 'Flame Book deletes one day of your absence', 800, 3, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (10, 'Energy Drink', 'One of the mentors will make a cup of coffee for you', 600, 2, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (7, 'Fountain of Relief', 'Psychologist will create a webinar on a topic that you choose', 1500, 2, false);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (2, 'Key of Chaos', 'Mentor will help your group for at least 2 hours', 2000, 2, true);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (8, 'Judgment Key', 'Your group will create a special coding exercise for the other groups in your module', 1700, 1, true);
INSERT INTO artifacts (id, name, description, price, artifact_type_id, is_group) VALUES (4, 'Vengeance Runes', 'Mentor will create a UML diagram for you', 3000, 2, true);

--
--

create table student_quests
(
    id               serial not null
        constraint student_quests_pk
            primary key,
    achievement_date date,
    quest_id         integer
        constraint student_quests_quests_quest_id_fk
            references quests,
    student_id       integer
        constraint student_quests_students_user_id_fk
            references students (user_id)
);


create unique index student_quests_student_quest_id_uindex
    on student_quests (id);

INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (1, '2020-07-06', 3, 9);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (3, '2020-08-14', 4, 9);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (4, '2020-08-03', 7, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (5, '2020-05-10', 11, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (6, '2020-07-27', 5, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (7, '2020-06-30', 3, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (8, '2020-02-12', 2, 14);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (9, '2020-05-04', 4, 13);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (10, '2020-01-08', 12, 13);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (11, '2020-08-09', 3, 20);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (12, '2020-03-26', 4, 20);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (13, '2020-04-19', 5, 20);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (14, '2020-07-23', 3, 12);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (15, '2020-07-10', 2, 12);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (16, '2020-08-31', 10, 12);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (36, '2020-11-26', 5, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (37, '2020-11-26', 5, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (42, '2020-11-26', 7, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (43, '2020-11-26', 1, 29);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (46, '2020-11-26', 1, 13);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (47, '2020-11-27', 9, 13);
INSERT INTO student_quests (id, achievement_date, quest_id, student_id) VALUES (48, '2020-11-27', 8, 13);

--
--

create table bought_artifacts
(
    purchase_date varchar,
    id            serial not null
        constraint bought_artifacts_pk
            primary key,
    artifact_id   integer
        constraint bought_artifacts_artifacts_artifact_id_fk
            references artifacts,
    student_id    integer
        constraint bought_artifacts_students_user_id_fk
            references students (user_id)
);

create unique index bought_artifacts_bought_artifacts_id_uindex
    on bought_artifacts (id);

INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-07-22', 1, 5, 25);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-06-12', 3, 3, 29);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-04-22', 6, 9, 12);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-08-20', 7, 2, 25);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-08-17', 5, 5, 30);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-05-26', 4, 7, 23);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-08-10', 2, 6, 11);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2021-04-13', 76, 1, 30);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-10', 12, 9, 7);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-02', 13, 1, 7);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-28', 21, 7, 10);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-21', 22, 7, 13);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-24', 23, 9, 14);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-20', 26, 6, 17);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-23', 27, 3, 20);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-20', 29, 1, 22);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-23', 34, 5, 30);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-07-23', 8, 6, 17);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-09', 20, 7, 9);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-08', 32, 5, 27);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-03', 31, 1, 24);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-05-28', 11, 3, 29);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-06-16', 9, 1, 20);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-07-01', 10, 8, 23);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-02', 33, 10, 29);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-30', 28, 1, 20);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-10', 35, 4, 25);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-09-22', 36, 10, 25);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-10-01', 37, 6, 25);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('', 38, 4, 9);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-11-26', 59, 2, 13);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-11-26', 61, 7, 29);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-11-26', 62, 1, 29);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('', 60, 2, 29);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-11-26', 63, 10, 13);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('', 72, 2, 13);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-11-27', 73, 10, 13);
INSERT INTO bought_artifacts (purchase_date, id, artifact_id, student_id) VALUES ('2020-12-18', 75, 6, 13);

--
--

create table group_buying
(
    id                 serial not null
        constraint group_buying_pk
            primary key,
    student_payment    integer,
    payment_date       date,
    bought_artifact_id integer
        constraint group_buying_bought_artifacts_bought_artifact_id_fk
            references bought_artifacts,
    student_id         integer
        constraint group_buying_students_user_id_fk
            references students (user_id)
);


create unique index group_buying_group_buying_id_uindex
    on group_buying (id);

INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (6, 600, '2020-10-10', 35, 25);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (7, 600, '2020-10-10', 35, 7);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (8, 600, '2020-10-10', 35, 9);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (9, 600, '2020-10-10', 35, 10);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (10, 600, '2020-10-10', 35, 11);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (1, 600, '2020-08-20', 7, 25);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (5, 800, '2020-05-28', 10, 29);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (3, 700, '2020-06-16', 7, 20);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (2, 700, '2020-07-23', 7, 17);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (4, 900, '2020-07-01', 10, 23);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (11, 300, '2020-11-11', 38, 9);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (12, 100, '2020-11-25', 38, 11);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (17, 200, '2020-11-26', 59, 13);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (18, 500, '2020-11-26', 59, 12);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (19, 1300, '2020-11-26', 59, 29);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (20, 100, '2020-11-26', 60, 29);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (21, 100, '2020-11-26', 2, 13);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (22, 100, '2020-11-26', 2, 13);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (23, 150, '2020-11-26', 2, 13);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (24, 155, '2020-11-26', 2, 13);
INSERT INTO group_buying (id, student_payment, payment_date, bought_artifact_id, student_id) VALUES (29, 200, '2020-11-27', 72, 13);

--
--

