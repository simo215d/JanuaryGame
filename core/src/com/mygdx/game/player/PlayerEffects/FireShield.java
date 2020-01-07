package com.mygdx.game.player.PlayerEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.darkknight;

public class FireShield {
    private long spawnTime;
    private boolean isFading = false;
    //animation alive
    private int FRAME_COLS = 3, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime; // A variable for tracking elapsed time for the animation
    //animation fading
    private int FRAME_COLS_F = 3, FRAME_ROWS_F = 1;
    private Animation<TextureRegion> animation_F;
    private Texture sheet_F;
    private float stateTime_F; // A variable for tracking elapsed time for the animation
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;

    public FireShield(){
        spawnTime= darkknight.gameTimeCentiSeconds;
        //animation alive
        sheet = new Texture(Gdx.files.internal("fireShieldSheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.1f, animationFrames);
        stateTime = 0f;
        //animation fading
        sheet_F = new Texture(Gdx.files.internal("fireShieldFadeSheet.png"));
        TextureRegion[][] tmp_F = TextureRegion.split(sheet_F, sheet_F.getWidth() / FRAME_COLS_F, sheet_F.getHeight() / FRAME_ROWS_F);
        TextureRegion[] animationFrames_F = new TextureRegion[FRAME_COLS_F * FRAME_ROWS_F];
        int index_F = 0;
        for (int i = 0; i < FRAME_ROWS_F; i++) {
            for (int j = 0; j < FRAME_COLS_F; j++) {
                animationFrames_F[index_F++] = tmp_F[i][j];
            }
        }
        animation_F = new Animation<TextureRegion>(0.1f, animationFrames_F);
        stateTime_F = 0f;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(20, 6));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        body.setGravityScale(0);
        //create the shape
        Vector2[] verticesCollider = new Vector2[]{new Vector2(0f,-3f), new Vector2(-4f,0f), new Vector2(-5,10f), new Vector2(0,14), new Vector2(5f,10f), new Vector2(4f,0f)};
        sensorShape = new PolygonShape();
        sensorShape.set(verticesCollider);
        fixture = body.createFixture(sensorShape,0.0f);
        fixture.setUserData("playerFireShield");
        fixture.setSensor(true);
        sensorShape.dispose();
    }

    public void draw(Batch batch) {
        //when 5 seconds has passed; die
        if (darkknight.gameTimeCentiSeconds-spawnTime>50){
            isFading=true;
        }
        if (!isFading) {
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            //draw frame with position and scale
            batch.draw(currentFrame, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x - 8, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y - 2, (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
            //update sensor position
            body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y, 0);
        } else if (isFading){
            //Accumulate elapsed animation time of animation
            stateTime_F += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame_F = animation_F.getKeyFrame(stateTime_F, false);
            //draw frame with position and scale
            batch.draw(currentFrame_F, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x - 8, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y - 2, (float) sheet_F.getWidth() / FRAME_COLS_F / 2, (float) sheet_F.getHeight() / FRAME_ROWS_F / 2);
            //update sensor position
            body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y, 0);
            if (stateTime_F>=0.1f*FRAME_COLS_F*FRAME_ROWS_F){
                destroy();
            }
        }
    }

    public void destroy(){
        darkknight.player.getPlayerCombat().setImmuneToDamage(false);
        darkknight.bodiesToDestroy.add(body);
        darkknight.player.getPlayerCombat().setFireShieldToNull();
    }
}
