use [lab-2]
go

alter table Cat
    add [Длина хвоста] int
go

create table Flea
(
    Идентификатор bigint identity
        constraint PK_Flea
            primary key,
    Имя varchar(max) not null,
    Котик bigint not null
        constraint FK_Flea_Cat
            references Cat,
)
go