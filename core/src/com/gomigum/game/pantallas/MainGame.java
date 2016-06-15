package com.gomigum.game.pantallas;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gomigum.game.pantallas.GameOverScreen;
import com.gomigum.game.pantallas.GameScreen;

/*
 * Clase principal de nuestro juego que extiende de Game (ApplicationListener)
 * Created by REKA on 12-Jun-16.
 */
public class MainGame extends Game {
	//cargaremos todos los recursos desde un manager
	private AssetManager manager;

	//Pantallas de nuestro juego:
	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public GameWinScreen gameWinScreen;

	//Iniciamos la variable puntuacion
	private int score=0;

	public AssetManager getManager(){
		return manager;
	}


	@Override
	public void create () {
		//creaos y cargamos todos los recursos de nuestro juego
		manager = new AssetManager();
		manager.load("cuadrado.png",Texture.class);
		manager.load("personaje.png",Texture.class);
		manager.load("floor.png",Texture.class);
		manager.load("acierto.mp3", Sound.class);
		manager.load("fallo.mp3", Sound.class);
		manager.load("Hydrogen.ogg",Music.class);
		manager.load("Paris2.ogg",Music.class);
		manager.load("gameover.png",Texture.class);
		manager.load("grassMid.png",Texture.class);
		manager.load("personaje2.png",Texture.class);
		manager.load("cuadrado3.png",Texture.class);
		manager.load("logo.png",Texture.class);
		manager.load("loose.png", Texture.class);
		manager.load("fondoPal.png",Texture.class);
		manager.load("win.png",Texture.class);
		//manager.finishLoading(); Se llamara en la pantalla de Cargar

		//Arrancamos el juego con la pantalla de cargando
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);


	}

	/**
	 * Metodo que crea todas las pantallas y pone la pantalla del menu.
	 */
	public void finishLoading(){
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		gameWinScreen = new GameWinScreen(this);
		setScreen(menuScreen);
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
