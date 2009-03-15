/*
 * Copyright 2009 the original author or authors.
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
package org.codenarc.util

import org.codenarc.test.AbstractTest

/**
 * Tests for WildcardPattern
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class WildcardPatternTest extends AbstractTest {

    void testConstructor_Null() {
        shouldFailWithMessageContaining('pattern') { new WildcardPattern(null) } 
    }

    void testMatches() {
        assert new WildcardPattern('').matches('')
        assert new WildcardPattern('a').matches('a')
        assert new WildcardPattern('a@b.c').matches('a@b.c')
        assert new WildcardPattern('!@#$%^&,.()-_+=~`{}[]:;<>').matches('!@#$%^&,.()-_+=~`{}[]:;<>')
        assert new WildcardPattern('a*def').matches('abcdef')
        assert new WildcardPattern('a?cde?').matches('abcdef')
        assert new WildcardPattern('*?cd*').matches('abcdef')

        assert !new WildcardPattern('').matches('b')
        assert !new WildcardPattern('a').matches('b')
        assert !new WildcardPattern('a??cdef').matches('abcdef')
        assert !new WildcardPattern('a*fg').matches('abcdef')
    }

}