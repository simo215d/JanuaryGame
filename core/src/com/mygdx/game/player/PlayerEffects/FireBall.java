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

public class FireBall {
    private String name;
    private long spawnTime;
    private boolean flyRight = false;
    private float deathPositionX=0;
    private float deathPositionY=0;
    private boolean isDestroying = false;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;
    //animation fireball
    private int FRAME_COLS = 3, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation;
    private Texture sheet;
    private float stateTime; // A variable for tracking elapsed time for the animation
    //animation explosion
    private int FRAME_COLS_EX = 4, FRAME_ROWS_EX = 1;
    private Animation<TextureRegion> animation_EX;
    private Texture sheet_EX;
    private float stateTime_EX; // A variable for tracking elapsed time for the animation

    public FireBall(String name){
        this.name=name;
        spawnTime=darkknight.gameTimeCentiSeconds;
        //animation fireball
        sheet = new Texture(Gdx.files.internal("FireBall1Sheet.png"));
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
        //animation explosion
        sheet_EX = new Texture(Gdx.files.internal("FireBallExplosionSheet1.png"));
        TextureRegion[][] tmp_EX = TextureRegion.split(sheet_EX, sheet_EX.getWidth() / FRAME_COLS_EX, sheet_EX.getHeight() / FRAME_ROWS_EX);
        TextureRegion[] animationFrames_EX = new TextureRegion[FRAME_COLS_EX * FRAME_ROWS_EX];
        int index_EX = 0;
        for (int i = 0; i < FRAME_ROWS_EX; i++) {
            for (int j = 0; j < FRAME_COLS_EX; j++) {
                animationFrames_EX[index_EX++] = tmp_EX[i][j];
            }
        }
        animation_EX = new Animation<TextureRegion>(0.1f, animationFrames_EX);
        stateTime_EX = 0f;
        //find out what way we should fly
        if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            flyRight=false;
        } else flyRight=true;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (flyRight) bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+5);
        else bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+5);
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        body.setGravityScale(0);
        //create triangle so we make sure fireball only hits 1 target
        Vector2[] verticesSensor;
        //also make sure the triangle is flipped correctly
        if (flyRight)
        verticesSensor = new Vector2[]{new Vector2(1,0), new Vector2(0,-1), new Vector2(0,+1)};
        else verticesSensor = new Vector2[]{new Vector2(-1,0), new Vector2(0,-1), new Vector2(0,+1)};
        sensorShape = new PolygonShape();
        sensorShape.set(verticesSensor);
        fixture = body.createFixture(sensorShape,0);
        fixture.setUserData(name);
        fixture.setSensor(true);
        //set body position to fly forward based on what way player sprite is facing
        if (flyRight){
            body.setLinearVelocity(30f,0);
        } else body.setLinearVelocity(-30f,0);
    }

    public void draw(Batch batch){
        //after 100 deci seconds aka 10 seconds the fireball will game-end itself
        if (darkknight.gameTimeCentiSeconds-spawnTime>100){
            darkknight.bodiesToDestroy.add(body);
            destroy();
        }
        if (!isDestroying) {
            //Accumulate elapsed animation time of animation
            stateTime += Gdx.graphics.getDeltaTime();
            // Get current frame of animation for the current stateTime
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            //also flip sprite if it is flying left
            if (!flyRight && !currentFrame.isFlipX()) currentFrame.flip(true, false);
            //position and scale of frame
            if (!flyRight && body.getPosition().x<(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-5)){
                batch.draw(currentFrame, body.getPosition().x - 1, body.getPosition().y - 2f, (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
            } else if(flyRight && body.getPosition().x>(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x+5)){
                batch.draw(currentFrame, body.getPosition().x - 7, body.getPosition().y - 2f, (float) sheet.getWidth() / FRAME_COLS / 2, (float) sheet.getHeight() / FRAME_ROWS / 2);
            }
        }
        //if fireball is in the process of exploding/destroying then render explosion
        if (isDestroying){
            if (deathPositionX==0 && deathPositionY==0){
                if (!flyRight) deathPositionX=body.getPosition().x-1f;
                else deathPositionX=body.getPosition().x-3;
                deathPositionY=body.getPosition().y;
            }
            stateTime_EX += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame_EX = animation_EX.getKeyFrame(stateTime_EX, false);
            //check for destruction before we draw
            if (stateTime_EX>=0.40f){
                destroy();
            } else batch.draw(currentFrame_EX, deathPositionX, deathPositionY-1.5f, (float) sheet_EX.getWidth()/FRAME_COLS_EX/2,(float) sheet_EX.getHeight()/FRAME_ROWS_EX/2);
        }
    }

    public String getName(){
        return name;
    }

    public Body getBody(){
        return body;
    }

    public void setDestroying(boolean bool){
        isDestroying=bool;
    }

    public void destroy(){
        darkknight.player.deleteAFireBall(this);
        sensorShape.dispose();
        sheet.dispose();
        sheet_EX.dispose();
    }
}
