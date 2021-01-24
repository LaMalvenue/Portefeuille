alter table ${schema}.user_ add column portefeuille_id int8;
alter table ${schema}.user_ add constraint FKg91cca0666rmf400vlv8ii6da foreign key (portefeuille_id) references portefeuille.Portefeuille;