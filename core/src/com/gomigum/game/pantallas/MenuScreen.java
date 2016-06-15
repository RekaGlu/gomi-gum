package com.gomigum.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gomigum.game.utils.Constantes;

import static com.gomigum.game.utils.Constantes.*;

/**
 * Pantalla menú del juego
 * Created by REKA on 12-Jun-16.
 */
public class MenuScreen extends PantallaBaseGame {

    //Escenario de scene2d
    private Stage stage;
    //Estilos para los elementos de interfaz
    private Skin skin;
    //Musica de inicio
    private Music musicaDeFondo;
    //Imagenes para el fondo
    private Image fondoImagen;
    private Image logo;
    //Boton de jugar
    private TextButton play;
    //Text
    private Label texto;

    /**
     * Constructor de la pantalla menú
     * @param game comunicarlo con la aplicacion del juego
     */
    public MenuScreen(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(WIDHT_PANTALLA, HEIGHT_PANTALLA));// crea el escenario
        //stage = new Stage(new FitViewport(640,360));// crea el escenario
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));//se seleciona la skin

        //se crean los elementos de interfaz
        play = new TextButton("Play",skin);
        texto = new Label("",skin);
        musicaDeFondo = game.getManager().get("Hydrogen.ogg");
        fondoImagen = new Image(game.getManager().get("fondoPal.png", Texture.class));
        logo = new Image(game.getManager().get("logo.png", Texture.class));

        //se añade lisener para los botones de nuestra pantalla
        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);//lleva a la pantalla de jugar
            }
        });

        //Se colocan todos los elementos de interfaz de usuario
        fondoImagen.setPosition(0, 0);
        texto.setPosition(40,40);
        play.setSize(180,80);
        play.setPosition(40,120);
        logo.setPosition(120, 140);

        //Creditos
        texto.setText("Juego realizado por Rebeca Garnacho Navas\nPara el TFG de Desarrollo De Aplicaciones Multiplatorma \n Junio 2016");

        //se añaden los elementos al escenario

        stage.addActor(fondoImagen);
        stage.addActor(logo);
        stage.addActor(play);
        stage.addActor(texto);
        //volumen mas bajito para el menu
        musicaDeFondo.setVolume(0.6f);
    }

    @Override
    public void show() {
        /*Los stage son InputProcessor  (heredan de inputAdaper)
        Esta llamada procesa todos los eventos que ocurren en el escenario,
        cuando hacemos click en todos nuestros botones*/
        Gdx.input.setInputProcessor(stage);
        musicaDeFondo.play();

    }

    @Override
    public void hide() {
        musicaDeFondo.stop();
        //necesario establecer el InputProcessor vacio para que no se solapen con otras pantallas
        // y tengamos botones fantasma en la pantalla
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(COLOR_FONDO.r, COLOR_FONDO.g, COLOR_FONDO.b, COLOR_FONDO.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
