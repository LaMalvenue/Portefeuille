package fr.portefeuille.core.business.operation.model.comparator;

import org.iglooproject.jpa.business.generic.util.AbstractGenericEntityComparator;

import com.google.common.collect.ComparisonChain;

import fr.portefeuille.core.business.operation.model.Operation;

public class OperationComparator extends AbstractGenericEntityComparator<Long, Operation> {

	private static final long serialVersionUID = 1L;

	private static final OperationComparator INSTANCE = new OperationComparator();

	public static final OperationComparator get() {
		return INSTANCE;
	}

	@Override
	protected int compareNotNullObjects(Operation left, Operation right) {
		int order = ComparisonChain.start()
			.compare(left.getDate(), right.getDate())
			.result();
		if (order == 0) {
			order = super.compareNotNullObjects(left, right);
		}
		return order;
	}

}
