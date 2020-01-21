package com.mygdx.game.world.allies;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Level1Allies {
    Guard1 guard1 = new Guard1();
    Crow crow1;

    public void draw(Batch batch){
        if (crow1!=null)
        crow1.draw(batch);
        guard1.draw(batch);
    }

    public Guard1 getGuard1(){
        return guard1;
    }

    public void startCrow(){
        crow1 = new Crow();
    }
}
