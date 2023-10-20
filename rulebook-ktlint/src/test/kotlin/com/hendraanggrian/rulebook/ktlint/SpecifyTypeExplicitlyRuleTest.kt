package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.SpecifyTypeExplicitlyRule.Companion.MSG_FUNCTION
import com.hendraanggrian.rulebook.ktlint.SpecifyTypeExplicitlyRule.Companion.MSG_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class SpecifyTypeExplicitlyRuleTest {
    private val assertThatCode = assertThatRule { SpecifyTypeExplicitlyRule() }

    @Test
    fun `Regular function`() = assertThatCode("fun function() {}").hasNoLintViolations()

    @Test
    fun `Abstract function`() = assertThatCode("fun function()").hasNoLintViolations()

    @Test
    fun `Expression function`() =
        assertThatCode(
            """
            fun expressionFunction() = "Hello world"
            class MyClass {
                fun expressionFunction() = "Hello world"
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 25, Messages[MSG_FUNCTION]),
            LintViolation(3, 29, Messages[MSG_FUNCTION]),
        )

    @Test
    fun `Private expression function`() =
        assertThatCode(
            """
            private fun expressionFunction() = "Hello world"
            private class MyClass {
                fun expressionFunction() = "Hello world"
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Allow test function`() =
        assertThatCode(
            """
            class Tester {
                @Test
                fun test() = assertThat("Hello world").isNotEmpty()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Regular property`() =
        assertThatCode("val property = \"Hello world\"").hasNoLintViolations()

    @Test
    fun `Getter function`() =
        assertThatCode(
            """
            val propertyAccessor get() = "Hello world"
            class Class {
                val propertyAccessor get() = "Hello world"
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 21, Messages[MSG_PROPERTY]),
            LintViolation(3, 25, Messages[MSG_PROPERTY]),
        )

    @Test
    fun `Private getter function`() =
        assertThatCode(
            """
            private val propertyAccessor get() = "Hello world"
            private class MyClass {
                val propertyAccessor get() = "Hello world"
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Property within code block`() =
        assertThatCode(
            """
            fun codeBlock() {
                val propertyAccessor = "Hello world"
                listOf(1, 2, 3).forEach() {
                    val num = it
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
