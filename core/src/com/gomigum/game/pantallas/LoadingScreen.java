package com.gomigum.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Pantalla de carga del principio
 * Created by REKA on 12-Jun-16.
 */
public class LoadingScreen extends PantallaBaseGame {

    //Escenario de scene2d
    private Stage stage;
    //Estilos para los elementos de interfaz
    private Skin skin;
    //Texto
    private Label label;

    /**
     * Constructor de la pantalla cargar
     * @param game comunicarlo con la aplicacion del juego
     */
    public LoadingScreen(MainGame game) {
        super(game);
        stage = new Stage();// crea el escenario
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));//se seleciona la skin

        //se crean los elementos de interfaz
        label = new Label("Cargando...", skin);
       //se coloca en la parte de abajo a la izquierda
        label.setPosition(0 + label.getWidth(), 0 + label.getHeight());
        //se añade el texto
        stage.addActor(label);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Comprueba si manager esta actualizado
        if(game.getManager().update()){
            //cuando esten todos los recursos cargados, se llama al metodo que genera las pantallas
            game.finishLoading();
        }else{
            //cuando se encuentra actualizandose refresca el porcentaje
            generarPorcetanje();
        }

        stage.act();
        stage.draw();
    }

    /**
     * Escribe en un texto el progreso del Manager
     */
    public void generarPorcetanje(){
        int progreso = (int)(game.getManager().getProgress() * 100); //transformarlo para quitar decimales
        label.setText("Cargando: "+progreso+" %");
    }
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
