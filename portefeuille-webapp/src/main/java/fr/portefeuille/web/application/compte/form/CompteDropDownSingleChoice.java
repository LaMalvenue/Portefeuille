package fr.portefeuille.web.application.compte.form;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.iglooproject.wicket.more.application.CoreWicketApplication;
import org.iglooproject.wicket.more.markup.html.select2.GenericSelect2DropDownSingleChoice;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.search.ICompteSearchQuery;
import fr.portefeuille.web.application.compte.form.impl.CompteChoiceRenderer;

public class CompteDropDownSingleChoice extends GenericSelect2DropDownSingleChoice<Compte> {

	private static final long serialVersionUID = 6519677177216594364L;

	public CompteDropDownSingleChoice(String id, IModel<Compte> compteModel) {
		super(id, compteModel, new ChoicesModel(), CompteChoiceRenderer.get());
	}
	
	private static class ChoicesModel extends LoadableDetachableModel<List<Compte>> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		protected List<Compte> load() {
			return CoreWicketApplication.get().getApplicationContext().getBean(ICompteSearchQuery.class)
				.fullList();
		}
	}
}
