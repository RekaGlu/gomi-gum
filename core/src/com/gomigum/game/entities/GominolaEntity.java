package com.gomigum.game.entities;

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

import static com.gomigum.game.utils.Constantes.COLOR_AZUL;
import static com.gomigum.game.utils.Constantes.COLOR_NARANJA;
import static com.gomigum.game.utils.Constantes.PIXEL_RATIO;

/**
 * Created by REKA on 10-Jun-16.
 */
public class GominolaEntity extends Actor {
    //imagen a mostrar
    private Texture texture;

    //En que mundo esta
    private World world;

    //El cuerpo de la gominola
    private Body body;

    //Fixture del jugador
    private Fixture fixture;

    //el color que tendra la gominola
    private int idColor;

    //id única para cada gominola
    private int id;

    //informa si coincide el color del jugador con el de la gominola
    private boolean coincide;

    /**
     * Constructor de la Gominola
     * @param world mundo donde estará
     * @param texture imagen
     * @param posicion vector con x y
     * @param idColor color de la gominola
     * @param id id única
     */
    public GominolaEntity(World world, Texture texture, Vector2 posicion, int idColor, int id) {
        this.world = world;
        this.texture = texture;
        this.idColor =idColor;
        this.id = id;

        // Se crea el cuerpo y se posiciona
        BodyDef def = new BodyDef();
        def.position.set(posicion);
        //Como no se mueve es de tipo estatico
        def.type= BodyDef.BodyType.StaticBody;
        // Creamos el cuerpo en el mundo
        body = world.createBody(def);

        // Creamos la fixture
        PolygonShape jugadorShape = new PolygonShape();// Creamos una forma
        //le atribuimos un caja de metro por metro
        jugadorShape.setAsBox(0.5f,0.5f);//box2d trabaja en metros
        fixture = body.createFixture(jugadorShape,3);//crearemos la ficture

        //generamos dos identificadores
        body.setUserData(id);//Data de body contendrá la id de la gominola
        fixture.setUserData("gominola");//Data fixture dira el tipo de actor que es para despues usarlo en las colisiones
        jugadorShape.dispose();//cerramos la figura

        // Colocamos la gominola en la pantalla
        setSize(PIXEL_RATIO, PIXEL_RATIO);
        setPosition((body.getPosition().x - 0.5f) * PIXEL_RATIO,
                (body.getPosition().y - 0.5f) * PIXEL_RATIO);

        // Pintamos la gominola dependiendo la idColor que tenga
        if(idColor == 1){
            setColor(COLOR_NARANJA);
        }else if(idColor == 0){
            setColor(COLOR_AZUL);
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(texture, getX(), getY(), getWidth()* getScaleX(), getHeight()* getScaleY());
        batch.setColor(color.r, color.g, color.b, 1f);
    }


    @Override
    public void act(float delta) {
        //Si coincide se "borra" de la pantalla
        if(coincide){
            setColor(new Color(1,1,1,0));//se hace invisible
            body.setActive(false);//El body deja de estar activo

        }
    }

    /**
     * Destruye al completo la gominola
     * La fixture y el cuerpo
     */
    public void destruir() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    /******  Getter and Setter ******/
    public boolean isCoincide() {
        return coincide;
    }

    public void setCoincide(boolean coincide) {
        this.coincide = coincide;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
