package fr.portefeuille.core.business.compte.model.comparator;

import org.iglooproject.jpa.business.generic.util.AbstractGenericEntityComparator;
import fr.portefeuille.core.business.compte.model.Compte;

import com.google.common.collect.ComparisonChain;

public class CompteComparator extends AbstractGenericEntityComparator<Long, Compte> {

	private static final long serialVersionUID = 1L;

	private static final CompteComparator INSTANCE = new CompteComparator();

	public static final CompteComparator get() {
		return INSTANCE;
	}

	@Override
	protected int compareNotNullObjects(Compte left, Compte right) {
		int order = ComparisonChain.start()
			.compare(left.getLabel(), right.getLabel())
			.result();
		if (order == 0) {
			order = super.compareNotNullObjects(left, right);
		}
		return order;
	}

}
