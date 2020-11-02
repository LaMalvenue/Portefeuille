package fr.portefeuille.core.business.compte.search;

import org.iglooproject.jpa.more.business.search.query.ISearchQuery;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.model.atomic.TypeCompte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

public interface ICompteSearchQuery extends ISearchQuery<Compte, CompteSort> {

	ICompteSearchQuery text(String text);

	ICompteSearchQuery typeCompte(TypeCompte typeCompte);

	ICompteSearchQuery portefeuille(Portefeuille portefeuille);

}
