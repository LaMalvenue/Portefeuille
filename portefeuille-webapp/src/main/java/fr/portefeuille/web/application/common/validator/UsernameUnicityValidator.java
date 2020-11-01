package fr.portefeuille.web.application.common.validator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.service.IUserService;
import org.iglooproject.wicket.more.util.validate.validators.AbstractUnicityValidator;

public class UsernameUnicityValidator extends AbstractUnicityValidator<User, String> {

	private static final long serialVersionUID = 7351972908406394930L;
	
	@SpringBean
	private IUserService userService;
	
	public UsernameUnicityValidator(IModel<? extends User> mainObjectModel) {
		super(mainObjectModel, "common.validator.username.unicity");
		Injector.get().inject(this);
	}

	@Override
	protected User getByUniqueField(String value) {
		return userService.getByUsername(value);
	}

}
