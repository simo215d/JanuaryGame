package com.mygdx.game.world.undead1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.darkknight;

public class Undead1Graphics {
    //health bar sprite green
    private Texture greenHealthBarTexture;
    private Sprite greenHealthBarSprite;
    //health bar sprite red
    private Texture redHealthBarTexture;
    private Sprite redHealthBarSprite;
    //animation idle
    private static final int FRAME_COLS_idle = 2, FRAME_ROWS_idle = 1;
    private Animation<TextureRegion> animation_idle;
    private Texture sheet_idle;
    private float stateTime_idle;
    //animation run
    private static final int FRAME_COLS_run = 5, FRAME_ROWS_run = 1;
    private Animation<TextureRegion> animation_run;
    private Texture sheet_run;
    private float stateTime_run;
    //animation slam
    private static final int FRAME_COLS_slam = 5, FRAME_ROWS_slam = 1;
    private Animation<TextureRegion> animation_slam;
    private Texture sheet_slam;
    private float stateTime_slam;

    public Undead1Graphics(){
        //health bar sprite green
        greenHealthBarTexture = new Texture(Gdx.files.internal("greenHealthBar1.png"));
        greenHealthBarSprite = new Sprite(greenHealthBarTexture,0,0,10,2);
        greenHealthBarSprite.setSize(5,1);
        //health bar sprite red
        redHealthBarTexture = new Texture(Gdx.files.internal("redHealthBar1.png"));
        redHealthBarSprite = new Sprite(redHealthBarTexture,0,0,10,2);
        redHealthBarSprite.setSize(5,1);
        //animation idle
        sheet_idle = new Texture(Gdx.files.internal("undead1IdleSheet.png"));
        TextureRegion[][] tmp_idle = TextureRegion.split(sheet_idle, sheet_idle.getWidth() / FRAME_COLS_idle, sheet_idle.getHeight() / FRAME_ROWS_idle);
        TextureRegion[] frames_idle = new TextureRegion[FRAME_COLS_idle * FRAME_ROWS_idle];
        int index_idle = 0;
        for (int i = 0; i < FRAME_ROWS_idle; i++) {
            for (int j = 0; j < FRAME_COLS_idle; j++) {
                frames_idle[index_idle++] = tmp_idle[i][j];
            }
        }
        animation_idle = new Animation<TextureRegion>(1f, frames_idle);
        stateTime_idle = 0f;
        //animation run
        sheet_run = new Texture(Gdx.files.internal("undead1RunSheet.png"));
        TextureRegion[][] tmp_run = TextureRegion.split(sheet_run, sheet_run.getWidth() / FRAME_COLS_run, sheet_run.getHeight() / FRAME_ROWS_run);
        TextureRegion[] frames_run = new TextureRegion[FRAME_COLS_run * FRAME_ROWS_run];
        int index_run = 0;
        for (int i = 0; i < FRAME_ROWS_run; i++) {
            for (int j = 0; j < FRAME_COLS_run; j++) {
                frames_run[index_run++] = tmp_run[i][j];
            }
        }
        animation_run = new Animation<TextureRegion>(0.4f, frames_run);
        stateTime_run = 0f;
        //animation slam
        sheet_slam = new Texture(Gdx.files.internal("undead1SlamSheet.png"));
        TextureRegion[][] tmp_slam = TextureRegion.split(sheet_slam, sheet_slam.getWidth() / FRAME_COLS_slam, sheet_slam.getHeight() / FRAME_ROWS_slam);
        TextureRegion[] frames_slam = new TextureRegion[FRAME_COLS_slam * FRAME_ROWS_slam];
        int index_slam = 0;
        for (int i = 0; i < FRAME_ROWS_slam; i++) {
            for (int j = 0; j < FRAME_COLS_slam; j++) {
                frames_slam[index_slam++] = tmp_slam[i][j];
            }
        }
        animation_slam = new Animation<TextureRegion>(0.3f, frames_slam);
        stateTime_slam = 0f;
    }

    public void draw(Batch batch, String actionState, float physicsX, float physicsY, int health, int maxHealth){
        switch (actionState){
            case "idle":
                //Accumulate elapsed animation time of animation
                stateTime_idle += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_idle = animation_idle.getKeyFrame(stateTime_idle, true);
                //flip frame based on player pos in relation to this pos
                if (physicsX> darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x){
                    currentFrame_idle.flip(false,false);
                }else {
                    if (!currentFrame_idle.isFlipX()) {
                        currentFrame_idle.flip(true, false);
                    }
                }
                //position and scale of frame
                batch.draw(currentFrame_idle, physicsX-16, physicsY-7.5f,32,32);
                break;
            case "running":
                //Accumulate elapsed animation time of animation
                stateTime_run += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_run = animation_run.getKeyFrame(stateTime_run, true);
                //flip frame based on player pos in relation to this pos
                if (physicsX> darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x){
                    currentFrame_run.flip(false,false);
                }else {
                    if (!currentFrame_run.isFlipX()) {
                        currentFrame_run.flip(true, false);
                    }
                }
                //position and scale of frame
                batch.draw(currentFrame_run, physicsX-16, physicsY-7.5f,32,32);
                break;
            case "slamming":
                //Accumulate elapsed animation time of animation
                stateTime_slam += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_slam = animation_slam.getKeyFrame(stateTime_slam, true);
                //flip frame based on player pos in relation to this pos
                if (physicsX> darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x){
                    currentFrame_slam.flip(false,false);
                }else {
                    if (!currentFrame_slam.isFlipX()) {
                        currentFrame_slam.flip(true, false);
                    }
                }
                //position and scale of frame
                batch.draw(currentFrame_slam, physicsX-16, physicsY-7.5f,32,32);
                //TODO end of animation starts swing attack
                break;
        }
        //render the healthBar
        redHealthBarSprite.setPosition(-2.5f+physicsX,physicsY+8);
        redHealthBarSprite.draw(batch);
        greenHealthBarSprite.setPosition(-2.5f+physicsX,physicsY+8);
        //this calculates the scale of the green bar based on how many percent the object is missing from its max health
        float healthScale = 5f*((float) health/(float) maxHealth);
        greenHealthBarSprite.setSize(healthScale,greenHealthBarSprite.getHeight());
        greenHealthBarSprite.draw(batch);
    }
}
