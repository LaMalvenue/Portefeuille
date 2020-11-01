package fr.portefeuille.web.application.compte.model;

import org.apache.wicket.model.IModel;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel.CompositingStrategy;
import org.iglooproject.wicket.more.model.AbstractSearchQueryDataProvider;
import org.iglooproject.wicket.more.model.GenericEntityModel;

import com.google.common.collect.ImmutableMap;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.search.CompteSort;
import fr.portefeuille.core.business.compte.search.ICompteSearchQuery;

public class CompteDataProvider extends AbstractSearchQueryDataProvider<Compte, CompteSort> {

	private static final long serialVersionUID = 3864000715169877412L;

	private final CompositeSortModel<CompteSort> sortModel = new CompositeSortModel<>(
		CompositingStrategy.LAST_ONLY,
		ImmutableMap.of(
			CompteSort.LABEL, CompteSort.LABEL.getDefaultOrder()
		),
		ImmutableMap.of(
			CompteSort.ID, CompteSort.ID.getDefaultOrder()
		)
	);

	@Override
	public IModel<Compte> model(Compte object) {
		return GenericEntityModel.of(object);
	}

	@Override
	protected ISearchQuery<Compte, CompteSort> getSearchQuery() {
		return createSearchQuery(ICompteSearchQuery.class)
			.sort(sortModel.getObject());
	}

}
