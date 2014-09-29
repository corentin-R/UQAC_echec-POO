package ca.uqac.pat.echec.pieces;

import ca.uqac.pat.echec.Plateau;

/**
  * Cette classe d�finit les rois, mais seulement les d�placements de base.
  * @see ca.uqac.pat.Echec
  */
public class Roi extends Piece implements Cloneable{
	/**
	 * Constructeur standard
	 * @param J le plateau de jeu
	 * @param Coul 1: blanc, -1: noir
	 * @param PX : no colonne
	 * @param PY : no ligne
	 */
	public Roi (Plateau J, int Coul, int PX, int PY){
		super (J, 20, Coul, PX, PY);
	}

/**
  * Il y a ici une validation sp�cifique pour ne permettre que les mouvements 
  * de 1 case..
  * @param incX D�placement demand� en X.
  * @param incY D�placement demand� en Y.
  * @return Vrai si le d�placement a eu lieu correctement, faux sinon.
  */
	public boolean Bouger (int Joueur, int incX, int incY, boolean PourVrai){
		if (Math.abs(incX) + Math.abs (incY) <= 2 )
			return super.Bouger(Joueur, incX, incY, PourVrai);
		else return false;
	}

	
	public String toString()	{ return (retCoulBlanc() == 1 ? "R" : "r");	}
	public Object clone() 		{ return super.clone();	}
}