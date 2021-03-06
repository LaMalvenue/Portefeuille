package fr.portefeuille.core.business.operation.search;

import java.util.List;

import org.apache.lucene.search.SortField;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.more.business.sort.ISort;
import org.iglooproject.jpa.more.business.sort.SortUtils;

import com.google.common.collect.ImmutableList;

import fr.portefeuille.core.business.operation.model.Operation;

public enum OperationSort implements ISort<SortField> {

	ID {
		@Override
		public List<SortField> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.luceneSortField(
					this, sortOrder, SortField.Type.LONG,
					GenericEntity.ID_SORT
				)			
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.ASC;
		}
	},

	LABEL {
		@Override
		public List<SortField> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.luceneSortField(
					this, sortOrder, SortField.Type.STRING,
					Operation.LABEL_SORT
				)	
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.ASC;
		}
	};

}
