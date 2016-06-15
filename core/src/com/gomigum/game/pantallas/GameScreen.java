package com.gomigum.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gomigum.game.utils.Constantes;
import com.gomigum.game.utils.GestorGame;
import com.gomigum.game.entities.GominolaEntity;
import com.gomigum.game.entities.PlayerEntity;
import com.gomigum.game.entities.SueloEntity;
import com.gomigum.game.utils.RandomGenerate;


import java.util.ArrayList;

import static com.gomigum.game.utils.Constantes.*;
import static com.gomigum.game.utils.Constantes.CANTIDAD_GOMINOLAS;
import static com.gomigum.game.utils.Constantes.DISTANCIA_GOMINOLAS;

/**
 * Pantalla del juego
 * Created by REKA on 10-Jun-16.
 */
public class GameScreen extends PantallaBaseGame {

    //Escenario de scene2D
    private Stage stage;

    //Mundo de box2D
    private World world;

    //Actor jugador
    private PlayerEntity player;

    // Actor suelo
    private SueloEntity suelo;

    //Posicion en vector para la camara
    private Vector3 position;

    //Lisener para detectar colisiones
    private JuegoContactListener gcl;
    //clase que nos ayuda con metodos
    private GestorGame controlGame;

    //Array con gominolas
    private ArrayList<GominolaEntity> gominolasli = new ArrayList<GominolaEntity>();

    //Puntuacion del jugador
    private int score = 0;

    //Sonidos para el juego
    private Sound acierto, fallo;

    //Musica de fondo
    private Music musicaDeFondo, musicaLoose;

    //clase que generará n randoms
    private RandomGenerate rnd;

    /**
     * Constructor de la pantalla del juego
     *
     * @param game comunicarlo con la aplicacion del juego
     */
    public GameScreen(MainGame game) {
        super(game);
        rnd = new RandomGenerate();
        // Crea un escenario
        stage = new Stage(new FitViewport(Constantes.WIDHT_PANTALLA, Constantes.HEIGHT_PANTALLA));
        position = new Vector3(stage.getCamera().position);//Pondremos

        // Crea un mundo Box2D
        world = new World(new Vector2(0, -10), true);
        //Crea el lisener para las colisiones en box2D
        gcl = new JuegoContactListener();
        controlGame = new GestorGame();

        //Seleciona los recursos que necesita
        acierto = game.getManager().get("acierto.mp3");
        fallo = game.getManager().get("fallo.mp3");
        musicaDeFondo = game.getManager().get("Paris2.ogg");
        musicaLoose = game.getManager().get("Hydrogen.ogg");

        //incrusta el lisener al mundo
        world.setContactListener(gcl);
    }

    @Override
    public void show() {
        stage.setDebugAll(false);//Si es verdadero dibuja las lineas de los actores
        //Seleciona los recursos
        Texture playerTexture = game.getManager().get("personaje2.png");
        Texture gominolaTexture = game.getManager().get("cuadrado3.png");
        Texture sueloTexture = game.getManager().get("floor.png");

        //Creamos gominolas
        generarGominolas(gominolaTexture);

        //creamos el suelo y lo añadimos al escenario
        suelo = new SueloEntity(world, sueloTexture, 0, 1000, 1);
        stage.addActor(suelo);

        //crear al jugador y colocarlo en el escenario
        player = new PlayerEntity(world, playerTexture, new Vector2(1.5f, 1.5f));
        stage.addActor(player);


        stage.getCamera().position.set(position);//Colomos la camara a la izquierda
        stage.getCamera().update();

        //Modifica el volumen de las canciones de fondo y pone en funcionamiento una de ellas
        musicaLoose.setVolume(0.75f);
        musicaDeFondo.setVolume(0.75f);
        musicaDeFondo.play();
    }


    @Override
    public void hide() {
        //Para la música
        musicaDeFondo.stop();
        musicaLoose.stop();
        //Limpia el escenario
        stage.clear();
        //elimina todos los actores
        for (int i = 0; i < gominolasli.size(); i++) {
            gominolasli.get(i).destruir();

        }
        gominolasli.clear();
        player.destruir();
        suelo.destruir();

        score = 0; //Reiniciar al puntuacion

    }

    /**
     * This method is executed whenever the game requires this screen to be rendered. This will
     * display things on the screen. This method is also used to update the game.
     */

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(COLOR_FONDO.r, COLOR_FONDO.g, COLOR_FONDO.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Se aztualiza el mundo box2D
        world.step(delta, 6, 2);


        //Actualizamos todos los actores
        stage.act();

        //comprabamos se hay colision
        comprobarColision();

        //Si la puntuacion corresponde a la cantidad de gomila es que has ganado el nivel
        comprobarScore();

        //cuando se encuentre en la posX >150 comenzara la carrera
        if (player.getX() > 150 && player.isAlive()) {
            //multiplicamos la velocidad por delta y lo convertimos a pixeles
            float speed = (PLAYER_SPEED * delta * PIXEL_RATIO) - 0.2f;
            //para que el jugador este en la misma posicion la camara tiene que ir a la misma velocidad que el jugador
            stage.getCamera().translate(speed, 0, 0);
            stage.getCamera().update();
        }


        // Pinta la pantalla
        stage.draw();
    }


    /*
        Chekeamos que ha habido colision por parte del jugador y la gominola
         */
    public void comprobarColision(){
        if(gcl.getJugadorColision())        {
            // (1) Creamos la gominola que esta chocando contra nuestro personaje
            GominolaEntity gominolaChocada =
                    controlGame.devuelveGominolaPorID(gominolasli, gcl.getIdGominolaQueChoca());
            // (2) Comprobamos que hemos acertado el color con el personaje y la caja

            if (gominolaChocada != null) {

                if (player.getIdColor() == gominolaChocada.getIdColor()) {
                    // (3) Si acertamos seguimos vivos
                    gominolaChocada.setCoincide(true); //Y cambiamos el estado haciendo que el cuerpo se vuelva invisible
                    score++;// y sumamos el score
                    acierto.play(); //Sonido
                } else {
                    // (4) Si fallamos morimos y
                    player.setAlive(false);//dejamos de movernos

                    game.setScore(score); //Indicamos a la clase principal la puntuacion que ha obtenido para despues recuperarla
                    gcl.setJugadorColision(false);//Salir del bucle

                    musicaDeFondo.stop();
                    fallo.play();
                    //Muestra la pantalla de Game Over
                    pantallaGameOver();

                }

            }
        }
    }
    /**
     * Muestra la pantalla de Game Over
     */
    public void pantallaGameOver(){
        //una secuencia de acciones, al segundo se muestra la pantalla de game over

        stage.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        musicaLoose.play();
                        game.setScreen(game.gameOverScreen);

                    }
                })));
    }

    /**
     *  Cuando la puntuacion corresponde a la cantidad de gomilas ganas la partida
     */
 public void comprobarScore(){
     if (score == CANTIDAD_GOMINOLAS) {
         stage.addAction(
                 Actions.run(new Runnable() {
                     @Override
                     public void run() {
                         game.setScreen(game.gameWinScreen);

                     }
                 }));
     }
 }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    /**
     * Crea las gominolas y las colaca teniendo en cuenta la distancia entre ellas
     * y las añade al escenario
     * @param gominolaTexture
     */
    public void generarGominolas(Texture gominolaTexture) {
        float posX = 10f; //la primera gominola se coloca en esta posición
        for (int i = 0; i < CANTIDAD_GOMINOLAS; i++) {
            gominolasli.add(new GominolaEntity(world, gominolaTexture,
                            new Vector2(posX, 1.5f),
                    rnd.generarNumeroAleatorio(), i));

            posX = rnd.generarFloatAleatorio(DISTANCIA_GOMINOLAS,0.7f)+posX;

        }
        for (int i = 0; i < gominolasli.size(); i++) {
            stage.addActor(gominolasli.get(i));
        }
    }


    /**
     * Clase privado con Lisener que detecta colisiones
     */
    private class JuegoContactListener implements ContactListener {
        //informa si hay colision entre cuerpos
        private boolean jugadorColision = false;

        //variable que contiene la ID de una gominola
        int idGominolaQueChoca =-1;


        @Override
        public void beginContact(Contact contact) {

            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();

            if(fa.getUserData() != null && fa.getUserData().equals("gominola")) {
                if(player.isAlive()){
                    //Id de la gominola que choca contra el personaje
                    idGominolaQueChoca = (Integer) fa.getBody().getUserData();
                    //Se informa que ha colisionado
                    jugadorColision=true;
                }else{

                    jugadorColision=false;
                }
            }
            if(fb.getUserData() != null && fb.getUserData().equals("gominola")) {
                if(player.isAlive()){
                    //Id de la gominola que choca contra el personaje
                    idGominolaQueChoca = (Integer) fb.getBody().getUserData();
                    //Se informa que ha colisionado
                    jugadorColision=true;
                }else{

                    jugadorColision=false;
                }
            }

        }

        @Override
        public void endContact(Contact contact) {
            //cuando termine la colision se marca falso para que se ejecute la accion solo una vez
            //si no lo marcamos se encontraria el estado activado todo le tiempo
            jugadorColision=false;
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }

        /****** Geters and Seters ******/
        public boolean getJugadorColision() {
            return jugadorColision;
        }

        public void setJugadorColision(boolean jugadorColision) {
            this.jugadorColision = jugadorColision;
        }

        public int getIdGominolaQueChoca() {
            return idGominolaQueChoca;
        }

        public void setIdGominolaQueChoca(int idGominolaQueChoca) {
            this.idGominolaQueChoca = idGominolaQueChoca;
        }
    }

}
