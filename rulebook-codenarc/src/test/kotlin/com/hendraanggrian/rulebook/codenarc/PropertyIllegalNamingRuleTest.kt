package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.PropertyIllegalNamingVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertyIllegalNamingRuleTest : AbstractRuleTestCase<PropertyIllegalNamingRule>() {
    override fun createRule() = PropertyIllegalNamingRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("PropertyIllegalNaming", rule.name)
    }

    @Test
    fun `Descriptive names`() =
        assertNoViolations(
            """
            class Foo {
                String name = ""
            }
            """.trimIndent(),
        )

    @Test
    fun `Prohibited names`() =
        assertSingleViolation(
            """
            class MyClass {
                String string = ""
            }
            """.trimIndent(),
            2,
            "String string = \"\"",
            Messages[MSG],
        )
}