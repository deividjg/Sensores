package com.example.david.sensores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

class GameView extends SurfaceView implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometerSensor;
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Pelota pelota;
    int x, y;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        pelota = new Pelota(this, bmp);


        List<Sensor> listaSensores;

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listaSensores.isEmpty()) {
            acelerometerSensor = listaSensores.get(0);
            sensorManager.registerListener(this, acelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                gameLoopThread.stop();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#EA7417"));
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(20);
        canvas.drawLine(0, (float)(getHeight()*0.5), getWidth(), (float)(getHeight()*0.5), paint);
        canvas.drawLine((float)(getWidth()*0.1), (float)(getHeight()*0.1), (float)(getWidth()*0.9), (float)(getHeight()*0.1), paint);
        canvas.drawLine((float)(getWidth()*0.1), (float)(getHeight()*0.9), (float)(getWidth()*0.9), (float)(getHeight()*0.9), paint);
        canvas.drawLine((float)(getWidth()*0.1), (float)(getHeight()*0.1), (float)(getWidth()*0.1), (float)(getHeight()*0.9), paint);
        canvas.drawLine((float)(getWidth()*0.9), (float)(getHeight()*0.1), (float)(getWidth()*0.9), (float)(getHeight()*0.9), paint);
        canvas.drawLine((float)(getWidth()*0.1), (float)(getHeight()*0.3), (float)(getWidth()*0.9), (float)(getHeight()*0.3), paint);
        canvas.drawLine((float)(getWidth()*0.1), (float)(getHeight()*0.7), (float)(getWidth()*0.9), (float)(getHeight()*0.7), paint);
        canvas.drawLine((float)(getWidth()*0.5), (float)(getHeight()*0.3), (float)(getWidth()*0.5), (float)(getHeight()*0.7), paint);
        pelota.onDraw(canvas);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                x = (int) sensorEvent.values[0]*-2;
                y = (int) sensorEvent.values[1]*2;
            }
            pelota.setX(x);
            pelota.setY(y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}