package fr.portefeuille.core.cli;

import fr.portefeuille.core.config.spring.PortefeuilleCoreHeadlessConfig;
import org.iglooproject.jpa.migration.SqlUpdateScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class PortefeuilleSqlUpdateScriptMain extends AbstractPortefeuilleMain {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortefeuilleSqlUpdateScriptMain.class);

	private PortefeuilleSqlUpdateScriptMain() {
	}

	public static void main(String[] args) {
		new PortefeuilleSqlUpdateScriptMain().run(args);
	}

	/*
	 * This script uses Hibernate's hbm2ddl to write in an sql file either the script which will create the jpa model
	 * in the database or the differences between the jpa model and the existing database.
	 */
	protected void run(String[] args) {
		try (AnnotationConfigApplicationContext context = startContext("development", PortefeuilleCoreHeadlessConfig.class)) {
			String fileName;
			String action;
			
			if(args.length == 2) {
				fileName = args[1];
				action = args[0];
			} else {
				fileName = "/tmp/script.sql";
				action = "create";
			}
			
			SqlUpdateScript.writeSqlDiffScript(context, fileName, action);
			
			LOGGER.info("Initialization complete");
		} catch (Throwable e) { // NOSONAR We just want to log the Exception/Error, no error handling here.
			LOGGER.error("Error during initialization", e);
		}
	}

}
