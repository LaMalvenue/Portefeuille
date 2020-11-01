package fr.portefeuille.core.business.referencedata.model;

import org.bindgen.Bindable;
import fr.portefeuille.core.business.common.model.embeddable.LocalizedText;
import org.iglooproject.jpa.more.business.referencedata.model.IGenericReferenceDataBindingInterface;

@Bindable
public interface IReferenceDataBindingInterface extends IGenericReferenceDataBindingInterface<LocalizedText> {

	String getCode();

}
