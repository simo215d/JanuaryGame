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

public class BridgeTorch {
    public static boolean isOn;
    public static boolean playerIsNear;
    //r button
    private Texture rTexture = new Texture(Gdx.files.internal("rButton.png"));
    private Sprite rSprite;
    //sprite
    private Texture torchOffTexture = new Texture(Gdx.files.internal("torchOff.png"));
    private Sprite torchOffSprite;
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

    public BridgeTorch(float x, float y){
        //sprite
        torchOffSprite = new Sprite(torchOffTexture,0,0,32,32);
        torchOffSprite.setPosition(x,y);
        torchOffSprite.setSize(16,16);
        rSprite = new Sprite(rTexture,0,0,40,16);
        rSprite.setPosition(torchOffSprite.getX()+10,torchOffSprite.getY()+10);
        rSprite.setSize(10,4);
        //animation
        sheet = new Texture(Gdx.files.internal("torchOn.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.2f, frames);
        stateTime = 0f;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(torchOffSprite.getX()+7, torchOffSprite.getY()+4));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        colliderShape = new PolygonShape();
        colliderShape.setAsBox(5,5);
        fixture = body.createFixture(colliderShape,0.0f);
        fixture.setUserData("FFFFTorchSensor");
        fixture.setSensor(true);
        colliderShape.dispose();
    }

    public void draw(Batch batch){
        if (isOn){
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            //position and scale of frame
            batch.draw(currentFrame, torchOffSprite.getX(), torchOffSprite.getY(),16,16);
        } else {
            torchOffSprite.draw(batch);
        }
        if (!isOn && playerIsNear){
            rSprite.draw(batch);
        }
    }
}
