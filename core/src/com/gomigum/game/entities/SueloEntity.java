package com.gomigum.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.gomigum.game.utils.Constantes.PIXEL_RATIO;

/**
 * Suelo del juego
 * Created by REKA on 10-Jun-16.
 */
public class SueloEntity extends Actor {

    //imagen a mostrar
    private Texture texture;

    //En que mundo esta
    private World world;

    //El cuerpo del suelo
    private Body body;

    //Fixture del suelo
    private Fixture fixture;

    /**
     * Se construye el suelo
     * @param world mundo
     * @param texture imagen
     * @param x posicion x (en metros)
     * @param width anchura del suelo en metros
     * @param y posicion y (en metros)
     */
    public SueloEntity(World world, Texture texture, float x, float width, float y) {
        this.world = world;
        this.texture = texture;

        // Se crea el cuerpo y se posiciona
        BodyDef def = new BodyDef();
        def.position.set(x + width / 2, y - 1);  // Centra el suelo en las coordenadas dadas
        // Creamos el cuerpo en el mundo
        body = world.createBody(def);

        // Creamos la fixture
        PolygonShape rectangulo = new PolygonShape();// Creamos una forma
        rectangulo.setAsBox(width / 2, 1); // Rectangulo de ancho entre 2 por 1 metro
        fixture = body.createFixture(rectangulo, 1);//crearemos la fixture
        fixture.setUserData("suelo");//Data fixture dira el tipo de actor que es para despues usarlo en las colisione
        rectangulo.dispose();//cerramos la figura

        // Colocamos al jugador en la pantalla
        setSize(width * PIXEL_RATIO, PIXEL_RATIO);//convertir a pixeles por ese multiplicamos PiXEL_RATIO
        setPosition(x * PIXEL_RATIO, (y -1) * PIXEL_RATIO); //resta -1 para colocarlo en el suelo
        setColor(1,1,1,1);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Renderiza el suelo
        Color color = new Color(1,1,1,1);
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());

    }
    /**
     * Destruye al completo al suelo
     * La fixture y el cuerpo
     */
    public void destruir(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
