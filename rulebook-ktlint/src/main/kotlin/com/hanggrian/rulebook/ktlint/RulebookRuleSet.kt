package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider

public class RulebookRuleSet : RuleSetProviderV3(Rule.ID) {
    override fun getRuleProviders(): Set<RuleProvider> =
        setOf(
            RuleProvider { BlockCommentLineJoiningRule() },
            RuleProvider { BlockCommentLineTrimmingRule() },
            RuleProvider { BlockCommentSpacingRule() },
            RuleProvider { BlockTagInitialLineSpacingRule() },
            RuleProvider { BlockTagOrderingRule() },
            RuleProvider { BlockTagPunctuationRule() },
            RuleProvider { CaseLineJoiningRule() },
            RuleProvider { ClassFinalNameBlacklistingRule() },
            RuleProvider { ClassMemberOrderingRule() },
            RuleProvider { ClassNameAcronymCapitalizationRule() },
            RuleProvider { CommentLineJoiningRule() },
            RuleProvider { CommentLineTrimmingRule() },
            RuleProvider { DefaultFlatteningRule() },
            RuleProvider { ElseFlatteningRule() },
            RuleProvider { ElvisWrappingRule() },
            RuleProvider { EmptyCodeBlockConcisenessRule() },
            RuleProvider { ExceptionExtendingRule() },
            RuleProvider { ExceptionSubclassThrowingRule() },
            RuleProvider { FileInitialLineTrimmingRule() },
            RuleProvider { FileSizeLimitationRule() },
            RuleProvider { GenericsNameWhitelistingRule() },
            RuleProvider { IdentifierNameBlacklistingRule() },
            RuleProvider { IfElseFlatteningRule() },
            RuleProvider { InfixParameterWrappingRule() },
            RuleProvider { InnerClassPositionRule() },
            RuleProvider { KotlinApiPriorityRule() },
            RuleProvider { NullStructuralEqualityRule() },
            RuleProvider { OverloadFunctionPositionRule() },
            RuleProvider { PropertyNameJavaInteroperabilityRule() },
            RuleProvider { QualifierConsistencyRule() },
            RuleProvider { SpecialFunctionPositionRule() },
            RuleProvider { SwitchMultipleBranchingRule() },
            RuleProvider { TodoCommentStylingRule() },
        )
}