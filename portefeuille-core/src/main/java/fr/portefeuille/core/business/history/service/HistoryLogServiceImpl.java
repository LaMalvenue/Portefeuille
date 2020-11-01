package fr.portefeuille.core.business.history.service;

import java.util.Date;
import java.util.List;

import fr.portefeuille.core.business.history.dao.IHistoryLogDao;
import fr.portefeuille.core.business.history.model.HistoryDifference;
import fr.portefeuille.core.business.history.model.HistoryLog;
import fr.portefeuille.core.business.history.model.atomic.HistoryEventType;
import fr.portefeuille.core.business.history.model.bean.HistoryLogAdditionalInformationBean;
import fr.portefeuille.core.business.user.model.User;
import fr.portefeuille.core.business.user.service.IUserService;
import org.iglooproject.functional.Supplier2;
import org.iglooproject.jpa.more.business.history.service.AbstractHistoryLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryLogServiceImpl extends AbstractHistoryLogServiceImpl<HistoryLog, HistoryEventType,
		HistoryDifference, HistoryLogAdditionalInformationBean>
		implements IHistoryLogService {

	private static final Supplier2<HistoryDifference> HISTORY_DIFFERENCE_SUPPLIER = () -> new HistoryDifference();

	@Autowired
	private IUserService userService;

	@Autowired
	public HistoryLogServiceImpl(IHistoryLogDao dao) {
		super(dao);
	}

	@Override
	protected <T> HistoryLog newHistoryLog(Date date, HistoryEventType eventType, List<HistoryDifference> differences,
			T mainObject, HistoryLogAdditionalInformationBean additionalInformation) {
		HistoryLog log = new HistoryLog(date, eventType, valueService.create(mainObject));
		
		User subject = userService.getAuthenticatedUser();
		log.setSubject(valueService.create(subject));
		
		if (additionalInformation != null) {
			setAdditionalInformation(log, additionalInformation);
		}
		
		return log;
	}

	@Override
	protected Supplier2<HistoryDifference> newHistoryDifferenceSupplier() {
		return HISTORY_DIFFERENCE_SUPPLIER;
	}

}
