package com.mygdx.game.world.targetdummy;

public class TargetDummyActions {
    //TODO
    //private boolean isAttacked = false;
    private int maxHealth = 10;
    private int health = 10;

    public void takeDamage(int damage){
        health-=damage;
        if (health<=0){
            health=maxHealth;
        }
        //System.out.println("i took damage: "+damage+" any my health is: "+health+".");
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }
}
