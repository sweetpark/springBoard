create table member(
    id BIGINT not null AUTO_INCREMENT,
    name varchar(10) not null,
    loginID varchar(20) not null,
    passwd varchar(12) not null,
    address varchar(100),
    phone varchar(13) not null,
    email varchar(100) not null,
    primary key(id)
);


create table board(
    id BIGINT not null AUTO_INCREMENT,
    title varchar(100) not null,
    body varchar(2000),
    views BIGINT default 0,
    create_time timestamp default CURRENT_TIMESTAMP,
    good BIGINT default 0,
    bad BIGINT default 0,
    member_id BIGINT not null,
    foreign key(member_id) references member(id),
    primary key(id)
)


create table attach_file(
    id BIGINT not null AUTO_INCREMENT,
    store_filename varchar(100),
    upload_filename varchar(100),
    board_id BIGINT,
    foreign key(board_id) references board(id),
    primary key(id)
)

create table image_file(
    id BIGINT not null AUTO_INCREMENT,
    store_filename varchar(100),
    upload_filename varchar(100),
    board_id BIGINT,
    foreign key(board_id) references board(id),
    primary key(id)
)

create table comment(
    id BIGINT not null AUTO_INCREMENT,
    body varchar(1000) not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    good BIGINT default 0,
    bad BIGINT default 0,
    board_id BIGINT,
    member_id BIGINT,
    primary key(id),
    foreign key(board_id) references board(id),
    foreign key(member_id) references member(id)
)