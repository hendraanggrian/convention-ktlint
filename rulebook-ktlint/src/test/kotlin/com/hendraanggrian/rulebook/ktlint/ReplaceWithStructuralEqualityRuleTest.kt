package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ReplaceWithStructuralEqualityRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ReplaceWithStructuralEqualityRuleTest {
    private val assertThatCode = assertThatRule { ReplaceWithStructuralEqualityRule() }

    @Test
    fun `Structural equalities`() =
        assertThatCode(
            """
            fun foo() {
                if (0 == 1) {
                }
                if (0 != 1) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Referential equalities`() =
        assertThatCode(
            """
            fun foo() {
                if (0 === 1) {
                }
                if (0 !== 1) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 11, Messages.get(MSG, "==")),
            LintViolation(4, 11, Messages.get(MSG, "!=")),
        )
}
