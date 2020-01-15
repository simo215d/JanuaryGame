package com.mygdx.game.world.undead1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.darkknight;

public class Undead1Graphics {
    //to make sure we only call die once and dont delete same body multiple times
    private boolean hasCalledDie = false;
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
    private float frameDuration_slam = 0.3f;
    //animation swing
    private static final int FRAME_COLS_swing = 8, FRAME_ROWS_swing = 1;
    private Animation<TextureRegion> animation_swing;
    private Texture sheet_swing;
    private float stateTime_swing;
    private float frameDuration_swing = 0.3f;
    //animation death
    private static final int FRAME_COLS_death = 5, FRAME_ROWS_death = 1;
    private Animation<TextureRegion> animation_death;
    private Texture sheet_death;
    private float stateTime_death;
    private float frameDuration_death = 0.5f;

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
        animation_slam = new Animation<TextureRegion>(frameDuration_slam, frames_slam);
        stateTime_slam = 0f;
        //animation swing
        sheet_swing = new Texture(Gdx.files.internal("undead1SwingSheet.png"));
        TextureRegion[][] tmp_swing = TextureRegion.split(sheet_swing, sheet_swing.getWidth() / FRAME_COLS_swing, sheet_swing.getHeight() / FRAME_ROWS_swing);
        TextureRegion[] frames_swing = new TextureRegion[FRAME_COLS_swing * FRAME_ROWS_swing];
        int index_swing = 0;
        for (int i = 0; i < FRAME_ROWS_swing; i++) {
            for (int j = 0; j < FRAME_COLS_swing; j++) {
                frames_swing[index_swing++] = tmp_swing[i][j];
            }
        }
        animation_swing = new Animation<TextureRegion>(frameDuration_swing, frames_swing);
        stateTime_swing = 0f;
        //animation death
        sheet_death = new Texture(Gdx.files.internal("undead1DeathSheet.png"));
        TextureRegion[][] tmp_death = TextureRegion.split(sheet_death, sheet_death.getWidth() / FRAME_COLS_death, sheet_death.getHeight() / FRAME_ROWS_death);
        TextureRegion[] frames_death = new TextureRegion[FRAME_COLS_death * FRAME_ROWS_death];
        int index_death = 0;
        for (int i = 0; i < FRAME_ROWS_death; i++) {
            for (int j = 0; j < FRAME_COLS_death; j++) {
                frames_death[index_death++] = tmp_death[i][j];
            }
        }
        animation_death = new Animation<TextureRegion>(frameDuration_death, frames_death);
        stateTime_death = 0f;
    }

    public void draw(Batch batch, Undead1Actions actions, float physicsX, float physicsY, int health, int maxHealth, Undead1 undead1){
        switch (actions.getActionState()){
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
                //if we should render red because of damage taken
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_idle, physicsX-16, physicsY-7.5f,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_idle, physicsX-16, physicsY-7.5f,32,32);
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
                //if we should render red because of damage taken
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_run, physicsX-16, physicsY-7.5f,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_run, physicsX-16, physicsY-7.5f,32,32);
                break;
            case "slamming":
                //Accumulate elapsed animation time of animation
                stateTime_slam += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_slam = animation_slam.getKeyFrame(stateTime_slam, false);
                //flip frame based on player pos in relation to this pos
                if (physicsX> darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x){
                    currentFrame_slam.flip(false,false);
                }else {
                    if (!currentFrame_slam.isFlipX()) {
                        currentFrame_slam.flip(true, false);
                    }
                }
                //if we should render red because of damage taken
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_slam, physicsX-16, physicsY-7.5f,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_slam, physicsX-16, physicsY-7.5f,32,32);
                //check if attack if 2/4 done to 4/4 done
                if (stateTime_slam>=frameDuration_slam*FRAME_COLS_slam*FRAME_ROWS_slam/2){
                    actions.damagePlayerIfInRange("slamming");
                }
                //event upon animation end
                if (stateTime_slam>=frameDuration_slam*FRAME_COLS_slam*FRAME_ROWS_slam){
                    actions.endAnAttack("slamming");
                    stateTime_slam=0;
                }
                break;
            case "swinging":
                //Accumulate elapsed animation time of animation
                stateTime_swing += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_swing = animation_swing.getKeyFrame(stateTime_swing, false);
                //flip frame based on player pos in relation to this pos
                if (physicsX> darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x){
                    currentFrame_swing.flip(false,false);
                }else {
                    if (!currentFrame_swing.isFlipX()) {
                        currentFrame_swing.flip(true, false);
                    }
                }
                //position and scale of frame
                //if we should render red because of damage taken
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_swing, physicsX-16, physicsY-7.5f,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_swing, physicsX-16, physicsY-7.5f,32,32);
                //check if attack if 2/4 done to 3/4 done
                if (stateTime_swing>=frameDuration_swing*FRAME_COLS_swing*FRAME_ROWS_swing/2 && stateTime_swing<frameDuration_swing*FRAME_COLS_swing*FRAME_ROWS_swing-frameDuration_swing*FRAME_COLS_swing*FRAME_ROWS_swing/4){
                    actions.damagePlayerIfInRange("swinging");
                }
                //animation end
                if (stateTime_swing>=frameDuration_swing*FRAME_COLS_swing*FRAME_ROWS_swing){
                    actions.endAnAttack("swinging");
                    stateTime_swing=0;
                }
                break;
            case "dying":
                //Accumulate elapsed animation time of animation
                stateTime_death += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_death = animation_death.getKeyFrame(stateTime_death, false);
                //flip frame based on player pos in relation to this pos
                if (physicsX> darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x){
                    currentFrame_death.flip(false,false);
                }else {
                    if (!currentFrame_death.isFlipX()) {
                        currentFrame_death.flip(true, false);
                    }
                }
                //animation end
                if (stateTime_death>=frameDuration_death*FRAME_COLS_death*FRAME_ROWS_death){
                    if (!hasCalledDie) {
                        undead1.die();
                        redHealthBarTexture.dispose();
                        greenHealthBarTexture.dispose();
                        sheet_death.dispose();
                        sheet_idle.dispose();
                        sheet_run.dispose();
                        sheet_slam.dispose();
                        sheet_swing.dispose();
                        hasCalledDie=true;
                    }
                }
                //position and scale of frame
                //if we should render red because of damage taken
                else if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_death, physicsX-16, physicsY-7.5f,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_death, physicsX-16, physicsY-7.5f,32,32);
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
