-- liquibase formatted sql

-- changeset frost:3
create table sessions
(
    id uuid primary key,
    user_id bigint references users(id) on delete cascade,
    expires_at timestamp not null default current_timestamp + interval '8 hours'
);