package fr.portefeuille.core.business.portefeuille.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.bindgen.Bindable;
import org.hibernate.annotations.SortComparator;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;
import org.iglooproject.commons.util.collections.CollectionUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.search.bridge.GenericEntityIdFieldBridge;

import fr.portefeuille.core.business.common.model.Montant;
import fr.portefeuille.core.business.portefeuille.model.comparator.OperationComparator;
import fr.portefeuille.core.business.referencedata.model.CompteType;

@Indexed
@Entity
@Bindable
@Cacheable
public class Compte extends GenericEntity<Long, Compte> {

	private static final long serialVersionUID = 5899160609031078380L;

	public static final String SOLDE = "solde";

	public static final String TYPE = "type";

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private Portefeuille portefeuille;

	@Basic(optional = false)
	@Column(precision = Montant.PRECISION, scale = Montant.SCALE)
	@Field(name = SOLDE)
	@SortableField(forField = SOLDE)
	private BigDecimal solde;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Field(name = TYPE, bridge = @FieldBridge(impl = GenericEntityIdFieldBridge.class))
	@SortableField(forField = TYPE)
	private CompteType type;
	
	@OneToMany(mappedBy = "compte", fetch = FetchType.LAZY, orphanRemoval = true)
	@SortComparator(OperationComparator.class)
	@ContainedIn
	private SortedSet<Operation> operations;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Portefeuille getPortefeuille() {
		return portefeuille;
	}

	public void setPortefeuille(Portefeuille portefeuille) {
		this.portefeuille = portefeuille;
	}

	public BigDecimal getSolde() {
		operations.stream().forEach(o -> solde.add(o.getMontant()));
		return solde;
	}

	public void setSolde(BigDecimal solde) {
		this.solde = solde;
	}

	public CompteType getType() {
		return type;
	}

	public void setType(CompteType type) {
		this.type = type;
	}

	public SortedSet<Operation> getOperations() {
		return Collections.unmodifiableSortedSet(operations);
	}

	public void setOperations(SortedSet<Operation> operations) {
		CollectionUtils.replaceAll(this.operations, operations);
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

}
