// Generated from KotlinParser.g4 by ANTLR 4.8
package com.itdroid.pocketkotlin.syntax.parser.kotlin;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KotlinParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            ShebangLine = 1, DelimitedComment = 2, LineComment = 3, WS = 4, NL = 5, RESERVED = 6,
            DOT = 7, COMMA = 8, LPAREN = 9, RPAREN = 10, LSQUARE = 11, RSQUARE = 12, LCURL = 13,
            RCURL = 14, MULT = 15, MOD = 16, DIV = 17, ADD = 18, SUB = 19, INCR = 20, DECR = 21, CONJ = 22,
            DISJ = 23, EXCL_WS = 24, EXCL_NO_WS = 25, COLON = 26, SEMICOLON = 27, ASSIGNMENT = 28,
            ADD_ASSIGNMENT = 29, SUB_ASSIGNMENT = 30, MULT_ASSIGNMENT = 31, DIV_ASSIGNMENT = 32,
            MOD_ASSIGNMENT = 33, ARROW = 34, DOUBLE_ARROW = 35, RANGE = 36, COLONCOLON = 37,
            DOUBLE_SEMICOLON = 38, HASH = 39, AT_NO_WS = 40, AT_POST_WS = 41, AT_PRE_WS = 42,
            AT_BOTH_WS = 43, QUEST_WS = 44, QUEST_NO_WS = 45, LANGLE = 46, RANGLE = 47, LE = 48,
            GE = 49, EXCL_EQ = 50, EXCL_EQEQ = 51, AS_SAFE = 52, EQEQ = 53, EQEQEQ = 54, SINGLE_QUOTE = 55,
            RETURN_AT = 56, CONTINUE_AT = 57, BREAK_AT = 58, THIS_AT = 59, SUPER_AT = 60, FILE = 61,
            FIELD = 62, PROPERTY = 63, GET = 64, SET = 65, RECEIVER = 66, PARAM = 67, SETPARAM = 68,
            DELEGATE = 69, PACKAGE = 70, IMPORT = 71, CLASS = 72, INTERFACE = 73, FUN = 74, OBJECT = 75,
            VAL = 76, VAR = 77, TYPE_ALIAS = 78, CONSTRUCTOR = 79, BY = 80, COMPANION = 81, INIT = 82,
            THIS = 83, SUPER = 84, TYPEOF = 85, WHERE = 86, IF = 87, ELSE = 88, WHEN = 89, TRY = 90,
            CATCH = 91, FINALLY = 92, FOR = 93, DO = 94, WHILE = 95, THROW = 96, RETURN = 97, CONTINUE = 98,
            BREAK = 99, AS = 100, IS = 101, IN = 102, NOT_IS = 103, NOT_IN = 104, OUT = 105, DYNAMIC = 106,
            PUBLIC = 107, PRIVATE = 108, PROTECTED = 109, INTERNAL = 110, ENUM = 111, SEALED = 112,
            ANNOTATION = 113, DATA = 114, INNER = 115, TAILREC = 116, OPERATOR = 117, INLINE = 118,
            INFIX = 119, EXTERNAL = 120, SUSPEND = 121, OVERRIDE = 122, ABSTRACT = 123, FINAL = 124,
            OPEN = 125, CONST = 126, LATEINIT = 127, VARARG = 128, NOINLINE = 129, CROSSINLINE = 130,
            REIFIED = 131, EXPECT = 132, ACTUAL = 133, RealLiteral = 134, FloatLiteral = 135,
            DoubleLiteral = 136, IntegerLiteral = 137, HexLiteral = 138, BinLiteral = 139,
            UnsignedLiteral = 140, LongLiteral = 141, BooleanLiteral = 142, NullLiteral = 143,
            CharacterLiteral = 144, Identifier = 145, IdentifierOrSoftKey = 146, FieldIdentifier = 147,
            QUOTE_OPEN = 148, TRIPLE_QUOTE_OPEN = 149, UNICODE_CLASS_LL = 150, UNICODE_CLASS_LM = 151,
            UNICODE_CLASS_LO = 152, UNICODE_CLASS_LT = 153, UNICODE_CLASS_LU = 154, UNICODE_CLASS_ND = 155,
            UNICODE_CLASS_NL = 156, QUOTE_CLOSE = 157, LineStrRef = 158, LineStrText = 159,
            LineStrEscapedChar = 160, LineStrExprStart = 161, TRIPLE_QUOTE_CLOSE = 162,
            MultiLineStringQuote = 163, MultiLineStrRef = 164, MultiLineStrText = 165, MultiLineStrExprStart = 166,
            Inside_WS = 167, Inside_NL = 168, ErrorCharacter = 169;
    public static final int
            RULE_kotlinFile = 0;

    private static String[] makeRuleNames() {
        return new String[]{
                "kotlinFile"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, null, null, null, null, null, "'...'", "'.'", "','", "'('", "')'",
                "'['", "']'", "'{'", "'}'", "'*'", "'%'", "'/'", "'+'", "'-'", "'++'",
                "'--'", "'&&'", "'||'", null, "'!'", "':'", "';'", "'='", "'+='", "'-='",
                "'*='", "'/='", "'%='", "'->'", "'=>'", "'..'", "'::'", "';;'", "'#'",
                "'@'", null, null, null, null, "'?'", "'<'", "'>'", "'<='", "'>='", "'!='",
                "'!=='", "'as?'", "'=='", "'==='", "'''", null, null, null, null, null,
                "'file'", "'field'", "'property'", "'get'", "'set'", "'receiver'", "'param'",
                "'setparam'", "'delegate'", "'package'", "'import'", "'class'", "'interface'",
                "'fun'", "'object'", "'val'", "'var'", "'typealias'", "'constructor'",
                "'by'", "'companion'", "'init'", "'this'", "'super'", "'typeof'", "'where'",
                "'if'", "'else'", "'when'", "'try'", "'catch'", "'finally'", "'for'",
                "'do'", "'while'", "'throw'", "'return'", "'continue'", "'break'", "'as'",
                "'is'", "'in'", null, null, "'out'", "'dynamic'", "'public'", "'private'",
                "'protected'", "'internal'", "'enum'", "'sealed'", "'annotation'", "'data'",
                "'inner'", "'tailrec'", "'operator'", "'inline'", "'infix'", "'external'",
                "'suspend'", "'override'", "'abstract'", "'final'", "'open'", "'const'",
                "'lateinit'", "'vararg'", "'noinline'", "'crossinline'", "'reified'",
                "'expect'", "'actual'", null, null, null, null, null, null, null, null,
                null, "'null'", null, null, null, null, null, "'\"\"\"'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "ShebangLine", "DelimitedComment", "LineComment", "WS", "NL", "RESERVED",
                "DOT", "COMMA", "LPAREN", "RPAREN", "LSQUARE", "RSQUARE", "LCURL", "RCURL",
                "MULT", "MOD", "DIV", "ADD", "SUB", "INCR", "DECR", "CONJ", "DISJ", "EXCL_WS",
                "EXCL_NO_WS", "COLON", "SEMICOLON", "ASSIGNMENT", "ADD_ASSIGNMENT", "SUB_ASSIGNMENT",
                "MULT_ASSIGNMENT", "DIV_ASSIGNMENT", "MOD_ASSIGNMENT", "ARROW", "DOUBLE_ARROW",
                "RANGE", "COLONCOLON", "DOUBLE_SEMICOLON", "HASH", "AT_NO_WS", "AT_POST_WS",
                "AT_PRE_WS", "AT_BOTH_WS", "QUEST_WS", "QUEST_NO_WS", "LANGLE", "RANGLE",
                "LE", "GE", "EXCL_EQ", "EXCL_EQEQ", "AS_SAFE", "EQEQ", "EQEQEQ", "SINGLE_QUOTE",
                "RETURN_AT", "CONTINUE_AT", "BREAK_AT", "THIS_AT", "SUPER_AT", "FILE",
                "FIELD", "PROPERTY", "GET", "SET", "RECEIVER", "PARAM", "SETPARAM", "DELEGATE",
                "PACKAGE", "IMPORT", "CLASS", "INTERFACE", "FUN", "OBJECT", "VAL", "VAR",
                "TYPE_ALIAS", "CONSTRUCTOR", "BY", "COMPANION", "INIT", "THIS", "SUPER",
                "TYPEOF", "WHERE", "IF", "ELSE", "WHEN", "TRY", "CATCH", "FINALLY", "FOR",
                "DO", "WHILE", "THROW", "RETURN", "CONTINUE", "BREAK", "AS", "IS", "IN",
                "NOT_IS", "NOT_IN", "OUT", "DYNAMIC", "PUBLIC", "PRIVATE", "PROTECTED",
                "INTERNAL", "ENUM", "SEALED", "ANNOTATION", "DATA", "INNER", "TAILREC",
                "OPERATOR", "INLINE", "INFIX", "EXTERNAL", "SUSPEND", "OVERRIDE", "ABSTRACT",
                "FINAL", "OPEN", "CONST", "LATEINIT", "VARARG", "NOINLINE", "CROSSINLINE",
                "REIFIED", "EXPECT", "ACTUAL", "RealLiteral", "FloatLiteral", "DoubleLiteral",
                "IntegerLiteral", "HexLiteral", "BinLiteral", "UnsignedLiteral", "LongLiteral",
                "BooleanLiteral", "NullLiteral", "CharacterLiteral", "Identifier", "IdentifierOrSoftKey",
                "FieldIdentifier", "QUOTE_OPEN", "TRIPLE_QUOTE_OPEN", "UNICODE_CLASS_LL",
                "UNICODE_CLASS_LM", "UNICODE_CLASS_LO", "UNICODE_CLASS_LT", "UNICODE_CLASS_LU",
                "UNICODE_CLASS_ND", "UNICODE_CLASS_NL", "QUOTE_CLOSE", "LineStrRef",
                "LineStrText", "LineStrEscapedChar", "LineStrExprStart", "TRIPLE_QUOTE_CLOSE",
                "MultiLineStringQuote", "MultiLineStrRef", "MultiLineStrText", "MultiLineStrExprStart",
                "Inside_WS", "Inside_NL", "ErrorCharacter"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "KotlinParser.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public KotlinParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class KotlinFileContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(KotlinParser.EOF, 0);
        }

        public KotlinFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_kotlinFile;
        }
    }

    public final KotlinFileContext kotlinFile() throws RecognitionException {
        KotlinFileContext _localctx = new KotlinFileContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_kotlinFile);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(2);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u00ab\7\4\2\t\2\3" +
                    "\2\3\2\3\2\2\2\3\2\2\2\2\5\2\4\3\2\2\2\4\5\7\2\2\3\5\3\3\2\2\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}