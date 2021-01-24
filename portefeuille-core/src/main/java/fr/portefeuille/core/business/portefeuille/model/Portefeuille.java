package fr.portefeuille.core.business.portefeuille.model;

import java.util.Collections;
import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

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
import fr.portefeuille.core.business.user.model.User;

@Indexed
@Entity
@Bindable
public class Portefeuille extends GenericEntity<Long, Portefeuille> {

	private static final long serialVersionUID = -1482204779794633259L;

	public static final String NOM = "nom";
	public static final String NOM_SORT = "nomSort";

	@Id
	@DocumentId
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "portefeuille")
	private User user;

	@Field(name = NOM, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT_STEMMING))
	@Field(name = NOM_SORT, normalizer = @Normalizer(definition = HibernateSearchNormalizer.TEXT))	
	@SortableField(forField = NOM_SORT)
	@Basic(optional = false)
	private String nom;

	@OneToMany(mappedBy = "portefeuille", fetch = FetchType.LAZY, orphanRemoval = true)
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public SortedSet<Compte> getComptes() {
		return Collections.unmodifiableSortedSet(comptes);
	}

	public void setComptes(SortedSet<Compte> comptes) {
		CollectionUtils.replaceAll(this.comptes, comptes);
	}

	@Transient
	public double getFondsTotauxDisponibles() {
		double fondsTotauxDisponibles = 0;
		if (!this.getComptes().isEmpty()) {
			for (Compte compte : this.getComptes()) {
				fondsTotauxDisponibles += compte.getFondsDisponibles();
			}
		}
		return fondsTotauxDisponibles;
	}

	public boolean addCompte(Compte compte) {
		return comptes.add(compte);
	}

}
