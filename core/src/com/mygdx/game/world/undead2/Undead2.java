package com.mygdx.game.world.undead2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.darkknight;
import com.mygdx.game.world.DamageNumber;

public class Undead2 {
    private String name;
    private Undead2Physics physics;
    private Undead2Graphics graphics = new Undead2Graphics();
    private Undead2Actions actions = new Undead2Actions();
    private boolean shouldBeDeleted = false;

    public Undead2(String name, float x, float y){
        this.name=name;
        physics = new Undead2Physics(name, x, y);
    }

    public void takeDamage(int damage){
        actions.takeDamage(damage);
    }

    public void draw(Batch batch){
        actions.update();
        for (DamageNumber damageNumber : actions.getDamageNumbers()){
            damageNumber.draw(batch,physics.getBody().getPosition().x,physics.getBody().getPosition().y);
        }
        graphics.draw(batch, actions, physics.getBody().getPosition().x, physics.getBody().getPosition().y, actions.getHealth(), actions.getMaxHealth(), this);
        for (Undead2Arrow undead2Arrow : actions.getArrows()){
            undead2Arrow.draw(batch);
        }
    }

    public void die(){
        System.out.println("HI IM UNDEAD2 AND IM DEAD :D");
        darkknight.bodiesToDestroy.add(physics.getBody());
        shouldBeDeleted=true;
    }

    public Undead2Physics getPhysics(){
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

    public Undead2Actions getActions(){
        return actions;
    }
}
