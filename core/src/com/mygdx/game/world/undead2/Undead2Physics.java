package com.mygdx.game.world.undead2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.darkknight;

public class Undead2Physics {
    //physical collider
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape bodyShape;
    private Fixture bodyFixture;
    //enter combat sensor
    private PolygonShape sensorShapeCombat;
    private FixtureDef fixtureDefCombat;
    private Fixture fixtureCombat;

    public Undead2Physics(String userdata, float x, float y){
        //physical collider
        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        body = darkknight.world.createBody(bodyDef);
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setFixedRotation(true);
        bodyShape = new PolygonShape();
        bodyShape.setAsBox(1.5f, 7f);
        bodyFixture = body.createFixture(bodyShape, 50.0f);
        bodyFixture.setFriction(0.5f);
        bodyFixture.setUserData(userdata);
        bodyShape.dispose();
        //enter combat sensor
        sensorShapeCombat = new PolygonShape();
        sensorShapeCombat.setAsBox(30,10);
        fixtureDefCombat = new FixtureDef();
        fixtureDefCombat.shape = sensorShapeCombat;
        fixtureDefCombat.isSensor=true;
        fixtureCombat = body.createFixture(fixtureDefCombat);
        fixtureCombat.setUserData("CombatSensor_"+userdata);
        sensorShapeCombat.dispose();
    }

    public Body getBody(){
        return body;
    }
}
