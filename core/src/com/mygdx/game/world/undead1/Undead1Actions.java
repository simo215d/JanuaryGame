package com.mygdx.game.world.undead1;

public class Undead1Actions {
    private String actionState = "idle";
    private int health;
    private int maxHealth;

    public Undead1Actions(){
        maxHealth=20;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        if(health-damage<=0){
            health=maxHealth;
        } else{
            health-=damage;
            System.out.println("undead took "+damage+" damage...");
        }
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
