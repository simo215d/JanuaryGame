package com.mygdx.game.world.targetdummy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class TargetDummyPhysics {
    //body stuff
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape bodyShape;
    private Fixture bodyFixture;

    public TargetDummyPhysics(String userdata, float x, float y){
        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        body = darkknight.world.createBody(bodyDef);
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setFixedRotation(true);
        bodyShape = new PolygonShape();
        bodyShape.setAsBox(1.25f, 6f);
        bodyFixture = body.createFixture(bodyShape, 50.0f);
        bodyFixture.setFriction(0.5f);
        bodyFixture.setUserData(userdata);
        bodyShape.dispose();
    }

    public Body getBody(){
        return body;
    }
}
