package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class BoxCollider {
    private BodyDef groundBodyDef;
    private Body groundBody;
    private PolygonShape groundBox;
    private Fixture fixture1;

    public BoxCollider(float x, float y, float width, float height, String userdata){
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x, y));
        groundBody = darkknight.world.createBody(groundBodyDef);
        groundBox = new PolygonShape();
        groundBox.setAsBox(width, height);
        fixture1 = groundBody.createFixture(groundBox, 0.0f);
        fixture1.setFriction(1);
        fixture1.setUserData(userdata);
        groundBox.dispose();
    }
    public BoxCollider(float x, float y, float width, float height, float rotation, String userdata){
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x, y));
        groundBody = darkknight.world.createBody(groundBodyDef);
        groundBox = new PolygonShape();
        //TODO convert the rotation from degrees to radian
        groundBox.setAsBox(width, height,new Vector2(width/2,height/2),rotation);
        fixture1 = groundBody.createFixture(groundBox, 0.0f);
        fixture1.setFriction(1);
        fixture1.setUserData(userdata);
        groundBox.dispose();
    }
}
