package fr.portefeuille.web.application.compte.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel.CompositingStrategy;
import org.iglooproject.wicket.more.model.AbstractSearchQueryDataProvider;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;

import com.google.common.collect.ImmutableMap;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.model.atomic.TypeCompte;
import fr.portefeuille.core.business.compte.search.CompteSort;
import fr.portefeuille.core.business.compte.search.ICompteSearchQuery;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

public class CompteDataProvider extends AbstractSearchQueryDataProvider<Compte, CompteSort> {

	private static final long serialVersionUID = 3864000715169877412L;

	private final IModel<String> textModel = Model.of();
	private final IModel<TypeCompte> typeCompteModel = Model.of();
	private final IModel<Portefeuille> portefeuilleModel = new GenericEntityModel<>();

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

	public IModel<String> getTextModel() {
		return textModel;
	}

	public IModel<TypeCompte> getTypeCompteModel() {
		return typeCompteModel;
	}

	public IModel<Portefeuille> getPortefeuilleModel() {
		return portefeuilleModel;
	}

	public CompositeSortModel<CompteSort> getSortModel() {
		return sortModel;
	}

	@Override
	protected ISearchQuery<Compte, CompteSort> getSearchQuery() {
		return createSearchQuery(ICompteSearchQuery.class)
			.text(textModel.getObject())
			.typeCompte(typeCompteModel.getObject())
			.portefeuille(portefeuilleModel.getObject())
			.sort(sortModel.getObject());
	}

	@Override
	public void detach() {
		super.detach();
		Detachables.detach(
			textModel,
			typeCompteModel,
			portefeuilleModel,
			sortModel
		);
	}

}
