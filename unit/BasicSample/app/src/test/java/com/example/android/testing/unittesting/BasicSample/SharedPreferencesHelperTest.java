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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.SharedPreferences;

import java.util.Calendar;


/**
 * 进行单元测试。
 */
@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final String TEST_NAME = "Test name";
    private static final String TEST_EMAIL = "test@email.com";
    private static final Calendar TEST_DATE_OF_BIRTH = Calendar.getInstance();
    static {
        TEST_DATE_OF_BIRTH.set(1980, 1, 1);
    }

    private SharedPreferenceEntry mSharedPreferenceEntry;
    private SharedPreferencesHelper mMockSharedPreferencesHelper;
    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;
    @Mock
    SharedPreferences mMockBrokenSharedPreferences;
    @Mock
    SharedPreferences.Editor mMockEditor;
    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void initMocks() {
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_NAME, TEST_DATE_OF_BIRTH,TEST_EMAIL);

        // 创建模拟的SharedPreferences。
        mMockSharedPreferencesHelper = createMockSharedPreference();

        // 创建一个在保存数据时失败的模拟SharedPreferences。
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        // 将个人信息保存到SharedPreferences
        boolean success = mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("检查SharedPreferenceEntry.save……返回true",success, is(true));

        // 从SharedPreferences读取个人信息
        SharedPreferenceEntry savedSharedPreferenceEntry =mMockSharedPreferencesHelper.getPersonalInfo();

        // 确保书面的和检索的个人信息是平等的。
        assertThat("检查SharedPreferenceEntry.name是否已被正确持久化和读取",
                mSharedPreferenceEntry.getName(),
                is(equalTo(savedSharedPreferenceEntry.getName())));
        assertThat("检查SharedPreferenceEntry。已持久化并读取出生日期 "
                + " 正确 ",
                mSharedPreferenceEntry.getDateOfBirth(),
                is(equalTo(savedSharedPreferenceEntry.getDateOfBirth())));
        assertThat("检查SharedPreferenceEntry。电子邮件已被保存和阅读"
                + " 正确 ",
                mSharedPreferenceEntry.getEmail(),
                is(equalTo(savedSharedPreferenceEntry.getEmail())));
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // 从损坏的SharedPreferencesHelper中读取个人信息
        boolean success =mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("确保写入损坏的SharedPreferencesHelper返回false", success,is(false));
    }

    /**
     * Creates a mocked SharedPreferences.
     */
    private SharedPreferencesHelper createMockSharedPreference() {
        // 模拟读取SharedPreferences，就好像mMockSharedPreferences是以前编写的一样
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString()))
                .thenReturn(mSharedPreferenceEntry.getName());
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString()))
                .thenReturn(mSharedPreferenceEntry.getEmail());
        when(mMockSharedPreferences.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong()))
                .thenReturn(mSharedPreferenceEntry.getDateOfBirth().getTimeInMillis());

        // 模拟成功的提交。
        when(mMockEditor.commit()).thenReturn(true);

        // 请求模拟程序时返回模拟程序。
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * 创建在编写时失败的模拟SharedPreferences。
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // 模拟失败的提交。
        when(mMockBrokenEditor.commit()).thenReturn(false);

        // 请求模型时返回损坏的模型。
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }
}
