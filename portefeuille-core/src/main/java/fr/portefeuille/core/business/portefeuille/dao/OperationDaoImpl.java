package fr.portefeuille.core.business.portefeuille.dao;

import org.iglooproject.jpa.business.generic.dao.GenericEntityDaoImpl;
import org.springframework.stereotype.Repository;

import fr.portefeuille.core.business.portefeuille.model.Operation;

@Repository
public class OperationDaoImpl extends GenericEntityDaoImpl<Long, Operation> implements IOperationDao {

}
