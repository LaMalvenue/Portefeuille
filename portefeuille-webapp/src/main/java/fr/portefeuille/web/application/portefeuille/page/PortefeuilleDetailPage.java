package fr.portefeuille.web.application.portefeuille.page;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.link.descriptor.mapper.IOneParameterLinkDescriptorMapper;
import org.iglooproject.wicket.more.link.descriptor.parameter.CommonParameters;
import org.iglooproject.wicket.more.markup.html.action.IAjaxAction;
import org.iglooproject.wicket.more.markup.html.basic.EnclosureContainer;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.template.js.bootstrap.confirm.component.AjaxConfirmLink;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.web.application.portefeuille.template.PortefeuilleTemplate;

public class PortefeuilleDetailPage extends PortefeuilleTemplate {

	private static final long serialVersionUID = -4765386531030260936L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuilleDetailPage.class);

	public static final IOneParameterLinkDescriptorMapper<IPageLinkDescriptor, Portefeuille> MAPPER =
		LinkDescriptorBuilder.start()
			.model(Portefeuille.class)
			.map(CommonParameters.ID).mandatory()
			.page(PortefeuilleDetailPage.class);

	public PortefeuilleDetailPage(PageParameters parameters) {
		super(parameters);
		
		IModel<Portefeuille> portefeuilleModel = new GenericEntityModel<>();
		
		MAPPER
		.map(portefeuilleModel)
		.extractSafely(
			parameters,
			PortefeuilleListPage.linkDescriptor(),
			getString("common.error.unexpected")
		);
		
		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		headerElementsSection
			.add(
				new EnclosureContainer("actionsContainer")
					.anyChildVisible()
					.add(
						AjaxConfirmLink.<Portefeuille>build()
							.title(new ResourceModel("site.action.delete.confirmation.title"))
							.content(new ResourceModel("site.action.delete.confirmation.content"))
							.confirm()
							.onClick(new IAjaxAction() {
								private static final long serialVersionUID = 1L;
								@Override
								public void execute(AjaxRequestTarget target) {
									try {
										portefeuilleService.delete(portefeuilleModel.getObject());
										Session.get().success(getString("site.action.delete.success"));
										throw PortefeuilleListPage.linkDescriptor().newRestartResponseException();
									}catch (RestartResponseException e) {
										throw e;
									} catch (Exception e) {
										LOGGER.error("Error occured while enabling user", e);
										Session.get().error(getString("common.error.unexpected"));
									}
									FeedbackUtils.refreshFeedback(target, getPage());
								}
							})
							.create("delete", portefeuilleModel)
					)
				);
		
		add(
			PortefeuilleListPage.linkDescriptor().link("backToList"),
			headerElementsSection,
			new CoreLabel("title", new StringResourceModel("portefeuille.detail.title.param", portefeuilleModel))
		);
		
	}

}
