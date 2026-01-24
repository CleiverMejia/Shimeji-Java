package view.ski;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import enums.Action;
import view.Main;
import view.Ski;

public class Step extends Thread {

    private final Ski ski;

    public Step(Ski ski) {
        this.ski = ski;
    }

    @Override
    public void run() {
        new Timer(10, (ActionEvent e) -> {
            ski.spriteIndex += 10;

            switch (ski.action) {
                case WALK, RUN -> {
                    if (ski.hSpeed < ski.hLim) {
                        ski.hSpeed += .5f;
                    }

                    for (int i = 0; i < ski.hSpeed; i++) {
                        if (Math.signum(ski.randomXPosition - (ski.x + ski.direction)) != 0) {
                            ski.x += ski.direction;
                            continue;
                        }

                        ski.setAction(Action.IDLE);
                        break;
                    }

                    if (((int) (Math.random() * 50)) == 0) {
                        ski.vSpeed = -6f;
                        ski.setAction(Action.JUMP);
                    }
                }
                case JUMP -> {
                    if (ski.vSpeed < 0) {
                        ski.x += ski.hSpeed * ski.direction;
                        ski.y += ski.vSpeed;
                        ski.vSpeed += .2f;
                        break;
                    }

                    ski.setAction(Action.FALLING);
                }
                case FALLING -> {
                    if (ski.vSpeed < ski.VLIM) {
                        ski.x += ski.hSpeed * ski.direction;
                        ski.vSpeed += .2f;
                    }

                    for (int i = 0; i < ski.vSpeed; i++) {
                        if (ski.y < Main.SCREEN_HEIGHT - ski.height) {
                            ski.y++;
                            continue;
                        }

                        ski.y = Main.SCREEN_HEIGHT - ski.height;

                        int randomNumber = (int) (Math.random() * 20);
                        ski.setAction(randomNumber == 0 ? Action.HELPY : Action.IDLE);
                    }
                }
                default -> {
                    ski.hSpeed -= .2f;
                    if (ski.hSpeed < 0) {
                        ski.hSpeed = 0f;
                        break;
                    }

                    ski.x += ski.direction * ski.hSpeed;
                }
            }

            ski.setBounds((int) ski.x, (int) ski.y, ski.width, ski.height);

            // Animation
            if (ski.spriteIndex > ski.groupFrame.getDuration(ski.imageIndex)) {
                ski.spriteIndex %= ski.groupFrame.getDuration(ski.imageIndex);

                ski.imageIndex++;
                if (ski.groupFrame.isRepeat()) {
                    ski.imageIndex %= ski.groupFrame.size();
                } else {
                    ski.imageIndex = Math.min(ski.imageIndex, ski.groupFrame.size() - 1);
                }

                ski.frame = ski.groupFrame.getFrame(ski.imageIndex);
            }
        }).start();
    }
}
