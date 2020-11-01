package fr.portefeuille.core.config.spring;

import org.apache.lucene.search.SortField;
import fr.portefeuille.core.business.PortefeuilleCoreCommonBusinessPackage;
import fr.portefeuille.core.business.referencedata.model.ReferenceData;
import fr.portefeuille.core.business.referencedata.search.BasicReferenceDataSearchQueryImpl;
import fr.portefeuille.core.business.referencedata.search.IBasicReferenceDataSearchQuery;
import fr.portefeuille.core.config.hibernate.HibernateConfigPackage;
import org.iglooproject.jpa.config.spring.provider.JpaPackageScanProvider;
import org.iglooproject.jpa.more.business.sort.ISort;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableAspectJAutoProxy
public class PortefeuilleCoreCommonJpaConfig {

	/**
	 * Déclaration des packages de scan pour l'application.
	 */
	@Bean
	public JpaPackageScanProvider applicationJpaPackageScanProvider() {
		return new JpaPackageScanProvider(
			PortefeuilleCoreCommonBusinessPackage.class.getPackage(),
			HibernateConfigPackage.class.getPackage() // Typedef config
		);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public <T extends ReferenceData<? super T>, S extends ISort<SortField>> IBasicReferenceDataSearchQuery<T, S> basicReferenceDataSearchQuery(Class<T> clazz) {
		return new BasicReferenceDataSearchQueryImpl<>(clazz);
	}

}
