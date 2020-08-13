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

package com.example.android.testing.ServiceTestRuleSample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * {@link Service} 生成随机数。
 * <p>
 * 随机数生成器的种子可以通过传递给的{@link Intent}来设置
 * {@link #onBind(Intent)}.
 */
public class LocalService extends Service {
    // 用作Intent的键。
    public static final String SEED_KEY = "SEED_KEY";

    // Binder
    private final IBinder mBinder = new LocalBinder();

    private Random mGenerator = new Random();

    private long mSeed;

    @Override
    public IBinder onBind(Intent intent) {
        // 如果intent带有数字生成器的种子，就用它。
        if (intent.hasExtra(SEED_KEY)) {
            mSeed = intent.getLongExtra(SEED_KEY, 0);
            mGenerator.setSeed(mSeed);
        }
        return mBinder;
    }

    public class LocalBinder extends Binder {

        public LocalService getService() {
            // 返回LocalService的此实例，以便客户端可以调用公共方法。
            return LocalService.this;
        }
    }

    /**
     * 返回[0,100]中的随机整数。
     */
    public int getRandomInt() {
        return mGenerator.nextInt(100);
    }
}
