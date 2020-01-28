package com.mygdx.game.world.allies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class Guard1 {
    private boolean isTalking;
    private ArrayList<Texture> textures = new ArrayList<>();
    //sprite left
    private Texture texture_L = new Texture(Gdx.files.internal("Guard1_Left.png"));
    private Sprite sprite_L;
    //sprite right
    private Texture texture_R = new Texture(Gdx.files.internal("Guard1_Right.png"));
    private Sprite sprite_R;
    //sprite text
    private Texture texture_T = new Texture(Gdx.files.internal("guard1_Text1.png"));
    private Sprite sprite_T;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;

    public Guard1(){
        textures.add(texture_L);
        textures.add(texture_R);
        textures.add(texture_T);
        //sprite left
        sprite_L = new Sprite(texture_L,0,0,32,32);
        sprite_L.setPosition(44f,15f);
        sprite_L.setSize(16,16);
        //sprite right
        sprite_R = new Sprite(texture_R,0,0,32,32);
        sprite_R.setPosition(44f,15f);
        sprite_R.setSize(16,16);
        //sprite text
        sprite_T = new Sprite(texture_T,0,0,128,70);
        sprite_T.setPosition(36,29);
        sprite_T.setSize(32,17.5f);
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(48, 23));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        sensorShape = new PolygonShape();
        sensorShape.setAsBox(12,8);
        fixture = body.createFixture(sensorShape,0.0f);
        fixture.setUserData("AllyForestGuard");
        fixture.setSensor(true);
        sensorShape.dispose();
    }

    public void draw(Batch batch){
        //if player is to the right of guard, and is within the sensor then face right else left
        if (darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-sprite_R.getX()>=5 && isTalking){
            sprite_R.draw(batch);
        } else sprite_L.draw(batch);
        if (isTalking){
            sprite_T.draw(batch);
        }
    }

    public void setTalking(boolean bool){
        isTalking=bool;
    }

    public ArrayList<Texture> getTextures(){
        return textures;
    }
}
