package fr.portefeuille.core.business.referencedata.service;

import fr.portefeuille.core.business.common.model.PostalCode;
import fr.portefeuille.core.business.referencedata.dao.ICityDao;
import fr.portefeuille.core.business.referencedata.model.City;
import org.iglooproject.jpa.business.generic.service.GenericEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends GenericEntityServiceImpl<Long, City> implements ICityService {

	private ICityDao dao;

	@Autowired
	public CityServiceImpl(ICityDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public City getByLabelAndPostalCode(String label, PostalCode postalCode) {
		return dao.getByLabelAndPostalCode(label, postalCode);
	}

}
