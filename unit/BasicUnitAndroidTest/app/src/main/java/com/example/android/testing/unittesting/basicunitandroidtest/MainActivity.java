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

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 允许用户向多行日志添加行。当设备旋转时，状态被保存和恢复。
 */
public class MainActivity extends Activity {

    private static final String KEY_HISTORY_DATA = "KEY_HISTORY_DATA";

    LogHistory mLogHistory;
    boolean mIsHistoryEmpty = true;
    private TextView mHistoryTextView;
    private DateFormat mSimpleDateFormatter;

    /**
     *当用户想要向历史记录添加一个条目时调用。
     */
    public void updateHistory(View view) {
        // 获取要添加的文本和时间戳。
        EditText editText = (EditText) view.getRootView().findViewById(R.id.editText);
        CharSequence textToAdd = editText.getText();
        long timestamp = System.currentTimeMillis();

        // 把它显示给用户。
        appendEntryToView(textToAdd.toString(), timestamp);

        // 更新历史。
        mLogHistory.addEntry(textToAdd.toString(), timestamp);

        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogHistory = new LogHistory();

        mHistoryTextView = ((TextView) findViewById(R.id.history));
        mSimpleDateFormatter = new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault());

        if (savedInstanceState != null) {
            //我们有一个过去状态，应用到UI。
            mLogHistory = savedInstanceState.getParcelable(KEY_HISTORY_DATA);
            for (Pair<String, Long> entry : mLogHistory.getData()) {
                appendEntryToView(entry.first, entry.second);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_HISTORY_DATA, mLogHistory);
    }

    private void appendEntryToView(String text, long timestamp) {
        Date date = new Date(timestamp);
        // 如果需要，添加一个换行符或清除文本视图(以消除提示)。
        if (!mIsHistoryEmpty) {
            mHistoryTextView.append("\n");
        } else {
            mHistoryTextView.setText("");
        }
        // 将新条目的表示添加到文本视图中。
        mHistoryTextView.append(String.format("%s [%s]", text, mSimpleDateFormatter.format(date)));

        mIsHistoryEmpty = false;
    }
}
