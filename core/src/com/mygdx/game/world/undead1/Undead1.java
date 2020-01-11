package com.mygdx.game.world.undead1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.darkknight;
import com.mygdx.game.world.DamageNumber;

public class Undead1 {
    private String name;
    private Undead1Physics physics;
    private Undead1Graphics graphics = new Undead1Graphics();
    private Undead1Actions actions = new Undead1Actions();
    private boolean shouldBeDeleted = false;

    public Undead1(String name, float x, float y){
        this.name=name;
        physics = new Undead1Physics(name, x, y);
    }

    public void takeDamage(int damage){
        actions.takeDamage(damage);
    }

    public void draw(Batch batch){
        actions.update(physics);
        for (DamageNumber damageNumber : actions.getDamageNumbers()){
            damageNumber.draw(batch,physics.getBody().getPosition().x,physics.getBody().getPosition().y);
        }
        graphics.draw(batch, actions, physics.getBody().getPosition().x, physics.getBody().getPosition().y, actions.getHealth(), actions.getMaxHealth(), this);
    }

    public void die(){
        System.out.println("HI IM UNDEAD1 AND IM DEAD :D");
        darkknight.bodiesToDestroy.add(physics.getBody());
        shouldBeDeleted=true;
    }

    public Undead1Physics getPhysics(){
        return physics;
    }

    public String getName(){
        return name;
    }

    public void setInCombat(boolean bool) {
        actions.setInCombat(bool);
    }

    public boolean isShouldBeDeleted(){
        return shouldBeDeleted;
    }
}
