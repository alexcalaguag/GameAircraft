package com.br.agavilan.game.engine;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.br.agavilan.game.R;

/**
 * Created by agavilan on 16/06/2015.
 */
public class Impossible extends SurfaceView implements Runnable {
    boolean running = false;
    Thread renderThread = null;
    SurfaceHolder holder;
    Paint paint;

    private int playerX = 300, playerY = 300, playerRadius = 50;
    private int enemyX, enemyY, enemyRadius = 50;
    private double distance;
    private boolean gameover;
    private int score;

    public void addScore(int points){
        score+=points;
    }

    public Impossible(Context context) {
        super(context);
        paint = new Paint();
        holder = getHolder();
    }

    @Override
    public void run() {
        while (running) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            if (gameover) {
                break;
            }
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sky),0,0,null);
            drawEnemy(canvas);
            drawPlayer(canvas);
            drawButtons(canvas);
            checkCollision(canvas);
            drawScore(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void init(){
        enemyX = enemyY = enemyRadius = 0;
        playerX = playerY = 300;
        playerRadius = 50;
        gameover=false;
    }

    private void drawPlayer(Canvas canvas) {
        paint.setColor(Color.BLUE);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nave), playerX-50, playerY-50, null);
        //canvas.drawCircle(playerX, playerY, 50, paint);
    }

    private void drawButtons(Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Restart", 50, 300, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Exit", 50, 500, paint);
    }

    private void drawEnemy(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        enemyRadius++;
        canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
    }

    private void checkCollision(Canvas canvas) {
        distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerY - enemyX, 2);
        distance = Math.sqrt(distance);
        if (distance <= playerRadius + enemyRadius) {
            gameover = true;
            stopGame(canvas);
        }
    }

    public void moveDown(int pixels) {
        playerY += pixels;
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    private void stopGame(Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(100);
        canvas.drawText("GAME OVER!", 50, 150, paint);
    }

    private void drawScore(Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText(String.valueOf(score),50,200,paint);
    }
}
