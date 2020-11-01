package fr.portefeuille.core.business.referencedata.dao;

import fr.portefeuille.core.business.common.model.PostalCode;
import fr.portefeuille.core.business.referencedata.model.City;
import fr.portefeuille.core.business.referencedata.model.QCity;
import org.iglooproject.jpa.business.generic.dao.GenericEntityDaoImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class CityDaoImpl extends GenericEntityDaoImpl<Long, City> implements ICityDao {

	@Override
	public City getByLabelAndPostalCode(String label, PostalCode postalCode) {
		return new JPAQuery<>(getEntityManager())
			.select(QCity.city)
			.from(QCity.city)
			.where(QCity.city.label.fr.eq(label))
			.where(QCity.city.postalCode.eq(postalCode))
			.fetchOne();
	}

}
