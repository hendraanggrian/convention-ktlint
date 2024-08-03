package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.SpecialFunctionPositionRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class SpecialFunctionPositionRuleTest {
    private val assertThatCode = assertThatRule { SpecialFunctionPositionRule() }

    @Test
    fun `Rule properties`() = SpecialFunctionPositionRule().assertProperties()

    @Test
    fun `Special function at the bottom`() =
        assertThatCode(
            """
            class Foo {
                fun bar() {}

                fun baz() {}

                override fun toString() = "foo"

                override fun hashCode() = 0
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Special function not at the function`() =
        assertThatCode(
            """
            class Foo {
                override fun toString() = "foo"

                fun bar() {}

                override fun hashCode() = 0

                fun baz() {}
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, Messages.get(MSG, "toString")),
            LintViolation(6, 5, Messages.get(MSG, "hashCode")),
        )

    @Test
    fun `Grouped overridden functions`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                override fun toString() = "foo"

                override fun hashCode() = 0

                override fun equals(other: Any?) = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
