package com.mygdx.game.world.undead1;

import com.mygdx.game.darkknight;

public class Undead1Actions {
    private String actionState = "idle";
    private int health;
    private int maxHealth;
    private boolean inCombat = false;
    //this is used to make sure he doesnt jump when he first starts running because his velocity is 0 when he starts
    private boolean hasMoved = false;

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
        inCombat=true;
    }

    public void update(Undead1Physics physics){
        float distanceToPlayer = physics.getBody().getPosition().x-darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x;
        //run
        if (inCombat && distanceToPlayer>15 || inCombat && distanceToPlayer<-15){
            System.out.println("run");
            //jump if stuck
            if (physics.getBody().getLinearVelocity().x==0.0f && hasMoved){
                //jump right
                if (distanceToPlayer<0){
                    physics.getBody().setLinearVelocity(physics.getBody().getLinearVelocity().x,10);
                    System.out.println("JUMP");
                }
                //jump left
                if (distanceToPlayer>0){
                    physics.getBody().setLinearVelocity(physics.getBody().getLinearVelocity().x,10);
                    System.out.println("JUMP");
                }
            }
            //run left else right
            if (distanceToPlayer>0){
                physics.getBody().setLinearVelocity(-10,physics.getBody().getLinearVelocity().y);
            } else physics.getBody().setLinearVelocity(10,physics.getBody().getLinearVelocity().y);
            hasMoved=true;
        } else hasMoved=false;
        //fight
        if (inCombat && distanceToPlayer<=15 && distanceToPlayer>=-15){
            System.out.println("fight");
            physics.getBody().setLinearVelocity(0,physics.getBody().getLinearVelocity().y);
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

    public void setInCombat(Boolean bool){
        inCombat=bool;
    }
}
