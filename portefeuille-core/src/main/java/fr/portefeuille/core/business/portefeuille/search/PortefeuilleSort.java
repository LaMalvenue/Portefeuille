package fr.portefeuille.core.business.portefeuille.search;

import java.util.List;

import org.iglooproject.jpa.more.business.sort.ISort;
import org.iglooproject.jpa.more.business.sort.SortUtils;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.types.OrderSpecifier;

import fr.portefeuille.core.business.portefeuille.model.QPortefeuille;

public enum PortefeuilleSort implements ISort<OrderSpecifier<?>> {

	ID {
		@Override
		public List<OrderSpecifier<?>> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.orderSpecifier(this, sortOrder, QPortefeuille.portefeuille.id)
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.ASC;
		}
	},

	PROPRIETAIRE {
		@Override
		public List<OrderSpecifier<?>> getSortFields(SortOrder sortOrder) {
			return ImmutableList.of(
				SortUtils.orderSpecifier(this, sortOrder, QPortefeuille.portefeuille.proprietaire.id)
			);
		}
		@Override
		public SortOrder getDefaultOrder() {
			return SortOrder.ASC;
		}
	};

}
