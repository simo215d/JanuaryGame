package com.mygdx.game.player.PlayerEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class FireBreath {
    private long spawnTime;
    private boolean isFacingRight = false;
    //animation
    private int FRAME_COLS = 7, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime; // A variable for tracking elapsed time for the animation
    //box2d so we can detect what enemies to apply force and damage to
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;

    public FireBreath(){
        spawnTime= darkknight.gameTimeCentiSeconds;
        //what way to breathe
        if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            isFacingRight=false;
        } else isFacingRight=true;
        //animation
        sheet = new Texture(Gdx.files.internal("fireBreathEffectSheet.png"));
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
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(20, 6));
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        body.setGravityScale(0);
        //create the shape
        Vector2[] verticesCollider;
        if (isFacingRight) {
            verticesCollider = new Vector2[]{new Vector2(0f, 4f), new Vector2(18f, 0f), new Vector2(20, 10f), new Vector2(2, 10)};
        } else {
            verticesCollider = new Vector2[]{new Vector2(0f, 4f), new Vector2(-18f, 0f), new Vector2(-20, 10f), new Vector2(-2, 10)};
        }
        sensorShape = new PolygonShape();
        sensorShape.set(verticesCollider);
        fixture = body.createFixture(sensorShape,0.0f);
        fixture.setUserData("playerFireBreath");
        fixture.setSensor(true);
        sensorShape.dispose();
    }

    public void draw(Batch batch){
        if (isFacingRight){
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
            //draw frame with position and scale
            batch.draw(currentFrame, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x+2, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y, (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
            //update sensor position
            body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y, 0);
            if (stateTime>0.1f*FRAME_COLS*FRAME_ROWS){
                destroy();
            }
        }
        if (!isFacingRight){
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
            if (!currentFrame.isFlipX()){
                currentFrame.flip(true,false);
            }
            //draw frame with position and scale
            batch.draw(currentFrame, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x - 18, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y , (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
            //update sensor position
            body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y, 0);
            if (stateTime>0.1f*FRAME_COLS*FRAME_ROWS){
                destroy();
            }
        }
    }

    public void pushBackEnemy(Body body){
        if (isFacingRight){
            body.setLinearVelocity(25f, 10f);
        } else {
            body.setLinearVelocity(-25f, 10f);
        }
    }

    private void destroy(){
        darkknight.bodiesToDestroy.add(body);
        darkknight.player.getPlayerCombat().setFireBreathToNull();
    }
}
