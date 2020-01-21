package com.mygdx.game.world.allies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class Crow {
    public static boolean isInPosition;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape colliderShape;
    private Fixture fixture;
    //animation
    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime;

    public Crow(){
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(315, 60));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        colliderShape = new PolygonShape();
        colliderShape.setAsBox(8,2);
        fixture = body.createFixture(colliderShape,10f);
        fixture.setUserData("TFFFCrowBody");
        fixture.setFriction(1);
        colliderShape.dispose();
        //animation
        sheet = new Texture(Gdx.files.internal("crowFlapSheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.10f, frames);
        stateTime = 0f;
    }

    public void flap(){
        body.setLinearVelocity(4,5);
        stateTime=0;
    }

    public void draw(Batch batch){
        if (body.getPosition().y<20){
            isInPosition=true;
        }
        //Accumulate elapsed animation time of animation
        stateTime += Gdx.graphics.getDeltaTime();
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        //position and scale of frame
        batch.draw(currentFrame, body.getPosition().x-8, body.getPosition().y-8,16,16);
        if (stateTime>=0.1*FRAME_COLS*FRAME_ROWS && isInPosition && body.getPosition().y<30){
            flap();
        }
    }
}
