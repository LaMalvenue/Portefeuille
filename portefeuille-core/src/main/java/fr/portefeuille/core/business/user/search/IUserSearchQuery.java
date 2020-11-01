package fr.portefeuille.core.business.user.search;

import fr.portefeuille.core.business.user.model.User;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public interface IUserSearchQuery<U extends User> extends IAbstractUserSearchQuery<U> {

}
