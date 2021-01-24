create table ${schema}.Compte (id int8 not null, fondsDisponibles float8 not null, label ${type.text}, type varchar(255) not null, portefeuille_id int8 not null, primary key (id));
create table ${schema}.Operation (id int8 not null, type varchar(255) not null, categorie varchar(255), date timestamp not null, label ${type.text} not null, budgetAffecte timestamp not null, montant float8 not null, statut varchar(255) not null, compte_id int8 not null, primary key (id));
create table ${schema}.Portefeuille (id int8 not null, nom ${type.text} not null, primary key (id));

create sequence ${schema}.Compte_id_seq start 1 increment 1;
create sequence ${schema}.Operation_id_seq start 1 increment 1;
create sequence ${schema}.Portefeuille_id_seq start 1 increment 1;

alter table ${schema}.Compte add constraint FKk6v4jny49vdsh1722s3ppwd2l foreign key (portefeuille_id) references  ${schema}.Portefeuille;
alter table ${schema}.Operation add constraint FK6wh35xtcpl1e2s3sip8fvlntb foreign key (compte_id) references  ${schema}.Compte;