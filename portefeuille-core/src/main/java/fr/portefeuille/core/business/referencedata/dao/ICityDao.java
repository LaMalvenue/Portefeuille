package fr.portefeuille.core.business.referencedata.dao;

import fr.portefeuille.core.business.common.model.PostalCode;
import fr.portefeuille.core.business.referencedata.model.City;
import org.iglooproject.jpa.business.generic.dao.IGenericEntityDao;

public interface ICityDao extends IGenericEntityDao<Long, City>{

	City getByLabelAndPostalCode(String label, PostalCode postalCode);

}
