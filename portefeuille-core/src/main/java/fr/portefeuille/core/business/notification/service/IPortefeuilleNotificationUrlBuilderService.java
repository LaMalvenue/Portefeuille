package fr.portefeuille.core.business.notification.service;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.spring.notification.service.INotificationUrlBuilderService;

public interface IPortefeuilleNotificationUrlBuilderService extends INotificationUrlBuilderService {

	String getUserDescriptionUrl(User user);

}
