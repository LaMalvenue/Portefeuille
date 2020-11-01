package fr.portefeuille.core.business.announcement.search;

import java.util.List;

import fr.portefeuille.core.business.announcement.model.QAnnouncement;
import org.iglooproject.jpa.more.business.sort.ISort;
import org.iglooproject.jpa.more.business.sort.SortUtils;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.types.OrderSpecifier;

public enum AnnouncementSort implements ISort<OrderSpecifier<?>> {

	ID {
		@Override
		public List<OrderSpecifier<?>> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.orderSpecifier(this, sortOrder, QAnnouncement.announcement.id)
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.DESC;
		}
	},
	PUBLICATION_START_DATE_TIME {
		@Override
		public List<OrderSpecifier<?>> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.orderSpecifier(this, sortOrder, QAnnouncement.announcement.publication.startDateTime)
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.ASC;
		}
	};

	@Override
	public abstract List<OrderSpecifier<?>> getSortFields(SortOrder sortOrder);

	@Override
	public abstract SortOrder getDefaultOrder();

}
