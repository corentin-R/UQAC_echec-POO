package ca.uqac.pat.echec.pieces;

import ca.uqac.pat.echec.Plateau;

/**
  * Cette classe d�finit les tours, qui ne font que des d�placement en ligne droite.
  * @see ca.uqac.pat.Echec
  */
public class Tour extends Piece{
/**
  * Constructeur standard.
  */
	public Tour (Plateau J, int Coul, int PX, int PY){
		super (J, 5, Coul, PX, PY);
	}

/**
  * Il y a ici une validation sp�cifique pour ne permettre que les mouvements droits.
  * @param incX D�placment demand� en X.
  * @param incY D�placement demand� en Y.
  * @return Vrai si le d�placement a eu lieu correctement, faux sinon.
  */
	public boolean Bouger (int Joueur, int incX, int incY, boolean PourVrai){
		if ((incX == 0 && incY != 0) || (incX != 0 && incY == 0))
			return super.Bouger(Joueur, incX, incY, PourVrai);
		else
			return false;
	}
	
	public String toString()	{ return (retCoulBlanc() == 1 ? "T" : "t");	}
	public Object clone() 		{ return super.clone();	}
}