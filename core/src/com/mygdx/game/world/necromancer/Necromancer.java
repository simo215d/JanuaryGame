package com.mygdx.game.world.necromancer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.world.DamageNumber;

public class Necromancer {
    private String name = "TTTFNecromancerBody";
    private NecromancerActions necromancerActions;
    private NecromancerGraphics necromancerGraphics;
    private NecromancerPhysics necromancerPhysics;

    public Necromancer(){
        necromancerActions = new NecromancerActions();
        necromancerGraphics = new NecromancerGraphics();
        necromancerPhysics = new NecromancerPhysics(name);
    }

    public void takeDamage(int damage){
        necromancerActions.takeDamage(damage);
    }

    public void update(Batch batch){
        necromancerGraphics.draw(batch, necromancerPhysics, necromancerActions);
        necromancerActions.update();
        for (DamageNumber damageNumber : necromancerActions.getDamageNumbers()){
            damageNumber.draw(batch, necromancerPhysics.getBody().getPosition().x-1, necromancerPhysics.getBody().getPosition().y);
        }
        for (BloodBall bloodBall : necromancerActions.getBloodBalls()){
            bloodBall.draw(batch);
        }
        if (necromancerActions.getBloodMeteor()!=null){
            necromancerActions.getBloodMeteor().draw(batch, necromancerActions);
        }
    }

    public NecromancerActions getNecromancerActions(){
        return necromancerActions;
    }

    public NecromancerGraphics getNecromancerGraphics(){
        return necromancerGraphics;
    }

    public NecromancerPhysics getNecromancerPhysics(){
        return necromancerPhysics;
    }

    public String getName(){
        return name;
    }

    public void setInCombat(boolean b){
        necromancerActions.setInCombat(b);
    }

    public NecromancerActions getActions(){
        return necromancerActions;
    }
}
