package fr.portefeuille.web.application.portefeuille.model;

import org.apache.wicket.model.IModel;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel.CompositingStrategy;
import org.iglooproject.wicket.more.model.AbstractSearchQueryDataProvider;
import org.iglooproject.wicket.more.model.GenericEntityModel;

import com.google.common.collect.ImmutableMap;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.search.IPortefeuilleSearchQuery;
import fr.portefeuille.core.business.portefeuille.search.PortefeuilleSort;

public class PortefeuilleDataProvider extends AbstractSearchQueryDataProvider<Portefeuille, PortefeuilleSort> {

	private static final long serialVersionUID = -8029251075537510035L;

	private final CompositeSortModel<PortefeuilleSort> sortModel = new CompositeSortModel<>(
		CompositingStrategy.LAST_ONLY,
		ImmutableMap.of(
				PortefeuilleSort.NAME, PortefeuilleSort.NAME.getDefaultOrder()
		),
		ImmutableMap.of(
				PortefeuilleSort.ID, PortefeuilleSort.ID.getDefaultOrder()
		)
	);

	@Override
	public IModel<Portefeuille> model(Portefeuille object) {
		return GenericEntityModel.of(object);
	}

	@Override
	protected ISearchQuery<Portefeuille, PortefeuilleSort> getSearchQuery() {
		return createSearchQuery(IPortefeuilleSearchQuery.class)
			.sort(sortModel.getObject());
	}

}
