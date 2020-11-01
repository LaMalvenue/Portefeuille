package fr.portefeuille.web.application.common.template.theme.advanced;

import java.util.List;
import java.util.function.Supplier;

import org.apache.wicket.markup.html.WebPage;
import fr.portefeuille.web.application.common.component.ApplicationEnvironmentPanel;
import fr.portefeuille.web.application.common.template.theme.common.AbstractNavbarPanel;
import fr.portefeuille.web.application.common.template.theme.common.ChangeApplicationThemeAjaxLink;
import org.iglooproject.wicket.bootstrap4.markup.html.template.js.sidebar.SidebarBehavior;
import org.iglooproject.wicket.more.markup.html.template.model.NavigationMenuItem;

public class SidebarPanel extends AbstractNavbarPanel {

	private static final long serialVersionUID = 3187936834300791175L;

	public SidebarPanel(
		String id,
		Supplier<List<NavigationMenuItem>> mainNavSupplier,
		Supplier<Class<? extends WebPage>> firstMenuPageSupplier,
		Supplier<Class<? extends WebPage>> secondMenuPageSupplier
	) {
		super(id);
		
		add(
			new SidebarNavbarPanel("navbar"),
			new ApplicationEnvironmentPanel("environment")
				.compact()
		);
		
		add(
			new SidebarHeaderPanel("header"),
			new SidebarUserPanel("user"),
			new SidebarQuickSearchPanel("quickSearch"),
			new SidebarMenuPanel("menu", mainNavSupplier, firstMenuPageSupplier, secondMenuPageSupplier),
			new ChangeApplicationThemeAjaxLink("changeTheme")
		);
		
		add(
			new SidebarFooterPanel("footer")
		);
		
		add(
			new SidebarBehavior()
		);
	}

}
