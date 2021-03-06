package fr.portefeuille.core.business.announcement.dao;

import java.util.Date;
import java.util.List;

import fr.portefeuille.core.business.announcement.model.Announcement;
import fr.portefeuille.core.business.announcement.model.QAnnouncement;
import org.iglooproject.jpa.business.generic.dao.GenericEntityDaoImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class AnnouncementDaoImpl extends GenericEntityDaoImpl<Long, Announcement> implements IAnnouncementDao {

	private QAnnouncement qAnnouncement = QAnnouncement.announcement;

	@Override
	public List<Announcement> listActive() {
		Date now = new Date();
		return new JPAQuery<>(getEntityManager())
			.select(qAnnouncement)
			.from(qAnnouncement)
			.where(qAnnouncement.publication.startDateTime.loe(now))
			.where(qAnnouncement.publication.endDateTime.goe(now))
			.where(qAnnouncement.active.isTrue())
			.orderBy(qAnnouncement.publication.startDateTime.asc())
			.fetch();
	}

	@Override
	public Date getMostRecentPublicationStartDate() {
		Date now = new Date();
		return new JPAQuery<>(getEntityManager())
			.select(qAnnouncement.publication.startDateTime)
			.from(qAnnouncement)
			.where(qAnnouncement.publication.startDateTime.loe(now))
			.where(qAnnouncement.publication.endDateTime.goe(now))
			.where(qAnnouncement.active.isTrue())
			.orderBy(qAnnouncement.publication.startDateTime.desc())
			.fetchFirst();
	}

}
