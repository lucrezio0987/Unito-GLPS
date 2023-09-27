begin;

drop table if exists SP;
drop table if exists S;
drop table if exists P;

create table S (
  SNum varchar(3),
  SName varchar(20) not null,
  Status decimal(3),
  City varchar(20) not null,
  constraint S_PK primary key(SNum)
);

create table P (
  PNum varchar(3),
  PName varchar(20) not null,
  Color varchar(20) not null,
  Weight decimal(3),
  City varchar(20) not null,
  constraint P_PK primary key(PNum)
);

create table SP (
  SNum varchar(3),
  PNum varchar(3),
  QTY decimal(5) not null,
  constraint SP_PK primary key(SNum, PNum),
  constraint SP_FK_S foreign key(SNum) references S(SNum) on delete cascade,
  constraint SP_FK_P foreign key(PNum) references P(PNum) on delete cascade
);

insert into S(SNum, SName, Status, City) values ('S1','Smith',20,'London');
insert into S(SNum, SName, Status, City) values ('S2','Jones',10,'Paris');
insert into S(SNum, SName, Status, City) values ('S3','Blake',30,'Paris');
insert into S(SNum, SName, Status, City) values ('S4','Clark',20,'London');
insert into S(SNum, SName, Status, City) values ('S5','Adams',30,'Athens');

insert into P(PNum, PName, Color, Weight, City) values ('P1','Nut','Red',12,'London');
insert into P(PNum, PName, Color, Weight, City) values ('P2','Bolt','Green',17,'Paris');
insert into P(PNum, PName, Color, Weight, City) values ('P3','Screw','Blue',17,'Rome');
insert into P(PNum, PName, Color, Weight, City) values ('P4','Screw','Red',14,'London');
insert into P(PNum, PName, Color, Weight, City) values ('P5','Cam','Blue',12,'Paris');
insert into P(PNum, PName, Color, Weight, City) values ('P6','Cog','Red',19,'London');

insert into SP(SNum, PNum, QTY) values ('S1','P1',300);
insert into SP(SNum, PNum, QTY) values ('S1','P2',200);
insert into SP(SNum, PNum, QTY) values ('S1','P3',400);
insert into SP(SNum, PNum, QTY) values ('S1','P4',200);
insert into SP(SNum, PNum, QTY) values ('S1','P5',100);
insert into SP(SNum, PNum, QTY) values ('S1','P6',100);
insert into SP(SNum, PNum, QTY) values ('S2','P1',300);
insert into SP(SNum, PNum, QTY) values ('S2','P2',400);
insert into SP(SNum, PNum, QTY) values ('S3','P2',200);
insert into SP(SNum, PNum, QTY) values ('S4','P2',200);
insert into SP(SNum, PNum, QTY) values ('S4','P4',300);
insert into SP(SNum, PNum, QTY) values ('S4','P5',400);

commit;
