package fr.portefeuille.core.business.history.service;

import fr.portefeuille.core.business.history.model.HistoryDifference;
import fr.portefeuille.core.business.history.model.HistoryLog;
import fr.portefeuille.core.business.history.model.atomic.HistoryEventType;
import fr.portefeuille.core.business.history.model.bean.HistoryLogAdditionalInformationBean;
import org.iglooproject.jpa.more.business.history.service.IGenericHistoryLogService;

public interface IHistoryLogService extends IGenericHistoryLogService<HistoryLog, HistoryEventType,
		HistoryDifference, HistoryLogAdditionalInformationBean> {

}
