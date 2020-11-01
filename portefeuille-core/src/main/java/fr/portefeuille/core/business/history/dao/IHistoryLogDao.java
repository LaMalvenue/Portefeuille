package fr.portefeuille.core.business.history.dao;

import fr.portefeuille.core.business.history.model.HistoryLog;
import fr.portefeuille.core.business.history.model.atomic.HistoryEventType;
import org.iglooproject.jpa.more.business.history.dao.IGenericHistoryLogDao;

public interface IHistoryLogDao extends IGenericHistoryLogDao<HistoryLog, HistoryEventType> {

}
