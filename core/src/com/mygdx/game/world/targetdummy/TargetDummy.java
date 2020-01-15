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

    public void takeDamage(int damage){
        graphics.isAttacked=true;
        actions.takeDamage(damage);
    }

    public void draw(Batch batch){
        graphics.draw(batch, physics.getBody().getPosition().x, physics.getBody().getPosition().y, actions.getHealth(), actions.getMaxHealth());
    }

    public TargetDummyPhysics getTargetDummyPhysics(){
        return physics;
    }

    public String getName(){
        return name;
    }

    public TargetDummyGraphics getGraphics(){
        return graphics;
    }
}
