package fr.portefeuille.web.application.portefeuille.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.iglooproject.functional.Suppliers2;
import org.iglooproject.wicket.more.bindable.model.BindableModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.compte.model.CompteBindableModel;

public class PortefeuilleBindableModel extends BindableModel<Portefeuille> {

	private static final long serialVersionUID = 415186690417706566L;

	public PortefeuilleBindableModel(IModel<Portefeuille> portefeuilleModel) {
		super(portefeuilleModel);
	
		bindWithCache(
			Bindings.portefeuille().nom(),
			new Model<>()
		);

		bindCollectionWithCache(
			Bindings.portefeuille().comptes(),
			Suppliers2.treeSetAsSortedSet(),
			compte -> new CompteBindableModel(GenericEntityModel.of(compte))
		);

	}

}
