package com.mygdx.game.world.targetdummy;

public class TargetDummyActions {
    //TODO
    //private boolean isAttacked = false;
    private int health = 3;

    public void takeDamage(int damage){
        health-=damage;
        System.out.println("i took damage: "+damage+" any my health is: "+health+".");
    }
}
