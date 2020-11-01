alter table ${schema}.Portefeuille rename column name to nom;
alter table ${schema}.Operation add column type varchar(255) not null;
