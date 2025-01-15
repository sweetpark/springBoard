create table member(
    id INTEGER not null AUTO_INCREMENT,
    name varchar(10) not null,
    passwd varchar(12) not null,
    address varchar(100),
    primary key(id)
);


create table board(
    id INTEGER not null AUTO_INCREMENT,
    title varchar(100) not null,
    body varchar(2000),
    member_id Integer not null,
    image_files varchar(2000),
    upload_files varchar(2000),
    foreign key(member_id) references member(id),
    primary key(id)
)
