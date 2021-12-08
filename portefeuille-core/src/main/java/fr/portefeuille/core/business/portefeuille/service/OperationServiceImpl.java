package fr.portefeuille.core.business.portefeuille.service;

import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;
import org.springframework.stereotype.Service;

import fr.portefeuille.core.business.portefeuille.dao.IOperationDao;
import fr.portefeuille.core.business.portefeuille.model.Operation;

@Service
public class OperationServiceImpl extends GenericEntityServiceImpl<Long, Operation> implements IOperationService {

	public OperationServiceImpl(IOperationDao dao) {
		super(dao);
	}

}
