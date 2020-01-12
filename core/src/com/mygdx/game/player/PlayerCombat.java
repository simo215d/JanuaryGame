package com.mygdx.game.player;

import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerEffects.*;
import com.mygdx.game.world.CollisionDetector;
import com.mygdx.game.world.Level1;

import java.util.ArrayList;

public class PlayerCombat {
    private int maxHealth;
    private int health;
    private boolean isImmuneToDamage = false;
    private int maxMana;
    private int mana;
    private int manaGenerationRate = 1;
    private int previousManaTime = 0;
    //attack damage
    private int attackSwordDamage = 2;
    public static int attackFireballDamage = 4;
    public static int attackOrbDamage = 2;
    public static int attackMeteorDamage = 8;
    //mana costs
    public static int attackSwordMana =0;
    public static int attackShieldMana =25;
    public static int attackFireballMana =12;
    public static int attackOrbsMana =3;
    public static int attackMeteorMana =42;
    public static int attackFireBreathMana =23;
    //spell keepers
    private ArrayList<FireBall> fireBalls = new ArrayList<>();
    //we need this variable, because we need unique names for every fire ball because of collision detection
    private int fireBallCount = 0;
    private Meteor currentMeteor;
    private ArrayList<Orb> orbs = new ArrayList<>();
    private FireShield fireShield = null;
    private FireBreath fireBreath = null;
    private boolean isShielding = false;
    private ShieldBlockEffect shieldBlockEffect = null;

    public PlayerCombat(){
        maxHealth = 100;
        health=maxHealth;
        maxMana = 100;
        mana=maxMana;
    }

    public void takeDamage(int damage){
        if (!isImmuneToDamage) {
            health -= damage;
            Player.renderRed =true;
            Player.renderRedStartTime =(int)darkknight.gameTimeCentiSeconds;
            if (health <= 0) {
                health = maxHealth;
            }
        } else System.out.println("im immune bitch!");
    }

    //this is for when a real enemy attacks you and we need to check if blockable
    public void takeDamage(int damage, float distance){
        System.out.println("shielding: "+isShielding+" distance: "+distance+" isFlip: "+darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX());
        if (isShielding && distance<0 && darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() || isShielding && distance>0 && !darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()) {
            if (!darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
                darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(-7,0);
            } else darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(7,0);
            setFireShieldToNull();
            shieldBlockEffect=new ShieldBlockEffect(darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x,darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y);
        } else if (isImmuneToDamage){
            System.out.println("im immune bitch!");
        } else {
            health -= damage;
            Player.renderRed =true;
            Player.renderRedStartTime =(int)darkknight.gameTimeCentiSeconds;
            if (health <= 0) {
                health = maxHealth;
            }
        }
    }

    public void drainMana(int spellNumber){
        switch (spellNumber){
            case 1: mana-= attackSwordMana; break;
            case 2: mana-= attackFireballMana; break;
            case 3: mana-= attackOrbsMana; break;
            case 4: mana-= attackShieldMana; break;
            case 5: mana-= attackMeteorMana; break;
            case 6: mana-= attackFireBreathMana; break;
        }
    }

    public void generateMana(){
        if ((int)darkknight.gameTimeCentiSeconds/10>previousManaTime){
            previousManaTime=(int)darkknight.gameTimeCentiSeconds/10;
            if (mana<=100-manaGenerationRate){
                mana+=manaGenerationRate;
            } else mana=maxMana;
        }
    }

    //sword
    public void attackSword(){
        if (mana>= attackSwordMana) {
            drainMana(1);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            //if the player jumps and immediately presses 1, then his foot is still grounded.
            // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
            // therefore we need to make sure that the player is static when he attacks
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attackingSword");
            //handling the actual attack logic
            //if sprite facing left
            if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()) {
                if (CollisionDetector.currentLeftEnemies.size() > 0) {
                    for (String string : CollisionDetector.currentLeftEnemies) {
                        Level1.level1Enemies.attackAnEnemy(string, attackSwordDamage);
                    }
                }
            }
            //if sprite facing right
            if (!darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()) {
                if (CollisionDetector.currentRightEnemies.size() > 0) {
                    for (String string : CollisionDetector.currentRightEnemies) {
                        Level1.level1Enemies.attackAnEnemy(string, attackSwordDamage);
                    }
                }
            }
        }
    }

    //fireball
    public void attackFireball(){
        if (mana>= attackFireballMana) {
            drainMana(2);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            //if the player jumps and immediately presses 1, then his foot is still grounded.
            // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
            // therefore we need to make sure that the player is static when he attacks
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attackingFireball");
            //handling the actual attack logic
            darkknight.player.getPlayerCombat().attack2FireBall();
        }
    }

    //this method can be called from the animation or directly from above method depending on when fireball should spawn
    public void attack2FireBall(){
        FireBall fireBall = new FireBall("FireBall"+fireBallCount);
        fireBallCount++;
        fireBalls.add(fireBall);
    }

    //meteor
    public void attackMeteor(){
        //i only allow 1 meteor
        if (currentMeteor==null && mana>= attackMeteorMana) {
            drainMana(5);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attackingMeteor");
            currentMeteor = new Meteor();
        }
    }

    //orbs
    public void attackOrbs(){
        if (orbs.size()>0){
            for (Orb orb : orbs){
                if (orb.getAnimationState().equals("Stationary")){
                    orb.setAnimationState("Launching");
                    break;
                }
            }
        }
        if (orbs.size()==0 && mana>= attackOrbsMana){
            drainMana(3);
            Player.orbsToDelete.clear();
            for (int i = 1; i <= 3; i++) {
                orbs.add(new Orb("playerOrb"+i));
            }
        }
    }

    /*
    //Fire Shield
    public void attackShield(){
        if (fireShield==null && mana>= attackShieldMana){
            drainMana(4);
            fireShield = new FireShield();
            //sets player to be immune
            setImmuneToDamage(true);
            //in the fireShield class this is set to false when it dies
        }
    }
     */

    public void attackShield(){
        if (!isShielding){
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            System.out.println("SET VELOCITY TO 0");
        }
        isShielding=true;
        darkknight.player.getPlayerMovement().setAttacking1(true);
        darkknight.player.getPlayerGraphics().setAnimationState("attackingShield");
    }

    //FireBreath
    public void attackFireBreath(){
        if (fireBreath==null && mana>= attackFireBreathMana){
            drainMana(6);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attackingFireBreath");
            fireBreath = new FireBreath();
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<FireBall> getFireBalls(){
        return fireBalls;
    }

    public Meteor getCurrentMeteor() {
        return currentMeteor;
    }

    public void setCurrentMeteorToNull(){
        currentMeteor=null;
    }

    public ArrayList<Orb> getOrbs(){
        return orbs;
    }

    public FireShield getFireShield(){
        return fireShield;
    }

    public void setFireShieldToNull(){
        fireShield=null;
    }

    public void setImmuneToDamage(boolean bool){
        isImmuneToDamage=bool;
    }

    public boolean getIsImmuneToDamage(){
        return isImmuneToDamage;
    }

    public void setFireBreathToNull(){
        fireBreath=null;
    }

    public FireBreath getFireBreath(){
        return fireBreath;
    }

    public int getMaxMana(){
        return maxMana;
    }

    public int getMana(){
        return mana;
    }

    public boolean isShielding(){
        return isShielding;
    }

    public void setShielding(boolean bool){
        isShielding=bool;
    }

    public void setShieldBlockEffectToNull(){
        shieldBlockEffect=null;
    }

    public ShieldBlockEffect getShieldBlockEffect(){
        return shieldBlockEffect;
    }
}
