package fr.portefeuille.core.business.portefeuille.search;

import org.iglooproject.jpa.more.business.search.query.ISearchQuery;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.atomic.CompteType;

public interface ICompteSearchQuery extends ISearchQuery<Compte, CompteSort> {

	ICompteSearchQuery text(String text);

	ICompteSearchQuery typeCompte(CompteType typeCompte);

}
