package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.hasReturnOrThrow
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#else-flattening) */
public class ElseFlatteningCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_IF)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip single if
        node
            .takeIf { LITERAL_ELSE in it }
            ?: return

        // checks for violation
        var lastElse: DetailAST? = null
        var `if`: DetailAST? = node
        while (`if` != null) {
            `if`
                .takeIf { it.hasReturnOrThrow() }
                ?: return
            lastElse = `if`.findFirstToken(LITERAL_ELSE)
            `if` = lastElse?.findFirstToken(LITERAL_IF)
        }
        lastElse ?: return
        log(lastElse, Messages[MSG])
    }

    internal companion object {
        const val MSG = "else.flattening"
    }
}
