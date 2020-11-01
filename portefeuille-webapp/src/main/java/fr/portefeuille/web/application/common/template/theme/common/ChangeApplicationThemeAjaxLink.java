package fr.portefeuille.web.application.common.template.theme.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.spring.injection.annot.SpringBean;
import fr.portefeuille.web.application.common.template.theme.PortefeuilleApplicationTheme;
import fr.portefeuille.web.application.navigation.page.HomePage;
import fr.portefeuille.web.application.property.PortefeuilleWebappPropertyIds;
import org.iglooproject.spring.property.service.IPropertyService;

public class ChangeApplicationThemeAjaxLink extends AjaxLink<Void> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IPropertyService propertyService;

	public ChangeApplicationThemeAjaxLink(String id) {
		super(id);
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		try {
			PortefeuilleApplicationTheme applicationTheme = propertyService.get(PortefeuilleWebappPropertyIds.APPLICATION_THEME);
			
			if (applicationTheme == null) {
				return;
			}
			
			propertyService.set(PortefeuilleWebappPropertyIds.APPLICATION_THEME, applicationTheme.next());
			
			throw HomePage.linkDescriptor().newRestartResponseException();
		} catch (Exception e) {
			throw new IllegalStateException("Error on updating application theme.", e);
		}
	}

}
