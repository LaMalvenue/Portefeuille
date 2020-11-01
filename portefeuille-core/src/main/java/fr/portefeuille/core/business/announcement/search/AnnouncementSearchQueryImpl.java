package fr.portefeuille.core.business.announcement.search;

import fr.portefeuille.core.business.announcement.model.Announcement;
import fr.portefeuille.core.business.announcement.model.QAnnouncement;
import org.iglooproject.jpa.more.business.search.query.AbstractJpaSearchQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AnnouncementSearchQueryImpl
		extends AbstractJpaSearchQuery<Announcement, AnnouncementSort>
		implements IAnnouncementSearchQuery {

	protected AnnouncementSearchQueryImpl() {
		super(QAnnouncement.announcement);
	}

}
