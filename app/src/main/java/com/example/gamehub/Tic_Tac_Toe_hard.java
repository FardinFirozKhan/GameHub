package com.example.gamehub;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Tic_Tac_Toe_hard extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    int count = 0, moves = 0;
    String b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_hard);

        init();
    }

    private void init() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
    }

    public void check(View view) {
        Button currentBtn = (Button) view;

        if (currentBtn.getText().toString().equals("")) {
            moves++;

            if (count == 0) {
                currentBtn.setText("X");
                count = 1;
                if (moves < 9) {
                    makeAIMove();
                }
            } else {
                currentBtn.setText("0");
                count = 0;
            }
            if (moves >= 5) {
                b1 = btn1.getText().toString();
                b2 = btn2.getText().toString();
                b3 = btn3.getText().toString();
                b4 = btn4.getText().toString();
                b5 = btn5.getText().toString();
                b6 = btn6.getText().toString();
                b7 = btn7.getText().toString();
                b8 = btn8.getText().toString();
                b9 = btn9.getText().toString();

                if (b1.equals(b2) && b2.equals(b3) && !b1.equals("")) {
                    showGameOverDialog(b1);
                } else if (b4.equals(b5) && b5.equals(b6) && !b4.equals("")) {
                    showGameOverDialog(b4);
                } else if (b7.equals(b8) && b8.equals(b9) && !b7.equals("")) {
                    showGameOverDialog(b7);
                } else if (b4.equals(b1) && b1.equals(b7) && !b4.equals("")) {
                    showGameOverDialog(b4);
                } else if (b2.equals(b5) && b5.equals(b8) && !b2.equals("")) {
                    showGameOverDialog(b2);
                } else if (b3.equals(b6) && b6.equals(b9) && !b3.equals("")) {
                    showGameOverDialog(b3);
                } else if (b1.equals(b5) && b1.equals(b9) && !b1.equals("")) {
                    showGameOverDialog(b1);
                } else if (b3.equals(b5) && b5.equals(b7) && !b7.equals("")) {
                    showGameOverDialog(b3);
                } else if (moves > 8) {
                    String b = "Match Draw";
                    showGameOverDialog(b);
                }
            }
        }
    }

    private void makeAIMove() {
        btn1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                Button[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
                Button aiMove;
                int index;
                do {
                    index = random.nextInt(9);
                    aiMove = buttons[index];
                } while (!aiMove.getText().toString().equals(""));

                aiMove.setText("0");
                count = 0;
                moves++;

                if (moves > 4) {
                    b1 = btn1.getText().toString();
                    b2 = btn2.getText().toString();
                    b3 = btn3.getText().toString();
                    b4 = btn4.getText().toString();
                    b5 = btn5.getText().toString();
                    b6 = btn6.getText().toString();
                    b7 = btn7.getText().toString();
                    b8 = btn8.getText().toString();
                    b9 = btn9.getText().toString();

                    if (b1.equals(b2) && b2.equals(b3) && !b1.equals("")) {
                        showGameOverDialog(b1);
                    } else if (b4.equals(b5) && b5.equals(b6) && !b4.equals("")) {
                        showGameOverDialog(b4);
                    } else if (b7.equals(b8) && b8.equals(b9) && !b7.equals("")) {
                        showGameOverDialog(b7);
                    } else if (b4.equals(b1) && b1.equals(b7) && !b4.equals("")) {
                        showGameOverDialog(b4);
                    } else if (b2.equals(b5) && b5.equals(b8) && !b2.equals("")) {
                        showGameOverDialog(b2);
                    } else if (b3.equals(b6) && b6.equals(b9) && !b3.equals("")) {
                        showGameOverDialog(b3);
                    } else if (b1.equals(b5) && b1.equals(b9) && !b1.equals("")) {
                        showGameOverDialog(b1);
                    } else if (b3.equals(b5) && b5.equals(b7) && !b7.equals("")) {
                        showGameOverDialog(b3);
                    } else if (moves > 8) {
                        String b = "Match Draw";
                        showGameOverDialog(b);
                    }
                }
            }
        }, 1000);
    }


    public void newGame() {
        count = 0;
        moves = 0;
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
    }

    private void showGameOverDialog(String b) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("Winner is: " + b);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                newGame();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
