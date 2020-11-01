package fr.portefeuille.core.business.history.dao;

import fr.portefeuille.core.business.history.model.HistoryLog;
import fr.portefeuille.core.business.history.model.atomic.HistoryEventType;
import org.iglooproject.jpa.more.business.history.dao.AbstractHistoryLogDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryLogDaoImpl extends AbstractHistoryLogDaoImpl<HistoryLog, HistoryEventType> implements IHistoryLogDao {

}
