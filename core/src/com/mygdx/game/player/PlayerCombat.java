package com.mygdx.game.player;

public class PlayerCombat {
    private int maxHealth;
    private int health;

    public PlayerCombat(){
        maxHealth = 100;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        health-=damage;
        if (health<=0){
            health=maxHealth;
        }
        System.out.println("im player and i took: "+damage+" damage.");
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }
}
