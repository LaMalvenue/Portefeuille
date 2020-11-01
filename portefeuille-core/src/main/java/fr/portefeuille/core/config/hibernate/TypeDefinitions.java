package fr.portefeuille.core.config.hibernate;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.TypeDef;
import fr.portefeuille.core.business.common.model.EmailAddress;
import fr.portefeuille.core.business.common.model.PhoneNumber;
import fr.portefeuille.core.business.common.model.PostalCode;
import fr.portefeuille.core.config.hibernate.type.EmailAddressType;
import fr.portefeuille.core.config.hibernate.type.PhoneNumberType;
import fr.portefeuille.core.config.hibernate.type.PostalCodeType;
import org.iglooproject.jpa.hibernate.usertype.StringClobType;

/**
 * Class used to define types with annotations.
 */
// We use "text" instead of "varchar" for String columns
@TypeDef(defaultForType = String.class, typeClass = StringClobType.class)
// We declare here the types we want to store as String instead of binary
@TypeDef(defaultForType = EmailAddress.class, typeClass = EmailAddressType.class)
@TypeDef(defaultForType = PhoneNumber.class, typeClass = PhoneNumberType.class)
@TypeDef(defaultForType = PostalCode.class, typeClass = PostalCodeType.class)
@MappedSuperclass
public final class TypeDefinitions {

	/**
	 * Usage:
	 * <pre>
	 *	&#064;Column
	 *	&#064;Type(type = TypeDefinitions.STRING_CLOB)
	 *	private String myStringClobField;
	 * </pre>
	 */
	public static final String STRING_CLOB = StringClobType.TYPENAME;

	/**
	 * Usage:
	 * <pre>
	 *	&#064;Column(length = 4)
	 *	&#064;Type(type = TypeDefinitions.STRING_VARCHAR)
	 *	private String myStringVarcharField;
	 * </pre>
	 */
	public static final String STRING_VARCHAR = "org.hibernate.type.StringType";

	private TypeDefinitions() { }

}
