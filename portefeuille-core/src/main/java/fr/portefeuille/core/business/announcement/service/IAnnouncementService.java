package fr.portefeuille.core.business.announcement.service;

import java.util.Date;
import java.util.List;

import fr.portefeuille.core.business.announcement.model.Announcement;
import org.iglooproject.jpa.business.generic.service.IGenericEntityService;
import org.iglooproject.jpa.exception.SecurityServiceException;
import org.iglooproject.jpa.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;

public interface IAnnouncementService extends IGenericEntityService<Long, Announcement> {

	void saveAnnouncement(Announcement announcement) throws ServiceException, SecurityServiceException;

	@Transactional(readOnly = true)
	void cleanWithoutSaving(Announcement announcement);

	List<Announcement> listActive();

	Date getMostRecentPublicationStartDate();

}
