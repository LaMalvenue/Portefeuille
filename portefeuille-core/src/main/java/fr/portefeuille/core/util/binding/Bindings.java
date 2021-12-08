package fr.portefeuille.core.util.binding;

import fr.portefeuille.core.business.announcement.model.AnnouncementBinding;
import fr.portefeuille.core.business.history.model.HistoryDifferenceBinding;
import fr.portefeuille.core.business.history.model.HistoryLogBinding;
import fr.portefeuille.core.business.portefeuille.model.CompteBinding;
import fr.portefeuille.core.business.portefeuille.model.OperationBinding;
import fr.portefeuille.core.business.portefeuille.model.PortefeuilleBinding;
import fr.portefeuille.core.business.referencedata.model.CityBinding;
import fr.portefeuille.core.business.referencedata.model.IReferenceDataBindingInterfaceBinding;
import fr.portefeuille.core.business.user.model.UserBinding;
import fr.portefeuille.core.business.user.model.UserGroupBinding;

public final class Bindings {

	private static final UserBinding USER = new UserBinding();

	private static final UserGroupBinding USER_GROUP = new UserGroupBinding();

	private static final HistoryLogBinding HISTORY_LOG = new HistoryLogBinding();

	private static final HistoryDifferenceBinding HISTORY_DIFFERENCE = new HistoryDifferenceBinding();

	private static final IReferenceDataBindingInterfaceBinding REFERENCE_DATA = new IReferenceDataBindingInterfaceBinding();

	private static final CityBinding CITY = new CityBinding();

	private static final AnnouncementBinding ANNOUNCEMENT = new AnnouncementBinding();

	private static final PortefeuilleBinding PORTEFEUILLE = new PortefeuilleBinding();

	private static final CompteBinding COMPTE = new CompteBinding();

	private static final OperationBinding OPERATION = new OperationBinding();

	public static UserBinding user() {
		return USER;
	}

	public static UserGroupBinding userGroup() {
		return USER_GROUP;
	}

	public static HistoryLogBinding historyLog() {
		return HISTORY_LOG;
	}

	public static HistoryDifferenceBinding historyDifference() {
		return HISTORY_DIFFERENCE;
	}

	public static IReferenceDataBindingInterfaceBinding referenceData() {
		return REFERENCE_DATA;
	}

	public static CityBinding city() {
		return CITY;
	}

	public static AnnouncementBinding announcement() {
		return ANNOUNCEMENT;
	}

	public static PortefeuilleBinding portefeuille() {
		return PORTEFEUILLE;
	}

	public static CompteBinding compte() {
		return COMPTE;
	}

	public static OperationBinding operation() {
		return OPERATION;
	}

	private Bindings() {
	}

}
