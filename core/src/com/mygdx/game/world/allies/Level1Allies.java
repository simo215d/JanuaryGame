package com.mygdx.game.world.allies;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Level1Allies {
    Guard1 guard1 = new Guard1();
    public void draw(Batch batch){
        guard1.draw(batch);
    }

    public Guard1 getGuard1(){
        return guard1;
    }
}
