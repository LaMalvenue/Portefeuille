package fr.portefeuille.core.business.referencedata.service;

import fr.portefeuille.core.business.common.model.PostalCode;
import fr.portefeuille.core.business.referencedata.model.City;
import org.iglooproject.jpa.business.generic.service.IGenericEntityService;

public interface ICityService extends IGenericEntityService<Long, City> {

	City getByLabelAndPostalCode(String label, PostalCode postalCode);

}
