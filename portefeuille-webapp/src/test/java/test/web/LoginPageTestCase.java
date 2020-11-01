package test.web;

import org.apache.wicket.util.tester.FormTester;
import fr.portefeuille.web.application.navigation.page.HomePage;
import fr.portefeuille.web.application.security.login.component.SignInContentPanel;
import fr.portefeuille.web.application.security.login.component.SignInFooterPanel;
import fr.portefeuille.web.application.security.login.page.SignInPage;
import fr.portefeuille.web.application.security.password.page.SecurityPasswordRecoveryPage;
import org.iglooproject.jpa.exception.SecurityServiceException;
import org.iglooproject.jpa.exception.ServiceException;
import org.junit.Test;

public class LoginPageTestCase extends AbstractPortefeuilleWebappTestCase {

	@Test
	public void initPage() {
		tester.startPage(SignInPage.class);
		tester.assertRenderedPage(SignInPage.class);
		
		tester.assertVisible("content", SignInContentPanel.class);
		tester.assertVisible("footer", SignInFooterPanel.class);
	}

	@Test
	public void redirectionToPasswordRecovery() {
		tester.startPage(SignInPage.class);
		tester.assertRenderedPage(SignInPage.class);
		
		tester.assertEnabled("footer:passwordRecovery");
		tester.clickLink("footer:passwordRecovery");
		
		tester.assertRenderedPage(SecurityPasswordRecoveryPage.class);
	}

	@Test
	public void formSubmitSuccess() throws ServiceException, SecurityServiceException {
		tester.startPage(SignInPage.class);
		tester.assertRenderedPage(SignInPage.class);
		
		tester.startComponentInPage(new SignInContentPanel("content"));
		tester.assertRequired("content:form:username");
		tester.assertRequired("content:form:password");
		
		FormTester form = tester.newFormTester("content:form");
		
		form.setValue(form.getForm().get("username"), basicUser.getUsername());
		form.setValue(form.getForm().get("password"), USER_PASSWORD);
		
		form.submit();
		
		tester.assertRenderedPage(HomePage.class);
	}

	@Test
	public void formSubmitFail() throws ServiceException, SecurityServiceException {
		tester.startPage(SignInPage.class);
		tester.assertRenderedPage(SignInPage.class);
		
		tester.startComponentInPage(new SignInContentPanel("content"));
		tester.assertRequired("content:form:username");
		tester.assertRequired("content:form:password");
		
		FormTester form = tester.newFormTester("content:form");
		
		form.setValue(form.getForm().get("username"), basicUser.getUsername());
		form.setValue(form.getForm().get("password"), "wrongPassword");
		
		form.submit();
		
		tester.assertErrorMessages(localize("signIn.error.authentication"));
		tester.assertRenderedPage(SignInPage.class);
	}
}
