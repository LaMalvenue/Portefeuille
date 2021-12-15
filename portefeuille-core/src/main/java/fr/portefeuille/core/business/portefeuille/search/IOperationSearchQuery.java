package fr.portefeuille.core.business.portefeuille.search;

import java.util.Date;

import org.iglooproject.jpa.more.business.search.query.ISearchQuery;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.Operation;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.model.atomic.OperationStatut;
import fr.portefeuille.core.business.referencedata.model.OperationCategorie;

public interface IOperationSearchQuery extends ISearchQuery<Operation, OperationSort> {

	IOperationSearchQuery label(String label);

	IOperationSearchQuery date(Date date);

	IOperationSearchQuery date(Date dateMin, Date dateMax);

	IOperationSearchQuery statut(OperationStatut statut);

	IOperationSearchQuery categorie(OperationCategorie categorie);

	IOperationSearchQuery portefeuille(Portefeuille portefeuille);

	IOperationSearchQuery compte(Compte compte);

}
