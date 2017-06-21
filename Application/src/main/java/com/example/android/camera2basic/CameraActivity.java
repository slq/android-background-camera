/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.camera2basic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CameraActivity extends Activity {

    private Camera2BasicFragment fragment;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean run = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            fragment = Camera2BasicFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Log.d("YYY", "DOWN");
            run = true;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    while(run) {
                        Log.d("YYY", "PIC");
                        fragment.takePicture();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("YYY", "Finished");
                }
            });
        }

        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Log.d("YYY", "UP");
            run = false;
        }
        return true;
    }
}
