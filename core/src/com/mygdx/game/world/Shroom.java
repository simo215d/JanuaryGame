package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.darkknight;

public class Shroom {
    private boolean isBouncing = false;
    //sprite
    private Texture shroomTexture = new Texture(Gdx.files.internal("shroom1.png"));
    private Sprite shroomSprite;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape colliderShape;
    private Fixture fixture;
    //animation
    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    private Animation<TextureRegion> bounceAnimation; // Must declare frame type (TextureRegion)
    private Texture shroomBounceSheet;
    private float stateTime; // A variable for tracking elapsed time for the animation

    public Shroom(){
        //sprite
        shroomSprite = new Sprite(shroomTexture,0,0,32,32);
        shroomSprite.setPosition(20f,5f);
        shroomSprite.setSize(16,16);
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(20, 6));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        //create the box
        Vector2[] verticesCollider = new Vector2[]{new Vector2(7.5f,5f), new Vector2(3f,4.5f), new Vector2(5,7.5f), new Vector2(8,8), new Vector2(11f,7f)};
        colliderShape = new PolygonShape();
        colliderShape.set(verticesCollider);
        fixture = body.createFixture(colliderShape,0.0f);
        fixture.setUserData("FFFShroomCollider1");
        fixture.setSensor(true);
        colliderShape.dispose();
        //animation
        shroomBounceSheet = new Texture(Gdx.files.internal("shroom1sheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(shroomBounceSheet, shroomBounceSheet.getWidth() / FRAME_COLS, shroomBounceSheet.getHeight() / FRAME_ROWS);
        TextureRegion[] bounceFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                bounceFrames[index++] = tmp[i][j];
            }
        }
        bounceAnimation = new Animation<TextureRegion>(0.10f, bounceFrames);
        stateTime = 0f;
    }

    public void setBouncing(boolean bounce){
        isBouncing=bounce;
    }

    public void draw(Batch batch, Sprite sprite){
        if (isBouncing){
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = bounceAnimation.getKeyFrame(stateTime, false);
            //position and scale of frame
            batch.draw(currentFrame, sprite.getX(), sprite.getY(),16,16);
            //if the animation time is over then set the boolean that enabled the animation to false and reset timer to next animation.
            if (stateTime>=0.30f){
                isBouncing=false;
                stateTime=0;
            }
        } else {
            sprite.draw(batch);
        }
    }

    public Sprite getShroomSprite(){
        return shroomSprite;
    }
}
