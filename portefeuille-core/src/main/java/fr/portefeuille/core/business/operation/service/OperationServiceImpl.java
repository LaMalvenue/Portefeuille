package fr.portefeuille.core.business.operation.service;

import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;

import fr.portefeuille.core.business.operation.dao.IOperationDao;
import fr.portefeuille.core.business.operation.model.Operation;

public class OperationServiceImpl extends GenericEntityServiceImpl<Long, Operation> implements IOperationService {

	public OperationServiceImpl(IOperationDao dao) {
		super(dao);
	}

}
