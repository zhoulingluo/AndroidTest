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
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储一个可打包的条目日志，每个条目都有一个时间戳。
 */
public class LogHistory implements Parcelable {

    // 用于存储活动使用的数据。
    private final List<Pair<String, Long>> mData = new ArrayList<>();

    public LogHistory() { }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // 准备一个字符串数组和一个时间戳数组。
        String[] texts = new String[mData.size()];
        long[] timestamps = new long[mData.size()];

        // 将数据存储在数组中。
        for (int i = 0; i < mData.size(); i++) {
            texts[i] = mData.get(i).first;
            timestamps[i] = mData.get(i).second;
        }
        // 首先写入数组的大小。
        out.writeInt(texts.length);

        // 按特定顺序编写这两个数组。
        out.writeStringArray(texts);
        out.writeLongArray(timestamps);
    }

    public static final Parcelable.Creator<LogHistory> CREATOR
            = new Parcelable.Creator<LogHistory>() {

        @Override
        public LogHistory createFromParcel(Parcel in) {
            return new LogHistory(in);
        }

        @Override
        public LogHistory[] newArray(int size) {
            return new LogHistory[size];
        }
    };

    /**
     * 返回活动所使用的当前数据的副本。
     */
    public List<Pair<String, Long>> getData() {
        return new ArrayList<>(mData);
    }

    /**
     * 向日志中添加一个新条目。
     * @param text 要存储在日志中的文本
     * @param timestamp 从UTC 1970年1月1日起的当前时间(以毫秒为单位)。
     */
    public void addEntry(String text, long timestamp) {
        mData.add(new Pair<String, Long>(text, timestamp));
    }

    // 从CREATOR字段中使用的构造函数，用于解包包裹。
    private LogHistory(Parcel in) {
        // 首先，读取包含数据的数组的大小。
        int length = in.readInt();

        // 创建数组来存储数据。
        String[] texts = new String[length];
        long[] timestamps = new long[length];

        // 按照特定的顺序读取数组。
        in.readStringArray(texts);
        in.readLongArray(timestamps);

        // 两个数组的长度应该匹配，否则数据已损坏。
        if (texts.length != timestamps.length) {
            throw new IllegalStateException("从已保存状态读取时出错。");
        }

        // 重置数据容器并更新数据。
        mData.clear();
        for (int i = 0; i < texts.length; i++) {
            Pair<String, Long> pair = new Pair<>(texts[i], timestamps[i]);
            mData.add(pair);
        }
    }
}