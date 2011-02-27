/*
 * Copyright 2010 the original author or authors.
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
package org.codenarc.rule.unnecessary

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnnecessaryNullCheckRule
 *
 * @author Hamlet D'Arcy
 * @version $Revision$ - $Date$
 */
class UnnecessaryNullCheckRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'UnnecessaryNullCheck'
    }

    void testSuccessScenario() {
        final SOURCE = '''
            // null check it OK
            if (obj != null) { }

            // null safe dereference in if is OK
            if (obj?.method()) { }

            // null safe dereference in ternary is OK
            (obj?.prop && obj?.prop2) ? x : y

            // obj is reused in a parameter list, so OK
            if (obj != null && obj.method() && isValid(obj)) { }

            // ok if changed
            if (obj != null && obj.method().method2()) { }
            if (obj != null && obj.prop1.prop2) { }

            // ok, different properties
            if (obj.prop1 && obj.prop2 != null) {  }
            if (obj.method1() && obj.method2() != null) {  }
        '''
        assertNoViolations(SOURCE)
    }

    void testNullCheckWithMethodCall() {
        final SOURCE = '''
            if (obj != null && obj.method()) { }
        '''
        assertSingleViolation(SOURCE, 2, 'if (obj != null && obj.method())',
                'The expression ((obj != null) && obj.method()) can be simplified to (obj?.method())')
    }

    void testNullCheckWithProperty() {
        final SOURCE = '''
            if (obj != null && obj.prop) { }
        '''
        assertSingleViolation(SOURCE, 2, 'if (obj != null && obj.prop)',
                'The expression ((obj != null) && obj.prop) can be simplified to (obj?.prop)')
    }

    void testPointlessNullCheckOnMethod() {
        final SOURCE = '''
            if (obj.method() && obj != null) { }
        '''
        assertSingleViolation(SOURCE, 2, 'if (obj.method() && obj != null)',
                'The expression (obj.method() && (obj != null)) can be simplified to (obj.method())')
    }

    void testPointlessNullCheckOnProperty() {
        final SOURCE = '''
            if (obj.prop && obj != null) { }
        '''
        assertSingleViolation(SOURCE, 2, 'if (obj.prop && obj != null)',
                'The expression (obj.prop && (obj != null)) can be simplified to (obj.prop)')
    }

    // todo: enable this test
//    void ignore_testNullCheckWithMethodCallAndAdditionalConditional() {
//        final SOURCE = '''
//            if (x.isValid() && obj != null && obj.method()) { }
//        '''
//        assertSingleViolation(SOURCE, 2, 'if (x.isValid() && obj != null && obj.method())', '...')
//    }

    //todo: enable this test
//    void ignore_testNullCheckWithPropertyAndMethod() {
//        final SOURCE = '''
//            (obj != null && obj.prop && obj.method()) ? x : y
//        '''
//        assertSingleViolation(SOURCE, 2, 'if (obj != null && obj.prop)', '...')
//    }

    protected Rule createRule() {
        new UnnecessaryNullCheckRule()
    }
}