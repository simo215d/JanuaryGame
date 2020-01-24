package com.mygdx.game.world.necromancer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class BloodMeteor {
    private String name = "";
    private boolean isExploding;
    private boolean shouldBeDeleted;
    private float necromancerX=0;
    public static final int damage = 45;
    private float deathX;
    private float deathY;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;
    //animation fly
    private int FRAME_COLS = 11, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime;
    //animation explosion
    private int FRAME_COLS_EX = 4, FRAME_ROWS_EX = 1;
    private Animation<TextureRegion> animation_EX;
    private Texture sheet_EX;
    private float stateTime_EX;

    public BloodMeteor(String name, float x, float y){
        this.name=name;
        necromancerX=x;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x-5, y+2);
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        body.setGravityScale(4);
        sensorShape = new PolygonShape();
        sensorShape.setAsBox(2,2);
        fixture = body.createFixture(sensorShape,0);
        fixture.setUserData("BloodMeteor");
        fixture.setSensor(true);
        body.setLinearVelocity(-25,30);
        sensorShape.dispose();
        //animation fly
        sheet = new Texture(Gdx.files.internal("bloodMeteorFlySheet.png"));
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
        //animation EX
        sheet_EX = new Texture(Gdx.files.internal("bloodMeteorExplodeSheet.png"));
        TextureRegion[][] tmp_EX = TextureRegion.split(sheet_EX, sheet_EX.getWidth() / FRAME_COLS_EX, sheet_EX.getHeight() / FRAME_ROWS_EX);
        TextureRegion[] frames_EX = new TextureRegion[FRAME_COLS_EX * FRAME_ROWS_EX];
        int index_EX = 0;
        for (int i = 0; i < FRAME_ROWS_EX; i++) {
            for (int j = 0; j < FRAME_COLS_EX; j++) {
                frames_EX[index_EX++] = tmp_EX[i][j];
            }
        }
        animation_EX = new Animation<TextureRegion>(0.1f, frames_EX);
        stateTime_EX = 0f;
    }

    public void draw(Batch batch, NecromancerActions actions){
        if (!isExploding){
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, body.getPosition().x-8, body.getPosition().y-8,16,16);
        } else {
            stateTime_EX += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame_EX = animation_EX.getKeyFrame(stateTime_EX, false);
            batch.draw(currentFrame_EX, deathX-8, deathY-8,16,16);
            if (stateTime_EX>=0.1*4){
                actions.setBloodMeteorToNull();
            }
        }
    }

    public void setExploding(boolean b){
        stateTime_EX=0;
        deathX=body.getPosition().x;
        deathY=body.getPosition().y;
        isExploding=b;
    }

    public boolean isExploding(){
        return isExploding;
    }

    public Body getBody(){
        return body;
    }
}
