package fr.portefeuille.core.business.portefeuille.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.bindgen.Bindable;
import org.hibernate.annotations.SortComparator;
import org.hibernate.search.annotations.ContainedIn;
import org.iglooproject.commons.util.collections.CollectionUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;

import com.google.common.collect.Sets;

import fr.portefeuille.core.business.portefeuille.model.comparator.CompteComparator;
import fr.portefeuille.core.business.user.model.User;

@Entity
@Bindable
@Cacheable
public class Portefeuille extends GenericEntity<Long, Portefeuille> {

	private static final long serialVersionUID = -1482204779794633259L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(optional = false)
	private User proprietaire;

	@OneToMany(mappedBy = "portefeuille")
	@SortComparator(CompteComparator.class)
	@ContainedIn
	private SortedSet<Compte> comptes = Sets.newTreeSet(CompteComparator.get());

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public User getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(User proprietaire) {
		this.proprietaire = proprietaire;
	}

	public SortedSet<Compte> getComptes() {
		return Collections.unmodifiableSortedSet(comptes);
	}

	public void setComptes(SortedSet<Compte> comptes) {
		CollectionUtils.replaceAll(this.comptes, comptes);
	}

	public boolean addCompte(Compte compte) {
		return comptes.add(compte);
	}

	@Transient
	public BigDecimal getSolde() {
		BigDecimal soldeTotal = BigDecimal.ZERO;
		if (!this.getComptes().isEmpty()) {
			for (Compte compte : this.getComptes()) {
				soldeTotal.add(compte.getSolde());
			}
		}
		return soldeTotal;
	}

	@Transient 
	public List<Operation> getOperations() {
		return this.getComptes().stream()
			.flatMap(c-> c.getOperations().stream())
			.collect(Collectors.toList());
	}

}
