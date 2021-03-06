package fr.portefeuille.web.application.administration.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.model.UserGroup;
import fr.portefeuille.core.business.user.search.IAbstractUserSearchQuery;
import fr.portefeuille.core.business.user.search.UserSort;
import org.iglooproject.jpa.more.business.search.query.ISearchQuery;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel;
import org.iglooproject.wicket.more.markup.html.sort.model.CompositeSortModel.CompositingStrategy;
import org.iglooproject.wicket.more.model.AbstractSearchQueryDataProvider;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;

import com.google.common.collect.ImmutableMap;

public abstract class AbstractUserDataProvider<U extends User> extends AbstractSearchQueryDataProvider<U, UserSort> {

	private static final long serialVersionUID = -8540890431031886412L;

	private final Class<U> clazz;

	private final IModel<String> nameModel = Model.of();

	private final IModel<UserGroup> groupModel = new GenericEntityModel<>();

	private final IModel<Boolean> includeInactivesModel = Model.of(Boolean.FALSE);

	private final CompositeSortModel<UserSort> sortModel = new CompositeSortModel<>(
		CompositingStrategy.LAST_ONLY,
		ImmutableMap.of(
			UserSort.FULL_NAME, UserSort.FULL_NAME.getDefaultOrder(),
			UserSort.ID, UserSort.ID.getDefaultOrder()
		),
		ImmutableMap.of(
			UserSort.ID, UserSort.ID.getDefaultOrder()
		)
	);

	public AbstractUserDataProvider(Class<U> clazz) {
		super();
		Injector.get().inject(this);
		
		this.clazz = clazz;
	}

	@Override
	public IModel<U> model(U item) {
		return new GenericEntityModel<>(item);
	}

	public Class<U> getClazz() {
		return clazz;
	}

	public IModel<String> getNameModel() {
		return nameModel;
	}

	public IModel<UserGroup> getGroupModel() {
		return groupModel;
	}

	public IModel<Boolean> getIncludeInactivesModel() {
		return includeInactivesModel;
	}

	public CompositeSortModel<UserSort> getSortModel() {
		return sortModel;
	}

	protected abstract IAbstractUserSearchQuery<U> createSearchQuery();

	@Override
	protected ISearchQuery<U, UserSort> getSearchQuery() {
		return createSearchQuery()
			.name(nameModel.getObject())
			.group(groupModel.getObject())
			.includeInactive(includeInactivesModel.getObject())
			.sort(sortModel.getObject());
	}

	@Override
	public void detach() {
		super.detach();
		Detachables.detach(
			nameModel,
			groupModel,
			includeInactivesModel,
			sortModel
		);
	}

}
