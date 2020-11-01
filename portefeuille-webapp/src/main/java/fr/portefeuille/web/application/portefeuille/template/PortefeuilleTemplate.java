package fr.portefeuille.web.application.portefeuille.template;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.portefeuille.core.business.portefeuille.service.IPortefeuilleService;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.portefeuille.page.PortefeuilleListPage;

public class PortefeuilleTemplate extends MainTemplate {

	private static final long serialVersionUID = 8056026070677470734L;

	@SpringBean
	protected IPortefeuilleService portefeuilleService;

	public PortefeuilleTemplate(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected Class<? extends WebPage> getFirstMenuPage() {
		return PortefeuilleListPage.class;
	}

}
