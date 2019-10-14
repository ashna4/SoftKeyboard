/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.example.android.softkeyboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.inputmethodcommon.InputMethodSettingsFragment;

import java.util.List;

/**
 * Displays the IME preferences inside the input method setting.
 */
public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        // We overwrite the title of the activity, as the default one is "Voice Search".
        setTitle(R.string.settings_name);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String packageLocal = getPackageName();
        boolean isInputDeviceEnabled = false;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> list = inputMethodManager.getEnabledInputMethodList();

        // check if our keyboard is enabled as input method
        for (InputMethodInfo inputMethod : list) {
            String packageName = inputMethod.getPackageName();
            if (packageName.equals(packageLocal)) {
                Toast.makeText(getApplicationContext(),"Your Keyboard Enable",Toast.LENGTH_SHORT).show();
                isInputDeviceEnabled = true;
            }
            else {
                isInputDeviceEnabled = false;
            }
        }

        if(!isInputDeviceEnabled){
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage("Enable Soft Keyboard?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent enableIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                            enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(enableIntent);

                        }
                    });
            builder.create();
            builder.show();

        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage("Active Soft Keyboard?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                            imeManager.showInputMethodPicker();

                        }
                    });
            builder.create();
            builder.show();
        }
    }
}
