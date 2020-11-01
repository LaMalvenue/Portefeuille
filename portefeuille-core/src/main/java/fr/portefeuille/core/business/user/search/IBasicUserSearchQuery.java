package fr.portefeuille.core.business.user.search;

import fr.portefeuille.core.business.user.model.BasicUser;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public interface IBasicUserSearchQuery extends IAbstractUserSearchQuery<BasicUser> {

}
