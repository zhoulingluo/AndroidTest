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

import android.content.SharedPreferences;

import java.util.Calendar;

/**
 *  用于管理访问的助手类
 */
public class SharedPreferencesHelper {

    static final String KEY_NAME = "key_name";
    static final String KEY_DOB = "key_dob_millis";
    static final String KEY_EMAIL = "key_email";

    // 注入的SharedPreferences实现用于持久性。
    private final SharedPreferences mSharedPreferences;

    /**
     * 带有依赖项注入的构造函数。
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * 保存一个对象
     */
    public boolean savePersonalInfo(SharedPreferenceEntry sharedPreferenceEntry){
        // 启动一个SharedPreferences事务。
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, sharedPreferenceEntry.getName());
        editor.putLong(KEY_DOB, sharedPreferenceEntry.getDateOfBirth().getTimeInMillis());
        editor.putString(KEY_EMAIL, sharedPreferenceEntry.getEmail());

        return editor.commit();
    }

    /**
     * 获取 保存的对象
     */
    public SharedPreferenceEntry getPersonalInfo() {
        String name = mSharedPreferences.getString(KEY_NAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        Long dobMillis =mSharedPreferences.getLong(KEY_DOB, Calendar.getInstance().getTimeInMillis());
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTimeInMillis(dobMillis);

        return new SharedPreferenceEntry(name, dateOfBirth, email);
    }
}
