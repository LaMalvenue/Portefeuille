package fr.portefeuille.core.business.operation.model;

import java.util.Date;

import javax.persistence.Basic;
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
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.SortableField;
import org.iglooproject.commons.util.CloneUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.search.util.HibernateSearchAnalyzer;
import org.iglooproject.jpa.search.util.HibernateSearchNormalizer;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.operation.model.atomic.Categorie;
import fr.portefeuille.core.business.operation.model.atomic.Mois;
import fr.portefeuille.core.business.operation.model.atomic.Statut;

@Indexed
@Entity
@Bindable
public class Operation extends GenericEntity <Long, Operation> {

	private static final long serialVersionUID = 3417147747771753992L;

	public static final String LABEL = "label";
	public static final String LABEL_SORT = "labelSort";
	public static final String DATE = "date";
	public static final String CATEGORIE = "categorie";
	public static final String COMPTE = "compte";
	public static final String COMPTE_PREFIX = COMPTE + ".";
	public static final String COMPTE_LABEL_SORT = COMPTE_PREFIX + Compte.LABEL_SORT;
	public static final String COMPTE_LABEL = COMPTE_PREFIX + Compte.LABEL;	
	public static final String STATUT = "statut";
	public static final String MOIS = "mois";

	@Id
	@DocumentId
	@GeneratedValue
	private Long id;

	@Field(name = LABEL, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT_STEMMING))
	@Field(name = LABEL_SORT, normalizer = @Normalizer(definition = HibernateSearchNormalizer.TEXT))
	@SortableField(forField = LABEL_SORT)
	@Basic(optional = false)
	private String label;

	@Basic(optional = false)
	private double montant;

	@Basic 
	@Temporal(TemporalType.DATE)	
	@Field(name = DATE)
	@SortableField(forField = DATE)
	private Date date;

	@Field(name = CATEGORIE)
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private Categorie categorie;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Compte compte;

	@Field(name = STATUT)
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private Statut statut;

	@Field(name = MOIS)
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private Mois budgetAffecte;

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

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getDate() {
		return CloneUtils.clone(date);
	}

	public void setDate(Date date) {
		this.date = CloneUtils.clone(date);
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Mois getBudgetAffecte() {
		return budgetAffecte;
	}

	public void setBudgetAffecte(Mois budgetAffecte) {
		this.budgetAffecte = budgetAffecte;
	}

}