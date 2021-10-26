package com.example.zgadywanka;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView startText;
    LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startText = findViewById(R.id.starttext);
        new CountDownTimer(3500, 1000) {
            int counter = 3;

            @Override
            public void onTick(long millisUntilFinished) {
                startText.setText("Start za:" + counter);
                counter--;
            }

            @Override
            public void onFinish() {
                startText.setText("");
                zgadywanka();
            }
        }.start();
    }

    public void resetuj(View view) {
        zgadywanka();
    }

    public void zgadywanka() {
        Button[] zgdwButtons = new Button[7];
        containerLayout = findViewById(R.id.container);
        containerLayout.removeAllViews();

        TextView endText = findViewById(R.id.endtext);
        endText.setText("");

        Random rand = new Random();
        for (int i = 0; i < zgdwButtons.length; i++) {
            zgdwButtons[i] = new Button(this);
        }
        for (Button button : zgdwButtons) {
            containerLayout.addView(button);
            button.setEnabled(true);
        }

        schowajPrzyciski(zgdwButtons);

        HashSet<Integer> losuj = new HashSet<>();
        while (losuj.size() < 4) {
            losuj.add(rand.nextInt(6));
        }
        ArrayList<Integer> losujLista = new ArrayList<>(losuj);
        int counter = 1;
        losujPrzyciski(zgdwButtons, losujLista, counter);
    }

    public void losujPrzyciski(Button[] zgdwButtons, ArrayList<Integer> losujLista, int counter) {
        new CountDownTimer(400, 1000) {
            public void onTick(long untilFinished) {

            }

            public void onFinish() {
                zgdwButtons[losujLista.get(counter - 1)].setBackgroundTintList(getResources().getColorStateList(R.color.red, getTheme()));
                zgdwButtons[losujLista.get(counter - 1)].setText(counter + "");
                if (counter < 4) {
                    losujPrzyciski(zgdwButtons, losujLista, counter + 1);
                } else {
                    schowajPrzyciski(zgdwButtons);
                    checkButtons(zgdwButtons, losujLista, 0);
                }
            }
        }.start();
    }

    public void schowajPrzyciski(Button[] zgdwButtons) {
        new CountDownTimer(400, 1000) {
            public void onTick(long untilFinished) {

            }

            public void onFinish() {
                for (Button button : zgdwButtons) {
                    button.setText("");
                    button.setBackgroundTintList(getResources().getColorStateList(R.color.gray, getTheme()));
                }
            }
        }.start();
    }

    public void checkButtons(Button[] zgdwButtons, ArrayList<Integer> losujLista, int counter) {
        for (Button button : zgdwButtons) {
            button.setOnClickListener(v -> {
                if (button == zgdwButtons[losujLista.get(counter)]) {
                    if (counter < 3) {
                        checkButtons(zgdwButtons, losujLista, counter + 1);
                    } else {
                        Log.i("GAME", "Wygrana");
                        endGame(true, zgdwButtons);
                    }
                } else {
                    Log.i("GAME", "Przegrana");
                    endGame(false, zgdwButtons);
                }
            });
        }
    }

    public void endGame(boolean wygrana, Button[] zgdwButtons) {
        TextView endText = findViewById(R.id.endtext);
        for (Button button : zgdwButtons) {
            button.setEnabled(false);
        }
        if (wygrana) {
            endText.setText(R.string.win);
        } else {
            endText.setText(R.string.lose);
        }
    }
}