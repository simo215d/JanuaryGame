package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class DamageObject {
    private boolean flipX;
    private boolean shouldBeRendered;
    private ArrayList<Long> usedTimes = new ArrayList<>();
    private int damage;
    private int modFactor;
    private String name = "";
    private boolean isActive = false;
    private float positionX;
    private float positionY;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;
    //animation
    private int FRAME_COLS, FRAME_ROWS;
    private Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    private Texture sheet;
    private float stateTime; // A variable for tracking elapsed time for the animation
    private float frameDuration;

    public DamageObject(String name, int damage, int modFactor, String textureURL, int textureColumns, int textureRows, float frameDuration, float positionX, float positionY, float boxWidth, float boxHeight, boolean flipX, boolean shouldBeRendered){
        this.shouldBeRendered=shouldBeRendered;
        this.flipX=flipX;
        usedTimes.add(0L);
        this.damage=damage;
        this.modFactor=modFactor;
        this.name=name;
        //animation
        this.FRAME_COLS=textureColumns;
        this.FRAME_ROWS=textureRows;
        this.positionX=positionX;
        this.positionY=positionY;
        sheet = new Texture(Gdx.files.internal(textureURL));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(frameDuration, animationFrames);
        stateTime = 0f;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        float frameWidth = sheet.getWidth()/(float)FRAME_COLS;
        bodyDef.position.set(positionX+frameWidth/4, positionY+boxHeight*2);
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        sensorShape = new PolygonShape();
        sensorShape.setAsBox(boxWidth,boxHeight);
        fixture = body.createFixture(sensorShape,0);
        fixture.setUserData(name);
        fixture.setSensor(true);
        sensorShape.dispose();
    }

    public void draw(Batch batch){
        //if 1 second has passed damage the player
        if (isActive && darkknight.gameTimeCentiSeconds%modFactor==0){
            //we render every frame, so we check every frame, but not every frame comes with a new number,
            //sometimes the same number gets through so we check if the number has already been used using array list
            if (darkknight.gameTimeCentiSeconds!=usedTimes.get(usedTimes.size()-1)){
                darkknight.player.getPlayerCombat().takeDamage(damage);
                usedTimes.add(darkknight.gameTimeCentiSeconds);
            }
        }
        //Accumulate elapsed animation time of animation
        stateTime += Gdx.graphics.getDeltaTime();
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        if (flipX && !currentFrame.isFlipX()){
            currentFrame.flip(true,false);
        }
        //position and scale of frame
        if (shouldBeRendered)
        batch.draw(currentFrame, positionX, positionY, (float) sheet.getWidth()/FRAME_COLS/2,(float) sheet.getHeight()/FRAME_ROWS/2);
    }

    public void setActive(boolean bool){
        isActive=bool;
        if (bool==true){
        } else {
            usedTimes.clear();
            usedTimes.add(0L);
        }
    }

    public String getName(){
        return name;
    }
}
