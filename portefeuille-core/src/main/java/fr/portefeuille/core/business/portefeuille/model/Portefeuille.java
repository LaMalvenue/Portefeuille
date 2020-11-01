package fr.portefeuille.core.business.portefeuille.model;

import java.util.Collections;
import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.bindgen.Bindable;
import org.hibernate.annotations.SortComparator;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.SortableField;
import org.iglooproject.commons.util.collections.CollectionUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.search.util.HibernateSearchAnalyzer;
import org.iglooproject.jpa.search.util.HibernateSearchNormalizer;

import com.google.common.collect.Sets;

import fr.portefeuille.core.business.compte.model.Compte;
import fr.portefeuille.core.business.compte.model.comparator.CompteComparator;

@Indexed
@Entity
@Bindable
public class Portefeuille extends GenericEntity<Long, Portefeuille> {

	private static final long serialVersionUID = -1482204779794633259L;

	public static final String NAME = "name";
	public static final String NAME_SORT = "nameSort";

	@Id
	@DocumentId
	@GeneratedValue
	private Long id;

	@Field(name = NAME, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT_STEMMING))
	@Field(name = NAME_SORT, normalizer = @Normalizer(definition = HibernateSearchNormalizer.TEXT))	
	@SortableField(forField = NAME_SORT)
	@Basic(optional = false)
	private String name;

	@OneToMany(mappedBy = "portefeuille", fetch = FetchType.LAZY, orphanRemoval = true)
	@SortComparator(CompteComparator.class)
	@ContainedIn
	private SortedSet<Compte> comptes = Sets.newTreeSet(CompteComparator.get());
	
	@Basic(optional = false)
	private double fondsTotauxDisponibles;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedSet<Compte> getComptes() {
		return Collections.unmodifiableSortedSet(comptes);
	}

	public void setComptes(SortedSet<Compte> comptes) {
		CollectionUtils.replaceAll(this.comptes, comptes);
	}

	public double getFondsTotauxDisponibles() {
		return fondsTotauxDisponibles;
	}

	public void setFondsTotauxDisponibles(double fondsTotauxDisponibles) {
		this.fondsTotauxDisponibles = fondsTotauxDisponibles;
	}

}
