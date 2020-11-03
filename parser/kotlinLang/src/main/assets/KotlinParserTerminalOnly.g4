/**
 * Kotlin syntax grammar in ANTLR4 notation
 */

parser grammar KotlinParserTerminalOnly;

options { tokenVocab = KotlinLexer; }

// SECTION: general

kotlinFile
    : EOF
    ;
