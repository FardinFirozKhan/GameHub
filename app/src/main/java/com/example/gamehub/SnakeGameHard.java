package com.example.gamehub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeGameHard extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private TextView scoreTV;

    private List<SnakePoints> snakePointsList;
    private int pointSize;
    private int snakeColor;
    private int snakeMovingSpeed;

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint pointColor;

    private String movingDirection;
    private int score;
    private int positionX;
    private int positionY;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        surfaceView = findViewById(R.id.surfaceView);
        scoreTV = findViewById(R.id.scoreTV);
        AppCompatImageButton topBtn = findViewById(R.id.topBtn);
        AppCompatImageButton bottomBtn = findViewById(R.id.bottomBtn);
        AppCompatImageButton leftBtn = findViewById(R.id.leftBtn);
        AppCompatImageButton rightBtn = findViewById(R.id.rightBtn);

        surfaceView.getHolder().addCallback(this);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movingDirection.equals("bottom")) {
                    movingDirection = "top";
                }
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movingDirection.equals("top")) {
                    movingDirection = "bottom";
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movingDirection.equals("right")) {
                    movingDirection = "left";
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movingDirection.equals("left")) {
                    movingDirection = "right";
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        timer.cancel();
        timer.purge();
        canvas = null;
    }

    private void init() {
        snakePointsList = new ArrayList<>();
        pointSize = 28;
        snakeColor = Color.GREEN;
        snakeMovingSpeed = 900;
        movingDirection = "right";
        score = 0;

        int startPositionX = (pointSize) * 3;
        for (int i = 0; i < 3; i++) {
            SnakePoints snakePoint = new SnakePoints(startPositionX, pointSize);
            snakePointsList.add(snakePoint);
            startPositionX = startPositionX - (pointSize * 2);
        }

        addPoint();
        moveSnake();
    }

    private void addPoint() {
        int surfaceWidth = surfaceView.getWidth() - (pointSize * 2);
        int surfaceHeight = surfaceView.getHeight() - (pointSize * 2);
        int randomXPosition = new Random().nextInt(surfaceWidth / pointSize);
        int randomYPosition = new Random().nextInt(surfaceHeight / pointSize);
        if ((randomXPosition % 2) != 0) {
            randomXPosition = randomXPosition + 1;
        }
        if ((randomYPosition % 2) != 0) {
            randomYPosition = randomYPosition + 1;
        }
        positionX = (pointSize * randomXPosition) + pointSize;
        positionY = (pointSize * randomYPosition) + pointSize;
    }

    private void moveSnake() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int headPositionX = snakePointsList.get(0).getPositionX();
                int headPositionY = snakePointsList.get(0).getPositionY();

                if (headPositionX == positionX && headPositionY == positionY) {
                    growSnake();
                    addPoint();
                }

                switch (movingDirection) {
                    case "right":
                        headPositionX += (pointSize * 2);
                        break;
                    case "left":
                        headPositionX -= (pointSize * 2);
                        break;
                    case "top":
                        headPositionY -= (pointSize * 2);
                        break;
                    case "bottom":
                        headPositionY += (pointSize * 2);
                        break;
                }

                if (checkGameOver(headPositionX, headPositionY)) {
                    timer.cancel();
                    timer.purge();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showGameOverDialog();
                        }
                    });
                } else {
                    SnakePoints newHead = new SnakePoints(headPositionX, headPositionY);
                    snakePointsList.add(0, newHead);

                    if (snakePointsList.size() > score + 3) {
                        snakePointsList.remove(score + 3);
                    }

                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                        for (SnakePoints snakePoint : snakePointsList) {
                            canvas.drawCircle(snakePoint.getPositionX(), snakePoint.getPositionY(), pointSize, createPointColor());
                        }

                        canvas.drawCircle(positionX, positionY, pointSize, createPointColor());

                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }, 1000 - snakeMovingSpeed, 1000 - snakeMovingSpeed);
    }

    private void growSnake() {
        score++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTV.setText(String.valueOf(score));
            }
        });
    }

    private boolean checkGameOver(int headPositionX, int headPositionY) {
        int surfaceWidth = surfaceView.getWidth();
        int surfaceHeight = surfaceView.getHeight();
        return headPositionX < 0 || headPositionY < 0 || headPositionX >= surfaceWidth || headPositionY >= surfaceHeight || isSnakeOverlap(headPositionX, headPositionY);
    }

    private boolean isSnakeOverlap(int x, int y) {
        for (int i = 1; i < snakePointsList.size(); i++) {
            SnakePoints snakePoint = snakePointsList.get(i);
            if (snakePoint.getPositionX() == x && snakePoint.getPositionY() == y) {
                return true;
            }
        }
        return false;
    }

    private Paint createPointColor() {
        if (pointColor == null) {
            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setStyle(Paint.Style.FILL);
        }
        return pointColor;
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("Your score: " + score);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
