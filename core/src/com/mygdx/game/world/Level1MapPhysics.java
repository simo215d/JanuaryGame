package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class Level1MapPhysics {
    //box colliders
    private BoxCollider groundBox1 = new BoxCollider(16,6,16,1,"TFFFGroundBody1");
    private BoxCollider groundBox2 = new BoxCollider(173,14,47*3,1,"TFFFGroundBody2");
    private BoxCollider groundBox3 = new BoxCollider(364,10,60,1,"TFFFGroundBody3");
    private BoxCollider groundBox4 = new BoxCollider(564,14,140,1,"TFFFGroundBody4");
    private BoxCollider wallBox1 = new BoxCollider(33f,10,1,3,"FTFFWallBody1");
    private BoxCollider wallBox2 = new BoxCollider(5,19,1,12,"FTFFWallBody2");
    private BoxCollider wallBox3 = new BoxCollider(669,22,1,12,"FTFFWallBody3");
    //test joy body
    private BodyDef joyBodyDef;
    public static Body joyBody;
    private PolygonShape joyShape;
    private Fixture joyFixture;

    public Level1MapPhysics(){
        //joy testing
        joyBodyDef = new BodyDef();
        joyBodyDef.position.set(new Vector2(4, 45f));
        joyBody = darkknight.world.createBody(joyBodyDef);
        joyBody.setType(BodyDef.BodyType.DynamicBody);
        joyBody.setAngularDamping(1f);
        joyShape = new PolygonShape();
        joyShape.setAsBox(4f, 4f);
        joyFixture = joyBody.createFixture(joyShape, 50.0f);
        joyFixture.setFriction(0.5f);
        joyFixture.setUserData("TTTFJoyBody");
        joyShape.dispose();
    }
}
