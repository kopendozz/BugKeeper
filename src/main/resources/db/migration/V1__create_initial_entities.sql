--
-- Create project table and seed default data
--

create sequence if not exists project_id_seq start with 1 increment by 1;

create table if not exists project
(
    id   bigserial primary key,
    name varchar(45) not null
);

insert into project
values (nextval('project_id_seq'), 'Gemini'),
       (nextval('project_id_seq'), 'Apollo');

--
-- Create users table and seed default data
--

create table if not exists users
(
    username   varchar(45) not null primary key,
    role       varchar(45) not null,
    first_name varchar(45) not null,
    last_name  varchar(45) not null,
    password   varchar(60) not null,
    enabled    boolean     not null
);

insert into users
values ('kevin', 'ADMIN', 'Kevin', 'Mazepa', '$2a$12$CqC6wuQlzt//jTfEBlzW3OAq4QtWuAM5X5h86Mk405rRVdKw3LvEi', true),
       ('sarah', 'USER', 'Sarah', 'Shevcheko', '$2a$12$xS2OQgKQJmUAkswF/lnMne5NWqGTDJyIng0iB7nXpviOQUhuAVcCu', true);

--
-- Create user_project table and seed default data
--

create table if not exists user_project
(
    username   varchar(45) not null references users,
    project_id bigint      not null references project,
    primary key (username, project_id)
);

insert into user_project
values ('kevin', (select id from project where name = 'Gemini')),
       ('sarah', (select id from project where name = 'Gemini')),
       ('kevin', (select id from project where name = 'Apollo'));

--
-- Create priority table and seed default data
--

create sequence if not exists priority_id_seq start with 1 increment by 1;

create table if not exists priority
(
    id   bigserial   not null primary key,
    name varchar(45) not null
);

insert into priority
values (nextval('priority_id_seq'), 'Critical'),
       (nextval('priority_id_seq'), 'High'),
       (nextval('priority_id_seq'), 'Medium'),
       (nextval('priority_id_seq'), 'Low');

--
-- Create status table and seed default data
--

create sequence if not exists status_id_seq start with 1 increment by 1;

create table if not exists status
(
    id   bigserial   not null primary key,
    name varchar(45) not null
);

insert into status
values (nextval('status_id_seq'), 'New'),
       (nextval('status_id_seq'), 'In Fix'),
       (nextval('status_id_seq'), 'Fix Complete - Not Deployed'),
       (nextval('status_id_seq'), 'Fix Complete - Deployed'),
       (nextval('status_id_seq'), 'In Test'),
       (nextval('status_id_seq'), 'System Test Complete'),
       (nextval('status_id_seq'), 'Fix Complete - Deployed'),
       (nextval('status_id_seq'), 'Failed'),
       (nextval('status_id_seq'), 'Cannot Reproduce'),
       (nextval('status_id_seq'), 'Not an Issue'),
       (nextval('status_id_seq'), 'Closed');

--
-- Create issue table
--

create table if not exists issue
(
    id           bigserial     not null primary key,
    project_id   bigint        not null references project,
    priority_id  bigint        not null references priority,
    status_id    bigint        not null references status,
    name         varchar(140)  not null,
    description  varchar(5000) not null,
    assigned_to  varchar(45),
    created_by   varchar(45)   not null,
    created_at   timestamp     not null,
    last_updated timestamp     not null
);

--
-- Create issue table
--

create table if not exists attachment
(
    id bigserial not null primary key,
    issue_id bigint default null references issue,
    name varchar(255) not null,
    attachment bytea not null
);
