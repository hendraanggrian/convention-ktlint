package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.PropertyNameJavaInteroperabilityRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class PropertyNameJavaInteroperabilityRuleTest {
    private val assertThatCode = assertThatRule { PropertyNameJavaInteroperabilityRule() }

    @Test
    fun `Rule properties`() = PropertyNameJavaInteroperabilityRule().assertProperties()

    @Test
    fun `Properties with is prefix`() =
        assertThatCode(
            """
            class Foo(val isBar: Boolean) {
                val isBaz: Boolean
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Properties without is prefix`() =
        assertThatCode(
            """
            class Foo(val bar: Boolean) {
                val baz: Boolean
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 15, Messages.get(MSG, "isBar")),
            LintViolation(2, 9, Messages.get(MSG, "isBaz")),
        )

    @Test
    fun `Skip annotated properties`() =
        assertThatCode(
            """
            class Foo(@JvmField val bar: Boolean) {
                val baz: Boolean
                    @JvmName("something")
                    get() = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip variables`() =
        assertThatCode(
            """
            fun foo() {
                val bar: Boolean = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
