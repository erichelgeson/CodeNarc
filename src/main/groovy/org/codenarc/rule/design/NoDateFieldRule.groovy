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

import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.AbstractFieldVisitor
import org.codenarc.util.AstUtil

/**
 * Using public fields is considered to be a bad design. Use properties instead.
 * Using java.util.Date is considered bad practice. Use java.time API instead.
 *
 * @author Eric Helgeson
  */
class NoDateFieldRule extends AbstractAstVisitorRule {
    String name = 'NoDateField'
    int priority = 2
    Class astVisitorClass = NoDateFieldAstVisitor
}

class NoDateFieldAstVisitor extends AbstractFieldVisitor {
    
    @Override
    void visitField(FieldNode node) {
        if(AstUtil.classNodeImplementsType(node.type, Date)) {
            addViolation node, createErrorMessage(node)
        }
    }

    private static createErrorMessage(node) {
        "Using java.util.Date is considered bad practice. Use java.time API instead for '$node.name'."
    }
}
