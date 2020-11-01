package fr.portefeuille.core.security.service;

import fr.portefeuille.core.business.referencedata.model.ReferenceData;
import fr.portefeuille.core.business.user.model.User;
import org.iglooproject.jpa.security.service.IGenericPermissionEvaluator;

public interface IReferenceDataPermissionEvaluator extends IGenericPermissionEvaluator<User, ReferenceData<?>> {

}
