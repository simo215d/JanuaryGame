package com.mygdx.game.world.undead1;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Undead1 {
    private String name;
    private Undead1Physics physics;
    private Undead1Graphics graphics = new Undead1Graphics();
    private Undead1Actions actions = new Undead1Actions();

    public Undead1(String name, float x, float y){
        this.name=name;
        physics = new Undead1Physics(name, x, y);
    }

    public void takeDamage(int damage){
        actions.takeDamage(damage);
    }

    public void draw(Batch batch){
        graphics.draw(batch, actions.getActionState(), physics.getBody().getPosition().x, physics.getBody().getPosition().y, actions.getHealth(), actions.getMaxHealth());
    }

    public Undead1Physics getPhysics(){
        return physics;
    }

    public String getName(){
        return name;
    }
}
