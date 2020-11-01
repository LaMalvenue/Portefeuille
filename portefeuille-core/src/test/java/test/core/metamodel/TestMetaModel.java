package test.core.metamodel;

import fr.portefeuille.core.business.common.model.PostalCode;
import org.junit.Test;

import test.core.AbstractPortefeuilleTestCase;

public class TestMetaModel extends AbstractPortefeuilleTestCase {

	@Test
	public void testMetaModel() throws NoSuchFieldException, SecurityException {
		// Class<?> est utilisé sur GenericEntityReference ; ATTENTION,
		// l'annotation @Type est nécessaire pour un traitement correct par Hibernate.
		super.testMetaModel(PostalCode.class, Class.class, Comparable.class);
	}

}
