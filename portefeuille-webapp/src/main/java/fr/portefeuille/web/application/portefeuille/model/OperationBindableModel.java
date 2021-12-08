package fr.portefeuille.web.application.portefeuille.model;

import org.apache.wicket.model.IModel;
import org.iglooproject.wicket.more.bindable.model.BindableModel;

import fr.portefeuille.core.business.portefeuille.model.Operation;

public class OperationBindableModel extends BindableModel<Operation> {

	private static final long serialVersionUID = -8456443924472058795L;

	public OperationBindableModel(IModel<Operation> mainModel) {
		super(mainModel);
		// TODO Auto-generated constructor stub
	}

}
