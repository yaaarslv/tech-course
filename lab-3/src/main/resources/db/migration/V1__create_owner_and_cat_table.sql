if not exists (select name from sys.databases where name = 'lab-2')
begin

create database [lab-2]
end
go

use [lab-2]
go


create table Owner
(
    Идентификатор bigint identity
        constraint PK_Owner
            primary key,
    Имя varchar(max) not null,
    [Дата рождения] date not null
)
go


create table Cat
(
    Идентификатор bigint identity
        constraint PK_Котик
            primary key,
    Имя varchar(max) not null,
    [Дата рождения] date not null,
    Порода varchar(max) not null,
    Цвет varchar(max) not null
        constraint CK_Cat
            check ([Цвет] = 'White' OR [Цвет] = 'Red' OR [Цвет] = 'Brown' OR [Цвет] = 'Gray' OR [Цвет] = 'Black'),
    Владелец bigint not null
        constraint FK_Cat_Owner
            references Owner
)
go