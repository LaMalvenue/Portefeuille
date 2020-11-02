package fr.portefeuille.web.application.compte.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.iglooproject.functional.Suppliers2;
import org.iglooproject.wicket.more.bindable.model.BindableModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.operation.model.OperationBindableModel;

public class CompteBindableModel extends BindableModel<Compte> {

	private static final long serialVersionUID = 2353202013552428741L;

	public CompteBindableModel(IModel<Compte> compteModel) {
		super(compteModel);

		bindWithCache(
			Bindings.compte().label(),
			new Model<>()
		);

		bindWithCache(
			Bindings.compte().type(),
			new Model<>()
		);

		bindCollectionWithCache(
			Bindings.compte().operations(),
			Suppliers2.treeSetAsSortedSet(),
			operation -> new OperationBindableModel(GenericEntityModel.of(operation))
		);

	}

}
