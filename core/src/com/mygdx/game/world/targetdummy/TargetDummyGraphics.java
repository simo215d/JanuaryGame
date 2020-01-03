package com.mygdx.game.world.targetdummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.darkknight;

public class TargetDummyGraphics{
    //actions TODO this should maybe be in a separate class later
    public boolean isAttacked = false;
    //body sprite stuff
    private Texture targetdummyTexture;
    private Sprite targetdummySprite;
    //animation stuff
    private static final int FRAME_COLS = 2, FRAME_ROWS = 1;
    private Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    private Texture walkSheet;
    private float stateTime; // A variable for tracking elapsed time for the animation

    public TargetDummyGraphics(){
        //sprite
        targetdummyTexture = new Texture(Gdx.files.internal("targetdummy.png"));
        targetdummySprite = new Sprite(targetdummyTexture,0,0,16,32);
        targetdummySprite.setSize(8,16);
        //animation
        walkSheet = new Texture(Gdx.files.internal("targetdummysheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation<TextureRegion>(0.15f, walkFrames);
        stateTime = 0f;
    }

    public void draw(Batch batch, float physicsX, float physicsY){
        targetdummySprite.setPosition(physicsX-3.75f,physicsY-7);
        if (isAttacked){
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, false);
            //flip frame based on player pos in relation to this pos
            if (physicsX>darkknight.playerPhysics.getPlayerBody().getPosition().x){
                currentFrame.flip(false,false);
            }else {
                if (!currentFrame.isFlipX()) {
                    currentFrame.flip(true, false);
                }
            }
            //position and scale of frame
            batch.draw(currentFrame, targetdummySprite.getX(), targetdummySprite.getY(),8,16);
            //if the animation time is over then set the boolean that enabled the animation to false and reset timer to next animation.
            if (stateTime>=0.30f){
                isAttacked=false;
                stateTime=0;
            }
        } else {
            if (physicsX>darkknight.playerPhysics.getPlayerBody().getPosition().x){
                targetdummySprite.setFlip(false,false);
            }else {
                targetdummySprite.setFlip(true,false);
            }
            targetdummySprite.draw(batch);
        }
    }
}
