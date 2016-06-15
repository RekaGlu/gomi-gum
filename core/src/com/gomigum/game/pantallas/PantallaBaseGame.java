package com.gomigum.game.pantallas;

import com.badlogic.gdx.Screen;

/**
 * Pantalla raiz de las que salen todas las demas pantallas
 * Created by REKA on 08-Jun-16.
 */
public class PantallaBaseGame implements Screen{
    //Contiene el main de la aplicación
    public MainGame game;

    public PantallaBaseGame(MainGame game) {
        //conectar la pantalla con el juego principal
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
