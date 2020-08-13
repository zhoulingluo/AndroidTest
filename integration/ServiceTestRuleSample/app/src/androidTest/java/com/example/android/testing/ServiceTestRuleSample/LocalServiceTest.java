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

import android.content.Intent;
import android.os.IBinder;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ServiceTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * 是一个JUnit规则，它提供了
 * *简化了之前启动和关闭服务的机制
 * *在您的测试期间之后。它还保证服务是成功的
 * *在启动(或绑定到)服务时连接。可以启动该服务
 * *(或绑定)使用帮助器方法之一。它将自动停止(或解除绑定)后
 * *测试完成，任何注释的方法
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class LocalServiceTest {
    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Test
    public void testWithBoundService() throws TimeoutException {
        // 创建serviceIntent
        Intent serviceIntent =new Intent(getApplicationContext(), LocalService.class);

        // 数据可以通过意图传递给服务。
        serviceIntent.putExtra(LocalService.SEED_KEY, 42L);

        // 绑定服务并获取绑定器的引用。
        IBinder binder = mServiceRule.bindService(serviceIntent);

        // 获取对服务的引用，或者可以直接调用绑定器上的公共方法。
        LocalService service = ((LocalService.LocalBinder) binder).getService();

        System.out.println(service.getRandomInt());
        // 验证服务是否正常工作。
        assertThat(service.getRandomInt(), is(any(Integer.class)));
    }
}
