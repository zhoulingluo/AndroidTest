/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.example.android.testing.unittesting.basicunitandroidtest;

import android.os.Parcel;
import androidx.test.filters.SmallTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Pair;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Locale;

import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * 测试parcelable接口是否正确实现。
 * SmallTest 测试时间<200ms
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogHistoryAndroidUnitTest {

    public static final String TEST_STRING = "This is a string";
    public static final long TEST_LONG = 12345678L;
    private LogHistory mLogHistory;

    @Before
    public void createLogHistory() {
        mLogHistory = new LogHistory();
    }

    @Test
    public void logHistory_ParcelableWriteRead() {
        // 设置发送和接收的Parcelable对象。
        mLogHistory.addEntry(TEST_STRING, TEST_LONG);

        Parcel parcel = Parcel.obtain();
        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());

        // 当你写完后，你需要重置包裹以便阅读。
        parcel.setDataPosition(0);

        // Read the data
        LogHistory createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
        List<Pair<String, Long>> createdFromParcelData = createdFromParcel.getData();

        // 验证接收到的数据是否正确。
        assertThat(createdFromParcelData.size()).isEqualTo(1);
        assertThat(createdFromParcelData.get(0).first).isEqualTo(TEST_STRING);
        assertThat(createdFromParcelData.get(0).second).isEqualTo(TEST_LONG);


        Long time=System.currentTimeMillis();
        mLogHistory=new LogHistory();
        mLogHistory.addEntry("我正在学习测试1", time);
        mLogHistory.addEntry("我正在学习测试2", time+1000);
        List<Pair<String, Long>> createdFromParcelData1 = mLogHistory.getData();

        MatcherAssert.assertThat("长度是不是1",createdFromParcelData1.size(), CoreMatchers.not(1));
        MatcherAssert.assertThat("字符串：",createdFromParcelData1.get(1).first,is(equalTo("我正在学习测试2")));
        MatcherAssert.assertThat("时间：",createdFromParcelData1.get(1).second,is(equalTo(time+1000)));

        //弄完之后是不是发现都没有测试MainActivity，MainActivity里面全是UI的
        //单元测试基本都是测试基本类，逻辑层的
    }
}
