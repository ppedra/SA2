package game.tiles;

import game.GameObject;
import game.GameTags;
import game.TrueHero;
import game.interfaces.Collidable;
import game.interfaces.Interactable;

import java.awt.Rectangle;

public class Spike extends GameObject implements Interactable{

	public Spike(int posX, int posY) {
		super("spike.png", posX, posY, 1, 1);
	}


	@Override
	public Rectangle getRectangle() {
		return new Rectangle(getPosX(), getPosY()+getHeight()/2, getWidth(), getHeight()/2);
	}


	@Override
	public void actionEnter(GameObject gameobj) {
		if(game.Hero.class.isInstance(gameobj)){
			game.Hero hero = (game.Hero) gameobj;
			hero.takeDamage();
			//System.out.println("Collision ENTER");
		}		
		
		if(game.TrueHero.class.isInstance(gameobj)){
			game.TrueHero hero = (game.TrueHero) gameobj;
			hero.takeDamage();
			hero.halt();
		}
		
	}
	
	@Override
	public void actionStay(GameObject gameobj) {
		
	}


	@Override
	public void actionExit(GameObject gameobj) {
		
	}


	
	
	
	
}
