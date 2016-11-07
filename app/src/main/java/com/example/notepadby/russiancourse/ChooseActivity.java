package com.example.notepadby.russiancourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Valera_alt on 07-Nov-16.
 */

public class ChooseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View.OnClickListener on = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, LevelActivity.class);
                intent.putExtra("id",v.getId());
                startActivity(intent);
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button level1 = (Button) findViewById(R.id.level1);
        level1.setOnClickListener(on);
    }
}
