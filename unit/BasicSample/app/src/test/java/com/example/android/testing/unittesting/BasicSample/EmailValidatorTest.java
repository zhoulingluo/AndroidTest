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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * 对EmailValidator逻辑进行单元测试。
 */
public class EmailValidatorTest {

    /**
     * 以后测试邮箱是否满足 就可以用这个
     */
    @Test
    public void emailValidator_CorrectEmailSimple() {
        assertTrue(EmailValidator.isValidEmail("www.1237@12.com"));
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"));
        assertFalse(EmailValidator.isValidEmail("name@email"));
        assertFalse(EmailValidator.isValidEmail("name@email..com"));
        assertFalse(EmailValidator.isValidEmail("@email.com"));
        assertFalse(EmailValidator.isValidEmail(".com"));
        assertFalse(EmailValidator.isValidEmail(""));
        assertFalse(EmailValidator.isValidEmail(null));
    }
}
