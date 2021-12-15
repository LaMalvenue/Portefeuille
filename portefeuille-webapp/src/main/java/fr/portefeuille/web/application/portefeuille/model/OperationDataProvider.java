package fr.portefeuille.web.application.portefeuille.model;

import java.util.Date;

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
import fr.portefeuille.core.business.portefeuille.model.Operation;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.model.atomic.OperationStatut;
import fr.portefeuille.core.business.portefeuille.search.IOperationSearchQuery;
import fr.portefeuille.core.business.portefeuille.search.OperationSort;
import fr.portefeuille.core.business.referencedata.model.OperationCategorie;

public class OperationDataProvider extends AbstractSearchQueryDataProvider<Operation, OperationSort> {

	private static final long serialVersionUID = -8029251075537510035L;

	private final IModel<String> labelModel = Model.of();
	private final IModel<Date> dateMinModel = Model.of();
	private final IModel<Date> dateMaxModel = Model.of();
	private final IModel<OperationStatut> statutModel = Model.of();
	private final IModel<OperationCategorie> categorieModel = Model.of();
	private final IModel<Portefeuille> portefeuilleModel = new GenericEntityModel<>();
	private final IModel<Compte> compteModel = new GenericEntityModel<>();
	private final CompositeSortModel<OperationSort> sortModel = new CompositeSortModel<>(
		CompositingStrategy.LAST_ONLY,
		ImmutableMap.of(
			OperationSort.DATE, OperationSort.DATE.getDefaultOrder(),
			OperationSort.MONTANT, OperationSort.MONTANT.getDefaultOrder()
		),
		ImmutableMap.of(
			OperationSort.ID, OperationSort.ID.getDefaultOrder()
		)
	);

	@Override
	public void detach() {
		super.detach();
		Detachables.detach(
			labelModel,
			dateMinModel,
			dateMaxModel,
			statutModel,
			categorieModel,
			portefeuilleModel,
			compteModel,
			sortModel
		);
	}

	@Override
	public IModel<Operation> model(Operation object) {
		return GenericEntityModel.of(object);
	}

	public IModel<String> getLabelModel() {
		return labelModel;
	}

	public IModel<Date> getDateMinModel() {
		return dateMinModel;
	}

	public IModel<Date> getDateMaxModel() {
		return dateMaxModel;
	}

	public IModel<OperationStatut> getStatutModel() {
		return statutModel;
	}

	public IModel<OperationCategorie> getCategorieModel() {
		return categorieModel;
	}

	public IModel<Portefeuille> getPortefeuilleModel() {
		return portefeuilleModel;
	}

	public IModel<Compte> getCompteModel() {
		return compteModel;
	}

	public CompositeSortModel<OperationSort> getSortModel() {
		return sortModel;
	}

	@Override
	protected ISearchQuery<Operation, OperationSort> getSearchQuery() {
		return createSearchQuery(IOperationSearchQuery.class)
			.label(getLabelModel().getObject())
			.date(getDateMinModel().getObject(), getDateMaxModel().getObject())
			.statut(getStatutModel().getObject())
			.categorie(getCategorieModel().getObject())
			.portefeuille(getPortefeuilleModel().getObject())
			.compte(getCompteModel().getObject())
			.sort(getSortModel().getObject());
	}

}
