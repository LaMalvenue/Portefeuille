package fr.portefeuille.core.business.portefeuille.model.comparator;

import org.iglooproject.jpa.business.generic.util.AbstractGenericEntityComparator;

import com.google.common.collect.ComparisonChain;

import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

public class PortefeuilleComparator extends AbstractGenericEntityComparator<Long, Portefeuille> {

	private static final long serialVersionUID = 1L;

	private static final PortefeuilleComparator INSTANCE = new PortefeuilleComparator();

	public static final PortefeuilleComparator get() {
		return INSTANCE;
	}

	@Override
	protected int compareNotNullObjects(Portefeuille left, Portefeuille right) {
		int order = ComparisonChain.start()
			.compare(left.getNom(), right.getNom())
			.result();
		if (order == 0) {
			order = super.compareNotNullObjects(left, right);
		}
		return order;
	}

}
