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
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.portefeuille.model.Compte;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.portefeuille.template.CompteTemplate;

public class CompteDetailPage extends CompteTemplate {

	private static final long serialVersionUID = -8075049022262384889L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CompteDetailPage.class);

	public static final IOneParameterLinkDescriptorMapper<IPageLinkDescriptor, Compte> MAPPER =
			LinkDescriptorBuilder.start()
				.model(Compte.class)
				.map(CommonParameters.ID).mandatory()
				.page(CompteDetailPage.class);

	public CompteDetailPage(PageParameters parameters) {
		super(parameters);
		
		IModel<Compte> compteModel = new GenericEntityModel<>();
		
		MAPPER
		.map(compteModel)
		.extractSafely(
			parameters,
			CompteListPage.linkDescriptor(),
			getString("common.error.unexpected")
		);
		
		EnclosureContainer headerElementsSection = new EnclosureContainer("headerElementsSection");
		add(headerElementsSection.anyChildVisible());
		
		headerElementsSection
		.add(
			new EnclosureContainer("actionsContainer")
				.anyChildVisible()
				.add(
					AjaxConfirmLink.<Compte>build()
						.title(new ResourceModel("compte.action.delete.confirmation.title"))
						.content(new StringResourceModel("compte.action.delete.confirmation.content", compteModel))
						.confirm()
						.onClick(new IAjaxAction() {
							private static final long serialVersionUID = 1L;
							@Override
							public void execute(AjaxRequestTarget target) {
								try {
									compteService.delete(compteModel.getObject());
									Session.get().success(getString("compte.action.delete.success"));
									throw CompteListPage.linkDescriptor().newRestartResponseException();
								}catch (RestartResponseException e) {
									throw e;
								} catch (Exception e) {
									LOGGER.error("Erreur lors de la suppression du compte", e);
									Session.get().error(getString("common.error.unexpected"));
								}
								FeedbackUtils.refreshFeedback(target, getPage());
							}
						})
						.create("delete", compteModel)
				)
			);
	
	add(
			CompteListPage.linkDescriptor().link("backToList"),
		headerElementsSection,
		new CoreLabel("title", new StringResourceModel("compte.detail.title.param", compteModel)),
		new CoreLabel("label", BindingModel.of(compteModel, Bindings.compte().label()))
			.showPlaceholder(),
		new CoreLabel("fondsDisponibles", BindingModel.of(compteModel, Bindings.compte().solde()))
			.showPlaceholder()
	);
	
	}
}
