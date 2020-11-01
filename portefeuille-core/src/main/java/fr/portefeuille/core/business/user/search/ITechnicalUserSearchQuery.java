package fr.portefeuille.core.business.user.search;

import fr.portefeuille.core.business.user.model.TechnicalUser;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public interface ITechnicalUserSearchQuery extends IAbstractUserSearchQuery<TechnicalUser> {

}
