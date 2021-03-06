package fr.portefeuille.core.business.user.search;

import java.util.List;

import org.apache.lucene.search.SortField;
import fr.portefeuille.core.business.user.model.UserGroup;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.more.business.sort.ISort;
import org.iglooproject.jpa.more.business.sort.SortUtils;

import com.google.common.collect.ImmutableList;

// TODO: switch to final List<SortField> attribute ?
public enum UserGroupSort implements ISort<SortField> {
	@SuppressWarnings("common-java:DuplicatedBlocks")
	NAME {
		@Override
		public List<SortField> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.luceneSortField(this, sortOrder, SortField.Type.STRING, UserGroup.NAME_SORT)
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.ASC;
		}
	},
	@SuppressWarnings("common-java:DuplicatedBlocks")
	ID {
		@Override
		public List<SortField> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.luceneSortField(this, sortOrder, SortField.Type.LONG, GenericEntity.ID_SORT)
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.DESC;
		}
	};

	@Override
	public abstract List<SortField> getSortFields(SortOrder sortOrder);

	@Override
	public abstract SortOrder getDefaultOrder();

}
