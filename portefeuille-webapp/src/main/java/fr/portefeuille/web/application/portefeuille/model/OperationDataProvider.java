package fr.portefeuille.web.application.portefeuille.model;

import org.apache.wicket.model.IModel;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel.CompositingStrategy;
import org.iglooproject.wicket.more.model.AbstractSearchQueryDataProvider;
import org.iglooproject.wicket.more.model.GenericEntityModel;

import com.google.common.collect.ImmutableMap;

import fr.portefeuille.core.business.portefeuille.model.Operation;
import fr.portefeuille.core.business.portefeuille.search.IOperationSearchQuery;
import fr.portefeuille.core.business.portefeuille.search.OperationSort;

public class OperationDataProvider extends AbstractSearchQueryDataProvider<Operation, OperationSort> {

	private static final long serialVersionUID = -8029251075537510035L;

	private final CompositeSortModel<OperationSort> sortModel = new CompositeSortModel<>(
		CompositingStrategy.LAST_ONLY,
		ImmutableMap.of(
			OperationSort.LABEL, OperationSort.LABEL.getDefaultOrder()
		),
		ImmutableMap.of(
			OperationSort.ID, OperationSort.ID.getDefaultOrder()
		)
	);

	@Override
	public IModel<Operation> model(Operation object) {
		return GenericEntityModel.of(object);
	}

	@Override
	protected ISearchQuery<Operation, OperationSort> getSearchQuery() {
		return createSearchQuery(IOperationSearchQuery.class)
			.sort(sortModel.getObject());
	}

	public CompositeSortModel<OperationSort> getSortModel() {
		return sortModel;
	}

}
