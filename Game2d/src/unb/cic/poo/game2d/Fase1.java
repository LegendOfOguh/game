package unb.cic.poo.game2d;

import java.util.LinkedList;

import unb.cic.poo.game2d.scenes.SceneManager;
import unb.cic.poo.game2d.waves.Wave1;

public class Fase1 extends Fase{
	public Fase1(){
		this.waves = new LinkedList<Wave>();
		this.waves.add(new Wave1());
	}	
}
