package fr.portefeuille.web.application.portefeuille.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel.CompositingStrategy;
import org.iglooproject.wicket.more.model.AbstractSearchQueryDataProvider;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;

import com.google.common.collect.ImmutableMap;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.business.portefeuille.model.atomic.CompteType;
import fr.portefeuille.core.business.portefeuille.search.CompteSort;
import fr.portefeuille.core.business.portefeuille.search.ICompteSearchQuery;

public class CompteDataProvider extends AbstractSearchQueryDataProvider<Compte, CompteSort> {

	private static final long serialVersionUID = 3864000715169877412L;

	private final IModel<String> textModel = Model.of();
	private final IModel<CompteType> typeCompteModel = Model.of();

	private final CompositeSortModel<CompteSort> sortModel = new CompositeSortModel<>(
		CompositingStrategy.LAST_ONLY,
		ImmutableMap.of(
			CompteSort.TYPE_COMPTE, CompteSort.TYPE_COMPTE.getDefaultOrder()
		),
		ImmutableMap.of(
			CompteSort.ID, CompteSort.ID.getDefaultOrder()
		)
	);

	@Override
	public IModel<Compte> model(Compte object) {
		return GenericEntityModel.of(object);
	}

	public IModel<String> getTextModel() {
		return textModel;
	}

	public IModel<CompteType> getTypeCompteModel() {
		return typeCompteModel;
	}

	public CompositeSortModel<CompteSort> getSortModel() {
		return sortModel;
	}

	@Override
	protected ISearchQuery<Compte, CompteSort> getSearchQuery() {
		return createSearchQuery(ICompteSearchQuery.class)
			.text(textModel.getObject())
			.typeCompte(typeCompteModel.getObject())
			.sort(sortModel.getObject());
	}

	@Override
	public void detach() {
		super.detach();
		Detachables.detach(
			textModel,
			typeCompteModel,
			sortModel
		);
	}

}
