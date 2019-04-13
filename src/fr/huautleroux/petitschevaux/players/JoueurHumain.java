package fr.huautleroux.petitschevaux.players;

public class JoueurHumain extends Joueur{

	JoueurHumain(String n, Couleur c){
		super(n, c);
	}
	
	@Override
	public abstract Pion choisirPion();

}
