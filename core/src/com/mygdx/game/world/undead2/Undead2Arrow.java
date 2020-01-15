package com.mygdx.game.world.undead2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class Undead2Arrow {
    private String name;
    public static int damage = 15;
    private boolean isFlipX;
    //sprite
    private Texture texture = new Texture(Gdx.files.internal("undead2Arrow.png"));
    private Sprite sprite;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape colliderShape;
    private Fixture fixture;

    Undead2Arrow(String name, float x, float y, boolean isFlipX){
        this.name=name;
        this.isFlipX=isFlipX;
        //sprite
        sprite = new Sprite(texture,0,0,8,3);
        sprite.setSize(4,1.5f);
        if (isFlipX) sprite.setFlip(true,false);
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (isFlipX) bodyDef.position.set(x+5,y+2);
        else bodyDef.position.set(x-5,y+2);
        body = darkknight.world.createBody(bodyDef);
        if (isFlipX) body.setLinearVelocity(30,4);
        else body.setLinearVelocity(-30,4);
        body.setGravityScale(1);
        colliderShape = new PolygonShape();
        colliderShape.setAsBox(2,1);
        fixture = body.createFixture(colliderShape,0.0f);
        fixture.setUserData(name);
        fixture.setSensor(true);
        colliderShape.dispose();
    }

    public void draw(Batch batch){
        sprite.setPosition(body.getPosition().x-2.5f,body.getPosition().y);
        if (!isFlipX) sprite.setRotation(-body.getLinearVelocity().y*2);
        else sprite.setRotation(-body.getLinearVelocity().y*-2);
        sprite.draw(batch);
    }

    public Body getBody(){
        return body;
    }

    public String getName(){
        return name;
    }

    public Texture getTexture(){
        return texture;
    }
}
