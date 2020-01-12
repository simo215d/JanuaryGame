package com.mygdx.game.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.darkknight;

public class PlayerPhysics {
    //player collider
    private BodyDef playerBodyDef;
    private Body playerBody;
    private PolygonShape playerBox;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    //player foot sensor
    private PolygonShape playerFootTriangle;
    private FixtureDef fixtureFootDef;
    private Fixture fixtureFoot;
    //player lower left body sensor
    private PolygonShape playerLLTriangle;
    private FixtureDef fixtureLLDef;
    private Fixture fixtureLL;
    //player upper left body sensor
    private PolygonShape playerULTriangle;
    private FixtureDef fixtureULDef;
    private Fixture fixtureUL;
    //player lower right body sensor
    private PolygonShape playerLRTriangle;
    private FixtureDef fixtureLRDef;
    private Fixture fixtureLR;
    //player upper right body sensor
    private PolygonShape playerURTriangle;
    private FixtureDef fixtureURDef;
    private Fixture fixtureUR;
    //player right combat sensor
    private PolygonShape playerRCTriangle;
    private FixtureDef fixtureRCDef;
    private Fixture fixtureRC;
    //player right combat sensor
    private PolygonShape playerLCTriangle;
    private FixtureDef fixtureLCDef;
    private Fixture fixtureLC;


    public PlayerPhysics(){
        //increase players body gravity
        //player collider
        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(14, 30);
        playerBodyDef.fixedRotation=true;
        playerBody = darkknight.world.createBody(playerBodyDef);
        playerBody.setGravityScale(2);
        //create the player box
        Vector2[] verticesPlayerCollider = new Vector2[]{new Vector2(0,-2), new Vector2(-2,-1), new Vector2(-2,10), new Vector2(2,10), new Vector2(2,-1)};
        playerBox = new PolygonShape();
        playerBox.set(verticesPlayerCollider);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;
        fixture = playerBody.createFixture(fixtureDef);
        fixture.setUserData("PlayerBody");
        playerBox.dispose();
        //player foot sensor
        Vector2[] verticesFootCollider = new Vector2[]{new Vector2(0,-2.3f), new Vector2(-1.8f,-1.2f), new Vector2(1.8f,-1.2f)};
        playerFootTriangle = new PolygonShape();
        playerFootTriangle.set(verticesFootCollider);
        fixtureFootDef = new FixtureDef();
        fixtureFootDef.shape = playerFootTriangle;
        fixtureFootDef.isSensor=true;
        fixtureFootDef.density = 1f;
        fixtureFootDef.friction = 0.0f;
        fixtureFootDef.restitution = 0f;
        fixtureFoot = playerBody.createFixture(fixtureFootDef);
        fixtureFoot.setUserData("Foot");
        playerFootTriangle.dispose();
        //player lower left body sensor
        Vector2[] verticesLLCollider = new Vector2[]{new Vector2(-2,-1f), new Vector2(-2.5f,-1f), new Vector2(-2.5f,4f), new Vector2(-2f,4f)};
        playerLLTriangle = new PolygonShape();
        playerLLTriangle.set(verticesLLCollider);
        fixtureLLDef = new FixtureDef();
        fixtureLLDef.shape = playerLLTriangle;
        fixtureLLDef.isSensor=true;
        fixtureLLDef.density = 1f;
        fixtureLLDef.friction = 0.0f;
        fixtureLLDef.restitution = 0f;
        fixtureLL = playerBody.createFixture(fixtureLLDef);
        fixtureLL.setUserData("LowerLeftBody");
        playerLLTriangle.dispose();
        //player upper left body sensor
        Vector2[] verticesULCollider = new Vector2[]{new Vector2(-2,4f), new Vector2(-2.5f,4f), new Vector2(-2.5f,9.5f), new Vector2(-2f,9.5f)};
        playerULTriangle = new PolygonShape();
        playerULTriangle.set(verticesULCollider);
        fixtureULDef = new FixtureDef();
        fixtureULDef.shape = playerULTriangle;
        fixtureULDef.isSensor=true;
        fixtureULDef.density = 1f;
        fixtureULDef.friction = 0.0f;
        fixtureULDef.restitution = 0f;
        fixtureUL = playerBody.createFixture(fixtureULDef);
        fixtureUL.setUserData("UpperLeftBody");
        playerULTriangle.dispose();
        //player lower right body sensor
        Vector2[] verticesLRCollider = new Vector2[]{new Vector2(2,-1f), new Vector2(2.5f,-1f), new Vector2(2.5f,4f), new Vector2(2f,4f)};
        playerLRTriangle = new PolygonShape();
        playerLRTriangle.set(verticesLRCollider);
        fixtureLRDef = new FixtureDef();
        fixtureLRDef.shape = playerLRTriangle;
        fixtureLRDef.isSensor=true;
        fixtureLRDef.density = 1f;
        fixtureLRDef.friction = 0.0f;
        fixtureLRDef.restitution = 0f;
        fixtureLR = playerBody.createFixture(fixtureLRDef);
        fixtureLR.setUserData("LowerRightBody");
        playerLRTriangle.dispose();
        //player upper right body sensor
        Vector2[] verticesURCollider = new Vector2[]{new Vector2(2,4f), new Vector2(2.5f,4f), new Vector2(2.5f,9.5f), new Vector2(2f,9.5f)};
        playerURTriangle = new PolygonShape();
        playerURTriangle.set(verticesURCollider);
        fixtureURDef = new FixtureDef();
        fixtureURDef.shape = playerURTriangle;
        fixtureURDef.isSensor=true;
        fixtureURDef.density = 1f;
        fixtureURDef.friction = 0.0f;
        fixtureURDef.restitution = 0f;
        fixtureUR = playerBody.createFixture(fixtureURDef);
        fixtureUR.setUserData("UpperRightBody");
        playerURTriangle.dispose();
        //player right combat sensor
        Vector2[] verticesRCCollider = new Vector2[]{new Vector2(2,-1), new Vector2(2,10), new Vector2(8,10), new Vector2(8,-1)};
        playerRCTriangle = new PolygonShape();
        playerRCTriangle.set(verticesRCCollider);
        fixtureRCDef = new FixtureDef();
        fixtureRCDef.shape = playerRCTriangle;
        fixtureRCDef.isSensor=true;
        fixtureRCDef.density = 1f;
        fixtureRCDef.friction = 0.0f;
        fixtureRCDef.restitution = 0f;
        fixtureRC = playerBody.createFixture(fixtureRCDef);
        fixtureRC.setUserData("RightCombatSensor");
        playerRCTriangle.dispose();
        //player left combat sensor
        Vector2[] verticesLCCollider = new Vector2[]{new Vector2(-2,-1), new Vector2(-2,10), new Vector2(-8,10), new Vector2(-8,-1)};
        playerLCTriangle = new PolygonShape();
        playerLCTriangle.set(verticesLCCollider);
        fixtureLCDef = new FixtureDef();
        fixtureLCDef.shape = playerLCTriangle;
        fixtureLCDef.isSensor=true;
        fixtureLCDef.density = 1f;
        fixtureLCDef.friction = 0.0f;
        fixtureLCDef.restitution = 0f;
        fixtureLC = playerBody.createFixture(fixtureLCDef);
        fixtureLC.setUserData("LeftCombatSensor");
        playerLCTriangle.dispose();
    }

    public Body getPlayerBody(){
        return playerBody;
    }

    public void fallFaster(float fallMultiplier) {
        if (darkknight.player.getPlayerMovement().getIsAirBorne() && darkknight.player.getPlayerPhysics().getPlayerBody().getLinearVelocity().y<0){
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(darkknight.player.getPlayerPhysics().getPlayerBody().getLinearVelocity().x,darkknight.player.getPlayerPhysics().getPlayerBody().getLinearVelocity().y-fallMultiplier);
        }
    }
}
