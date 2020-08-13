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

package com.example.android.testing.androidjunitrunnersample;

import junit.framework.TestSuite;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.testing.androidjunitrunnersample.HintMatcher.withHint;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * JUnit3 Ui测试{@link CalculatorActivity}使用{@link AndroidJUnitRunner}。这个类
 * * *使用Junit3语法进行测试并扩展了{@link ActivityInstrumentationTestCase2}。
 * * * < p >
 * * *使用新的AndroidJUnit运行器，您可以在单个测试中运行JUnit3和JUnit4测试
 * * *测试套件。AndroidRunnerBuilder}扩展JUnit的{@link
 * * * AllDefaultPossibilitiesBuilder}将从所有的测试中创建一个{@link TestSuite}并运行
 * * *。
 * * * < p >
 * * * ActivityInstrumentationTestCase2很快将被弃用。请使用{@link ActivityTestRule}
 * 当编写新的测试时。有关如何使用{@link ActivityTestRule}的示例，请参阅
 * * * {@link CalculatorInstrumentationTest}。
 */
@LargeTest
public class OperationHintLegacyInstrumentationTest
        extends ActivityInstrumentationTestCase2<CalculatorActivity> {

    private CalculatorActivity mActivity;

    public OperationHintLegacyInstrumentationTest() {
        super(CalculatorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity();
    }

    public void testPreconditions() {
        assertThat(mActivity, notNullValue());
    }

    public void testEditText_OperandOneHint() {
        String operandOneHint = mActivity.getString(R.string.type_operand_one_hint);
        onView(withId(R.id.operand_one_edit_text)).check(matches(withHint(operandOneHint)));
    }

    public void testEditText_OperandTwoHint() {
        String operandTwoHint = mActivity.getString(R.string.type_operant_two_hint);
        onView(withId(R.id.operand_two_edit_text)).check(matches(withHint(operandTwoHint)));
    }

    //看到最后，我表示没有看懂，现在想想做个单元测试也就可以了，哎...
}
