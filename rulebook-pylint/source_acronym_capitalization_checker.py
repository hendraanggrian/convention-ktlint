import re
from typing import TYPE_CHECKING

from astroid import ClassDef
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple

from messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class SourceAcronymCapitalizationChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#source-acronym-capitalization
    """
    ABBREVIATION_REGEX: re.Pattern = re.compile(r'[A-Z]{3,}')

    name: str = 'source-acronym-capitalization'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(name)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        if not bool(SourceAcronymCapitalizationChecker.ABBREVIATION_REGEX.search(node.name)):
            return
        self.add_message(
            SourceAcronymCapitalizationChecker.name,
            node=node,
            args=SourceAcronymCapitalizationChecker.transform(node.name),
        )

    @staticmethod
    def is_static_property_name(name: str) -> bool:
        return all(char.isupper() or char.isdigit() or char == '_' for char in name)

    @staticmethod
    def transform(name: str) -> str:
        def replace_match(match):
            group_value = match.group()
            if match.end() == len(name):
                return group_value[0] + group_value[1:].lower()
            else:
                return group_value[0] + group_value[1:-1].lower() + group_value[-1]

        return SourceAcronymCapitalizationChecker.ABBREVIATION_REGEX.sub(replace_match, name)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(SourceAcronymCapitalizationChecker(linter))
