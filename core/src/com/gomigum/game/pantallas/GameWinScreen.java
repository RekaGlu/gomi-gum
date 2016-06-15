package com.gomigum.game.pantallas;

import com.badlogic.gdx.Gdx;
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

import static com.gomigum.game.utils.Constantes.COLOR_FONDO;
import static com.gomigum.game.utils.Constantes.HEIGHT_PANTALLA;
import static com.gomigum.game.utils.Constantes.WIDHT_PANTALLA;

/**
 * Pantalla que salta cuando ganas la partida
 * Created by REKA on 15-Jun-16.
 */
public class GameWinScreen extends PantallaBaseGame {

    //Escenario de scene2d
    private Stage stage;
    //Estilos para los elementos de interfaz
    private Skin skin;
    //Imagen para mensaje Win
    private Image winImage;
    //Botones
    private TextButton retry,menu;
    //Texto
    private Label score;

    /**
     * Constructor de la pantalla ganar
     * @param game comunicarlo con la aplicacion del juego
     */
    public GameWinScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(Constantes.WIDHT_PANTALLA, Constantes.HEIGHT_PANTALLA));// crea el escenario

        skin = new Skin(Gdx.files.internal("skin/uiskin.json")); //se seleciona la skin

        //se crean los elementos de interfaz
        winImage = new Image(game.getManager().get("win.png", Texture.class));
        retry = new TextButton("Retry", skin);
        menu = new TextButton("Menu",skin);
        score = new Label("Score ",skin);

        //se añade lisener para los botones de nuestra pantalla
        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);//vuelve a la pantalla de jugar
            }
        });
        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);//vuelve al menu
            }
        });

        //Se posicionan los elementos
        retry.setSize(150,80);
        menu.setSize(150,80);

        winImage.setPosition((WIDHT_PANTALLA - winImage.getWidth())/2, HEIGHT_PANTALLA - winImage.getHeight());
        score.setPosition((WIDHT_PANTALLA - retry.getWidth())/2,190);
        retry.setPosition((WIDHT_PANTALLA - retry.getWidth())/2,90);
        menu.setPosition((WIDHT_PANTALLA - menu.getWidth())/2,5);




        //se añaden los elementos al escenario
        stage.addActor(winImage);
        stage.addActor(score);
        stage.addActor(retry);
        stage.addActor(menu);
    }

    @Override
    public void show() {
        /*Los stage son InputProcessor  (heredan de inputAdaper)
        Esta llamada procesa todos los eventos que ocurren en el escenario,
        cuando hacemos click en todos nuestros botones*/
        Gdx.input.setInputProcessor(stage);


        score.setText("Level superado");
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void hide() {
        //necesario establecer el InputProcessor vacio para que no se solapen con otras pantallas
        // y tengamos botones fantasma en la pantalla
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        // Do not forget to clean the screen.
        Gdx.gl.glClearColor(COLOR_FONDO.r,COLOR_FONDO.g,COLOR_FONDO.b,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
