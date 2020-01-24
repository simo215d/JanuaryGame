package com.mygdx.game.world.necromancer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NecromancerGraphics {
    private boolean hasLaunched;
    private boolean hasLaunchedMeteor;
    //health bar sprite green
    private Texture greenHealthBarTexture;
    private Sprite greenHealthBarSprite;
    //health bar sprite red
    private Texture redHealthBarTexture;
    private Sprite redHealthBarSprite;
    //idle animation
    private static final int FRAME_COLS_idle = 4, FRAME_ROWS_idle = 1;
    private Animation<TextureRegion> animation_idle;
    private Texture sheet_idle;
    private float stateTime_idle;
    //summon animation
    private static final int FRAME_COLS_summon = 8, FRAME_ROWS_summon = 1;
    private Animation<TextureRegion> animation_summon;
    private Texture sheet_summon;
    private float stateTime_summon;
    //shoot animation
    private static final int FRAME_COLS_shoot = 7, FRAME_ROWS_shoot = 1;
    private Animation<TextureRegion> animation_shoot;
    private Texture sheet_shoot;
    private float stateTime_shoot;
    //death animation
    private static final int FRAME_COLS_death = 4, FRAME_ROWS_death = 1;
    private Animation<TextureRegion> animation_death;
    private Texture sheet_death;
    private float stateTime_death;

    public NecromancerGraphics(){
        //health bar sprite green
        greenHealthBarTexture = new Texture(Gdx.files.internal("greenHealthBar1.png"));
        greenHealthBarSprite = new Sprite(greenHealthBarTexture,0,0,10,2);
        greenHealthBarSprite.setSize(5,1);
        //health bar sprite red
        redHealthBarTexture = new Texture(Gdx.files.internal("redHealthBar1.png"));
        redHealthBarSprite = new Sprite(redHealthBarTexture,0,0,10,2);
        redHealthBarSprite.setSize(5,1);
        //animation idle
        sheet_idle = new Texture(Gdx.files.internal("necromancerIdleSheet.png"));
        TextureRegion[][] tmp_idle = TextureRegion.split(sheet_idle, sheet_idle.getWidth() / FRAME_COLS_idle, sheet_idle.getHeight() / FRAME_ROWS_idle);
        TextureRegion[] frames_idle = new TextureRegion[FRAME_COLS_idle * FRAME_ROWS_idle];
        int index_idle = 0;
        for (int i = 0; i < FRAME_ROWS_idle; i++) {
            for (int j = 0; j < FRAME_COLS_idle; j++) {
                frames_idle[index_idle++] = tmp_idle[i][j];
            }
        }
        animation_idle = new Animation<TextureRegion>(0.20f, frames_idle);
        stateTime_idle = 0f;
        //animation summon
        sheet_summon = new Texture(Gdx.files.internal("necromancerSummonSheet.png"));
        TextureRegion[][] tmp_summon = TextureRegion.split(sheet_summon, sheet_summon.getWidth() / FRAME_COLS_summon, sheet_summon.getHeight() / FRAME_ROWS_summon);
        TextureRegion[] frames_summon = new TextureRegion[FRAME_COLS_summon * FRAME_ROWS_summon];
        int index_summon = 0;
        for (int i = 0; i < FRAME_ROWS_summon; i++) {
            for (int j = 0; j < FRAME_COLS_summon; j++) {
                frames_summon[index_summon++] = tmp_summon[i][j];
            }
        }
        animation_summon = new Animation<TextureRegion>(0.10f, frames_summon);
        stateTime_summon = 0f;
        //animation shoot
        sheet_shoot = new Texture(Gdx.files.internal("necromancerShootSheet.png"));
        TextureRegion[][] tmp_shoot = TextureRegion.split(sheet_shoot, sheet_shoot.getWidth() / FRAME_COLS_shoot, sheet_shoot.getHeight() / FRAME_ROWS_shoot);
        TextureRegion[] frames_shoot = new TextureRegion[FRAME_COLS_shoot * FRAME_ROWS_shoot];
        int index_shoot = 0;
        for (int i = 0; i < FRAME_ROWS_shoot; i++) {
            for (int j = 0; j < FRAME_COLS_shoot; j++) {
                frames_shoot[index_shoot++] = tmp_shoot[i][j];
            }
        }
        animation_shoot = new Animation<TextureRegion>(0.10f, frames_shoot);
        stateTime_shoot = 0f;
        //animation death
        sheet_death = new Texture(Gdx.files.internal("necromancerDeathSheet.png"));
        TextureRegion[][] tmp_death = TextureRegion.split(sheet_death, sheet_death.getWidth() / FRAME_COLS_death, sheet_death.getHeight() / FRAME_ROWS_death);
        TextureRegion[] frames_death = new TextureRegion[FRAME_COLS_death * FRAME_ROWS_death];
        int index_death = 0;
        for (int i = 0; i < FRAME_ROWS_death; i++) {
            for (int j = 0; j < FRAME_COLS_death; j++) {
                frames_death[index_death++] = tmp_death[i][j];
            }
        }
        animation_death = new Animation<TextureRegion>(0.10f, frames_death);
        stateTime_death = 0f;
    }

    public void draw(Batch batch, NecromancerPhysics physics, NecromancerActions actions){
        switch (actions.getActionState()){
            case "idle":
                stateTime_idle += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame_idle = animation_idle.getKeyFrame(stateTime_idle, true);
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_idle, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_idle, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                break;
            case "summon":
                stateTime_summon += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame_summon = animation_summon.getKeyFrame(stateTime_summon, false);
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_summon, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_summon, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                if (!hasLaunchedMeteor){
                    actions.launchMeteor(physics.getBody().getPosition().x,physics.getBody().getPosition().y);
                    hasLaunchedMeteor=true;
                }
                if (stateTime_summon>=0.1*FRAME_COLS_summon*FRAME_ROWS_summon){
                    actions.setCanAct(true);
                    actions.setPaused(true);
                    hasLaunchedMeteor=false;
                    stateTime_summon=0;
                }
                break;
            case "shoot":
                stateTime_shoot += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame_shoot = animation_shoot.getKeyFrame(stateTime_shoot, false);
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_shoot, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_shoot, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                if (!hasLaunched){
                    actions.launch(physics.getBody().getPosition().x,physics.getBody().getPosition().y);
                    hasLaunched=true;
                }
                if (stateTime_shoot>=0.1*FRAME_COLS_shoot*FRAME_ROWS_shoot){
                    hasLaunched=false;
                    actions.setCanAct(true);
                    stateTime_shoot=0;
                }
                break;
            case "death":
                stateTime_death += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame_death = animation_death.getKeyFrame(stateTime_death, false);
                if (actions.isRenderRed()){
                    batch.setColor(Color.RED);
                    batch.draw(currentFrame_death, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                    batch.setColor(Color.WHITE);
                } else batch.draw(currentFrame_death, physics.getBody().getPosition().x-16, physics.getBody().getPosition().y-10,32,32);
                break;
        }
        //render the healthBar
        redHealthBarSprite.setPosition(-2.5f+physics.getBody().getPosition().x,physics.getBody().getPosition().y+10);
        redHealthBarSprite.draw(batch);
        greenHealthBarSprite.setPosition(-2.5f+physics.getBody().getPosition().x,physics.getBody().getPosition().y+10);
        //this calculates the scale of the green bar based on how many percent the object is missing from its max health
        float healthScale = 5f*((float) actions.getHealth()/(float) actions.getMaxHealth());
        greenHealthBarSprite.setSize(healthScale,greenHealthBarSprite.getHeight());
        greenHealthBarSprite.draw(batch);
    }
}
