package game.scenes;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.transform.sax.TemplatesHandler;

import br.senai.sc.engine.Utils;
import game.GameStates;
import game.JetpackGame;
import game.Missile;
import game.Saw;
import game.TrueHero;
import game.tiles.BlackHole;
import game.tiles.Coletable;
import game.tiles.CompleteLevel;
import game.tiles.EndLevel;
import game.tiles.GravityDown;
import game.tiles.GravityUp;
import game.tiles.Spike;
import game.tiles.Stone;

public class Gameplay extends Scene {

	private int TILE_SIZE = 32;
	private int COLS = 40;
	private int ROWS = 20;
	private int[][] tileMapMatrix;
	private String tmxFile;
	Ui ui;

	public Gameplay(String tmxFile) {

		// getObjsInScene().add(new BlackHole(300, 100));

		LevelManager.getInstance().setColletableInScene(0);
		this.tmxFile = tmxFile;
		tileMapMatrix = new int[ROWS][COLS];
		lerArquivo();

		try {
			// qualquer coisa pra ver se ela existe
			ui.getClass();
		} catch (Exception e) {
			System.out.println("a fase nao tem heroi!");
			throw new NullPointerException();
		}

		
		// cria barreira fora do mapa...
		makeBoundaries();
	}

	private void lerArquivo() {
		try { // try ler arquivo tmx
			File file = new File("src/levels/" + tmxFile + ".tmx");
			System.out.println("src/levels/" + tmxFile + ".tmx 100%");

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String linha = br.readLine();

			int rowCount = 0;
			while (linha != null && !linha.isEmpty()) {
				String[] split = linha.split(",");
				// System.out.println(split.length);
				for (int i = 0; i < split.length; i++) {
					tileMapMatrix[rowCount][i] = Integer.parseInt(split[i]);
				}
				rowCount++;
				linha = br.readLine();
			}
			br.close();
			fr.close();
			
			for (int r = 0; r < tileMapMatrix.length; r++) {
				for (int c = 0; c < tileMapMatrix[r].length; c++) {
					if(tileMapMatrix[r][c] == 3){
						TrueHero tempHero = new TrueHero(c * TILE_SIZE, r * TILE_SIZE, this);
						tempHero.setScale(0.5f);
						getObjsInScene().add(tempHero);
						ui = new Ui(tempHero);
					}
				}
			}

			
			
			// instanciar objetos pelo tile
			for (int r = 0; r < tileMapMatrix.length; r++) {
				for (int c = 0; c < tileMapMatrix[r].length; c++) {
					// System.out.print(tileMapMatrix[r][c] + " ");
					int code = tileMapMatrix[r][c];

					switch (code) {
					case 1:
						getObjsInScene().add(new Spike(c * TILE_SIZE, r * TILE_SIZE));
						break;
					case 2:
						getObjsInScene().add(new Spike(c * TILE_SIZE, r * TILE_SIZE, (float) Math.PI));
						break;
					case 3:
						/*TrueHero tempHero = new TrueHero(c * TILE_SIZE, r * TILE_SIZE, this);
						tempHero.setScale(0.5f);
						getObjsInScene().add(tempHero);
						ui = new Ui(tempHero);*/
						break;
					case 4:
						getObjsInScene().add(new BlackHole(c * TILE_SIZE, r * TILE_SIZE));
						break;
					case 5:
						getObjsInScene().add(new Coletable(c * TILE_SIZE, r * TILE_SIZE));
						LevelManager.getInstance().addColletableInScene();
						break;

					case 6:
						getObjsInScene().add(new Stone(c * TILE_SIZE, r * TILE_SIZE, false));
						break;

					case 7:
						getObjsInScene().add(new GravityUp(c * TILE_SIZE, r * TILE_SIZE));
						break;
					case 8:
						getObjsInScene().add(new GravityDown(c * TILE_SIZE, r * TILE_SIZE));
						break;

					case 9:
						getObjsInScene().add(new CompleteLevel(c * TILE_SIZE, r * TILE_SIZE));
						break;

					case 10:
						getObjsInScene().add(new Stone(c * TILE_SIZE, r * TILE_SIZE, true));
						break;
						
					case 11:
						getObjsInScene().add(new Spike(c * TILE_SIZE, r * TILE_SIZE, (float) Math.PI / 2));
						break;
						
					case 12:
						getObjsInScene().add(new Spike(c * TILE_SIZE, r * TILE_SIZE, (float) Math.PI * 3 / 2));
						break;
						
					case 13:
						getObjsInScene().add(new EndLevel(c * TILE_SIZE, r * TILE_SIZE));
						break;
						
					case 14:
						getObjsInScene().add(new Saw(c * TILE_SIZE, r * TILE_SIZE, 5, 5));
						break;
						
					case 15:
						getObjsInScene().add(new Missile(c * TILE_SIZE, r * TILE_SIZE, 0, 5, Math.PI));
						break;
						
					case 16:
						getObjsInScene().add(new Missile(c * TILE_SIZE, r * TILE_SIZE, 5, 0, Math.PI / 2));
						break;
						
					default:
						break;
					}
				}
			}
			

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		if (JetpackGame.currentGameState == GameStates.Loading) {

			JetpackGame.currentGameState = GameStates.Gameplay;
			System.out.println("Carregou novo level!");
		}

	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		ui.draw(g);

	}

	private void makeBoundaries() {
		int[] x = new int[] {-1, 40};
		int[] y = new int[] {-1, 20};

		//horizontal
		for (int j : y) {
			for (int i = x[0]; i<=x[1]; i++) {
				getObjsInScene().add(new Stone(i * 32, j * 32, false));
			}
		}
		
		//Vertical
		for (int i : x) {
			for (int j = y[0]; j<=y[1]; j++) {
				getObjsInScene().add(new Stone(i * 32, j * 32, false));
			}
		}
	}

}
