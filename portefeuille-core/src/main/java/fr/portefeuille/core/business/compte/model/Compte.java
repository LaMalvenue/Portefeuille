package fr.portefeuille.core.business.compte.model;

import java.util.Collections;
import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.bindgen.Bindable;
import org.hibernate.annotations.SortComparator;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.SortableField;
import org.iglooproject.commons.util.collections.CollectionUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.search.bridge.GenericEntityIdFieldBridge;
import org.iglooproject.jpa.search.util.HibernateSearchAnalyzer;
import org.iglooproject.jpa.search.util.HibernateSearchNormalizer;

import fr.portefeuille.core.business.compte.model.atomic.Type;
import fr.portefeuille.core.business.operation.model.Operation;
import fr.portefeuille.core.business.operation.model.comparator.OperationComparator;
import fr.portefeuille.core.business.portefeuille.model.Portefeuille;

@Indexed
@Entity
@Bindable
public class Compte extends GenericEntity<Long, Compte> {

	private static final long serialVersionUID = 5899160609031078380L;

	public static final String LABEL = "label";
	public static final String LABEL_SORT = "labelSort";
	public static final String TYPE = "type";
	public static final String PORTEFEUILLE = "portefeuille";
	public static final String PORTEFEUILLE_PREFIX = PORTEFEUILLE + ".";
	public static final String PORTEFEUILLE_NOM_SORT = PORTEFEUILLE_PREFIX + Portefeuille.NOM_SORT;
	public static final String PORTEFEUILLE_NOM = PORTEFEUILLE_PREFIX + Portefeuille.NOM;

	@Id
	@DocumentId
	@GeneratedValue
	private Long id;

	@Field(name = LABEL, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT_STEMMING))
	@Field(name = LABEL_SORT, normalizer = @Normalizer(definition = HibernateSearchNormalizer.TEXT))
	@SortableField(forField = LABEL_SORT)
	private String label;
	
	@Basic(optional = false)
	private double fondsDisponibles;
	
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@OneToMany(mappedBy = "compte", fetch = FetchType.LAZY, orphanRemoval = true)
	@SortComparator(OperationComparator.class)
	@ContainedIn
	private SortedSet<Operation> operations;

	@Field(name = PORTEFEUILLE, bridge = @FieldBridge(impl = GenericEntityIdFieldBridge.class))
	@IndexedEmbedded(prefix = PORTEFEUILLE_PREFIX)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Portefeuille portefeuille;  
	
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

	public double getFondsDisponibles() {
		return fondsDisponibles;
	}

	public void setFondsDisponibles(double fondsDisponibles) {
		this.fondsDisponibles = fondsDisponibles;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public SortedSet<Operation> getOperations() {
		return Collections.unmodifiableSortedSet(operations);
	}

	public void setOperations(SortedSet<Operation> operations) {
		CollectionUtils.replaceAll(this.operations, operations);
	}

	public Portefeuille getPortefeuille() {
		return portefeuille;
	}

	public void setPortefeuille(Portefeuille portefeuille) {
		this.portefeuille = portefeuille;
	}

}
