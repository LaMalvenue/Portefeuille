package fr.portefeuille.core.business.operation.model.embeddable;

import java.io.Serializable;
import java.time.Month;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;

@Embeddable
public class OperationBudgetAffecte implements Serializable {

	private static final long serialVersionUID = 6523902352997439581L;

	public static final String MOIS = "mois";
	public static final String ANNEE = "annee";

	@Basic(optional = false)
	@Field(name = MOIS, analyze = Analyze.NO)
	@Enumerated(EnumType.STRING)
	private Month mois;

	@Basic(optional = false)
	@Field(name = ANNEE)
	private int annee;

	public Month getMois() {
		return mois;
	}

	public void setMois(Month mois) {
		this.mois = mois;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

}
