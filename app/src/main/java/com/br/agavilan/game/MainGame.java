package com.br.agavilan.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import com.br.agavilan.game.engine.Impossible;


public class MainGame extends AppCompatActivity implements View.OnTouchListener {
    Impossible viewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        viewGame = new Impossible(this);
        viewGame.setOnTouchListener(this);
        setContentView(viewGame);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Verifica ao toque na tela se a área contém um botão e move
        if(event.getX() < 100 && event.getY() > 290 &&  event.getY() < 310) {
            viewGame.init();
        }

        // Verifica ao toque na tela se a área contém um botão e finaliza
        if(event.getX() < 100 && event.getY() > 490 &&  event.getY() < 510) {
            System.exit(0);
        }

        viewGame.moveDown(10);
        viewGame.addScore(100);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        viewGame.resume();
    }
}
