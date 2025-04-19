create table users
(
    id bigserial primary key,
    login varchar(64) not null unique,
    password varchar(128) not null
);

create table locations
(
    id bigserial primary key,
    name varchar(64) not null,
    latitude decimal not null,
    longitude decimal not null,
    user_id bigserial references users(id) on delete cascade
);

create table sessions
(
    id uuid primary key,
    user_id bigserial references users(id) on delete cascade,
    expires_at timestamp not null default current_timestamp + interval '8 hours'
);