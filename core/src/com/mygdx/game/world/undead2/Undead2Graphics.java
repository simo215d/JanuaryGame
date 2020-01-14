package com.mygdx.game.world.undead2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.darkknight;

public class Undead2Graphics {
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
    private float frameDuration_idle = 1f;
    //animation shoot
    private static final int FRAME_COLS_shoot = 9, FRAME_ROWS_shoot = 1;
    private Animation<TextureRegion> animation_shoot;
    private Texture sheet_shoot;
    private float stateTime_shoot;
    private float frameDuration_shoot = 0.4f;
    //animation death
    private static final int FRAME_COLS_death = 4, FRAME_ROWS_death = 1;
    private Animation<TextureRegion> animation_death;
    private Texture sheet_death;
    private float stateTime_death;
    private float frameDuration_death = 1f;

    public Undead2Graphics(){
        //health bar sprite green
        greenHealthBarTexture = new Texture(Gdx.files.internal("greenHealthBar1.png"));
        greenHealthBarSprite = new Sprite(greenHealthBarTexture,0,0,10,2);
        greenHealthBarSprite.setSize(5,1);
        //health bar sprite red
        redHealthBarTexture = new Texture(Gdx.files.internal("redHealthBar1.png"));
        redHealthBarSprite = new Sprite(redHealthBarTexture,0,0,10,2);
        redHealthBarSprite.setSize(5,1);
        //animation idle
        sheet_idle = new Texture(Gdx.files.internal("undead2IdleSheet.png"));
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
        //animation shoot
        sheet_shoot = new Texture(Gdx.files.internal("undead2ShootSheet.png"));
        TextureRegion[][] tmp_shoot = TextureRegion.split(sheet_shoot, sheet_shoot.getWidth() / FRAME_COLS_shoot, sheet_shoot.getHeight() / FRAME_ROWS_shoot);
        TextureRegion[] frames_shoot = new TextureRegion[FRAME_COLS_shoot * FRAME_ROWS_shoot];
        int index_shoot = 0;
        for (int i = 0; i < FRAME_ROWS_shoot; i++) {
            for (int j = 0; j < FRAME_COLS_shoot; j++) {
                frames_shoot[index_shoot++] = tmp_shoot[i][j];
            }
        }
        animation_shoot = new Animation<TextureRegion>(frameDuration_shoot, frames_shoot);
        stateTime_shoot = 0f;
        //animation death
        sheet_death = new Texture(Gdx.files.internal("undead2DeathSheet.png"));
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

    public void draw(Batch batch, Undead2Actions actions, float physicsX, float physicsY, int health, int maxHealth, Undead2 undead2){
        switch (actions.getActionState()) {
            case "idle":
                //Accumulate elapsed animation time of animation
                stateTime_idle += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_idle = animation_idle.getKeyFrame(stateTime_idle, true);
                //flip frame based on player pos in relation to this pos
                if (physicsX > darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x) {
                    currentFrame_idle.flip(false, false);
                } else {
                    if (!currentFrame_idle.isFlipX()) {
                        currentFrame_idle.flip(true, false);
                    }
                }
                //position and scale of frame
                //if we should render red because of damage taken
                if (actions.isRenderRed()) {
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_idle, physicsX - 8, physicsY - 7.5f, 16, 16);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_idle, physicsX - 8, physicsY - 7.5f, 16, 16);
                break;
            case "shooting":
                //Accumulate elapsed animation time of animation
                stateTime_shoot += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_shoot = animation_shoot.getKeyFrame(stateTime_shoot, false);
                //flip frame based on player pos in relation to this pos
                if (physicsX > darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x) {
                    currentFrame_shoot.flip(false, false);
                } else {
                    if (!currentFrame_shoot.isFlipX()) {
                        currentFrame_shoot.flip(true, false);
                    }
                }
                //position and scale of frame
                //if we should render red because of damage taken
                if (actions.isRenderRed()) {
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_shoot, physicsX - 8, physicsY - 7.5f, 16, 16);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_shoot, physicsX - 8, physicsY - 7.5f, 16, 16);
                if (stateTime_shoot>=frameDuration_shoot*FRAME_COLS_shoot*FRAME_ROWS_shoot && !actions.getHasRecentlyShotArrow()){
                    actions.shootArrow(physicsX,physicsY,currentFrame_shoot.isFlipX(), undead2.getName());
                    actions.setHasRecentlyShotArrow(true);
                }
                if (stateTime_shoot>=frameDuration_shoot*FRAME_COLS_shoot*FRAME_ROWS_shoot*2){
                    stateTime_shoot=0;
                    actions.setHasRecentlyShotArrow(false);
                }
                break;
            case "dying":
                //Accumulate elapsed animation time of animation
                stateTime_death += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_death = animation_death.getKeyFrame(stateTime_death, false);
                //flip frame based on player pos in relation to this pos
                if (physicsX > darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x) {
                    currentFrame_death.flip(false, false);
                } else {
                    if (!currentFrame_death.isFlipX()) {
                        currentFrame_death.flip(true, false);
                    }
                }
                //position and scale of frame
                //if we should render red because of damage taken
                if (actions.isRenderRed()) {
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_death, physicsX - 8, physicsY - 7.5f, 16, 16);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_death, physicsX - 8, physicsY - 7.5f, 16, 16);
                if (stateTime_death>=frameDuration_death*FRAME_COLS_death*FRAME_ROWS_death*1.2f){
                    if (!hasCalledDie) {
                        undead2.die();
                        hasCalledDie=true;
                    }
                }
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
