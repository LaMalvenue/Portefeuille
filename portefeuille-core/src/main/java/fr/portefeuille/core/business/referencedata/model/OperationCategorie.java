package fr.portefeuille.core.business.referencedata.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.bindgen.Bindable;
import org.hibernate.search.annotations.Indexed;

import de.danielbechler.diff.introspection.ObjectDiffEqualsOnlyType;

@Entity
@Bindable
@Indexed
@Cacheable
@ObjectDiffEqualsOnlyType
public class OperationCategorie extends ReferenceData<OperationCategorie> {

	private static final long serialVersionUID = 5869714773005449766L;

}
