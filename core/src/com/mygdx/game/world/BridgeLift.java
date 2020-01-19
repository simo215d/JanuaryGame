package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class BridgeLift {
    public static String liftingState = "still";
    public static boolean playerIsNear = false;
    public static boolean isOn = false;
    private Texture bridge1Texture = new Texture(Gdx.files.internal("bridge1.png"));
    private Sprite bridge1Sprite;
    //lift platform
    private Texture bridgeLiftPlatformTexture = new Texture(Gdx.files.internal("bridgeLiftPlatform.png"));
    private Sprite bridgeLiftPlatformSprite;
    //lever
    private Texture bridgeLiftLeverTexture = new Texture(Gdx.files.internal("bridgeLiftLever.png"));
    private Sprite bridgeLiftLeverSprite;
    //r button
    private Texture rTexture = new Texture(Gdx.files.internal("rButton.png"));
    private Sprite rSprite;
    //lift animation
    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime;
    //box2d sensor
    private BodyDef bodyDef_Sensor;
    private Body body_Sensor;
    private PolygonShape colliderShape_Sensor;
    private Fixture fixture_Sensor;
    //box2d lift collider
    private BodyDef bodyDef_Lift;
    private Body body_Lift;
    private PolygonShape colliderShape_Lift;
    private Fixture fixture_Lift;
    //box2d structure collider
    private BodyDef bodyDef_Structure;
    private Body body_Structure;
    private PolygonShape colliderShape_Structure;
    private Fixture fixture_Structure;
    //box2d bridge collider
    private BodyDef bodyDef_Bridge;
    private Body body_Bridge;
    private PolygonShape colliderShape_Bridge;
    private Fixture fixture_Bridge;

    public BridgeLift(){
        bridge1Sprite = new Sprite(bridge1Texture,0,0,96,64);
        bridge1Sprite.setPosition(260,15f);
        bridge1Sprite.setSize(48,32);
        bridgeLiftPlatformSprite = new Sprite(bridgeLiftPlatformTexture,0,0,20,2);
        bridgeLiftPlatformSprite.setPosition(253,16);
        bridgeLiftPlatformSprite.setSize(10,1);
        bridgeLiftLeverSprite = new Sprite(bridgeLiftLeverTexture,0,0,6,7);
        bridgeLiftLeverSprite.setPosition(279.5f,21);
        bridgeLiftLeverSprite.setSize(3,3.5f);
        rSprite = new Sprite(rTexture,0,0,40,16);
        rSprite.setPosition(272,28.5f);
        rSprite.setSize(10,4);
        //animation
        sheet = new Texture(Gdx.files.internal("bridgeLiftSheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0f;
        //box2d sensor
        bodyDef_Sensor = new BodyDef();
        bodyDef_Sensor.type = BodyDef.BodyType.StaticBody;
        bodyDef_Sensor.position.set(new Vector2(278, 21));
        bodyDef_Sensor.fixedRotation=true;
        body_Sensor = darkknight.world.createBody(bodyDef_Sensor);
        colliderShape_Sensor = new PolygonShape();
        colliderShape_Sensor.setAsBox(4,6);
        fixture_Sensor = body_Sensor.createFixture(colliderShape_Sensor,0.0f);
        fixture_Sensor.setUserData("LiftSensor");
        fixture_Sensor.setSensor(true);
        colliderShape_Sensor.dispose();
        //box2d lift collider
        bodyDef_Lift = new BodyDef();
        bodyDef_Lift.type = BodyDef.BodyType.DynamicBody;
        bodyDef_Lift.position.set(new Vector2(253, 16));
        bodyDef_Lift.fixedRotation=true;
        body_Lift = darkknight.world.createBody(bodyDef_Lift);
        body_Lift.setGravityScale(0);
        colliderShape_Lift = new PolygonShape();
        colliderShape_Lift.setAsBox(5,0.5f);
        fixture_Lift = body_Lift.createFixture(colliderShape_Lift,100);
        fixture_Lift.setUserData("TFFFLiftCollider");
        fixture_Lift.setFriction(0);
        colliderShape_Lift.dispose();
        //box2d structure collider
        bodyDef_Structure = new BodyDef();
        bodyDef_Structure.type = BodyDef.BodyType.StaticBody;
        bodyDef_Structure.position.set(new Vector2(282, 15));
        bodyDef_Structure.fixedRotation=true;
        body_Structure = darkknight.world.createBody(bodyDef_Structure);
        Vector2[] verticesCollider = new Vector2[]{new Vector2(0,0), new Vector2(0,18f), new Vector2(21,18), new Vector2(13,0)};
        colliderShape_Structure = new PolygonShape();
        colliderShape_Structure.set(verticesCollider);
        fixture_Structure = body_Structure.createFixture(colliderShape_Structure,0.0f);
        fixture_Structure.setUserData("TTFFBridgeStructure");
        fixture_Structure.setSensor(false);
        colliderShape_Structure.dispose();
        //box2d lift collider
        bodyDef_Bridge = new BodyDef();
        bodyDef_Bridge.type = BodyDef.BodyType.StaticBody;
        bodyDef_Bridge.position.set(new Vector2(271, 32.5f));
        bodyDef_Bridge.fixedRotation=true;
        body_Bridge = darkknight.world.createBody(bodyDef_Bridge);
        colliderShape_Bridge = new PolygonShape();
        colliderShape_Bridge.setAsBox(11,0.5f);
        fixture_Bridge = body_Bridge.createFixture(colliderShape_Bridge,100);
        fixture_Bridge.setUserData("TFFFBridgeCollider");
        fixture_Bridge.setFriction(0);
        colliderShape_Bridge.dispose();
    }

    public static void turnOn(){
        System.out.println("TURNED ON LIFT");
        isOn=true;
        liftingState="up";
    }

    public void draw(Batch batch) {
        bridge1Sprite.draw(batch);
        if (body_Lift.getPosition().y > 34) {
            liftingState = "down";
            body_Lift.setLinearVelocity(0, -5);
        } else if (body_Lift.getPosition().y <= 16) {
            liftingState = "up";
            body_Lift.setLinearVelocity(0, 5);
        }
        if (!isOn) {
            TextureRegion currentFrameStill = animation.getKeyFrame(stateTime, false);
            batch.draw(currentFrameStill, 245, 15, 16, 32);
            body_Lift.setLinearVelocity(0, 0);
            if (body_Lift.getPosition().x<252.5f){
                body_Lift.setLinearVelocity(2, 0);
            } else if (body_Lift.getPosition().x>253.5f) {
                body_Lift.setLinearVelocity(-2, 0);
            } else body_Lift.setLinearVelocity(0, 0);
        }
        if (isOn && body_Lift.getLinearVelocity().y > 0){
            stateTime += Gdx.graphics.getDeltaTime();
            animation.setPlayMode(Animation.PlayMode.NORMAL);
            TextureRegion currentFrameUp = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrameUp, 245, 15, 16, 32);
            if (body_Lift.getPosition().x<252.5f){
                body_Lift.setLinearVelocity(2, 5);
            } else if (body_Lift.getPosition().x>253.5f) {
                body_Lift.setLinearVelocity(-2, 5);
            } else body_Lift.setLinearVelocity(0, 5);
        }
        if (isOn && body_Lift.getLinearVelocity().y < 0){
            stateTime += Gdx.graphics.getDeltaTime();
            animation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
            TextureRegion currentFrameDown = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrameDown, 245, 15,16,32);
            if (body_Lift.getPosition().x<252.5f){
                body_Lift.setLinearVelocity(2, -5);
            } else if (body_Lift.getPosition().x>253.5f) {
                body_Lift.setLinearVelocity(-2, -5);
            } else body_Lift.setLinearVelocity(0, -5);
        }
        //draw the platform last to make sure it is in front
        bridgeLiftPlatformSprite.setPosition(body_Lift.getPosition().x-5,body_Lift.getPosition().y-0.5f);
        bridgeLiftPlatformSprite.draw(batch);
        if (isOn){
            bridgeLiftLeverSprite.setFlip(false,true);
            bridgeLiftLeverSprite.draw(batch);
        } else {
            bridgeLiftLeverSprite.setFlip(false,false);
            bridgeLiftLeverSprite.draw(batch);
        }
        if (playerIsNear && !isOn){
            rSprite.draw(batch);
        }
    }
}