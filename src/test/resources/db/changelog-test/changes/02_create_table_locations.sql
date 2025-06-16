-- liquibase formatted sql

-- changeset frost:1
create table locations
(
    id bigserial primary key,
    name varchar(64) not null,
    latitude decimal not null,
    longitude decimal not null,
    user_id bigint references users(id) on delete cascade
);

-- changeset frost:2
alter table locations
    add constraint user_city_unique unique (name, user_id);