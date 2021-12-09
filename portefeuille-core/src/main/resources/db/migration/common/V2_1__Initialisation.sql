create table ${schema}.Compte (
	id int8 not null, 
	portefeuille_id int8 not null,
	label ${type.text}, 
	solde numeric(19, 2) not null, 
	type varchar(255) not null, 
	primary key (id)
);

create table ${schema}.Operation (
	id int8 not null, 
	budgetAffecte 
	date not null, 
	categorie_id int8, 
	date date not null, 
	label ${type.text} not null, 
	montant numeric(19, 2) not null, 
	statut varchar(255) not null, 
	type varchar(255) not null, 
	compte_id int8 not null, 
	primary key (id)
);

create table ${schema}.Portefeuille (
	id int8 not null, 
	proprietaire_id int8 not null,
	primary key (id)
);

create table ${schema}.OperationCategorie (id int8 not null, deleteable boolean not null, disableable boolean not null, editable boolean not null, enabled boolean not null, position int4 not null, label_en ${type.text}, label_fr ${type.text}, primary key (id));

create sequence ${schema}.Compte_id_seq start 1 increment 1;
create sequence ${schema}.Operation_id_seq start 1 increment 1;
create sequence ${schema}.Portefeuille_id_seq start 1 increment 1;
create sequence ${schema}.OperationCategorie_id_seq start 1 increment 1;

alter table ${schema}.Operation add constraint FK6wh35xtcpl1e2s3sip8fvlntb foreign key (compte_id) references ${schema}.Compte;
alter table ${schema}.Compte add constraint FKk6v4jny49vdsh1722s3ppwd2l foreign key (portefeuille_id) references ${schema}.Portefeuille;
alter table ${schema}.Portefeuille add constraint FK52rhdcoatklbirps3jqs2x40k foreign key (proprietaire_id) references ${schema}.user_;
alter table ${schema}.Operation add constraint FK498m4bfl39u7l6m0m5cglkmxj foreign key (categorie_id) references ${schema}.OperationCategorie;

