package fr.portefeuille.core.business.notification.service;

import fr.portefeuille.core.business.user.model.User;

public class EmptyNotificationUrlBuilderServiceImpl implements IPortefeuilleNotificationUrlBuilderService {

	@Override
	public String getUserDescriptionUrl(User user) {
		return null;
	}

}
