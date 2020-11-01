package fr.portefeuille.core.business.notification.service;

import java.util.Date;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.spring.notification.model.INotificationContentDescriptor;

public interface IPortefeuilleNotificationContentDescriptorFactory {

	INotificationContentDescriptor example(User user, Date date);

	INotificationContentDescriptor userPasswordRecoveryRequest(User user);

}
