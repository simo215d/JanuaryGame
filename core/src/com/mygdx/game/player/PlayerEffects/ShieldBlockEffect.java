package com.mygdx.game.player.PlayerEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.darkknight;

public class ShieldBlockEffect {
    private float x;
    private float y;
    //animation
    private int FRAME_COLS = 5, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime;
    private float frameDuration = 0.05f;

    public ShieldBlockEffect(float x, float y){
        this.x=x;
        this.y=y;
        if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            this.x-=6;
        }
        //animation
        sheet = new Texture(Gdx.files.internal("knightShieldEffectSheet.png"));
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
    }

    public void draw(Batch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        batch.draw(currentFrame, x, y+1, (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
        if (stateTime>=frameDuration*FRAME_COLS*FRAME_ROWS){
            darkknight.player.getPlayerCombat().setShieldBlockEffectToNull();
        }
    }
}
