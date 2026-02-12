package view.ski;

import enums.Action;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.Timer;
import view.Main;
import view.Ski;

public class ActionSeleted extends Thread {

    private final Ski ski;
    private final AtomicInteger timeRandom = new AtomicInteger(10000);

    private final AtomicReference<Action> action;

    /* private final AtomicInteger x;
    private final AtomicInteger hLim; */
    private final AtomicInteger direction;
    private final AtomicInteger randomXPosition;

    //private final AtomicReference<Float> vSpeed;
    private final Runnable setSprite;

    public ActionSeleted(Ski ski) {
        this.ski = ski;
        this.action = ski.action;
        /* this.x = ski.x;
        this.hLim = ski.hLim; */
        this.direction = ski.direction;
        this.randomXPosition = ski.randomXPosition;
        //this.vSpeed = ski.vSpeed;
        this.setSprite = ski::setSprite;
    }

    @Override
    public void run() {
        new Timer(timeRandom.get(), (ActionEvent e) -> {
            int rNumber = (int) (Math.random() * 8);
            Action actionSelected = Action.values()[rNumber];

            if (action.get() == Action.HELPY
                    && !(actionSelected == Action.JUMP || actionSelected == Action.SIT)) {
                return;
            }

            if (action.get() == Action.WALK
                    || action.get() == Action.RUN
                    || action.get() == Action.DRAG
                    || action.get() == Action.FALLING
                    || action.get() == Action.JUMP) {
                return;
            }

            action.set(actionSelected);

            switch (action.get()) {
                case WALK, RUN -> {
                    randomXPosition.set((int) (Main.SCREEN_WIDTH * Math.random()));
                    direction.set((int) Math.signum(randomXPosition.get() - ski.x));
                    ski.hLim = (actionSelected == Action.WALK ? 3 : 7);
                }
                case JUMP ->
                    ski.vSpeed = -8f;
                default -> {
                }
            }

            setSprite.run();

            timeRandom.set((int) (Math.random() * 10000));
            ((Timer) e.getSource()).setDelay(timeRandom.get());

            System.out.println(actionSelected);
        }).start();
    }
}
