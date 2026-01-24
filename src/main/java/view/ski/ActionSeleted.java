package view.ski;

import enums.Action;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Timer;
import view.Main;
import view.Ski;

public class ActionSeleted extends Thread {

    private final Ski ski;
    private final AtomicInteger timeRandom = new AtomicInteger(10000);

    public ActionSeleted(Ski ski) {
        this.ski = ski;
    }

    @Override
    public void run() {
        new Timer(timeRandom.get(), (ActionEvent e) -> {
            int rNumber = (int) (Math.random() * 7);
            Action actionSelected = Action.values()[rNumber];

            if (ski.action == Action.HELPY
                    && !(actionSelected == Action.JUMP || actionSelected == Action.SIT)) {
                return;
            }

            if (ski.action == Action.WALK
                    || ski.action == Action.RUN
                    || ski.action == Action.DRAG
                    || ski.action == Action.FALLING
                    || ski.action == Action.JUMP) {
                return;
            }

            ski.action = actionSelected;

            switch (ski.action) {
                case WALK, RUN -> {
                    ski.randomXPosition = (short) (Main.SCREEN_WIDTH * Math.random());
                    ski.direction = (int) Math.signum(ski.randomXPosition - ski.x);
                    ski.hLim = (actionSelected == Action.WALK ? 3 : 7);
                }
                case JUMP ->
                    ski.vSpeed = -8f;
                default -> {
                }
            }

            ski.setSprite();

            timeRandom.set((int) (Math.random() * 10000));
            ((Timer) e.getSource()).setDelay(timeRandom.get());

            System.out.println(actionSelected);
        }).start();
    }
}
