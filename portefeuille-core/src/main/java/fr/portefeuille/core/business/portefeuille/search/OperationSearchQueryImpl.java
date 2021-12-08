package fr.portefeuille.core.business.portefeuille.search;

import org.iglooproject.jpa.more.business.search.query.AbstractHibernateSearchSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.portefeuille.core.business.portefeuille.model.Operation;

@Component
@Scope("prototype")
public class OperationSearchQueryImpl extends AbstractHibernateSearchSearchQuery<Operation, OperationSort> implements IOperationSearchQuery {

	protected OperationSearchQueryImpl() {
		super(Operation.class);
	}

}
