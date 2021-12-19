delete from category;
delete from invitations;
delete from journal;
delete from note;
delete from user_roles;
delete from users;

insert into users(password,login) values ('ilyaaaa','ilya');

insert into category(name,id_jrnl) values ('Food',70);
insert into category(name,id_jrnl) values ('Car',70);
insert into category(name,id_jrnl) values ('Salary',70);
insert into category(name,id_jrnl) values ('Beauty',70);
insert into category(name,id_jrnl) values ('Food',76);
insert into category(name,id_jrnl) values ('Games',76);
insert into category(name,id_jrnl) values ('Transport',76);
insert into category(name,id_jrnl) values ('Food',77);
insert into category(name,id_jrnl) values ('Salary',77);
insert into category(name,id_jrnl) values ('Education',77);
insert into category(name,id_jrnl) values ('Clothes',77);

insert into invitations(id_user,id_jrnl,id_role,accepted,declined) values (3,10,70,2,true,false);
insert into invitations(id_user,id_jrnl,id_role,accepted,declined) values (4,9,70,3,true,false);

insert into journal(id,name) values (70,'Family');
insert into journal(id,name) values (76,'BatalinJournal');
insert into journal(id,name) values (77,'Journal');





