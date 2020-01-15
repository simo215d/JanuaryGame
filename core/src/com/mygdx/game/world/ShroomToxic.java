package com.mygdx.game.world;

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

public class ShroomToxic {
    private String name;
    public static int damage = 5;
    private boolean isExploding = false;
    //animation idle
    private static final int FRAME_COLS_idle = 2, FRAME_ROWS_idle = 1;
    private Animation<TextureRegion> animation_idle;
    private Texture sheet_idle;
    private float stateTime_idle;
    private float frameDuration_idle=1f;
    //animation idle
    private static final int FRAME_COLS_ex = 6, FRAME_ROWS_ex = 1;
    private Animation<TextureRegion> animation_ex;
    private Texture sheet_ex;
    private float stateTime_ex;
    private float frameDuration_ex=0.3f;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape colliderShape;
    private Fixture fixture;

    public ShroomToxic(String name, float x, float y){
        this.name=name;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        colliderShape = new PolygonShape();
        colliderShape.setAsBox(1.5f,1.5f);
        fixture = body.createFixture(colliderShape,0.0f);
        fixture.setUserData(name);
        fixture.setSensor(true);
        colliderShape.dispose();
        //animation
        sheet_idle = new Texture(Gdx.files.internal("shroom2IdleSheet.png"));
        TextureRegion[][] tmp_idle = TextureRegion.split(sheet_idle, sheet_idle.getWidth() / FRAME_COLS_idle, sheet_idle.getHeight() / FRAME_ROWS_idle);
        TextureRegion[] frames_idle = new TextureRegion[FRAME_COLS_idle * FRAME_ROWS_idle];
        int index_idle = 0;
        for (int i = 0; i < FRAME_ROWS_idle; i++) {
            for (int j = 0; j < FRAME_COLS_idle; j++) {
                frames_idle[index_idle++] = tmp_idle[i][j];
            }
        }
        animation_idle = new Animation<TextureRegion>(frameDuration_idle, frames_idle);
        stateTime_idle = 0f;
        //animation
        sheet_ex = new Texture(Gdx.files.internal("shroom2ExplodeSheet.png"));
        TextureRegion[][] tmp_ex = TextureRegion.split(sheet_ex, sheet_ex.getWidth() / FRAME_COLS_ex, sheet_ex.getHeight() / FRAME_ROWS_ex);
        TextureRegion[] frames_ex = new TextureRegion[FRAME_COLS_ex * FRAME_ROWS_ex];
        int index_ex = 0;
        for (int i = 0; i < FRAME_ROWS_ex; i++) {
            for (int j = 0; j < FRAME_COLS_ex; j++) {
                frames_ex[index_ex++] = tmp_ex[i][j];
            }
        }
        animation_ex = new Animation<TextureRegion>(frameDuration_ex, frames_ex);
        stateTime_ex = 0f;
    }

    public void draw(Batch batch){
        if (!isExploding) {
            stateTime_idle += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame_idle = animation_idle.getKeyFrame(stateTime_idle, true);
            //position and scale of frame
            batch.draw(currentFrame_idle, body.getPosition().x-8, body.getPosition().y-2, 16, 16);
        } else {
            stateTime_ex += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame_ex = animation_ex.getKeyFrame(stateTime_ex, false);
            //position and scale of frame
            batch.draw(currentFrame_ex, body.getPosition().x-8, body.getPosition().y-2, 16, 16);
            if (stateTime_ex>=frameDuration_ex*FRAME_COLS_ex*FRAME_ROWS_ex){
                isExploding=false;
                stateTime_ex=0;
            }
        }
    }

    public String getName(){
        return name;
    }

    public boolean isExploding(){
        return isExploding;
    }

    public void setExploding(boolean b){
        isExploding=b;
    }
}
