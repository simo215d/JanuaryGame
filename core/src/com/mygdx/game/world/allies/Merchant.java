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

public class Merchant {
    public static boolean playerIsNear;
    //r button
    private Texture rTexture = new Texture(Gdx.files.internal("rButton.png"));
    private Sprite rSprite;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape colliderShape;
    private Fixture fixture;
    //graphic
    private Texture potionWagonTexture = new Texture(Gdx.files.internal("potionWagon.png"));
    private Sprite potionWagonSprite;

    public Merchant(){
        playerIsNear=false;
        //graphic
        potionWagonSprite = new Sprite(potionWagonTexture,0,0,32,32);
        potionWagonSprite.setPosition(475,15f);
        potionWagonSprite.setSize(16,16);
        rSprite = new Sprite(rTexture,0,0,40,16);
        rSprite.setPosition(potionWagonSprite.getX()+10,potionWagonSprite.getY()+15);
        rSprite.setSize(10,4);
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(potionWagonSprite.getX()+8, potionWagonSprite.getY()+5));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        colliderShape = new PolygonShape();
        colliderShape.setAsBox(8,5);
        fixture = body.createFixture(colliderShape,0.0f);
        fixture.setUserData("FFFFPotionWagonSensor");
        fixture.setSensor(true);
        colliderShape.dispose();
    }

    public void healPlayer(){
        if (playerIsNear)
        darkknight.player.getPlayerCombat().fullHeal();
    }

    public void draw(Batch batch){
        potionWagonSprite.draw(batch);
        if (playerIsNear && darkknight.player.getPlayerCombat().getHealth()<darkknight.player.getPlayerCombat().getMaxHealth()) rSprite.draw(batch);
    }
}
