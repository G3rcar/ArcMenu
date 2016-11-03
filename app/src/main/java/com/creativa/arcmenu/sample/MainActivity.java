package com.creativa.arcmenu.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Toast;

import com.creativa.arcmenu.ArcInitor;
import com.creativa.arcmenu.ArcMenu;


public class MainActivity extends AppCompatActivity implements ArcInitor.OnArcMenuItemSelectedListener {

    private static final int[] ITEM_DRAWABLES = {
            R.drawable.ic_profile,
            R.drawable.ic_refresh,
            R.drawable.ic_publish,
            R.drawable.ic_applieds
    };
    ArcMenu arcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arcMenu = (ArcMenu) findViewById(R.id.arc_menu);

        ArcInitor menu = new ArcInitor();
        menu.setOnArcMenuItemSelected(this);
        menu.start(this, arcMenu, ITEM_DRAWABLES);
    }

    @Override
    public void onArcMenuItemSelected(int id) {
        Toast.makeText(this, "Clicked "+id, Toast.LENGTH_SHORT).show();
    }
}
