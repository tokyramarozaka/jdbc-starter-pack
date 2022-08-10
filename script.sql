create table todo
(
    id        serial primary key,
    name      varchar(200) not null,
    completed boolean
);
