package ca.uqac.pat.echec;

import ca.uqac.pat.echec.Plateau;
import ca.uqac.pat.echec.ai.COrdi;

/**
  * Cette classe n'a qu'un main qui permet de tester la classe Plateau.
  */
public class Echec{
	public static void main (String Arg[]){
		Plateau P = new Plateau();		
		COrdi Ordi = new COrdi (P);

		while (true){
			char roque = '\0';
			int typeRoque= 0;
			char initialX = '\0';
			char initialY = '\0';

			char finalX = '\0';
			char finalY = '\0';
			choixCoup :do{
				P.Afficher();
				System.out.print ("Votre coup? (R ou r pour un roque)");	
				roque = Lire();
				
				if(roque == 'R' || roque =='r')
				{
					ViderBuffer();
					boolean roqueOk = false;
					/*choixRoque :*/while(!roqueOk)
					{
						System.out.println("Quel type de roque voulez vous faire      ?");
						System.out.println("1 : Petit roque legal");
						System.out.println("2 : Petit roque triche");
						System.out.println("3 : Grand roque");
						char type = Lire();
						typeRoque = Character.getNumericValue(type);
						
						/**
						 * FIXME Le petit roque legal/grand roque ne fonctionne pas
						 * de maniere de normale quand un pion menace une case vide 
						 * entre le roi et la tour
						 */
						switch(typeRoque)
						{
						case 1:
							if(P.jouerPetitRoqueLegal()) roqueOk = true; break;
						case 2:
							if(P.jouerPetitRoqueTriche()) roqueOk = true; break;
						case 3:
							if(P.jouerGrandRoque()) roqueOk = true; break;
						default: 
							System.out.println("Incorrect !"); break;
						}
						ViderBuffer();
						
						if(!roqueOk)
							continue choixCoup; //retourner au while principal après le coup
						
						else
							break choixCoup;
					}
				}
				
				else
				{
					initialX = roque; //1er char != 'R'
					initialY = Lire();

					finalX = Lire();
					finalY = Lire();	
				}
				ViderBuffer();
			}

			while(!P.Bouger(	initialX-'a', 	initialY-'1', 
								finalX - 'a', 	finalY-'1', true));
			Ordi.Bouger();				
		}
	}

	private static char Lire(){		//Static parqu'utilis� par le main
		char C = 'A';				//Initialisation est obligatoire
		boolean OK;
		do{
			OK = true;				//On commence � OK � chaque boucle
			try{					//Pr�viens qu'il peut y avoir une exception
				C = (char)System.in.read();
			}

			catch (java.io.IOException e){		//Attrape l'exception
			
				OK = false;	//S'il y a une erreur, possible boucle sans fin
			}
		}
		while (!OK);			//S'il y a eu une erreur,on recommence
		return C;				//On retourne la lettre 
	}
	
	
	private static void ViderBuffer(){
		try{
			while (System.in.read() != '\n');
		}
		catch (java.io.IOException e){		//Attrape l'exception
			//S'ex�cute s'il y a une erreur Rien
		}
	}
}