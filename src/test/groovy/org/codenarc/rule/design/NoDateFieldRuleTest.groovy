/*
 * Copyright 2017 the original author or authors.
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
package org.codenarc.rule.design

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule
import org.junit.Test

/**
 * Tests for NoDateFieldRule
 *
 * @author EricHelgeson
  */
class NoDateFieldRuleTest extends AbstractRuleTestCase {

    @Test
    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'NoDateField'
    }

    @Test
    void testShouldAddNoViolationsForPrivateField() {
        final SOURCE = '''
            class Person {
                String name
            }
        '''
        println "foo"
        assertNoViolations SOURCE
    }

    @Test
    void testShouldAddViolationForPublicField() {
        final SOURCE = '''
            class Person {
                Date date
            }
        '''
        assertSingleViolation SOURCE, 3, null, "Using java.util.Date is considered bad practice. Use java.time API instead for 'date'."
    }

    @Test
    void testShouldAddViolationForPublicFieldWithInitializer() {
        final SOURCE = '''
            class Person {
                public Date date = new Date()
            }
        '''
        assertSingleViolation SOURCE, 3, null, "Using java.util.Date is considered bad practice. Use java.time API instead for 'date'."
    }

    @Test
    void testShouldAddNoViolationsForPublicStaticField() {
        final SOURCE = '''
            class Person {
                public static Date date
            }
        '''
        assertSingleViolation SOURCE, 3, null, "Using java.util.Date is considered bad practice. Use java.time API instead for 'date'."
    }

    @Test
    void testShouldAddNoViolationsForFieldNotNamedDate() {
        final SOURCE = '''
            class Person {
                public Date fooBar
            }
        '''
        assertSingleViolation SOURCE, 3, null, "Using java.util.Date is considered bad practice. Use java.time API instead for 'fooBar'."
    }

    protected Rule createRule() {
        new NoDateFieldRule()
    }
}
