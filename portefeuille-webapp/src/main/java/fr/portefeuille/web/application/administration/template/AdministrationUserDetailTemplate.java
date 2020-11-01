package fr.portefeuille.web.application.administration.template;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import fr.portefeuille.core.business.user.model.BasicUser;
import fr.portefeuille.core.business.user.model.TechnicalUser;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.service.IUserService;
import fr.portefeuille.core.security.service.ISecurityManagementService;
import fr.portefeuille.web.application.administration.page.AdministrationBasicUserDetailPage;
import fr.portefeuille.web.application.administration.page.AdministrationTechnicalUserDetailPage;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.link.descriptor.mapper.ITwoParameterLinkDescriptorMapper;
import org.iglooproject.wicket.more.link.descriptor.parameter.CommonParameters;
import org.iglooproject.wicket.more.link.model.PageModel;
import org.iglooproject.wicket.more.markup.html.factory.DetachableFactories;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.model.ReadOnlyModel;
import org.iglooproject.wicket.more.util.model.Detachables;

public abstract class AdministrationUserDetailTemplate<U extends User> extends AdministrationUserTemplate {

	private static final long serialVersionUID = -550100874222819991L;

	public static final <U extends User> ITwoParameterLinkDescriptorMapper<IPageLinkDescriptor, U, Page> mapper() {
		return mapper(User.class).<U>castParameter1();
	}

	protected static final <U extends User> ITwoParameterLinkDescriptorMapper<IPageLinkDescriptor, U, Page> mapper(Class<U> clazz) {
		return LinkDescriptorBuilder.start()
			.model(clazz)
			.model(Page.class)
			.pickFirst().map(CommonParameters.ID).mandatory()
			.pickSecond().map(CommonParameters.SOURCE_PAGE_ID).optional()
			.pickFirst().page(DetachableFactories.forUnit(
				ReadOnlyModel.factory(
					u -> {
						if (u instanceof BasicUser) {
							return AdministrationBasicUserDetailPage.class;
						} else if (u instanceof TechnicalUser) {
							return AdministrationTechnicalUserDetailPage.class;
						}
						return null;
					}
				)
			));
	}

	@SpringBean
	protected IUserService userService;

	@SpringBean
	protected ISecurityManagementService securityManagementService;

	protected final IModel<U> userModel = new GenericEntityModel<>();

	protected final IModel<Page> sourcePageModel = new PageModel<>();

	public AdministrationUserDetailTemplate(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(
			userModel,
			sourcePageModel
		);
	}

}
