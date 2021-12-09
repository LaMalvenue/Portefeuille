package fr.portefeuille.core.business.portefeuille.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.bindgen.Bindable;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.SortableField;
import org.iglooproject.commons.util.CloneUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.search.bridge.GenericEntityIdFieldBridge;
import org.iglooproject.jpa.search.util.HibernateSearchAnalyzer;
import org.iglooproject.jpa.search.util.HibernateSearchNormalizer;

import fr.portefeuille.core.business.portefeuille.model.atomic.OperationStatut;
import fr.portefeuille.core.business.portefeuille.model.atomic.OperationType;
import fr.portefeuille.core.business.referencedata.model.OperationCategorie;

@Indexed
@Entity
@Bindable
@Cacheable
public class Operation extends GenericEntity <Long, Operation> {

	private static final long serialVersionUID = 3417147747771753992L;

	public static final String LABEL = "label";
	public static final String LABEL_SORT = "labelSort";

	public static final String DATE = "date";

	public static final String CATEGORIE = "categorie";

	public static final String TYPE_OPERATION = "typeOperation";

	public static final String STATUT = "statut";

	public static final String BUDGET_AFFECTE = "budgetAffecte";

	public static final String COMPTE = "compte";
	public static final String COMPTE_PREFIX = COMPTE + ".";

	@Id
	@GeneratedValue
	private Long id;

	@Basic(optional = false)
	@Field(name = LABEL, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT_STEMMING))
	@Field(name = LABEL_SORT, normalizer = @Normalizer(definition = HibernateSearchNormalizer.TEXT))
	@SortableField(forField = LABEL_SORT)
	private String label;

	@Basic(optional = false)
	private BigDecimal montant;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Field(name = DATE)
	@SortableField(forField = DATE)
	private Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	@Field(name = CATEGORIE, bridge = @FieldBridge(impl = GenericEntityIdFieldBridge.class))
	private OperationCategorie categorie;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	@Field(name = TYPE_OPERATION)
	private OperationType type;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	@Field(name = STATUT)
	private OperationStatut statut;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Field(name = BUDGET_AFFECTE)
	@SortableField(forField = BUDGET_AFFECTE)
	private Date budgetAffecte;

	@ManyToOne(optional = false)
	private Compte compte;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public Date getDate() {
		return CloneUtils.clone(date);
	}

	public void setDate(Date date) {
		this.date = CloneUtils.clone(date);
	}

	public OperationCategorie getCategorie() {
		return categorie;
	}

	public void setCategorie(OperationCategorie categorie) {
		this.categorie = categorie;
	}

	public OperationType getTypeOperation() {
		return type;
	}

	public void setTypeOperation(OperationType type) {
		this.type = type;
	}

	public OperationStatut getStatut() {
		return statut;
	}

	public void setStatut(OperationStatut statut) {
		this.statut = statut;
	}

	public Date getBudgetAffecte() {
		return budgetAffecte;
	}

	public void setBudgetAffecte(Date budgetAffecte) {
		this.budgetAffecte = budgetAffecte;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

}
