package com.mygdx.game.player.PlayerEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;

public class FireBall {
    private String name;
    private boolean flyRight = false;
    //box2d
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape sensorShape;
    private Fixture fixture;
    //animation
    private int FRAME_COLS = 3, FRAME_ROWS = 1;
    private Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    private Texture sheet;
    private float stateTime; // A variable for tracking elapsed time for the animation

    public FireBall(String name){
        this.name=name;
        //animation
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
        //find out what way we should fly
        if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            flyRight=false;
        } else flyRight=true;
        //box2d
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (flyRight) bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x+4, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+5);
        else bodyDef.position.set(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-4, darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y+5);
        bodyDef.fixedRotation=true;
        body = darkknight.world.createBody(bodyDef);
        body.setGravityScale(0);
        sensorShape = new PolygonShape();
        sensorShape.setAsBox(4,1);
        fixture = body.createFixture(sensorShape,0);
        fixture.setUserData(name);
        fixture.setSensor(true);
        sensorShape.dispose();
        //set body position to fly forward based on what way player sprite is facing
        if (flyRight){
            body.setLinearVelocity(20f,0);
        } else body.setLinearVelocity(-20f,0);
    }

    public void draw(Batch batch){
        //Accumulate elapsed animation time of animation
        stateTime += Gdx.graphics.getDeltaTime();
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        //also flip sprite if it is flying left
        if (!flyRight && !currentFrame.isFlipX()) currentFrame.flip(true,false);
        //position and scale of frame
        batch.draw(currentFrame, body.getPosition().x-4, body.getPosition().y-2f, (float) sheet.getWidth()/FRAME_COLS/2,(float) sheet.getHeight()/FRAME_ROWS/2);
    }

    public String getName(){
        return name;
    }
}
