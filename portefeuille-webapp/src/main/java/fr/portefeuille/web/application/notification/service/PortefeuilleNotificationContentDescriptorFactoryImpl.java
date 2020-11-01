package fr.portefeuille.web.application.notification.service;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import fr.portefeuille.core.business.notification.service.IPortefeuilleNotificationContentDescriptorFactory;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.util.ResourceKeyGenerator;
import fr.portefeuille.core.util.binding.Bindings;
import fr.portefeuille.web.application.notification.component.ExampleHtmlNotificationPanel;
import fr.portefeuille.web.application.notification.component.UserPasswordRecoveryRequestHtmlNotificationPanel;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordCreationPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordResetPage;
import org.iglooproject.spring.notification.model.INotificationContentDescriptor;
import org.iglooproject.wicket.more.link.descriptor.generator.ILinkGenerator;
import org.iglooproject.wicket.more.link.descriptor.mapper.ITwoParameterLinkDescriptorMapper;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.notification.service.AbstractNotificationContentDescriptorFactory;
import org.iglooproject.wicket.more.notification.service.IWicketContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service("PortefeuilleNotificationPanelRendererService")
public class PortefeuilleNotificationContentDescriptorFactoryImpl
		extends AbstractNotificationContentDescriptorFactory
		implements IPortefeuilleNotificationContentDescriptorFactory {
	
	@Autowired
	public PortefeuilleNotificationContentDescriptorFactoryImpl(IWicketContextProvider contextProvider) {
		super(contextProvider);
	}

	@Override
	public INotificationContentDescriptor example(final User user, final Date date) {
		return new AbstractSimpleWicketNotificationDescriptor("notification.panel.example") {
			@Override
			public Object getSubjectParameter() {
				return user;
			}
			@Override
			public Iterable<?> getSubjectPositionalParameters() {
				return ImmutableList.of(user.getFullName(), date);
			}
			@Override
			public Component createComponent(String wicketId) {
				return new ExampleHtmlNotificationPanel(wicketId, GenericEntityModel.of(user), Model.of(date));
			}
		};
	}

	@Override
	public INotificationContentDescriptor userPasswordRecoveryRequest(final User user) {
		final ITwoParameterLinkDescriptorMapper<? extends ILinkGenerator, User, String> mapper;
		switch (user.getPasswordRecoveryRequest().getType()) {
		case CREATION:
			mapper = SecurityPasswordCreationPage.MAPPER;
			break;
		case RESET:
			mapper = SecurityPasswordResetPage.MAPPER;
			break;
		default:
			throw new IllegalStateException("Recovery request type unknown.");
		}
		
		final ResourceKeyGenerator keyGenerator =
			ResourceKeyGenerator.of("notification.panel.user.password.recovery.request")
				.append(user.getPasswordRecoveryRequest().getType().name())
				.append(user.getPasswordRecoveryRequest().getInitiator().name());
		
		return new AbstractSimpleWicketNotificationDescriptor(keyGenerator.resourceKey()) {
			@Override
			public IModel<?> getSubjectParameter() {
				return GenericEntityModel.of(user);
			}
			@Override
			public Component createComponent(String wicketId) {
				IModel<User> userModel = GenericEntityModel.of(user);
				return new UserPasswordRecoveryRequestHtmlNotificationPanel<>(
					wicketId,
					keyGenerator,
					userModel, userModel, Model.of(new Date()),
					mapper.map(userModel, BindingModel.of(userModel, Bindings.user().passwordRecoveryRequest().token()))
				);
			}
		};
	}

}
