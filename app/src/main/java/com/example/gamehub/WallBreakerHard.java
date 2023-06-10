package com.example.gamehub;

import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

public class WallBreakerHard extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean gameRunning;
    private int screenWidth, screenHeight;
    private int paddleWidth, paddleHeight;
    private int paddleX, paddleY;
    private int ballRadius, ballX, ballY, ballSpeedX, ballSpeedY;
    private int brickWidth, brickHeight;
    private int[][] bricks;
    private Paint paddlePaint, ballPaint, brickPaint, brickBorderPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_breaker_hard);

        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        initializeGame();
    }

    private void initializeGame() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = (int) (displayMetrics.density * 390);
        screenHeight = (int) (displayMetrics.density * 690);

        paddleWidth = screenWidth / 6;
        paddleHeight = screenHeight / 30;
        paddleX = screenWidth / 3 - paddleWidth / 3;
        paddleY = screenHeight - paddleHeight - 100;

        ballRadius = screenWidth / 50;
        ballX = screenWidth / 2;
        ballY = paddleY - ballRadius - 10;
        ballSpeedX = 20;
        ballSpeedY = -20;

        brickWidth = screenWidth / 8;
        brickHeight = screenHeight / 20;

        bricks = new int[5][8];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                bricks[row][col] = 1;
            }
        }

        paddlePaint = new Paint();
        paddlePaint.setColor(Color.BLUE);

        ballPaint = new Paint();
        ballPaint.setColor(Color.RED);

        brickPaint = new Paint();
        brickPaint.setColor(Color.WHITE);

        brickBorderPaint = new Paint();
        brickBorderPaint.setColor(Color.BLACK);
        brickBorderPaint.setStyle(Paint.Style.STROKE);
        brickBorderPaint.setStrokeWidth(2);
    }

    private void updateGame() {
        if (gameRunning) {
            ballX += ballSpeedX;
            ballY += ballSpeedY;


            if (ballY + ballRadius >= paddleY && ballY + ballRadius <= paddleY + paddleHeight) {
                if (ballX + ballRadius >= paddleX && ballX - ballRadius <= paddleX + paddleWidth) {
                    ballSpeedY = -ballSpeedY;
                }
            }


            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 8; col++) {
                    if (bricks[row][col] == 1) {
                        int brickX = col * brickWidth;
                        int brickY = row * brickHeight;
                        if (ballX + ballRadius >= brickX && ballX - ballRadius <= brickX + brickWidth &&
                                ballY + ballRadius >= brickY && ballY - ballRadius <= brickY + brickHeight) {
                            bricks[row][col] = 0;
                            ballSpeedY = -ballSpeedY;
                        }
                    }
                }
            }


            if (ballX + ballRadius >= screenWidth || ballX - ballRadius <= 0) {
                ballSpeedX = -ballSpeedX;
            }

            if (ballY - ballRadius <= 0) {
                ballSpeedY = -ballSpeedY;
            }


            boolean allBricksDestroyed = true;
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 8; col++) {
                    if (bricks[row][col] == 1) {
                        allBricksDestroyed = false;
                        break;
                    }
                }
            }

            if (allBricksDestroyed) {
                gameRunning = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showGameOverDialog();
                    }
                });
            } else if (ballY + ballRadius >= screenHeight) {
                gameRunning = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showGameOverDialog();
                    }
                });
            }
        }
    }

    private void drawGame() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);


            canvas.drawRect(paddleX, paddleY, paddleX + paddleWidth, paddleY + paddleHeight, paddlePaint);


            canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);


            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 8; col++) {
                    if (bricks[row][col] == 1) {
                        int brickX = col * brickWidth;
                        int brickY = row * brickHeight;
                        canvas.drawRect(brickX, brickY, brickX + brickWidth, brickY + brickHeight, brickPaint);
                        canvas.drawRect(brickX, brickY, brickX + brickWidth, brickY + brickHeight, brickBorderPaint);
                    }
                }
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("You lost the game!");

        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        restartGame();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void restartGame() {
        initializeGame();
        gameRunning = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (gameRunning) {
                    updateGame();
                    drawGame();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameRunning = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            paddleX = (int) event.getX() - paddleWidth / 2;
        }
        return true;
    }
}
