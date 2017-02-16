package com.example.david.sensores;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Pelota {
    private GameView gameView;
    private Bitmap bmp;
    int x;
    int y;
    int width;
    int height;


    public Pelota(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.gameView = gameView;
        this.bmp = bmp;
    }

    public void setX(int x){
        if (this.x + x < gameView.getWidth() - width - x && this.x + x >= 0) {
            this.x += x;
        }
    }

    public void setY(int y){

        if (this.y + y < gameView.getHeight() - height - y && this.y + y >= 0) {
            this.y += y;
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, x, y, null);
    }



}
