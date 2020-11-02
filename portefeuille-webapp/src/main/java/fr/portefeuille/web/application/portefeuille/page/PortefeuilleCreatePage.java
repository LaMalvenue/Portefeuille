package fr.portefeuille.web.application.portefeuille.page;

import java.util.SortedSet;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.more.bindable.component.BindableCollectionView;
import org.iglooproject.wicket.more.bindable.model.IBindableCollectionModel;
import org.iglooproject.wicket.more.bindable.model.IBindableModel;
import org.iglooproject.wicket.more.common.behavior.UpdateOnChangeAjaxEventBehavior;
import org.iglooproject.wicket.more.condition.Condition;
import org.iglooproject.wicket.more.link.descriptor.IPageLinkDescriptor;
import org.iglooproject.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import org.iglooproject.wicket.more.markup.html.basic.PlaceholderContainer;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.form.EnumDropDownSingleChoice;
import org.iglooproject.wicket.more.markup.repeater.collection.SpecificModelCollectionView;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.model.atomic.TypeCompte;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.portefeuille.model.PortefeuilleBindableModel;
import fr.portefeuille.web.application.portefeuille.template.PortefeuilleTemplate;

public class PortefeuilleCreatePage extends PortefeuilleTemplate {

	private static final long serialVersionUID = -3226804635737125342L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuilleCreatePage.class);

	@SpringBean
	private IPortefeuilleService siteService;

	@SpringBean
	private IPropertyService propertyService;

	private final PortefeuilleBindableModel portefeuilleBindableModel;

	public static final IPageLinkDescriptor linkDescriptor() {
		return LinkDescriptorBuilder.start()
			.page(PortefeuilleCreatePage.class);
	}

	public PortefeuilleCreatePage(PageParameters parameters) {
		super(parameters);
		
		this.portefeuilleBindableModel = new PortefeuilleBindableModel(new GenericEntityModel<>(new Portefeuille()));
		
		Form<Portefeuille> form = new Form<>("form");
		
		IModel<String> nomModel = portefeuilleBindableModel.bind(Bindings.portefeuille().nom());
		
		TextField<String> nom = new TextField<>("nom", nomModel, String.class);
		
		IBindableCollectionModel<Compte, SortedSet<Compte>> comptesBindableModel = portefeuilleBindableModel.bindCollectionAlreadyAdded(Bindings.portefeuille().comptes());
		
		form.add(
			nom
				.setLabel(new ResourceModel("business.portefeuille.nom"))
				.setRequired(true)
				.add(new UpdateOnChangeAjaxEventBehavior()),
			new BindableCollectionView<Compte>("comptes", comptesBindableModel) {
				private static final long serialVersionUID = 1L;
				@Override
				protected void populateItem(SpecificModelCollectionView<Compte, IBindableModel<Compte>>.SpecificModelItem item) {
					item.add(
						new TextField<>("label", item.getSpecificModel().bind(Bindings.compte().label()))
							.setLabel(new ResourceModel("business.compte.label"))
							.add(new UpdateOnChangeAjaxEventBehavior()),
						new EnumDropDownSingleChoice<TypeCompte>("typeCompte", item.getSpecificModel().bind(Bindings.compte().type()), TypeCompte.class)
							.setLabel(new ResourceModel("business.compte.type"))
							.add(new UpdateOnChangeAjaxEventBehavior()),
						new AjaxLink<Void>("delete") {
								private static final long serialVersionUID = 1L;
								@Override
								public void onClick(AjaxRequestTarget target) {
									portefeuilleBindableModel.writeAll();
									comptesBindableModel.remove(item.getModelObject());
									target.add(form);
								}
							}
					);
				}
			}
				.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance())
				.add(Condition.collectionModelNotEmpty(comptesBindableModel).thenShow()),
			new PlaceholderContainer("emptyPlaceholder")
				.condition(Condition.collectionModelNotEmpty(comptesBindableModel)),
			
			new AjaxLink<Void>("add") {
				private static final long serialVersionUID = 1L;
				@Override
				public void onClick(AjaxRequestTarget target) {
					portefeuilleBindableModel.writeAll();
					comptesBindableModel.add(new Compte());
					target.add(form);
				}
			},
			
			new AjaxButton("save") {
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void onSubmit(AjaxRequestTarget target) {
					try {
						portefeuilleBindableModel.writeAll();
						siteService.savePortefeuille(portefeuilleBindableModel.getObject());
						
						Session.get().success(getString("common.success"));
						throw PortefeuilleDetailPage.MAPPER.map(portefeuilleBindableModel).newRestartResponseException();
					} catch (RestartResponseException e) { // NOSONAR
						throw e;
					} catch (Exception e) {
						LOGGER.error("Erreur lors de la création du portefeuille.", e);
						Session.get().error(getString("common.error.unexpected"));
					}
					FeedbackUtils.refreshFeedback(target, getPage());
				}
				
				@Override
				protected void onError(AjaxRequestTarget target) {
					FeedbackUtils.refreshFeedback(target, getPage());
				}
			}
			
		);
		
		add(form);
		
	}
	

	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(portefeuilleBindableModel);
	}

}
