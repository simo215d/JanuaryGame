package com.mygdx.game.player.PlayerEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.darkknight;

public class Orb {
    private String name;
    private long spawnTime;
    private boolean flyRight = false;
    private float deathPositionX=0;
    private float deathPositionY=0;
    private boolean hasBeenLaunched = false;
    private String animationState = "Stationary";
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private CircleShape sensorShape;
    private Fixture fixture;
    //animation stationary / moving
    private int FRAME_COLS_S = 8, FRAME_ROWS_S = 1;
    private Animation<TextureRegion> animation_S;
    private Texture sheet_S;
    private float stateTime_S;
    //animation launching
    private int FRAME_COLS_L1 = 4, FRAME_ROWS_L1 = 1;
    private Animation<TextureRegion> animation_L1;
    private Texture sheet_L1;
    private float stateTime_L1;
    //animation launched
    private int FRAME_COLS_L2 = 2, FRAME_ROWS_L2 = 1;
    private Animation<TextureRegion> animation_L2;
    private Texture sheet_L2;
    private float stateTime_L2;
    //animation exploding
    private int FRAME_COLS_EX = 4, FRAME_ROWS_EX = 1;
    private Animation<TextureRegion> animation_EX;
    private Texture sheet_EX;
    private float stateTime_EX;


    public Orb(String name){
        this.name=name;
        spawnTime=darkknight.gameTimeCentiSeconds;
        //animation stationary
        sheet_S = new Texture(Gdx.files.internal("orb1StationarySheet.png"));
        TextureRegion[][] tmp_S = TextureRegion.split(sheet_S, sheet_S.getWidth() / FRAME_COLS_S, sheet_S.getHeight() / FRAME_ROWS_S);
        TextureRegion[] animationFrames_S = new TextureRegion[FRAME_COLS_S * FRAME_ROWS_S];
        int index_S = 0;
        for (int i = 0; i < FRAME_ROWS_S; i++) {
            for (int j = 0; j < FRAME_COLS_S; j++) {
                animationFrames_S[index_S++] = tmp_S[i][j];
            }
        }
        animation_S = new Animation<TextureRegion>(0.05f, animationFrames_S);
        stateTime_S = 0f;
        //animation launching
        sheet_L1 = new Texture(Gdx.files.internal("orb1LaunchingSheet.png"));
        TextureRegion[][] tmp_L1 = TextureRegion.split(sheet_L1, sheet_L1.getWidth() / FRAME_COLS_L1, sheet_L1.getHeight() / FRAME_ROWS_L1);
        TextureRegion[] animationFrames_L1 = new TextureRegion[FRAME_COLS_L1 * FRAME_ROWS_L1];
        int index_L1 = 0;
        for (int i = 0; i < FRAME_ROWS_L1; i++) {
            for (int j = 0; j < FRAME_COLS_L1; j++) {
                animationFrames_L1[index_L1++] = tmp_L1[i][j];
            }
        }
        animation_L1 = new Animation<TextureRegion>(0.05f, animationFrames_L1);
        stateTime_L1 = 0f;
        //animation launched
        sheet_L2 = new Texture(Gdx.files.internal("orb1LaunchedSheet.png"));
        TextureRegion[][] tmp_L2 = TextureRegion.split(sheet_L2, sheet_L2.getWidth() / FRAME_COLS_L2, sheet_L2.getHeight() / FRAME_ROWS_L2);
        TextureRegion[] animationFrames_L2 = new TextureRegion[FRAME_COLS_L2 * FRAME_ROWS_L2];
        int index_L2 = 0;
        for (int i = 0; i < FRAME_ROWS_L2; i++) {
            for (int j = 0; j < FRAME_COLS_L2; j++) {
                animationFrames_L2[index_L2++] = tmp_L2[i][j];
            }
        }
        animation_L2 = new Animation<TextureRegion>(0.1f, animationFrames_L2);
        stateTime_L2 = 0f;
        //animation explosion
        sheet_EX = new Texture(Gdx.files.internal("orb1ExplosionSheet.png"));
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
        switch (Character.getNumericValue(name.charAt(9))){
            case 1: bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-1.5f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+12.5f); break;
            case 2: bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+15); break;
            case 3: bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x+1.5f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+12.5f); break;
        }
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        body.setGravityScale(0);
        sensorShape = new CircleShape();
        sensorShape.setRadius(0.5f);
        fixture = body.createFixture(sensorShape,0);
        fixture.setUserData(name);
        fixture.setSensor(true);
        sensorShape.dispose();
    }

    public void draw(Batch batch){
        //after 200 deci seconds aka 20 seconds the fireball will game-end itself
        if (darkknight.gameTimeCentiSeconds-spawnTime>200){
            //setAnimationState("Exploding");
            //darkknight.bodiesToDestroy.add(body);
        }
        switch (animationState){
            case "Stationary":
                stateTime_S += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_S = animation_S.getKeyFrame(stateTime_S, true);
                //position and scale of frame
                batch.draw(currentFrame_S, body.getPosition().x-3.75f, body.getPosition().y-7.25f, (float) sheet_S.getWidth() / FRAME_COLS_S / 2, (float) sheet_S.getHeight() / FRAME_ROWS_S / 2);
                //set orbs to follow player
                switch (Character.getNumericValue(name.charAt(9))){
                    case 1: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-1.5f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+12.5f,0); break;
                    case 2: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+15,0); break;
                    case 3: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x+1.5f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+12.5f,0); break;
                }
                break;
            case "Launching":
                stateTime_L1 += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_L1 = animation_L1.getKeyFrame(stateTime_L1, false);
                //flip if left if player is facing left
                if (!flyRight) {
                    if (!currentFrame_L1.isFlipX()){
                        currentFrame_L1.flip(true,false);
                    }
                }
                //position and scale of frame depending on what orb it is
                switch (Character.getNumericValue(name.charAt(9))){
                    case 1:batch.draw(currentFrame_L1, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-3.75f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+6.25f, (float) sheet_L1.getWidth() / FRAME_COLS_L1 / 2, (float) sheet_L1.getHeight() / FRAME_ROWS_L1 / 2); break;
                    case 2:batch.draw(currentFrame_L1, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-3.75f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+7.25f, (float) sheet_L1.getWidth() / FRAME_COLS_L1 / 2, (float) sheet_L1.getHeight() / FRAME_ROWS_L1 / 2); break;
                    case 3:batch.draw(currentFrame_L1, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-3.75f, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+4.25f, (float) sheet_L1.getWidth() / FRAME_COLS_L1 / 2, (float) sheet_L1.getHeight() / FRAME_ROWS_L1 / 2); break;
                }
                //set orbs to follow players inside so it doesnt collide with unwanted stuff
                switch (Character.getNumericValue(name.charAt(9))){
                    case 1: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+4,0); break;
                    case 2: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+4,0); break;
                    case 3: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+4,0); break;
                }
                if (stateTime_L1>0.02f*FRAME_ROWS_L1*FRAME_ROWS_L1){
                    hasBeenLaunched=true;
                    launch();
                }
                if (stateTime_L1>0.05f*FRAME_COLS_L1*FRAME_ROWS_L1){
                    setAnimationState("Launched");
                }
                break;
            case "Launched":
                //animation
                stateTime_L2 += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_L2 = animation_L2.getKeyFrame(stateTime_L2, true);
                //flip if left if player is facing left
                if (!flyRight) {
                    if (!currentFrame_L2.isFlipX()){
                        currentFrame_L2.flip(true,false);
                    }
                }
                //position and scale of frame
                if (flyRight) {
                    batch.draw(currentFrame_L2, body.getPosition().x - 7f, body.getPosition().y - 1, (float) sheet_L2.getWidth() / FRAME_COLS_L2 / 2, (float) sheet_L2.getHeight() / FRAME_ROWS_L2 / 2);
                } else batch.draw(currentFrame_L2, body.getPosition().x - 1f, body.getPosition().y - 1, (float) sheet_L2.getWidth() / FRAME_COLS_L2 / 2, (float) sheet_L2.getHeight() / FRAME_ROWS_L2 / 2);
                break;
            case "Exploding":
                stateTime_EX += Gdx.graphics.getDeltaTime();
                // Get current frame of animation for the current stateTime
                TextureRegion currentFrame_EX = animation_EX.getKeyFrame(stateTime_EX, false);
                //flip if left if player is facing left
                if (!flyRight) {
                    if (!currentFrame_EX.isFlipX()){
                        currentFrame_EX.flip(true,false);
                    }
                }
                //position and scale of frame
                if (flyRight) {
                    batch.draw(currentFrame_EX, body.getPosition().x - 3f, body.getPosition().y - 2.25f, (float) sheet_EX.getWidth() / FRAME_COLS_EX / 2, (float) sheet_EX.getHeight() / FRAME_ROWS_EX / 2);
                } else batch.draw(currentFrame_EX, body.getPosition().x - 1.5f, body.getPosition().y - 2.25f, (float) sheet_EX.getWidth() / FRAME_COLS_EX / 2, (float) sheet_EX.getHeight() / FRAME_ROWS_EX / 2);
                if (stateTime_EX>0.1f*FRAME_COLS_EX*FRAME_ROWS_EX){
                    destroy();
                }
                break;
        }
    }

    public String getName(){
        return name;
    }

    public void setAnimationState(String state){
        this.animationState=state;
        //we need to when which direction we fire the orb
        if (state.equals("Launching")){
            if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
                flyRight=false;
            } else flyRight=true;
        }
    }

    public String getAnimationState(){
        return animationState;
    }

    public Body getBody(){
        return body;
    }

    public void destroy(){
        darkknight.player.deleteAnOrb(this);
    }

    private void launch(){
        //in this case we need to handle the orb position first because else we get 1 frame with the animation rendered on top of player
        //set orb to fly if it has been launched
        //position if flying right
        if (hasBeenLaunched && flyRight){
            //based on what orb it is we need to launch it from the correct position
            switch (Character.getNumericValue(name.charAt(9))){
                case 1: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+7,0); break;
                case 2: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+8.5f,0); break;
                case 3: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+5.5f,0); break;
            }
            body.setLinearVelocity(40,0);
            hasBeenLaunched=false;
        }
        //position if flying left
        if (hasBeenLaunched && !flyRight){
            //based on what orb it is we need to launch it from the correct position
            switch (Character.getNumericValue(name.charAt(9))){
                case 1: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+7,0); break;
                case 2: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+8.5f,0); break;
                case 3: body.setTransform(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+5.5f,0); break;
            }
            body.setLinearVelocity(-40,0);
            hasBeenLaunched=false;
        }
    }
}
