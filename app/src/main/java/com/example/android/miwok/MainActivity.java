/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // find the view that shows the numbers activity
        TextView numbers = findViewById(R.id.numbers);
        // define click listener for numbers
        numbers.setOnClickListener(createListener(MainActivity.this, NumbersActivity.class));

        TextView family = findViewById(R.id.family);
        family.setOnClickListener(createListener(MainActivity.this, FamilyActivity.class));

        TextView colors = findViewById(R.id.colors);
        colors.setOnClickListener(createListener(MainActivity.this, ColorsActivity.class));

        TextView phrases = findViewById(R.id.phrases);
        phrases.setOnClickListener(createListener(MainActivity.this, PhrasesActivity.class));
    }

    // function defines on click listener
    public View.OnClickListener createListener (final Context packageContext, final Class<?> cls) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(packageContext, cls);
                startActivity(i);
            }
        };
            return  onClickListener;
        }

}
