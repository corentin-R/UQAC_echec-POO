package ca.uqac.pat.echec.pieces;

import ca.uqac.pat.echec.Plateau;

/**
  * Cette classe permet d'implanter des pions au échec.
  * @see ca.uqac.pat.Echec
  */
public class Pion extends Piece implements Cloneable{
	/**
	 * Constructeur standard
	 * @param J le plateau de jeu
	 * @param Coul 1: blanc, -1: noir
	 * @param PX : no colonne
	 * @param PY : no ligne
	 */
	public Pion(Plateau J, int Coul, int PX, int PY){
		super (J, 1, Coul, PX, PY);
	}

/**
  * Il y a ici une validation spécifique pour ne permettre que les 
  * mouvement d'avant ou en diagonal pour une prise, et cela d'une seule case.
  * @param incX Déplacment demandé en X.
  * @param incY Déplacement demandé en Y.
  * @return Vrai si le déplacement a eu lieu correctement, faux sinon.
  */
	public boolean Bouger(int Joueur, int incX, int incY, boolean PourVrai){	
		
		//////////////////début modification//////////////////////////////////////////////////// 
		
		//si un pion blanc est sur la deuxieme ou un pion noir est sur 7ième ligne
		//et qu'un déplacement de 2 cases est demandé
		if(incX == 0 && ((PosY == 2-1  && incY == 2) || ( PosY == 7-1 && incY == -2))){
			//alors on met l'attribut déclarant qu'une prise en passant sera possible sur ce pion
			priseEnPassantPossible=true;
			//et on bouge le pion
			return super.Bouger(Joueur, incX, incY, PourVrai);			
		}
		
		else if (incY == CoulBlanc)			//Si c'est la bonne direction
			if (incX == 0)				// s'il avance
			{
				if (Jeu.Case(PosX, PosY+incY).isCaseVide()){	//vide devant
					if(PosY + incY == 8-1 || PosY + incY == 1-1){
						Jeu.setPos(PosX, PosY, new CaseVide (Jeu, PosX , PosY));
						Jeu.setPos(PosX + incX, PosY + incY, new Reine (Jeu,CoulBlanc, PosX + incX, PosY + incY));
						return true;
					}
					else
						return super.Bouger(Joueur, incX, incY, PourVrai);
				}
			}
			else{						// s'il mange
				if (Math.abs(incX) == 1){//S'il va en Dia (manger une piece)
					if (!Jeu.Case(PosX + incX, PosY + incY).isCaseVide()){
						if(PosY + incY == 8-1 || PosY + incY == 1-1){
							Jeu.setPos(PosX, PosY, new CaseVide (Jeu, PosX , PosY));
							Jeu.setPos(PosX + incX, PosY + incY, new Reine (Jeu,CoulBlanc, PosX + incX, PosY + incY));
							return true;
						}
						else
							return super.Bouger(Joueur, incX, incY, PourVrai);
					}
					else if (// si il la pièce est un ennemi
							Jeu.Case(PosX + incX, PosY).retCoulBlanc() != CoulBlanc 
							//et si la pièce à gauche ou à droite est bien un pion
							&& Jeu.Case(PosX + incX, PosY) instanceof Pion 
							//et si ce dernier à avancé de 2 cases au premier coup
							&& Jeu.Case(PosX + incX, PosY).getPriseEnPassantPossible() == true 
							//et si il vient d'être joué
							&& Jeu.Case(PosX + incX, PosY).getJustMove()==true){
						//alors le pion adverse se fait bouffer
						Jeu.setPos(PosX + incX, PosY, new CaseVide (Jeu, PosX + incX, PosY));
						//et on bouge notre pion
						return super.Bouger(Joueur, incX, incY, PourVrai);
					}
				}
			}
			//////////////////fin modification///////////////////////////////////////////////////////////	
		
		return false;
	}


	public String toString()	{ return (retCoulBlanc() == 1 ? "P" : "u");	}
	public Object clone() 		{ return super.clone();	}
	

}
