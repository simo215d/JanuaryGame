package com.mygdx.game.player.PlayerEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.darkknight;

public class DeathAnimationEffect {
    //animation alive
    private int FRAME_COLS = 7, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime;
    private float frameDuration = 0.25f;
    private float x;
    private float y;
    private boolean isFlipX;

    public DeathAnimationEffect(float x, float y, boolean isFlipX){
        this.x=x;
        this.y=y;
        System.out.println("X: "+x+" Y: "+y);
        this.isFlipX=isFlipX;
        //animation
        sheet = new Texture(Gdx.files.internal("knightDeathSheet.png"));
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
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        //draw frame with position and scale
        batch.draw(currentFrame, x-8, y-3, (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
        if (stateTime>=frameDuration*FRAME_COLS*FRAME_ROWS){
            darkknight.player.setDeathAnimationEffectToNull();
            darkknight.player.getPlayerUI().setDeathTextEffect();
            sheet.dispose();
        }
    }
}
