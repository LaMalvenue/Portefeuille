package fr.portefeuille.web.application.portefeuille.template;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.portefeuille.core.business.portefeuille.service.IOperationService;
import fr.portefeuille.web.application.common.template.MainTemplate;
import fr.portefeuille.web.application.portefeuille.page.OperationListePage;

public class OperationTemplate extends MainTemplate {

	@SpringBean
	protected IOperationService operationService;

	private static final long serialVersionUID = 8299430448300039024L;

	public OperationTemplate(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected Class<? extends WebPage> getFirstMenuPage() {
		return OperationListePage.class;
	}

}
