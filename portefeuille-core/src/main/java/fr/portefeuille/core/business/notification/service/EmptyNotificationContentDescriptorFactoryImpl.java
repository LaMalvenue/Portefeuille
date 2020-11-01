package fr.portefeuille.core.business.notification.service;

import java.util.Date;

import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.spring.notification.model.INotificationContentDescriptor;
import org.iglooproject.spring.notification.util.NotificationContentDescriptors;

public class EmptyNotificationContentDescriptorFactoryImpl implements IPortefeuilleNotificationContentDescriptorFactory {
	
	private static final INotificationContentDescriptor DEFAULT_DESCRIPTOR =
		NotificationContentDescriptors.explicit("defaultSubject", "defaultTextBody", "defaultHtmlBody");

	@Override
	public INotificationContentDescriptor example(User user, Date date) {
		return DEFAULT_DESCRIPTOR;
	}

	@Override
	public INotificationContentDescriptor userPasswordRecoveryRequest(User user) {
		return DEFAULT_DESCRIPTOR;
	}

}
