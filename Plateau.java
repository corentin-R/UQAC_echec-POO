package ca.uqac.pat.echec;

import ca.uqac.pat.echec.pieces.*;

/**
 * Cette classe permet de construire, afficher en console et valider certains
 * d�placement dans un jeu d'�chec.  
 * @see ca.uqac.pat.Echec ca.uqac.pat.echec.Piece
 */
public class Plateau{
	private final int T = 8;
	private Piece Jeu[][];

	private int Joueur = 1;		// 1 Blanc, -1 Noir

	/**
	 * Constructeur qui fait un jeu de taille et de disposition standard.
	 */
	public Plateau(){
		Jeu = new Piece[T][T];

		for (int y=2; y < T-2; y++)					// Les cases du centre
			for (int x=0; x<T; x++)
				Jeu[y][x] = new CaseVide (this, x, y);

		for (int x=0; x < T; x++){					// Les pions
			Jeu[1][x] = new Pion (this, 1, x, 1);
			Jeu[6][x] = new Pion (this, -1, x, 6);
		}
		for (int x=2; x<8; x +=3){					// Les 4 Fous
			Jeu[0][x] = new Fou (this, 1, x, 0);
			Jeu[7][x] = new Fou (this, -1, x, 7);
		}

		for (int x=1; x<8; x +=5){					// Les 4 Cavaliers
			Jeu[0][x] = new Cavalier (this, 1, x, 0);
			Jeu[7][x] = new Cavalier (this, -1, x, 7);
		}

		for (int x=0; x< 8; x+=7){					// Les 4 tours		
			Jeu[0][x] = new Tour (this, 1, x, 0);
			Jeu[7][x] = new Tour (this, -1, x, 7);
		}
		Jeu[0][3] = new Reine (this, 1, 3, 0);		// Reine Blanche
		Jeu[7][3] = new Reine (this, -1,3, 7);		// Reine Noire

		Jeu[0][4] = new Roi (this, 1, 4, 0);		// Roi Blanc
		Jeu[7][4] = new Roi (this, -1,4, 7);		// Roi Noir
	}
	


	/**
	 * Cette m�thode effectue une copie du plateau, mais pas des pi�ces.
	 */
	public Object clone(){
		Plateau Ret = new Plateau ();

		Ret.Jeu = new Piece[T][T];
		for (int y = 0; y < T; y++)	
			for (int x = 0; x < T; x++)
				Ret.Jeu[y][x] = (Piece)Jeu[y][x].clone();

		Ret.Joueur = Joueur;		// 1 Blanc, -1 Noir

		return Ret;
	}	


	/**
	 * Affiche le jeu au complet en utilisant le polymorphisme des pi�ces.
	 */
	public void Afficher(){
		for (int y = T - 1; y >= 0; y--){
			System.out.print ((char)('1' + y) + "| ");
			for (int x=0; x < T; x++)
				if (Jeu[y][x] != null){
					Jeu[y][x].Afficher();
					System.out.print (" ");
				}					
			System.out.println ();
		}

		System.out.print ("   ");
		for (int x=0; x < T; x++)
			System.out.print ((char)('a'+ x) + " ");
		System.out.println ();
	}


	/**
	 * Encapsuleur du tableau de Piece.
	 */
	public Piece Case(int x, int y){
		return Jeu[y][x];
	}


	/**
	 * Utilise le polymorphisme pour Appeler jouer sur le bon objet.
	 * @return Vrai si le coup est valide, faux sinon.
	 * @param IniX Valeur initiale en X.
	 * @param IniY Valeur initiale en Y.
	 * @param finX Valeur finale en Y.
	 * @param finX Valeur finale en Y
	 */  
	public boolean Bouger (int iniX, int iniY, int finX, int finY, 
			boolean PourVrai){
		if (finX-iniX == 0 && finY-iniY == 0) return false;

		boolean isGood = false;
		try{
			isGood = Jeu[iniY][iniX].Bouger(Joueur, finX-iniX, finY-iniY, 
					PourVrai);
		}
		catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
		if (isGood){
			Joueur = -Joueur;
		}

		return isGood;
	}

	/**
	 * Méthode permettant de jouer un petit roque legal
	 * @return succes
	 */
	public boolean jouerPetitRoqueLegal()
	{
		boolean succes = false, menace = false, libres = true;
		int lignePieces = -1;

		//Si joueur = blanc, ligne où se trouvent roi & tour = 0
		if(Joueur == 1) lignePieces = 0;
		else lignePieces = 7; //Sinon ligne 7 pour joueur noir

		if(Jeu[lignePieces][4] instanceof Roi && Jeu[lignePieces][7] instanceof Tour)
		{
			//Les cases entre le roi et la tour sont elles vides ?
			for(int i= 5;i<7;i++)
			{
				if(!(Jeu[lignePieces][i] instanceof CaseVide))
				{
					libres = false;
				}
			}
			if(libres)
			{
				//Verifier si les pieces ennemies peuvent empecher le roque
				for(int i =0;i<Jeu.length;i++)
				{
					for(int j =0;j<Jeu[i].length;j++)
					{
						//Piece ennemie menace le roi, la tour ou une des cases entres les deux
						if((Jeu[i][j].retCoulBlanc() == -Joueur )
								&& (Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][4].PosX-Jeu[i][j].PosX, Jeu[lignePieces][4].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][7].PosX-Jeu[i][j].PosX, Jeu[lignePieces][7].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][5].PosX-Jeu[i][j].PosX, Jeu[lignePieces][5].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][6].PosX-Jeu[i][j].PosX, Jeu[lignePieces][6].PosY-Jeu[i][j].PosY, false)))
						{
							System.out.println("Des pieces ennemies empechent de jouer le roque");
							menace = true;
						}	
					}
				}
				if(!menace) 
				{
					Bouger(Jeu[lignePieces][4].PosX,Jeu[lignePieces][4].PosY,6,Jeu[lignePieces][4].PosY, true);
					//setPos(6, lignePieces, Jeu[lignePieces][4]); //Le roi se met en case 6
					setPos(5,lignePieces,Jeu[lignePieces][7]); //La tour se met à gauche du roi
					//Anciennes cases deviennent vides
					//setPos(4,lignePieces, new CaseVide(this, 4, lignePieces));
					setPos(7,lignePieces, new CaseVide(this, 7, lignePieces));
					succes = true;
				}
			}
			else
				System.out.println("cases non libres entre roi et tour");
		}

		if(succes)
			System.out.println("Roque effectue");
		else
			System.out.println("Le roque ne peut être fait");
		
		return succes;
	}

	/**
	 * Méthode permettant de jouer un petit roque triche
	 * @return succes
	 */
	public boolean jouerPetitRoqueTriche()
	{
		boolean succes = false, menace = false, libres = true;
		int lignePieces = -1;

		//Si joueur = blanc, ligne où se trouvent roi & tour = 0
		if(Joueur == 1) lignePieces = 0;
		else lignePieces = 7; //Sinon ligne 7 pour joueur noir

		if(Jeu[lignePieces][4] instanceof Roi && Jeu[lignePieces][7] instanceof Tour)
		{
			//Les cases entre le roi et la tour sont elles vides ?
			for(int i= 5;i<7;i++)
			{
				if(!(Jeu[lignePieces][i] instanceof CaseVide))
				{
					libres = false;
				}
			}
			if(libres)
			{
					Bouger(Jeu[lignePieces][4].PosX,Jeu[lignePieces][4].PosY,6,Jeu[lignePieces][4].PosY, true);
					//setPos(6, lignePieces, Jeu[lignePieces][4]); //Le roi se met en case 6
					setPos(5,lignePieces,Jeu[lignePieces][7]); //La tour se met à gauche du roi
					//Anciennes cases deviennent vides
					//setPos(4,lignePieces, new CaseVide(this, 4, lignePieces));
					setPos(7,lignePieces, new CaseVide(this, 7, lignePieces));
					succes = true;
			}
			else
				System.out.println("cases non libres entre roi et tour");
		}

		if(succes)
			System.out.println("Petit roque triche effectue");
		else
			System.out.println("Le petit roque triche ne peut être fait");
		
		return succes;
	}

	/**
	 * Méthode permettant de jouer un grand roque
	 * @return succes
	 */
	public boolean jouerGrandRoque()
	{
		boolean succes = false, menace = false, libres = true;
		int lignePieces = -1;

		//Si joueur = blanc, ligne où se trouvent roi & tour = 0
		if(Joueur == 1) lignePieces = 0;
		else lignePieces = 7; //Sinon ligne 7 pour joueur noir

		if(Jeu[lignePieces][4] instanceof Roi && Jeu[lignePieces][0] instanceof Tour)
		{
			//Les cases entre le roi et la tour sont elles vides ?
			for(int i= 1;i<4;i++)
			{
				if(!(Jeu[lignePieces][i] instanceof CaseVide))
				{
					libres = false;
				}
			}
			if(libres)
			{
				//Verifier si les pieces ennemies peuvent empecher le roque
				for(int i =0;i<Jeu.length;i++)
				{
					for(int j =0;j<Jeu[i].length;j++)
					{
						//Piece ennemie menace le roi, la tour ou une des cases entres les deux
						if((Jeu[i][j].retCoulBlanc() == -Joueur )
								&& (Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][4].PosX-Jeu[i][j].PosX, Jeu[lignePieces][4].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][3].PosX-Jeu[i][j].PosX, Jeu[lignePieces][3].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][2].PosX-Jeu[i][j].PosX, Jeu[lignePieces][2].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][1].PosX-Jeu[i][j].PosX, Jeu[lignePieces][1].PosY-Jeu[i][j].PosY, false)
								|| Jeu[i][j].Bouger(-Joueur, Jeu[lignePieces][0].PosX-Jeu[i][j].PosX, Jeu[lignePieces][0].PosY-Jeu[i][j].PosY, false)))
						{
							System.out.println("Des pieces ennemies empechent de jouer le roque");
							menace = true;
						}	
					}
				}
				if(!menace) 
				{
					//Le roi bouge de 2 cases a gauche
					Bouger(Jeu[lignePieces][4].PosX,Jeu[lignePieces][4].PosY,2,Jeu[lignePieces][4].PosY, true);
					setPos(3,lignePieces,Jeu[lignePieces][0]); //La tour se met à droite du roi
					//Ancienne case de la tour devient vides
					setPos(0,lignePieces, new CaseVide(this, 0, lignePieces));
					succes = true;
				}
			}
			else
				System.out.println("cases non libres entre roi et tour");
		}

		if(succes)
			System.out.println("Grand roque effectue");
		else
			System.out.println("Le grand roque ne peut être fait");
		
		return succes;
	}

	/** Encapsulateur qui permet de mettre des pi�ces ou l'on veux dans le jeu.
	 * @param X Posision en X.
	 * @param Y Position en Y.
	 * @param P La pi�ce � ajouter.
	 */
	public void setPos(int X, int Y, Piece P){
		Jeu[Y][X] = P;
	}		
}