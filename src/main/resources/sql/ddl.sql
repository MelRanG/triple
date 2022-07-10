create table review
(
    created_date datetime,
    modified_date datetime,
    review_id   varchar(255) not null,
    content     varchar(255) comment '내용',
    place_id    varchar(255) comment '장소 id',
    user_id     varchar(255) comment '유저 id',
    primary key (review_id)
) comment '리뷰';

create table place
(
    place_id    varchar(255) not null,
    primary key (place_id)
) comment '장소';

create table photo
(
    photo_id    varchar(255) not null,
    review_id   varchar(255) not null,
    primary key (photo_id)
) comment '사진';

create table users
(
    user_id    varchar(255) not null,
    point integer not null,
    primary key (user_id)
) comment '유저';

create table point_history
(
    created_date datetime not null,
    modified_date datetime not null,
    point integer not null,
    review varchar(255),
    type varchar(255),
    user_id varchar(255),
    primary key (id)
) comment '포인트 기록';