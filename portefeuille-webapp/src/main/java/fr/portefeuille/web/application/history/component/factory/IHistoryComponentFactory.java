package fr.portefeuille.web.application.history.component.factory;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.portefeuille.core.business.history.model.HistoryDifference;
import org.iglooproject.wicket.more.markup.html.factory.IOneParameterComponentFactory;

public interface IHistoryComponentFactory
	extends IOneParameterComponentFactory<Component, IModel<HistoryDifference>> {

}
