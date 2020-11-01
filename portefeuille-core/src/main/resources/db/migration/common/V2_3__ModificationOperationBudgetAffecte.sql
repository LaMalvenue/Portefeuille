alter table ${schema}.Operation add column budgetAffecte_annee int4 not null;
alter table ${schema}.Operation add column budgetAffecte_mois varchar(255) not null;
alter table ${schema}.Operation drop column budgetaffecte;