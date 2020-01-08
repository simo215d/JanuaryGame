package com.mygdx.game.world.undead1;

public class Undead1Actions {
    private String actionState = "idle";
    public boolean isAttacked = false;
    private int health;
    private int maxHealth;

    public Undead1Actions(){
        maxHealth=100;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        health-=damage;
        System.out.println("undead took "+damage+" damage...");
    }

    public String getActionState() {
        return actionState;
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }
}
