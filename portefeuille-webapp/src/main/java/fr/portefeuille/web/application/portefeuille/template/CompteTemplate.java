package fr.portefeuille.web.application.portefeuille.template;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.spring.property.service.IPropertyService;

import fr.portefeuille.core.business.portefeuille.service.ICompteService;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.portefeuille.page.CompteListPage;

public class CompteTemplate extends MainTemplate {

	private static final long serialVersionUID = 8956488967771324908L;
	
	@SpringBean
	protected ICompteService compteService;

	@SpringBean
	protected IPropertyService propertyService;

	public CompteTemplate(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected Class<? extends WebPage> getFirstMenuPage() {
		return CompteListPage.class;
	}

}
