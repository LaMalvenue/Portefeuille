package fr.portefeuille.web.application.navigation.component;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.business.portefeuille.service.ICompteService;

public class OperationAddPanel extends GenericPanel<Portefeuille> {

	private static final long serialVersionUID = -2142250114710217646L;

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationAddPanel.class);

	@SpringBean
	private IPropertyService propertyService;

	@SpringBean
	private ICompteService compteService;

	public OperationAddPanel(String id, final IModel<Portefeuille> portefeuilleModel) {
		super(id, portefeuilleModel);
		setOutputMarkupId(true);
		
	}
	
}
