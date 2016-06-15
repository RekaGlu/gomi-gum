package com.gomigum.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gomigum.game.utils.Constantes;

import static com.gomigum.game.utils.Constantes.COLOR_AZUL;
import static com.gomigum.game.utils.Constantes.PIXEL_RATIO;
import static com.gomigum.game.utils.Constantes.PLAYER_SPEED;

/**
 * Actor del jugador
 * Created by REKA on 10-Jun-16.
 */
public class PlayerEntity extends Actor  {
    //imagen a mostrar
    private Texture texture;

    //En que mundo esta
    private World world;

    //El cuerpo del jugador
    private Body body;

    //Fixture del jugador
    private Fixture fixture;

    //el color que el que empieza
    private int idColor=0;//Empieza con el color azul

    //Bandera para cambiar el color
    private Boolean colorActual=false;

    //puntuacion del personaje
    private int score=0;

    //Informa si el personaje se encuentra con vida
    private boolean alive = true;

    /**
     * Crea un jugador
     * @param world mundo
     * @param texture imagen
     * @param position vector con x y
     */
    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        // Se crea el cuerpo y se posiciona
        BodyDef def = new BodyDef();
        def.position.set(position);
        //Como se mueve es de tipo dinamico
        def.type = BodyDef.BodyType.DynamicBody;
        // Creamos el cuerpo en el mundo
        body = world.createBody(def);

        // Creamos la fixture
        PolygonShape cuadrado = new PolygonShape();// Creamos una forma
        //le atribuimos un caja de metro por metro
        cuadrado.setAsBox(0.5f, 0.5f);//box2d trabaja en metros
        fixture = body.createFixture(cuadrado, 3);//crearemos la fixture
        fixture.setUserData("player");//Data fixture dira el tipo de actor que es para despues usarlo en las colisiones
        cuadrado.dispose();//cerramos la figura

        // Colocamos al jugador en la pantalla
        setSize(PIXEL_RATIO, PIXEL_RATIO);
        setColor(COLOR_AZUL);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXEL_RATIO,
                (body.getPosition().y - 0.5f) * PIXEL_RATIO); //al ser un objeto que se mueve tenemos que repintarlo
                                                                //cada vez para poder ver su movimiento!!
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(texture, getX(), getY(), getWidth()* getScaleX(), getHeight()* getScaleY());

    }
    @Override
    public void act(float delta) {

        //Si el jugador esta vivo se mueve y puede cambiar el color
        if (alive) {
            // Cambia el color cuando el usuario toca la pantalla
            if (Gdx.input.justTouched()) {
            cambiarColor();
            }
            // Si el jugador esta vivo, se incrementa la velocidad
            // Solo cambiamos la velocidad de la X por que no nos movemos verticalmente
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(PLAYER_SPEED, speedY);
        }

    }

    /**
     * Pinta nuestro persona de color naranja o azul
     * y marca en cada color una id entre 0 y 1
     */
    public void cambiarColor(){
        if(colorActual){
            setColor(Constantes.COLOR_AZUL);
            idColor=0;//cambia el estado a azul
            colorActual=false;
        }else{
            setColor(Constantes.COLOR_NARANJA);
            idColor=1;//cambia el estado a naranja
            colorActual=true;
        }
    }
    /**
     * Destruye al completo al jugador
     * La fixture y el cuerpo
     */
    public void destruir() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }


    /******  Getter and Setter ******/
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }
}
