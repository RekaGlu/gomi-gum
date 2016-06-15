package com.gomigum.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gomigum.game.utils.Constantes;


/**
 * Clase que pinta el fondo del juego. En la version final se encuedra en desuso
 * Created by REKA on 11-Jun-16.
 */
public class FondoEntity extends Actor {

    private ShapeRenderer shaper;

    private TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 180;

    private Color color;

    public FondoEntity(){
        //shaper = new ShapeRenderer();
        //color = Constantes.COLOR_FONDO;
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("fondo.png")));
        textureRegionBounds1 = new Rectangle(0- Constantes.WIDHT_PANTALLA / 2, 0,Constantes.WIDHT_PANTALLA, Constantes.HEIGHT_PANTALLA);
        textureRegionBounds2 = new Rectangle(Constantes.WIDHT_PANTALLA / 2, 0, Constantes.WIDHT_PANTALLA, Constantes.HEIGHT_PANTALLA);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        Color color = new Color(1,1,1,1);
        batch.setColor(color.r, color.g, color.b, color.a);

        batch.draw(textureRegion, textureRegionBounds1.x, textureRegionBounds1.y,Constantes.WIDHT_PANTALLA,
                Constantes.HEIGHT_PANTALLA);
        batch.draw(textureRegion, textureRegionBounds2.x, textureRegionBounds2.y, Constantes.WIDHT_PANTALLA,
                Constantes.HEIGHT_PANTALLA);
    }

    @Override
    public void act(float delta) {

        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }
    private boolean leftBoundsReached(float delta) {

        return (textureRegionBounds2.x - (delta * speed)) <= 0;
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x += delta * speed;
        textureRegionBounds2.x += delta * speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(Constantes.WIDHT_PANTALLA, 0, Constantes.WIDHT_PANTALLA, Constantes.HEIGHT_PANTALLA);
    }

}
