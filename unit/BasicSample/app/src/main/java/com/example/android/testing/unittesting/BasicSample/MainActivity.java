/*
 * Copyright 2015, The Android Open Source Project
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

package com.example.android.testing.unittesting.BasicSample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 *一个输入表单页面，用户可以在其中提供自己的姓名、日期
 *出生日期和电子邮件地址。个人信息可以保存到{@link SharedPreferences}
 *点击一个按钮。
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private SharedPreferencesHelper mSharedPreferencesHelper;

    // 用户输入姓名的输入字段。
    private EditText mNameText;
    // 用户输入其出生日期的日期选择器。
    private DatePicker mDobPicker;
    // 用户输入电子邮件的输入字段。
    private EditText mEmailText;
    // 电子邮件输入字段的验证器。
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameText = (EditText) findViewById(R.id.userNameInput);
        mDobPicker = (DatePicker) findViewById(R.id.dateOfBirthInput);
        mEmailText = (EditText) findViewById(R.id.emailInput);

        mEmailValidator = new EmailValidator();
        mEmailText.addTextChangedListener(mEmailValidator);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);

        populateUi();
    }

    /**
     * 从SharedPreferences中保存的个人信息初始化所有字段。
     */
    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();
        mNameText.setText(sharedPreferenceEntry.getName());
        mEmailText.setText(sharedPreferenceEntry.getEmail());
        Calendar dateOfBirth = sharedPreferenceEntry.getDateOfBirth();
        mDobPicker.init(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH),
                dateOfBirth.get(Calendar.DAY_OF_MONTH), null);
    }


    /**
     * 当单击“保存”按钮时调用。
     */
    public void onSaveClick(View view) {
        // 如果字段没有验证，就不要保存。
        if (!mEmailValidator.isValid()) {
            mEmailText.setError("无效的电子邮件");
            Log.w(TAG, "没有保存个人信息:无效的电子邮件");
            return;
        }

        // 从输入字段中获取文本。
        String name = mNameText.getText().toString();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(mDobPicker.getYear(), mDobPicker.getMonth(), mDobPicker.getDayOfMonth());
        String email = mEmailText.getText().toString();

        // 创建要持久化的设置模型类。
        SharedPreferenceEntry sharedPreferenceEntry =new SharedPreferenceEntry(name, dateOfBirth, email);

        // 持久化个人信息。
        boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
        if (isSuccess) {
            Toast.makeText(this, "个人信息保存", Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "未能将个人信息写入共享首选项");
        }
    }

    /**
     * 当单击“恢复”按钮时调用。
     */
    public void onRevertClick(View view) {
        populateUi();
        Toast.makeText(this, "个人信息恢复", Toast.LENGTH_LONG).show();
    }
}
