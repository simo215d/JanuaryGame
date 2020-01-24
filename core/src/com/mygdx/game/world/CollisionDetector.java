package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerCombat;
import com.mygdx.game.player.PlayerEffects.FireBall;
import com.mygdx.game.player.PlayerEffects.Orb;
import com.mygdx.game.world.allies.Merchant;
import com.mygdx.game.world.necromancer.BloodBall;
import com.mygdx.game.world.necromancer.BloodMeteor;
import com.mygdx.game.world.necromancer.Necromancer;
import com.mygdx.game.world.undead1.Undead1;
import com.mygdx.game.world.undead2.Undead2;
import com.mygdx.game.world.undead2.Undead2Arrow;

import java.util.ArrayList;
import java.util.logging.Level;

public class CollisionDetector implements ContactListener {
    public static ArrayList<String> currentContacts = new ArrayList<>();
    public static ArrayList<String> currentRightEnemies = new ArrayList<>();
    public static ArrayList<String> currentLeftEnemies = new ArrayList<>();

    @Override
    public void beginContact(Contact contact) {
        //Regarding fixture a and b: We don't know which is which, so we'll need to check.
        //The first letters of other objects are T or F. Current booleans: walkable, pushable, attackable, damageObject
        //System.out.println("ENTERED: FA: "+contact.getFixtureA().getUserData()+" FB: "+contact.getFixtureB().getUserData());
        //airborne checker
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().toString().charAt(0)=='T' || contact.getFixtureA().getUserData().toString().charAt(0)=='T' && contact.getFixtureB().getUserData().equals("Foot")){
            //if we land while we should be pushing it needs to know to go to pushing animation, because there is a scenario where
            //you jump without exiting pushing sensor, so we need to set it back to pushing
            if (darkknight.player.getPlayerMovement().getIsAirBorne()){
                //set animation state based on ongoing player-actions
                if (darkknight.player.getPlayerMovement().getPushingRight()){
                    darkknight.player.getPlayerGraphics().setAnimationState("pushingRight");
                }
                if (darkknight.player.getPlayerMovement().getPushingLeft()){
                    darkknight.player.getPlayerGraphics().setAnimationState("pushingLeft");
                }
            }
            darkknight.player.getPlayerMovement().setAirBorne(false);
            //check if our foot collided with a walkable, if so then add
            addToContacts(contact);
        }
        //shroom bounce
        //TODO WE SHOULD ADD A THIRD DATA BOOLEAN: BOUNCABLE BECAUSE WE MIGHT NEED MULTIBLE SHROOMS WITH DIFFERENT ID
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().equals("FFFFShroomCollider1") || contact.getFixtureA().getUserData().equals("FFFFShroomCollider1") && contact.getFixtureB().getUserData().equals("Foot")){
            if (contact.getFixtureB().getUserData().equals("FFFFShroomCollider1")){
                darkknight.player.getPlayerMovement().shroomBounce(contact.getFixtureB().getUserData().toString());
            } else darkknight.player.getPlayerMovement().shroomBounce(contact.getFixtureA().getUserData().toString());
        }
        //push right
        if (contact.getFixtureA().getUserData().equals("UpperRightBody") && contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperRightBody")){
            darkknight.player.getPlayerMovement().setPushingRight(true);
            darkknight.player.getPlayerGraphics().setAnimationState("pushingRight");
            darkknight.player.getPlayerGraphics().getSpritePlayer().setFlip(false,false);
        }
        //push left
        if (contact.getFixtureA().getUserData().equals("UpperLeftBody") && contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperLeftBody")){
            darkknight.player.getPlayerMovement().setPushingLeft(true);
            darkknight.player.getPlayerGraphics().setAnimationState("pushingLeft");
            darkknight.player.getPlayerGraphics().getSpritePlayer().setFlip(true,false);
        }
        //player right combat sensor
        if (contact.getFixtureA().getUserData().equals("RightCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("RightCombatSensor")){
            addToCurrentEnemies(contact,currentRightEnemies,"RightCombatSensor");
        }
        //player left combat sensor
        if (contact.getFixtureA().getUserData().equals("LeftCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T'|| contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("LeftCombatSensor")){
            addToCurrentEnemies(contact,currentLeftEnemies,"LeftCombatSensor");
        }
        //damage object
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().toString().charAt(3)=='T'|| contact.getFixtureA().getUserData().toString().charAt(3)=='T' && contact.getFixtureB().getUserData().equals("Foot")){
            for (DamageObject object : Level1.level1Enemies.damageObjects){
                if (object.getName().equals(contact.getFixtureA().getUserData()) || object.getName().equals(contact.getFixtureB().getUserData())){
                    object.setActive(true);
                }
            }
        }
        //fireBall. first make sure we only call the substring on strings that even have that amount of characters in it
        if (contact.getFixtureA().getUserData().toString().length()>=8 && contact.getFixtureB().getUserData().toString().length()>=8){
            //then check if its a fireBall and an enemy
            if (contact.getFixtureA().getUserData().toString().substring(0,8).equals("FireBall") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().toString().substring(0,8).equals("FireBall")){
                //check if fixture A is an enemy
                if (contact.getFixtureA().getUserData().toString().charAt(2)=='T'){
                    Level1.level1Enemies.attackAnEnemy(contact.getFixtureA().getUserData().toString(), PlayerCombat.attackFireballDamage);
                }
                //check if fixture B is an enemy
                if (contact.getFixtureB().getUserData().toString().charAt(2)=='T'){
                    Level1.level1Enemies.attackAnEnemy(contact.getFixtureB().getUserData().toString(), PlayerCombat.attackFireballDamage);
                }
                //destroy the fireball
                for (FireBall fireBall : darkknight.player.getPlayerCombat().getFireBalls()){
                    if (contact.getFixtureA().getUserData().toString().equals(fireBall.getName()) || contact.getFixtureB().getUserData().toString().equals(fireBall.getName())){
                        fireBall.setDestroying(true);
                        darkknight.bodiesToDestroy.add(fireBall.getBody());
                    }
                }
            } else {
                //check if fireBall hits a contact whose body is not sensor and does not belong to player
                if (contact.getFixtureA().getUserData().toString().substring(0,8).equals("FireBall") && contact.getFixtureB().getUserData().toString().charAt(2)!='T' && !contact.getFixtureB().getUserData().equals("PlayerBody") && !contact.getFixtureB().isSensor() || contact.getFixtureA().getUserData().toString().charAt(2)!='T' && !contact.getFixtureA().getUserData().equals("PlayerBody") && !contact.getFixtureA().isSensor() && contact.getFixtureB().getUserData().toString().substring(0,8).equals("FireBall")){
                    //destroy the fireball
                    System.out.println("we destroy fireball because it hit a non sensor and non attackable");
                    for (FireBall fireBall : darkknight.player.getPlayerCombat().getFireBalls()){
                        if (contact.getFixtureA().getUserData().toString().equals(fireBall.getName()) || contact.getFixtureB().getUserData().toString().equals(fireBall.getName())){
                            fireBall.setDestroying(true);
                            darkknight.bodiesToDestroy.add(fireBall.getBody());
                        }
                    }
                }
            }
        }
        //meteor. only fixtures where they are walkable and not enemies
        if (contact.getFixtureA().getUserData().equals("Meteor") && contact.getFixtureB().getUserData().toString().charAt(0)=='T' && contact.getFixtureB().getUserData().toString().charAt(2)=='F' || contact.getFixtureA().getUserData().toString().charAt(0)=='T' && contact.getFixtureA().getUserData().toString().charAt(2)=='F' && contact.getFixtureB().getUserData().equals("Meteor")){
            darkknight.player.getPlayerCombat().getCurrentMeteor().setDestroying(true);
            darkknight.bodiesToDestroy.add(darkknight.player.getPlayerCombat().getCurrentMeteor().getBody());
        }
        //meteor if it hits an enemy
        if (contact.getFixtureA().getUserData().equals("Meteor") || contact.getFixtureB().getUserData().equals("Meteor")){
            //check fixture A if is enemy
            if (contact.getFixtureA().getUserData().toString().charAt(2)=='T'){
                Level1.level1Enemies.attackAnEnemy(contact.getFixtureA().getUserData().toString(),PlayerCombat.attackMeteorDamage);
            }
            //check fixture B if is enemy
            if (contact.getFixtureB().getUserData().toString().charAt(2)=='T'){
                Level1.level1Enemies.attackAnEnemy(contact.getFixtureB().getUserData().toString(),PlayerCombat.attackMeteorDamage);
            }
        }
        //orb collision
        if (contact.getFixtureA().getUserData().toString().length()>=9 && contact.getFixtureB().getUserData().toString().length()>=9){
            //then check if its a fireBall and an enemy
            if (contact.getFixtureA().getUserData().toString().substring(0,9).equals("playerOrb") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().toString().substring(0,9).equals("playerOrb")){
                //check if fixture A is an enemy
                if (contact.getFixtureA().getUserData().toString().charAt(2)=='T'){
                    Level1.level1Enemies.attackAnEnemy(contact.getFixtureA().getUserData().toString(), PlayerCombat.attackOrbDamage);
                }
                //check if fixture B is an enemy
                if (contact.getFixtureB().getUserData().toString().charAt(2)=='T'){
                    Level1.level1Enemies.attackAnEnemy(contact.getFixtureB().getUserData().toString(), PlayerCombat.attackOrbDamage);
                }
                //destroy the orb body
                for (Orb orb : darkknight.player.getPlayerCombat().getOrbs()){
                    if (contact.getFixtureA().getUserData().toString().equals(orb.getName()) || contact.getFixtureB().getUserData().toString().equals(orb.getName())){
                        orb.setAnimationState("Exploding");
                        System.out.println("we destroy "+orb.getName()+" because it hits an enemy");
                        darkknight.bodiesToDestroy.add(orb.getBody());
                        orb.setBodyHasBeenDeleted(true);
                    }
                }
            } else {
                //check if orb hits a contact whose body is not sensor and does not belong to player
                if (contact.getFixtureA().getUserData().toString().substring(0,9).equals("playerOrb") && contact.getFixtureB().getUserData().toString().charAt(2)!='T' && !contact.getFixtureB().getUserData().equals("PlayerBody") && !contact.getFixtureB().isSensor() || contact.getFixtureA().getUserData().toString().charAt(2)!='T' && !contact.getFixtureA().getUserData().equals("PlayerBody") && !contact.getFixtureA().isSensor() && contact.getFixtureB().getUserData().toString().substring(0,9).equals("playerOrb")){
                    //destroy the orb
                    System.out.println("we destroy orb because it hit a non sensor and non attackable");
                    for (Orb orb : darkknight.player.getPlayerCombat().getOrbs()){
                        if (contact.getFixtureA().getUserData().toString().equals(orb.getName()) || contact.getFixtureB().getUserData().toString().equals(orb.getName())){
                            if (!orb.getAnimationState().equals("Stationary")) {
                                orb.setAnimationState("Exploding");
                                System.out.println("we destroy " + orb.getName() + " because it hits an object");
                                darkknight.bodiesToDestroy.add(orb.getBody());
                                orb.setBodyHasBeenDeleted(true);
                            }
                        }
                    }
                }
            }
        }
        //fire breath
        if (contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().toString().equals("playerFireBreath") || contact.getFixtureA().getUserData().toString().equals("playerFireBreath") && contact.getFixtureB().getUserData().toString().charAt(2)=='T'){
            if (contact.getFixtureA().getUserData().toString().charAt(2)=='T')
            darkknight.player.getPlayerCombat().getFireBreath().pushBackEnemy(contact.getFixtureA().getBody());
            else darkknight.player.getPlayerCombat().getFireBreath().pushBackEnemy(contact.getFixtureB().getBody());
        }
        //forest guard text enable
        if (contact.getFixtureA().getUserData().toString().equals("AllyForestGuard") && contact.getFixtureB().getUserData().toString().equals("PlayerBody") ||  contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("AllyForestGuard")){
            Level1.level1Allies.getGuard1().setTalking(true);
        }
        //check for combatSensors
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().length()>13 || contact.getFixtureA().getUserData().toString().length()>13 && contact.getFixtureB().getUserData().toString().equals("PlayerBody")){
            if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().substring(0, 13).equals("CombatSensor_")) {
                System.out.println("we found a combatSensor that belongs to: "+contact.getFixtureB().getUserData().toString().substring(13));
                Level1.level1Enemies.enterCombatWith(contact.getFixtureB().getUserData().toString().substring(13));
            }
            if (contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().substring(0, 13).equals("CombatSensor_")) {
                System.out.println("we found a combatSensor that belongs to: "+contact.getFixtureA().getUserData().toString().substring(13));
                Level1.level1Enemies.enterCombatWith(contact.getFixtureA().getUserData().toString().substring(13));
            }
        }
        //check for undead 2 arrows if collision is not player and not attackable but is walkable
        if (contact.getFixtureA().getUserData().toString().length()>=10 && contact.getFixtureB().getUserData().toString().charAt(0)=='T' && contact.getFixtureB().getUserData().toString().charAt(2)=='F' || contact.getFixtureA().getUserData().toString().charAt(0)=='T' && contact.getFixtureA().getUserData().toString().charAt(2)=='F' && contact.getFixtureB().getUserData().toString().length()>=10){
            if (contact.getFixtureA().getUserData().toString().substring(0,9).equals("FFFFArrow")){
                for (Undead2 undead2 : Level1.level1Enemies.undead2s){
                    if (contact.getFixtureA().getUserData().toString().substring(contact.getFixtureA().getUserData().toString().length()-3).equals(undead2.getName().substring(undead2.getName().length()-3)));
                    undead2.getActions().destroyAnArrow(contact.getFixtureA().getUserData().toString());
                }
            }
            if (contact.getFixtureB().getUserData().toString().substring(0,9).equals("FFFFArrow")){
                for (Undead2 undead2 : Level1.level1Enemies.undead2s){
                    if (contact.getFixtureB().getUserData().toString().substring(contact.getFixtureB().getUserData().toString().length()-3).equals(undead2.getName().substring(undead2.getName().length()-3)));
                    undead2.getActions().destroyAnArrow(contact.getFixtureB().getUserData().toString());
                }
            }
        }
        //check for undead 2 arrows if collision is with player
        if (contact.getFixtureA().getUserData().toString().length()>=10 && contact.getFixtureB().getUserData().toString().equals("PlayerBody") || contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().length()>=10){
            if (contact.getFixtureA().getUserData().toString().substring(0,9).equals("FFFFArrow")){
                for (Undead2 undead2 : Level1.level1Enemies.undead2s){
                    //make arrow damage player if player is not facing arrow and shielding
                    for (Undead2Arrow undead2Arrow : undead2.getActions().getArrows()){
                        if (undead2Arrow.getName().equals(contact.getFixtureA().getUserData().toString())){
                            if (!(undead2Arrow.getBody().getLinearVelocity().x<0 && !darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding()) && !(undead2Arrow.getBody().getLinearVelocity().x>0 && darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding())){
                                darkknight.player.getPlayerCombat().takeDamage(Undead2Arrow.damage);
                            } else darkknight.player.getPlayerCombat().block();
                        }
                    }
                    //destroy the arrow
                    if (contact.getFixtureA().getUserData().toString().substring(contact.getFixtureA().getUserData().toString().length()-3).equals(undead2.getName().substring(undead2.getName().length()-3)));
                    undead2.getActions().destroyAnArrow(contact.getFixtureA().getUserData().toString());
                }
            }
            if (contact.getFixtureB().getUserData().toString().substring(0,9).equals("FFFFArrow")){
                for (Undead2 undead2 : Level1.level1Enemies.undead2s){
                    //make arrow damage player if player is not facing arrow and shielding
                    for (Undead2Arrow undead2Arrow : undead2.getActions().getArrows()){
                        if (undead2Arrow.getName().equals(contact.getFixtureB().getUserData().toString())){
                            if (!(undead2Arrow.getBody().getLinearVelocity().x<0 && !darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding()) && !(undead2Arrow.getBody().getLinearVelocity().x>0 && darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding())){
                                darkknight.player.getPlayerCombat().takeDamage(Undead2Arrow.damage);
                            } else darkknight.player.getPlayerCombat().block();
                        }
                    }
                    //destroy the arrow
                    if (contact.getFixtureB().getUserData().toString().substring(contact.getFixtureB().getUserData().toString().length()-3).equals(undead2.getName().substring(undead2.getName().length()-3)));
                    undead2.getActions().destroyAnArrow(contact.getFixtureB().getUserData().toString());
                }
            }
        }
        //toxic shroom on collision with enemies
        if (contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().toString().length()>=15 && contact.getFixtureB().getUserData().toString().substring(0,15).equals("FFFFShroomToxic") || contact.getFixtureA().getUserData().toString().length()>=15 && contact.getFixtureA().getUserData().toString().substring(0,15).equals("FFFFShroomToxic") && contact.getFixtureA().getUserData().toString().charAt(2)=='T'){
            if (contact.getFixtureA().getUserData().toString().length()>=15 && contact.getFixtureA().getUserData().toString().substring(0,15).equals("FFFFShroomToxic")){
                //find the shroom and explode it
                for (ShroomToxic shroomToxic : Level1.level1Enemies.shroomToxics){
                    if (shroomToxic.getName().equals(contact.getFixtureA().getUserData().toString())){
                        shroomToxic.setExploding(true);
                    }
                }
                //find the enemy and deal damage to it
                for (Undead1 undead1 : Level1.level1Enemies.undead1s){
                    if (undead1.getName().equals(contact.getFixtureB().getUserData().toString())){
                        undead1.takeDamage(ShroomToxic.damage);
                    }
                }
                for (Undead2 undead2 : Level1.level1Enemies.undead2s){
                    if (undead2.getName().equals(contact.getFixtureB().getUserData().toString())){
                        undead2.takeDamage(ShroomToxic.damage);
                    }
                }
            }
            if (contact.getFixtureB().getUserData().toString().length()>=15 && contact.getFixtureB().getUserData().toString().substring(0,15).equals("FFFFShroomToxic")){
                //find the shroom and explode it
                for (ShroomToxic shroomToxic : Level1.level1Enemies.shroomToxics){
                    if (shroomToxic.getName().equals(contact.getFixtureB().getUserData().toString())){
                        shroomToxic.setExploding(true);
                    }
                }
                //find the enemy and deal damage to it
                for (Undead1 undead1 : Level1.level1Enemies.undead1s){
                    if (undead1.getName().equals(contact.getFixtureA().getUserData().toString())){
                        undead1.takeDamage(ShroomToxic.damage);
                    }
                }
                for (Undead2 undead2 : Level1.level1Enemies.undead2s){
                    if (undead2.getName().equals(contact.getFixtureA().getUserData().toString())){
                        undead2.takeDamage(ShroomToxic.damage);
                    }
                }
            }
        }
        //toxic shroom on collision with player
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().length()>=15 && contact.getFixtureB().getUserData().toString().substring(0,15).equals("FFFFShroomToxic") || contact.getFixtureA().getUserData().toString().length()>=15 && contact.getFixtureA().getUserData().toString().substring(0,15).equals("FFFFShroomToxic") && contact.getFixtureB().getUserData().toString().equals("PlayerBody")){
            //find the shroom and explode it
            if (contact.getFixtureA().getUserData().toString().length()>=15 && contact.getFixtureA().getUserData().toString().substring(0,15).equals("FFFFShroomToxic")) {
                for (ShroomToxic shroomToxic : Level1.level1Enemies.shroomToxics) {
                    if (shroomToxic.getName().equals(contact.getFixtureA().getUserData().toString())) {
                        shroomToxic.setExploding(true);
                    }
                }
            }
            //find the shroom and explode it
            if (contact.getFixtureB().getUserData().toString().length()>=15 && contact.getFixtureB().getUserData().toString().substring(0,15).equals("FFFFShroomToxic")) {
                for (ShroomToxic shroomToxic : Level1.level1Enemies.shroomToxics) {
                    if (shroomToxic.getName().equals(contact.getFixtureB().getUserData().toString())) {
                        shroomToxic.setExploding(true);
                    }
                }
            }
            //damage player
            darkknight.player.getPlayerCombat().takeDamage(ShroomToxic.damage);
        }
        //bridge lift sensor
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("LiftSensor") || contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().equals("LiftSensor")){
            BridgeLift.playerIsNear=true;
        }
        //bridge torch sensor
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("FFFFTorchSensor") || contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().equals("FFFFTorchSensor")){
            BridgeTorch.playerIsNear=true;
        }
        //bridge merchant sensor
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("FFFFPotionWagonSensor") || contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().equals("FFFFPotionWagonSensor")){
            Merchant.playerIsNear=true;
        }
        //check for BloodBall if collision is with player
        if (contact.getFixtureA().getUserData().toString().length()>=10 && contact.getFixtureB().getUserData().toString().equals("PlayerBody") || contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().length()>=10){
            if (contact.getFixtureA().getUserData().toString().substring(0,9).equals("BloodBall")){
                //make ball damage player if player is not facing ball and shielding
                for (BloodBall bloodBall : darkknight.level1.level1Enemies.getNecromancer1().getActions().getBloodBalls()){
                    if (bloodBall.getName().equals(contact.getFixtureA().getUserData().toString())){
                        if (!(bloodBall.getBody().getLinearVelocity().x<0 && !darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding())){
                                darkknight.player.getPlayerCombat().takeDamage(BloodBall.damage);
                        } else darkknight.player.getPlayerCombat().block();
                    }
                }
                //destroy the arrow
                Level1.level1Enemies.getNecromancer1().getActions().destroyBloodBall(contact.getFixtureA().getUserData().toString());
            }
            if (contact.getFixtureB().getUserData().toString().substring(0,9).equals("BloodBall")){
                //make ball damage player if player is not facing ball and shielding
                for (BloodBall bloodBall : darkknight.level1.level1Enemies.getNecromancer1().getActions().getBloodBalls()){
                    if (bloodBall.getName().equals(contact.getFixtureB().getUserData().toString())){
                        if (!(bloodBall.getBody().getLinearVelocity().x<0 && !darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding())){
                            darkknight.player.getPlayerCombat().takeDamage(BloodBall.damage);
                        } else darkknight.player.getPlayerCombat().block();
                    }
                }
                //destroy the blood ball
                Level1.level1Enemies.getNecromancer1().getActions().destroyBloodBall(contact.getFixtureB().getUserData().toString());
            }
        }
        //check for blood balls if collision is not player and not attackable but is pushable
        if (contact.getFixtureA().getUserData().toString().length()>=10 && contact.getFixtureB().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().toString().charAt(2)=='F' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureA().getUserData().toString().charAt(2)=='F' && contact.getFixtureB().getUserData().toString().length()>=10){
            if (contact.getFixtureA().getUserData().toString().substring(0,9).equals("BloodBall")){
                Level1.level1Enemies.getNecromancer1().getActions().destroyBloodBall(contact.getFixtureA().getUserData().toString());
            }
            if (contact.getFixtureB().getUserData().toString().substring(0,9).equals("BloodBall")){
                Level1.level1Enemies.getNecromancer1().getActions().destroyBloodBall(contact.getFixtureB().getUserData().toString());
            }
        }
        //check for blood meteor if collision is not player and not attackable but is walkable
        if (contact.getFixtureA().getUserData().toString().equals("BloodMeteor") && contact.getFixtureB().getUserData().toString().charAt(0)=='T' && contact.getFixtureB().getUserData().toString().charAt(2)=='F' || contact.getFixtureA().getUserData().toString().charAt(0)=='T' && contact.getFixtureA().getUserData().toString().charAt(2)=='F' && contact.getFixtureB().getUserData().toString().equals("BloodMeteor")){
            if (!Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().isExploding()) {
                Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().setExploding(true);
                darkknight.bodiesToDestroy.add(Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().getBody());
            }
            if (!Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().isExploding()) {
                Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().setExploding(true);
                darkknight.bodiesToDestroy.add(Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().getBody());
            }
        }
        //blood meteor and player body
        //check for blood meteor if collision is not player and not attackable but is walkable
        if (contact.getFixtureA().getUserData().toString().equals("BloodMeteor") && contact.getFixtureB().getUserData().toString().equals("PlayerBody") || contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("BloodMeteor")){
            //damage player if not shielding and facing right
            if (!(!darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX() && darkknight.player.getPlayerCombat().isShielding())){
                darkknight.player.getPlayerCombat().takeDamage(BloodMeteor.damage);
            }
            if (!Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().isExploding()) {
                Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().setExploding(true);
                darkknight.bodiesToDestroy.add(Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().getBody());
            }
            if (!Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().isExploding()) {
                Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().setExploding(true);
                darkknight.bodiesToDestroy.add(Level1.level1Enemies.getNecromancer1().getActions().getBloodMeteor().getBody());
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //System.out.println("EXITED: FA: "+contact.getFixtureA().getUserData()+" FB: "+contact.getFixtureB().getUserData());
        //check if something exited from our foot. important that we check this before we use it in our statement next.
        if (contact.getFixtureA().getUserData().equals("Foot") || contact.getFixtureB().getUserData().equals("Foot")) {
            removeFromContacts(contact);
        }
        //airborne checker
        if (contact.getFixtureA().getUserData().equals("Foot") && getCountOfWalkableContacts()==0 || getCountOfWalkableContacts()==0 && contact.getFixtureB().getUserData().equals("Foot")){
            darkknight.player.getPlayerMovement().setAirBorne(true);
            darkknight.player.getPlayerGraphics().setAnimationState("jumping");
        }
        //push right
        if (contact.getFixtureA().getUserData().equals("UpperRightBody") &&contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperRightBody")){
            darkknight.player.getPlayerMovement().setPushingRight(false);
            if (darkknight.player.getPlayerMovement().getIsAirBorne()){
                darkknight.player.getPlayerGraphics().setAnimationState("jumping");
            }
        }
        //push left
        if (contact.getFixtureA().getUserData().equals("UpperLeftBody") && contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperLeftBody")){
            darkknight.player.getPlayerMovement().setPushingLeft(false);
            if (darkknight.player.getPlayerMovement().getIsAirBorne()){
                darkknight.player.getPlayerGraphics().setAnimationState("jumping");
            }
        }
        //player right combat sensor
        if (contact.getFixtureA().getUserData().equals("RightCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("RightCombatSensor")){
            removeFromCurrentEnemies(contact,currentRightEnemies);
        }
        //player left combat sensor
        if (contact.getFixtureA().getUserData().equals("LeftCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T'|| contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("LeftCombatSensor")){
            removeFromCurrentEnemies(contact,currentLeftEnemies);
        }
        //damage object
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().toString().charAt(3)=='T'|| contact.getFixtureA().getUserData().toString().charAt(3)=='T' && contact.getFixtureB().getUserData().equals("Foot")){
            for (DamageObject object : Level1.level1Enemies.damageObjects){
                if (object.getName().equals(contact.getFixtureA().getUserData()) || object.getName().equals(contact.getFixtureB().getUserData())){
                    object.setActive(false);
                }
            }
        }
        //forest guard text disable
        if (contact.getFixtureA().getUserData().toString().equals("AllyForestGuard") && contact.getFixtureB().getUserData().toString().equals("PlayerBody") ||  contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("AllyForestGuard")){
            Level1.level1Allies.getGuard1().setTalking(false);
        }
        //bridge lift sensor
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("LiftSensor") || contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().equals("LiftSensor")){
            BridgeLift.playerIsNear=false;
        }
        //bridge torch sensor
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("FFFFTorchSensor") || contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().equals("FFFFTorchSensor")){
            BridgeTorch.playerIsNear=false;
        }
        //bridge merchant sensor
        if (contact.getFixtureA().getUserData().toString().equals("PlayerBody") && contact.getFixtureB().getUserData().toString().equals("FFFFPotionWagonSensor") || contact.getFixtureB().getUserData().toString().equals("PlayerBody") && contact.getFixtureA().getUserData().toString().equals("FFFFPotionWagonSensor")){
            Merchant.playerIsNear=false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    //we use this to check if we have 0 walkable contacts then allow for airborne
    private int getCountOfWalkableContacts(){
        int countsOfWalkable = 0;
        for (String string : currentContacts){
            if (string.charAt(0)=='T'){
                countsOfWalkable++;
            }
        }
        return countsOfWalkable;
    }

    //find a non player string that ended collision with foot and remove from list
    private void removeFromContacts(Contact contact) {
        //find a non player string in fixtureA
        String fixtureAString = contact.getFixtureA().getUserData().toString();
        int ia = -1;
        for (String aUserdata : currentContacts) {
            if (fixtureAString.equals(aUserdata)) {
                ia = currentContacts.indexOf(aUserdata);
            }
        }
        if (ia >= 0) {
            currentContacts.remove(ia);
            //System.out.println("we ended contact with: "+fixtureAString);
        }
        //find a non player string in fixtureB
        String fixtureBString = contact.getFixtureB().getUserData().toString();
        int ib = -1;
        for (String bUserdata : currentContacts){
            if (fixtureBString.equals(bUserdata)){
                ib=currentContacts.indexOf(bUserdata);
            }
        }
        if (ib>=0) {
            currentContacts.remove(ib);
            //System.out.println("we ended contact with: "+fixtureBString);
        }
    }

    //if our foot is involved in an enter collision then add the thing we collided with
    private void addToContacts(Contact contact){
        String fixtureAString = contact.getFixtureA().getUserData().toString();
        String fixtureBString = contact.getFixtureB().getUserData().toString();
        if (fixtureAString.equals("Foot")){
            currentContacts.add(fixtureBString);
        }
        if (fixtureBString.equals("Foot")){
            currentContacts.add(fixtureAString);
        }
    }

    private void addToCurrentEnemies(Contact contact, ArrayList<String> list, String playerSensor){
        String fixA = contact.getFixtureA().getUserData().toString();
        String fixB = contact.getFixtureB().getUserData().toString();
        if (fixA.equals(playerSensor)){
            list.add(fixB);
        } else list.add(fixA);
    }

    private void removeFromCurrentEnemies(Contact contact, ArrayList<String> list){
        String fixA = contact.getFixtureA().getUserData().toString();
        String fixB = contact.getFixtureB().getUserData().toString();
        int stringToRemove = -1;
        for (String string : list){
            if (fixA.equals(string) || fixB.equals(string)){
                stringToRemove=list.indexOf(string);
            }
        }
        if (stringToRemove>-1){
            list.remove(stringToRemove);
        }
    }
}
