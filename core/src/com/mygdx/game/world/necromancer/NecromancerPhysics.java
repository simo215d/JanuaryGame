package com.mygdx.game.world.necromancer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class NecromancerPhysics {
    //box2d body
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape colliderShape;
    private Fixture fixture;
    //box2d body
    private PolygonShape colliderShape_combat;
    private Fixture fixture_combat;

    public NecromancerPhysics(String name){
        //box2d body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(115, 40));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        colliderShape = new PolygonShape();
        colliderShape.setAsBox(4,10);
        fixture = body.createFixture(colliderShape,10f);
        fixture.setFriction(1f);
        fixture.setUserData(name);
        colliderShape.dispose();
        //box2d combat sensor
        colliderShape_combat = new PolygonShape();
        colliderShape_combat.setAsBox(60,10);
        fixture_combat = body.createFixture(colliderShape_combat,0);
        fixture_combat.setUserData("CombatSensor_"+name);
        fixture_combat.setSensor(true);
        colliderShape_combat.dispose();
    }

    public Body getBody(){
        return body;
    }
}
