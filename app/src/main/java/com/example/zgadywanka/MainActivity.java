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
        Button[] zgdwBttns = new Button[7];
        containerLayout = findViewById(R.id.container);
        containerLayout.removeAllViews();

        TextView endText = findViewById(R.id.endtext);
        endText.setText("");

        Random rand = new Random();
        for (int i = 0; i < zgdwBttns.length; i++) {
            zgdwBttns[i] = new Button(this);
            containerLayout.removeView(zgdwBttns[i]);
        }
        for (Button button : zgdwBttns) {
            containerLayout.addView(button);
            button.setEnabled(true);
        }


        schowajPrzyciski(zgdwBttns);

        HashSet<Integer> losuj = new HashSet<>();
        while (losuj.size() < 4) {
            losuj.add(rand.nextInt(6));
        }
        ArrayList<Integer> losujLista = new ArrayList<>(losuj);
        int counter = 1;
        losujPrzyciski(zgdwBttns, losujLista, counter);
    }

    public void losujPrzyciski(Button[] zgdwBttns, ArrayList<Integer> losujLista, int counter) {
        new CountDownTimer(400, 1000) {
            public void onTick(long untilFinished) {

            }

            public void onFinish() {
                zgdwBttns[losujLista.get(counter - 1)].setBackgroundTintList(getResources().getColorStateList(R.color.red, getTheme()));
                zgdwBttns[losujLista.get(counter - 1)].setText(counter + "");
                if (counter < 4) {
                    losujPrzyciski(zgdwBttns, losujLista, counter + 1);
                } else {
                    schowajPrzyciski(zgdwBttns);
                    checkButtons(zgdwBttns, losujLista, 0);
                }
            }
        }.start();
    }

    public void schowajPrzyciski(Button[] zgdwBttns) {
        new CountDownTimer(400, 1000) {
            public void onTick(long untilFinished) {

            }

            public void onFinish() {
                for (Button button : zgdwBttns) {
                    button.setText("");
                    button.setBackgroundTintList(getResources().getColorStateList(R.color.gray, getTheme()));
                }
            }
        }.start();
    }

    public void checkButtons(Button[] zgdwBttns, ArrayList<Integer> losujLista, int counter) {
        for (Button button : zgdwBttns) {
            button.setOnClickListener(v -> {
                if (button == zgdwBttns[losujLista.get(counter)]) {
                    if (counter < 3) {
                        checkButtons(zgdwBttns, losujLista, counter + 1);
                    } else {
                        Log.i("GAME", "Wygrana");
                        endGame(true, zgdwBttns);
                    }
                } else {
                    Log.i("GAME", "Przegrana");
                    endGame(false, zgdwBttns);
                }
            });
        }
    }

    public void endGame(boolean wygrana, Button[] zgdwBttns) {
        TextView endText = findViewById(R.id.endtext);
        for (Button button : zgdwBttns) {
            button.setEnabled(false);
        }
        if (wygrana) {
            endText.setText(R.string.win);
        } else {
            endText.setText(R.string.lose);
        }
    }
}