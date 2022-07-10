create table review
(
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
    primary key (user_id)
) comment '유저';