import unittest

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.todo_comment_formatting_checker import TodoCommentFormattingChecker

from .testing import msg


# pylint: disable=missing-class-docstring
class TestTodoCommentFormattingChecker(CheckerTestCase):
    CHECKER_CLASS = TodoCommentFormattingChecker

    def test_uppercase_todo_comments(self):
        def_all = parse(
            '''
            # TODO add tests
            # FIXME fix bug
            ''',
        )
        with self.assertNoMessages():
            self.checker.process_module(def_all)

    def test_lowercase_todo_comments(self):
        def_all = parse(
            '''
            # todo add tests
            # fixme fix bug
            ''',
        )
        with self.assertAddsMessages(
            msg(TodoCommentFormattingChecker.MSG_KEYWORD, 2, args='todo'),
            msg(TodoCommentFormattingChecker.MSG_KEYWORD, 3, args='fixme'),
        ):
            self.checker.process_module(def_all)

    def test_unknown_todo_comments(self):
        def_all = parse(
            '''
            # TODO: add tests
            # FIXME1 fix bug
            ''',
        )
        with self.assertAddsMessages(
            msg(TodoCommentFormattingChecker.MSG_SEPARATOR, 2, args=':'),
            msg(TodoCommentFormattingChecker.MSG_SEPARATOR, 3, args='1'),
        ):
            self.checker.process_module(def_all)

    def test_todo_keyword_mid_sentence(self):
        def_all = parse(
            '''
            # Untested. Todo: add tests.
            ''',
        )
        with self.assertAddsMessages(
            msg(TodoCommentFormattingChecker.MSG_KEYWORD, 2, args='Todo'),
            msg(TodoCommentFormattingChecker.MSG_SEPARATOR, 2, args=':'),
        ):
            self.checker.process_module(def_all)


if __name__ == '__main__':
    unittest.main()
