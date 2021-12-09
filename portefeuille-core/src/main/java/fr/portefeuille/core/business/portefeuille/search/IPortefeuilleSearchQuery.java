package fr.portefeuille.core.business.portefeuille.search;

import org.iglooproject.jpa.more.business.search.query.ISearchQuery;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.user.model.User;

public interface IPortefeuilleSearchQuery extends ISearchQuery<Portefeuille, PortefeuilleSort> {

	IPortefeuilleSearchQuery proprietaire(User proprietaire);

}
