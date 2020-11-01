package fr.portefeuille.core.config.spring;

import java.util.Collection;

import org.apache.commons.lang3.EnumUtils;
import fr.portefeuille.core.business.task.model.PortefeuilleTaskQueueId;
import org.iglooproject.jpa.more.business.task.model.IQueueId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortefeuilleCoreTaskManagementConfig {

	@Bean
	public Collection<? extends IQueueId> queueIds() {
		return EnumUtils.getEnumList(PortefeuilleTaskQueueId.class);
	}

}
