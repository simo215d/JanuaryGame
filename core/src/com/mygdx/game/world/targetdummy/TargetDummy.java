package com.mygdx.game.world.targetdummy;

import com.badlogic.gdx.graphics.g2d.Batch;

public class TargetDummy {
    private String name;
    private TargetDummyPhysics physics;
    private TargetDummyGraphics graphics = new TargetDummyGraphics();
    private TargetDummyActions actions = new TargetDummyActions();

    public TargetDummy(String name, float x, float y){
        this.name=name;
        physics = new TargetDummyPhysics(name, x, y);
    }

    public void attack1(int damage){
        //TODO THIS SEQUENCE FROM PLAYER ACTIONS SEND THIS INTO ACTIONS
        System.out.println("i recieved attack1 my name is: "+getName());
        graphics.isAttacked=true;
        actions.takeDamage(1);
    }

    public void draw(Batch batch){
        graphics.draw(batch, physics.getBody().getPosition().x, physics.getBody().getPosition().y);
        //System.out.println("we drew with pos x: "+physics.getBody().getPosition().x+" y: "+physics.getBody().getPosition().y);
    }

    public TargetDummyPhysics getTargetDummyPhysics(){
        return physics;
    }

    public String getName(){
        return name;
    }
}
