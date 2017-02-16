package com.example.david.sensores;

import android.graphics.Canvas;

class GameLoopThread extends Thread {
    private boolean running = false;
    private GameView view;
    static final long FPS = 24;

    public void setRunning(boolean run) {
        running = run;
    }

    public GameLoopThread(GameView view) {
        this.view = view;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }

}
