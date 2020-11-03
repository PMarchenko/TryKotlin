/**
 * Kotlin syntax grammar in ANTLR4 notation
 */

parser grammar KotlinParser;

options { tokenVocab = KotlinLexer; }

// SECTION: general

kotlinFile
    : NL* fileAnnotation* packageHeader importList topLevelObject* EOF
    ;

fileAnnotation
    : (AT_NO_WS | AT_PRE_WS) FILE NL* COLON NL* (LSQUARE unescapedAnnotation+ RSQUARE | unescapedAnnotation) NL*
    ;

packageHeader
    : (PACKAGE identifier semi?)?
    ;

importList
    : importHeader*
    ;

importHeader
    : IMPORT identifier (DOT MULT | importAlias)? semi?
    ;

importAlias
    : AS simpleIdentifier
    ;

topLevelObject
    : declaration semis?
    ;

typeAlias
    : modifiers? TYPE_ALIAS NL* simpleIdentifier (NL* typeParameters)? NL* ASSIGNMENT NL* type
    ;

declaration
    : classDeclaration
    | objectDeclaration
    | functionDeclaration
    | propertyDeclaration
    | typeAlias
    ;

// SECTION: classes

classDeclaration
    : modifiers? (CLASS | (FUN NL*)? INTERFACE) NL* simpleIdentifier
    (NL* typeParameters)? (NL* primaryConstructor)?
    (NL* COLON NL* delegationSpecifiers)?
    (NL* typeConstraints)?
    (NL* classBody | NL* enumClassBody)?
    ;

primaryConstructor
    : (modifiers? CONSTRUCTOR NL*)? classParameters
    ;

classBody
    : LCURL NL* classMemberDeclarations NL* RCURL
    ;

classParameters
    : LPAREN NL* (classParameter (NL* COMMA NL* classParameter)* (NL* COMMA)?)? NL* RPAREN
    ;

classParameter
    : modifiers? (VAL | VAR)? NL* simpleIdentifier COLON NL* type (NL* ASSIGNMENT NL* expression)?
    ;

delegationSpecifiers
    : annotatedDelegationSpecifier (NL* COMMA NL* annotatedDelegationSpecifier)*
    ;

delegationSpecifier
    : constructorInvocation
    | explicitDelegation
    | userType
    | functionType
    ;

constructorInvocation
    : userType valueArguments
    ;

annotatedDelegationSpecifier
    : NL* delegationSpecifier
    ;

explicitDelegation
    : (userType | functionType) NL* BY NL* expression
    ;

typeParameters
    : LANGLE NL* typeParameter (NL* COMMA NL* typeParameter)* (NL* COMMA)? NL* RANGLE
    ;

typeParameter
    : typeParameterModifiers? NL* simpleIdentifier (NL* COLON NL* type)?
    ;

typeConstraints
    : WHERE NL* typeConstraint (NL* COMMA NL* typeConstraint)*
    ;

typeConstraint
    : simpleIdentifier NL* COLON NL* type
    ;

// SECTION: classMembers

classMemberDeclarations
    : (classMemberDeclaration semis?)*
    ;

classMemberDeclaration
    : declaration
    | companionObject
    | anonymousInitializer
    | secondaryConstructor
    ;

anonymousInitializer
    : INIT NL* block
    ;

companionObject
    : modifiers? COMPANION NL* OBJECT
    (NL* simpleIdentifier)?
    (NL* COLON NL* delegationSpecifiers)?
    (NL* classBody)?
    ;

functionValueParameters
    : LPAREN NL* (functionValueParameter (NL* COMMA NL* functionValueParameter)* (NL* COMMA)?)? NL* RPAREN
    ;

functionValueParameter
    : parameterModifiers? parameter (NL* ASSIGNMENT NL* expression)?
    ;

functionDeclaration
    : modifiers?
    FUN (NL* typeParameters)? (NL* receiverType NL* DOT)? NL* simpleIdentifier
    NL* functionValueParameters
    (NL* COLON NL* type)?
    (NL* typeConstraints)?
    (NL* functionBody)?
    ;

functionBody
    : block
    | ASSIGNMENT NL* expression
    ;

variableDeclaration
    : NL* simpleIdentifier (NL* COLON NL* type)?
    ;

multiVariableDeclaration
    : LPAREN NL* variableDeclaration (NL* COMMA NL* variableDeclaration)* (NL* COMMA)? NL* RPAREN
    ;

propertyDeclaration
    : modifiers? (VAL | VAR)
    (NL* typeParameters)?
    (NL* receiverType NL* DOT)?
    (NL* (multiVariableDeclaration | variableDeclaration))
    (NL* typeConstraints)?
    (NL* (ASSIGNMENT NL* expression | propertyDelegate))?
    (NL+ SEMICOLON)? NL* (getter? (NL* semi? setter)? | setter? (NL* semi? getter)?)
    ;

propertyDelegate
    : BY NL* expression
    ;

getter
    : modifiers? GET
    | modifiers? GET NL* LPAREN NL* RPAREN (NL* COLON NL* type)? NL* functionBody
    ;

setter
    : modifiers? SET
    | modifiers? SET NL* LPAREN NL* parameterWithOptionalType (NL* COMMA)? NL* RPAREN (NL* COLON NL* type)? NL* functionBody
    ;

parametersWithOptionalType
    : LPAREN NL* (parameterWithOptionalType (NL* COMMA NL* parameterWithOptionalType)* (NL* COMMA)?)? NL* RPAREN
    ;

parameterWithOptionalType
    : parameterModifiers? simpleIdentifier NL* (COLON NL* type)?
    ;

parameter
    : simpleIdentifier NL* COLON NL* type
    ;

objectDeclaration
    : modifiers? OBJECT
    NL* simpleIdentifier
    (NL* COLON NL* delegationSpecifiers)?
    (NL* classBody)?
    ;

secondaryConstructor
    : modifiers? CONSTRUCTOR NL* functionValueParameters (NL* COLON NL* constructorDelegationCall)? NL* block?
    ;

constructorDelegationCall
    : THIS NL* valueArguments
    | SUPER NL* valueArguments
    ;

// SECTION: enumClasses

enumClassBody
    : LCURL NL* enumEntries? (NL* SEMICOLON NL* classMemberDeclarations)? NL* RCURL
    ;

enumEntries
    : enumEntry (NL* COMMA NL* enumEntry)* NL* COMMA?
    ;

enumEntry
    : (modifiers NL*)? simpleIdentifier (NL* valueArguments)? (NL* classBody)?
    ;

// SECTION: types

type
    : typeModifiers?
    ( parenthesizedType
    | nullableType
    | typeReference
    | functionType)
    ;

typeReference
    : userType
    | DYNAMIC
    ;

nullableType
    : (typeReference | parenthesizedType) NL* quest+
    ;

quest
    : QUEST_NO_WS
    | QUEST_WS
    ;

userType
    : simpleUserType (NL* DOT NL* simpleUserType)*
    ;

simpleUserType
    : simpleIdentifier (NL* typeArguments)?
    ;

typeProjection
    : typeProjectionModifiers? type | MULT
    ;

typeProjectionModifiers
    : typeProjectionModifier+
    ;

typeProjectionModifier
    : varianceModifier NL*
    ;

functionType
    : (receiverType NL* DOT NL*)? functionTypeParameters NL* ARROW NL* type
    ;

functionTypeParameters
    : LPAREN NL* (parameter | type)? (NL* COMMA NL* (parameter | type))* (NL* COMMA)? NL* RPAREN
    ;

parenthesizedType
    : LPAREN NL* type NL* RPAREN
    ;

receiverType
    : typeModifiers?
    ( parenthesizedType
    | nullableType
    | typeReference)
    ;

parenthesizedUserType
    : LPAREN NL* userType NL* RPAREN
    | LPAREN NL* parenthesizedUserType NL* RPAREN
    ;

// SECTION: statements

statements
    : (statement (semis statement)*)? semis?
    ;

statement
    : (label)*
    ( declaration
    | assignment
    | loopStatement
    | expression)
    ;

label
    : simpleIdentifier (AT_NO_WS | AT_POST_WS) NL*
    ;

controlStructureBody
    : block
    | statement
    ;

block
    : LCURL NL* statements NL* RCURL
    ;

loopStatement
    : forStatement
    | whileStatement
    | doWhileStatement
    ;

forStatement
    : FOR NL* LPAREN (variableDeclaration | multiVariableDeclaration) IN expression RPAREN NL* controlStructureBody?
    ;

whileStatement
    : WHILE NL* LPAREN expression RPAREN NL* controlStructureBody
    | WHILE NL* LPAREN expression RPAREN NL* SEMICOLON
    ;

doWhileStatement
    : DO NL* controlStructureBody? NL* WHILE NL* LPAREN expression RPAREN
    ;

assignment
    : directlyAssignableExpression ASSIGNMENT NL* expression
    | assignableExpression assignmentAndOperator NL* expression
    ;

semi
    : (SEMICOLON | NL) NL*
    | EOF;

semis
    : (SEMICOLON | NL)+
    | EOF
    ;

// SECTION: expressions

expression
    : Letter? NL
    ;


prefixUnaryExpression
    : unaryPrefix* postfixUnaryExpression
    ;

unaryPrefix
    : label
    | prefixUnaryOperator NL*
    ;

postfixUnaryExpression
    : primaryExpression
    | primaryExpression postfixUnarySuffix+
    ;

postfixUnarySuffix
    : postfixUnaryOperator
    | typeArguments
    | callSuffix
    | indexingSuffix
    | navigationSuffix
    ;

directlyAssignableExpression
    : postfixUnaryExpression assignableSuffix
    | simpleIdentifier
    | parenthesizedDirectlyAssignableExpression
    ;

parenthesizedDirectlyAssignableExpression
    : LPAREN NL* directlyAssignableExpression NL* RPAREN
    ;

assignableExpression
    : prefixUnaryExpression | parenthesizedAssignableExpression
    ;

parenthesizedAssignableExpression
    : LPAREN NL* assignableExpression NL* RPAREN
    ;

assignableSuffix
    : typeArguments
    | indexingSuffix
    | navigationSuffix
    ;

indexingSuffix
    : LSQUARE NL* expression (NL* COMMA NL* expression)* (NL* COMMA)? NL* RSQUARE
    ;

navigationSuffix
    : NL* memberAccessOperator NL* (simpleIdentifier | parenthesizedExpression | CLASS)
    ;

callSuffix
    : typeArguments? valueArguments? annotatedLambda
    | typeArguments? valueArguments
    ;

annotatedLambda
    : label? NL* lambdaLiteral
    ;

typeArguments
    : LANGLE NL* typeProjection (NL* COMMA NL* typeProjection)* (NL* COMMA)? NL* RANGLE
    ;

valueArguments
    : LPAREN NL* RPAREN
    | LPAREN NL* valueArgument (NL* COMMA NL* valueArgument)* (NL* COMMA)? NL* RPAREN
    ;

valueArgument
    : NL* (simpleIdentifier NL* ASSIGNMENT NL*)? MULT? NL* expression
    ;

primaryExpression
    : parenthesizedExpression
    | simpleIdentifier
    | literalConstant
    | stringLiteral
    | callableReference
    | functionLiteral
    | objectLiteral
    ;

parenthesizedExpression
    : LPAREN NL* expression NL* RPAREN
    ;

literalConstant
    : BooleanLiteral
    | IntegerLiteral
    | HexLiteral
    | BinLiteral
    | CharacterLiteral
    | RealLiteral
    | NullLiteral
    | LongLiteral
    | UnsignedLiteral
    ;

stringLiteral
    : lineStringLiteral
    | multiLineStringLiteral
    ;

lineStringLiteral
    : QUOTE_OPEN (lineStringContent | lineStringExpression)* QUOTE_CLOSE
    ;

multiLineStringLiteral
    : TRIPLE_QUOTE_OPEN (multiLineStringContent | multiLineStringExpression | MultiLineStringQuote)* TRIPLE_QUOTE_CLOSE
    ;

lineStringContent
    : LineStrText
    | LineStrEscapedChar
    | LineStrRef
    ;

lineStringExpression
    : LineStrExprStart expression RCURL
    ;

multiLineStringContent
    : MultiLineStrText
    | MultiLineStringQuote
    | MultiLineStrRef
    ;

multiLineStringExpression
    : MultiLineStrExprStart NL* expression NL* RCURL
    ;

lambdaLiteral
    : LCURL NL* statements NL* RCURL
    | LCURL NL* lambdaParameters? NL* ARROW NL* statements NL* RCURL
    ;

lambdaParameters
    : lambdaParameter (NL* COMMA NL* lambdaParameter)* (NL* COMMA)?
    ;

lambdaParameter
    : variableDeclaration
    | multiVariableDeclaration (NL* COLON NL* type)?
    ;

anonymousFunction
    : FUN
    (NL* type NL* DOT)?
    NL* parametersWithOptionalType
    (NL* COLON NL* type)?
    (NL* typeConstraints)?
    (NL* functionBody)?
    ;

functionLiteral
    : lambdaLiteral
    | anonymousFunction
    ;

objectLiteral
    : OBJECT NL* COLON NL* delegationSpecifiers NL* classBody
    | OBJECT NL* classBody
    ;

callableReference
    : (receiverType? NL* COLONCOLON NL* (simpleIdentifier | CLASS))
    ;

assignmentAndOperator
    : ADD_ASSIGNMENT
    | SUB_ASSIGNMENT
    | MULT_ASSIGNMENT
    | DIV_ASSIGNMENT
    | MOD_ASSIGNMENT
    ;

prefixUnaryOperator
    : INCR
    | DECR
    | SUB
    | ADD
    | excl
    ;

postfixUnaryOperator
    : INCR
    | DECR
    | EXCL_NO_WS excl
    ;

excl
    : EXCL_NO_WS
    | EXCL_WS
    ;

memberAccessOperator
    : DOT | safeNav | COLONCOLON
    ;

safeNav
    : QUEST_NO_WS DOT
    ;

// SECTION: modifiers

modifiers
    : (modifier)+
    ;

parameterModifiers
    : (parameterModifier)+
    ;

modifier
    : (classModifier
    | memberModifier
    | visibilityModifier
    | functionModifier
    | propertyModifier
    | inheritanceModifier
    | parameterModifier
    | platformModifier) NL*
    ;

typeModifiers
    : typeModifier+
    ;

typeModifier
    : SUSPEND NL*
    ;

classModifier
    : ENUM
    | SEALED
    | ANNOTATION
    | DATA
    | INNER
    ;

memberModifier
    : OVERRIDE
    | LATEINIT
    ;

visibilityModifier
    : PUBLIC
    | PRIVATE
    | INTERNAL
    | PROTECTED
    ;

varianceModifier
    : IN
    | OUT
    ;

typeParameterModifiers
    : typeParameterModifier+
    ;

typeParameterModifier
    : reificationModifier NL*
    | varianceModifier NL*
    ;

functionModifier
    : TAILREC
    | OPERATOR
    | INFIX
    | INLINE
    | EXTERNAL
    | SUSPEND
    ;

propertyModifier
    : CONST
    ;

inheritanceModifier
    : ABSTRACT
    | FINAL
    | OPEN
    ;

parameterModifier
    : VARARG
    | NOINLINE
    | CROSSINLINE
    ;

reificationModifier
    : REIFIED
    ;

platformModifier
    : EXPECT
    | ACTUAL
    ;

unescapedAnnotation
    : constructorInvocation
    | userType
    ;

// SECTION: identifiers

simpleIdentifier: Identifier
    | ABSTRACT
    | ANNOTATION
    | BY
    | CATCH
    | COMPANION
    | CONSTRUCTOR
    | CROSSINLINE
    | DATA
    | DYNAMIC
    | ENUM
    | EXTERNAL
    | FINAL
    | FINALLY
    | GET
    | IMPORT
    | INFIX
    | INIT
    | INLINE
    | INNER
    | INTERNAL
    | LATEINIT
    | NOINLINE
    | OPEN
    | OPERATOR
    | OUT
    | OVERRIDE
    | PRIVATE
    | PROTECTED
    | PUBLIC
    | REIFIED
    | SEALED
    | TAILREC
    | SET
    | VARARG
    | WHERE
    | FIELD
    | PROPERTY
    | RECEIVER
    | PARAM
    | SETPARAM
    | DELEGATE
    | FILE
    | EXPECT
    | ACTUAL
    | CONST
    | SUSPEND
    ;

identifier
    : simpleIdentifier (NL* DOT simpleIdentifier)*
    ;