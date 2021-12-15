package fr.portefeuille.core.business.portefeuille.search;

import java.util.Date;

import org.iglooproject.jpa.more.business.search.query.AbstractHibernateSearchSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.Operation;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.model.atomic.OperationStatut;
import fr.portefeuille.core.business.referencedata.model.OperationCategorie;

@Component
@Scope("prototype")
public class OperationSearchQueryImpl extends AbstractHibernateSearchSearchQuery<Operation, OperationSort> implements IOperationSearchQuery {

	protected OperationSearchQueryImpl() {
		super(Operation.class);
	}

	@Override
	public IOperationSearchQuery label(String label) {
		must(matchIfGiven(Operation.LABEL, label));
		return this;
	}

	@Override
	public IOperationSearchQuery date(Date date) {
		must(matchRange(
			Operation.DATE,
			date,
			date
		));
		return this;
	}

	@Override
	public IOperationSearchQuery date(Date dateMin, Date dateMax) {
		must(matchRange(
			Operation.DATE,
			dateMin,
			dateMax
		));
		return this;
	}

	@Override
	public IOperationSearchQuery statut(OperationStatut statut) {
		must(matchIfGiven(Operation.STATUT, statut));
		return this;
	}

	@Override
	public IOperationSearchQuery categorie(OperationCategorie categorie) {
		must(matchIfGiven(Operation.CATEGORIE, categorie));
		return this;
	}

	@Override
	public IOperationSearchQuery portefeuille(Portefeuille portefeuille) {
		must(matchIfGiven(Operation.PORTEFEUILLE, portefeuille));
		return this;
	}

	@Override
	public IOperationSearchQuery compte(Compte compte) {
		must(matchIfGiven(Operation.COMPTE, compte));
		return this;
	}

}
