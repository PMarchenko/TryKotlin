package com.pmarchenko.itdroid.pocketkotlin.syntax.parser.kotlin

import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser.*
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParserBaseListener
import com.pmarchenko.itdroid.pocketkotlin.utils.iLog
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * @author Pavel Marchenko
 */
open class KotlinParserLoggerImplementation : KotlinParserBaseListener() {

    override fun enterKotlinFile(ctx: KotlinFileContext?) {
        iLog("KotlinParser", msg = "enterKotlinFile")
    }

    override fun exitKotlinFile(ctx: KotlinFileContext?) {
        iLog("KotlinParser", msg = "exitKotlinFile")
    }

    override fun enterScript(ctx: ScriptContext?) {
        iLog("KotlinParser", msg = "enterScript")
    }

    override fun exitScript(ctx: ScriptContext?) {
        iLog("KotlinParser", msg = "exitScript")
    }

    override fun enterShebangLine(ctx: ShebangLineContext?) {
        iLog("KotlinParser", msg = "enterShebangLine")
    }

    override fun exitShebangLine(ctx: ShebangLineContext?) {
        iLog("KotlinParser", msg = "exitShebangLine")
    }

    override fun enterFileAnnotation(ctx: FileAnnotationContext?) {
        iLog("KotlinParser", msg = "enterFileAnnotation")
    }

    override fun exitFileAnnotation(ctx: FileAnnotationContext?) {
        iLog("KotlinParser", msg = "exitFileAnnotation")
    }

    override fun enterPackageHeader(ctx: PackageHeaderContext?) {
        iLog("KotlinParser", msg = "enterPackageHeader")
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext?) {
        iLog("KotlinParser", msg = "exitPackageHeader")
    }

    override fun enterImportList(ctx: ImportListContext?) {
        iLog("KotlinParser", msg = "enterImportList")
    }

    override fun exitImportList(ctx: ImportListContext?) {
        iLog("KotlinParser", msg = "exitImportList")
    }

    override fun enterImportHeader(ctx: ImportHeaderContext?) {
        iLog("KotlinParser", msg = "enterImportHeader")
    }

    override fun exitImportHeader(ctx: ImportHeaderContext?) {
        iLog("KotlinParser", msg = "exitImportHeader")
    }

    override fun enterImportAlias(ctx: ImportAliasContext?) {
        iLog("KotlinParser", msg = "enterImportAlias")
    }

    override fun exitImportAlias(ctx: ImportAliasContext?) {
        iLog("KotlinParser", msg = "exitImportAlias")
    }

    override fun enterTopLevelObject(ctx: TopLevelObjectContext?) {
        iLog("KotlinParser", msg = "enterTopLevelObject")
    }

    override fun exitTopLevelObject(ctx: TopLevelObjectContext?) {
        iLog("KotlinParser", msg = "exitTopLevelObject")
    }

    override fun enterTypeAlias(ctx: TypeAliasContext?) {
        iLog("KotlinParser", msg = "enterTypeAlias")
    }

    override fun exitTypeAlias(ctx: TypeAliasContext?) {
        iLog("KotlinParser", msg = "exitTypeAlias")
    }

    override fun enterDeclaration(ctx: DeclarationContext?) {
        iLog("KotlinParser", msg = "enterDeclaration")
    }

    override fun exitDeclaration(ctx: DeclarationContext?) {
        iLog("KotlinParser", msg = "exitDeclaration")
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext?) {
        iLog("KotlinParser", msg = "enterClassDeclaration")
    }

    override fun exitClassDeclaration(ctx: ClassDeclarationContext?) {
        iLog("KotlinParser", msg = "exitClassDeclaration")
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext?) {
        iLog("KotlinParser", msg = "enterPrimaryConstructor")
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext?) {
        iLog("KotlinParser", msg = "exitPrimaryConstructor")
    }

    override fun enterClassBody(ctx: ClassBodyContext?) {
        iLog("KotlinParser", msg = "enterClassBody")
    }

    override fun exitClassBody(ctx: ClassBodyContext?) {
        iLog("KotlinParser", msg = "exitClassBody")
    }

    override fun enterClassParameters(ctx: ClassParametersContext?) {
        iLog("KotlinParser", msg = "enterClassParameters")
    }

    override fun exitClassParameters(ctx: ClassParametersContext?) {
        iLog("KotlinParser", msg = "exitClassParameters")
    }

    override fun enterClassParameter(ctx: ClassParameterContext?) {
        iLog("KotlinParser", msg = "enterClassParameter")
    }

    override fun exitClassParameter(ctx: ClassParameterContext?) {
        iLog("KotlinParser", msg = "exitClassParameter")
    }

    override fun enterDelegationSpecifiers(ctx: DelegationSpecifiersContext?) {
        iLog("KotlinParser", msg = "enterDelegationSpecifiers")
    }

    override fun exitDelegationSpecifiers(ctx: DelegationSpecifiersContext?) {
        iLog("KotlinParser", msg = "exitDelegationSpecifiers")
    }

    override fun enterDelegationSpecifier(ctx: DelegationSpecifierContext?) {
        iLog("KotlinParser", msg = "enterDelegationSpecifier")
    }

    override fun exitDelegationSpecifier(ctx: DelegationSpecifierContext?) {
        iLog("KotlinParser", msg = "exitDelegationSpecifier")
    }

    override fun enterConstructorInvocation(ctx: ConstructorInvocationContext?) {
        iLog("KotlinParser", msg = "enterConstructorInvocation")
    }

    override fun exitConstructorInvocation(ctx: ConstructorInvocationContext?) {
        iLog("KotlinParser", msg = "exitConstructorInvocation")
    }

    override fun enterAnnotatedDelegationSpecifier(ctx: AnnotatedDelegationSpecifierContext?) {
        iLog("KotlinParser", msg = "enterAnnotatedDelegationSpecifier")
    }

    override fun exitAnnotatedDelegationSpecifier(ctx: AnnotatedDelegationSpecifierContext?) {
        iLog("KotlinParser", msg = "exitAnnotatedDelegationSpecifier")
    }

    override fun enterExplicitDelegation(ctx: ExplicitDelegationContext?) {
        iLog("KotlinParser", msg = "enterExplicitDelegation")
    }

    override fun exitExplicitDelegation(ctx: ExplicitDelegationContext?) {
        iLog("KotlinParser", msg = "exitExplicitDelegation")
    }

    override fun enterTypeParameters(ctx: TypeParametersContext?) {
        iLog("KotlinParser", msg = "enterTypeParameters")
    }

    override fun exitTypeParameters(ctx: TypeParametersContext?) {
        iLog("KotlinParser", msg = "exitTypeParameters")
    }

    override fun enterTypeParameter(ctx: TypeParameterContext?) {
        iLog("KotlinParser", msg = "enterTypeParameter")
    }

    override fun exitTypeParameter(ctx: TypeParameterContext?) {
        iLog("KotlinParser", msg = "exitTypeParameter")
    }

    override fun enterTypeConstraints(ctx: TypeConstraintsContext?) {
        iLog("KotlinParser", msg = "enterTypeConstraints")
    }

    override fun exitTypeConstraints(ctx: TypeConstraintsContext?) {
        iLog("KotlinParser", msg = "exitTypeConstraints")
    }

    override fun enterTypeConstraint(ctx: TypeConstraintContext?) {
        iLog("KotlinParser", msg = "enterTypeConstraint")
    }

    override fun exitTypeConstraint(ctx: TypeConstraintContext?) {
        iLog("KotlinParser", msg = "exitTypeConstraint")
    }

    override fun enterClassMemberDeclarations(ctx: ClassMemberDeclarationsContext?) {
        iLog("KotlinParser", msg = "enterClassMemberDeclarations")
    }

    override fun exitClassMemberDeclarations(ctx: ClassMemberDeclarationsContext?) {
        iLog("KotlinParser", msg = "exitClassMemberDeclarations")
    }

    override fun enterClassMemberDeclaration(ctx: ClassMemberDeclarationContext?) {
        iLog("KotlinParser", msg = "enterClassMemberDeclaration")
    }

    override fun exitClassMemberDeclaration(ctx: ClassMemberDeclarationContext?) {
        iLog("KotlinParser", msg = "exitClassMemberDeclaration")
    }

    override fun enterAnonymousInitializer(ctx: AnonymousInitializerContext?) {
        iLog("KotlinParser", msg = "enterAnonymousInitializer")
    }

    override fun exitAnonymousInitializer(ctx: AnonymousInitializerContext?) {
        iLog("KotlinParser", msg = "exitAnonymousInitializer")
    }

    override fun enterCompanionObject(ctx: CompanionObjectContext?) {
        iLog("KotlinParser", msg = "enterCompanionObject")
    }

    override fun exitCompanionObject(ctx: CompanionObjectContext?) {
        iLog("KotlinParser", msg = "exitCompanionObject")
    }

    override fun enterFunctionValueParameters(ctx: FunctionValueParametersContext?) {
        iLog("KotlinParser", msg = "enterFunctionValueParameters")
    }

    override fun exitFunctionValueParameters(ctx: FunctionValueParametersContext?) {
        iLog("KotlinParser", msg = "exitFunctionValueParameters")
    }

    override fun enterFunctionValueParameter(ctx: FunctionValueParameterContext?) {
        iLog("KotlinParser", msg = "enterFunctionValueParameter")
    }

    override fun exitFunctionValueParameter(ctx: FunctionValueParameterContext?) {
        iLog("KotlinParser", msg = "exitFunctionValueParameter")
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext?) {
        iLog("KotlinParser", msg = "enterFunctionDeclaration")
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext?) {
        iLog("KotlinParser", msg = "exitFunctionDeclaration")
    }

    override fun enterFunctionBody(ctx: FunctionBodyContext?) {
        iLog("KotlinParser", msg = "enterFunctionBody")
    }

    override fun exitFunctionBody(ctx: FunctionBodyContext?) {
        iLog("KotlinParser", msg = "exitFunctionBody")
    }

    override fun enterVariableDeclaration(ctx: VariableDeclarationContext?) {
        iLog("KotlinParser", msg = "enterVariableDeclaration")
    }

    override fun exitVariableDeclaration(ctx: VariableDeclarationContext?) {
        iLog("KotlinParser", msg = "exitVariableDeclaration")
    }

    override fun enterMultiVariableDeclaration(ctx: MultiVariableDeclarationContext?) {
        iLog("KotlinParser", msg = "enterMultiVariableDeclaration")
    }

    override fun exitMultiVariableDeclaration(ctx: MultiVariableDeclarationContext?) {
        iLog("KotlinParser", msg = "exitMultiVariableDeclaration")
    }

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext?) {
        iLog("KotlinParser", msg = "enterPropertyDeclaration")
    }

    override fun exitPropertyDeclaration(ctx: PropertyDeclarationContext?) {
        iLog("KotlinParser", msg = "exitPropertyDeclaration")
    }

    override fun enterPropertyDelegate(ctx: PropertyDelegateContext?) {
        iLog("KotlinParser", msg = "enterPropertyDelegate")
    }

    override fun exitPropertyDelegate(ctx: PropertyDelegateContext?) {
        iLog("KotlinParser", msg = "exitPropertyDelegate")
    }

    override fun enterGetter(ctx: GetterContext?) {
        iLog("KotlinParser", msg = "enterGetter")
    }

    override fun exitGetter(ctx: GetterContext?) {
        iLog("KotlinParser", msg = "exitGetter")
    }

    override fun enterSetter(ctx: SetterContext?) {
        iLog("KotlinParser", msg = "enterSetter")
    }

    override fun exitSetter(ctx: SetterContext?) {
        iLog("KotlinParser", msg = "exitSetter")
    }

    override fun enterParametersWithOptionalType(ctx: ParametersWithOptionalTypeContext?) {
        iLog("KotlinParser", msg = "enterParametersWithOptionalType")
    }

    override fun exitParametersWithOptionalType(ctx: ParametersWithOptionalTypeContext?) {
        iLog("KotlinParser", msg = "exitParametersWithOptionalType")
    }

    override fun enterParameterWithOptionalType(ctx: ParameterWithOptionalTypeContext?) {
        iLog("KotlinParser", msg = "enterParameterWithOptionalType")
    }

    override fun exitParameterWithOptionalType(ctx: ParameterWithOptionalTypeContext?) {
        iLog("KotlinParser", msg = "exitParameterWithOptionalType")
    }

    override fun enterParameter(ctx: ParameterContext?) {
        iLog("KotlinParser", msg = "enterParameter")
    }

    override fun exitParameter(ctx: ParameterContext?) {
        iLog("KotlinParser", msg = "exitParameter")
    }

    override fun enterObjectDeclaration(ctx: ObjectDeclarationContext?) {
        iLog("KotlinParser", msg = "enterObjectDeclaration")
    }

    override fun exitObjectDeclaration(ctx: ObjectDeclarationContext?) {
        iLog("KotlinParser", msg = "exitObjectDeclaration")
    }

    override fun enterSecondaryConstructor(ctx: SecondaryConstructorContext?) {
        iLog("KotlinParser", msg = "enterSecondaryConstructor")
    }

    override fun exitSecondaryConstructor(ctx: SecondaryConstructorContext?) {
        iLog("KotlinParser", msg = "exitSecondaryConstructor")
    }

    override fun enterConstructorDelegationCall(ctx: ConstructorDelegationCallContext?) {
        iLog("KotlinParser", msg = "enterConstructorDelegationCall")
    }

    override fun exitConstructorDelegationCall(ctx: ConstructorDelegationCallContext?) {
        iLog("KotlinParser", msg = "exitConstructorDelegationCall")
    }

    override fun enterEnumClassBody(ctx: EnumClassBodyContext?) {
        iLog("KotlinParser", msg = "enterEnumClassBody")
    }

    override fun exitEnumClassBody(ctx: EnumClassBodyContext?) {
        iLog("KotlinParser", msg = "exitEnumClassBody")
    }

    override fun enterEnumEntries(ctx: EnumEntriesContext?) {
        iLog("KotlinParser", msg = "enterEnumEntries")
    }

    override fun exitEnumEntries(ctx: EnumEntriesContext?) {
        iLog("KotlinParser", msg = "exitEnumEntries")
    }

    override fun enterEnumEntry(ctx: EnumEntryContext?) {
        iLog("KotlinParser", msg = "enterEnumEntry")
    }

    override fun exitEnumEntry(ctx: EnumEntryContext?) {
        iLog("KotlinParser", msg = "exitEnumEntry")
    }

    override fun enterType(ctx: TypeContext?) {
        iLog("KotlinParser", msg = "enterType")
    }

    override fun exitType(ctx: TypeContext?) {
        iLog("KotlinParser", msg = "exitType")
    }

    override fun enterTypeReference(ctx: TypeReferenceContext?) {
        iLog("KotlinParser", msg = "enterTypeReference")
    }

    override fun exitTypeReference(ctx: TypeReferenceContext?) {
        iLog("KotlinParser", msg = "exitTypeReference")
    }

    override fun enterNullableType(ctx: NullableTypeContext?) {
        iLog("KotlinParser", msg = "enterNullableType")
    }

    override fun exitNullableType(ctx: NullableTypeContext?) {
        iLog("KotlinParser", msg = "exitNullableType")
    }

    override fun enterQuest(ctx: QuestContext?) {
        iLog("KotlinParser", msg = "enterQuest")
    }

    override fun exitQuest(ctx: QuestContext?) {
        iLog("KotlinParser", msg = "exitQuest")
    }

    override fun enterUserType(ctx: UserTypeContext?) {
        iLog("KotlinParser", msg = "enterUserType")
    }

    override fun exitUserType(ctx: UserTypeContext?) {
        iLog("KotlinParser", msg = "exitUserType")
    }

    override fun enterSimpleUserType(ctx: SimpleUserTypeContext?) {
        iLog("KotlinParser", msg = "enterSimpleUserType")
    }

    override fun exitSimpleUserType(ctx: SimpleUserTypeContext?) {
        iLog("KotlinParser", msg = "exitSimpleUserType")
    }

    override fun enterTypeProjection(ctx: TypeProjectionContext?) {
        iLog("KotlinParser", msg = "enterTypeProjection")
    }

    override fun exitTypeProjection(ctx: TypeProjectionContext?) {
        iLog("KotlinParser", msg = "exitTypeProjection")
    }

    override fun enterTypeProjectionModifiers(ctx: TypeProjectionModifiersContext?) {
        iLog("KotlinParser", msg = "enterTypeProjectionModifiers")
    }

    override fun exitTypeProjectionModifiers(ctx: TypeProjectionModifiersContext?) {
        iLog("KotlinParser", msg = "exitTypeProjectionModifiers")
    }

    override fun enterTypeProjectionModifier(ctx: TypeProjectionModifierContext?) {
        iLog("KotlinParser", msg = "enterTypeProjectionModifier")
    }

    override fun exitTypeProjectionModifier(ctx: TypeProjectionModifierContext?) {
        iLog("KotlinParser", msg = "exitTypeProjectionModifier")
    }

    override fun enterFunctionType(ctx: FunctionTypeContext?) {
        iLog("KotlinParser", msg = "enterFunctionType")
    }

    override fun exitFunctionType(ctx: FunctionTypeContext?) {
        iLog("KotlinParser", msg = "exitFunctionType")
    }

    override fun enterFunctionTypeParameters(ctx: FunctionTypeParametersContext?) {
        iLog("KotlinParser", msg = "enterFunctionTypeParameters")
    }

    override fun exitFunctionTypeParameters(ctx: FunctionTypeParametersContext?) {
        iLog("KotlinParser", msg = "exitFunctionTypeParameters")
    }

    override fun enterParenthesizedType(ctx: ParenthesizedTypeContext?) {
        iLog("KotlinParser", msg = "enterParenthesizedType")
    }

    override fun exitParenthesizedType(ctx: ParenthesizedTypeContext?) {
        iLog("KotlinParser", msg = "exitParenthesizedType")
    }

    override fun enterReceiverType(ctx: ReceiverTypeContext?) {
        iLog("KotlinParser", msg = "enterReceiverType")
    }

    override fun exitReceiverType(ctx: ReceiverTypeContext?) {
        iLog("KotlinParser", msg = "exitReceiverType")
    }

    override fun enterParenthesizedUserType(ctx: ParenthesizedUserTypeContext?) {
        iLog("KotlinParser", msg = "enterParenthesizedUserType")
    }

    override fun exitParenthesizedUserType(ctx: ParenthesizedUserTypeContext?) {
        iLog("KotlinParser", msg = "exitParenthesizedUserType")
    }

    override fun enterStatements(ctx: StatementsContext?) {
        iLog("KotlinParser", msg = "enterStatements")
    }

    override fun exitStatements(ctx: StatementsContext?) {
        iLog("KotlinParser", msg = "exitStatements")
    }

    override fun enterStatement(ctx: StatementContext?) {
        iLog("KotlinParser", msg = "enterStatement")
    }

    override fun exitStatement(ctx: StatementContext?) {
        iLog("KotlinParser", msg = "exitStatement")
    }

    override fun enterLabel(ctx: LabelContext?) {
        iLog("KotlinParser", msg = "enterLabel")
    }

    override fun exitLabel(ctx: LabelContext?) {
        iLog("KotlinParser", msg = "exitLabel")
    }

    override fun enterControlStructureBody(ctx: ControlStructureBodyContext?) {
        iLog("KotlinParser", msg = "enterControlStructureBody")
    }

    override fun exitControlStructureBody(ctx: ControlStructureBodyContext?) {
        iLog("KotlinParser", msg = "exitControlStructureBody")
    }

    override fun enterBlock(ctx: BlockContext?) {
        iLog("KotlinParser", msg = "enterBlock")
    }

    override fun exitBlock(ctx: BlockContext?) {
        iLog("KotlinParser", msg = "exitBlock")
    }

    override fun enterLoopStatement(ctx: LoopStatementContext?) {
        iLog("KotlinParser", msg = "enterLoopStatement")
    }

    override fun exitLoopStatement(ctx: LoopStatementContext?) {
        iLog("KotlinParser", msg = "exitLoopStatement")
    }

    override fun enterForStatement(ctx: ForStatementContext?) {
        iLog("KotlinParser", msg = "enterForStatement")
    }

    override fun exitForStatement(ctx: ForStatementContext?) {
        iLog("KotlinParser", msg = "exitForStatement")
    }

    override fun enterWhileStatement(ctx: WhileStatementContext?) {
        iLog("KotlinParser", msg = "enterWhileStatement")
    }

    override fun exitWhileStatement(ctx: WhileStatementContext?) {
        iLog("KotlinParser", msg = "exitWhileStatement")
    }

    override fun enterDoWhileStatement(ctx: DoWhileStatementContext?) {
        iLog("KotlinParser", msg = "enterDoWhileStatement")
    }

    override fun exitDoWhileStatement(ctx: DoWhileStatementContext?) {
        iLog("KotlinParser", msg = "exitDoWhileStatement")
    }

    override fun enterAssignment(ctx: AssignmentContext?) {
        iLog("KotlinParser", msg = "enterAssignment")
    }

    override fun exitAssignment(ctx: AssignmentContext?) {
        iLog("KotlinParser", msg = "exitAssignment")
    }

    override fun enterSemi(ctx: SemiContext?) {
        iLog("KotlinParser", msg = "enterSemi")
    }

    override fun exitSemi(ctx: SemiContext?) {
        iLog("KotlinParser", msg = "exitSemi")
    }

    override fun enterSemis(ctx: SemisContext?) {
        iLog("KotlinParser", msg = "enterSemis")
    }

    override fun exitSemis(ctx: SemisContext?) {
        iLog("KotlinParser", msg = "exitSemis")
    }

    override fun enterExpression(ctx: ExpressionContext?) {
        iLog("KotlinParser", msg = "enterExpression")
    }

    override fun exitExpression(ctx: ExpressionContext?) {
        iLog("KotlinParser", msg = "exitExpression")
    }

    override fun enterDisjunction(ctx: DisjunctionContext?) {
        iLog("KotlinParser", msg = "enterDisjunction")
    }

    override fun exitDisjunction(ctx: DisjunctionContext?) {
        iLog("KotlinParser", msg = "exitDisjunction")
    }

    override fun enterConjunction(ctx: ConjunctionContext?) {
        iLog("KotlinParser", msg = "enterConjunction")
    }

    override fun exitConjunction(ctx: ConjunctionContext?) {
        iLog("KotlinParser", msg = "exitConjunction")
    }

    override fun enterEquality(ctx: EqualityContext?) {
        iLog("KotlinParser", msg = "enterEquality")
    }

    override fun exitEquality(ctx: EqualityContext?) {
        iLog("KotlinParser", msg = "exitEquality")
    }

    override fun enterComparison(ctx: ComparisonContext?) {
        iLog("KotlinParser", msg = "enterComparison")
    }

    override fun exitComparison(ctx: ComparisonContext?) {
        iLog("KotlinParser", msg = "exitComparison")
    }

    override fun enterGenericCallLikeComparison(ctx: GenericCallLikeComparisonContext?) {
        iLog("KotlinParser", msg = "enterGenericCallLikeComparison")
    }

    override fun exitGenericCallLikeComparison(ctx: GenericCallLikeComparisonContext?) {
        iLog("KotlinParser", msg = "exitGenericCallLikeComparison")
    }

    override fun enterInfixOperation(ctx: InfixOperationContext?) {
        iLog("KotlinParser", msg = "enterInfixOperation")
    }

    override fun exitInfixOperation(ctx: InfixOperationContext?) {
        iLog("KotlinParser", msg = "exitInfixOperation")
    }

    override fun enterElvisExpression(ctx: ElvisExpressionContext?) {
        iLog("KotlinParser", msg = "enterElvisExpression")
    }

    override fun exitElvisExpression(ctx: ElvisExpressionContext?) {
        iLog("KotlinParser", msg = "exitElvisExpression")
    }

    override fun enterElvis(ctx: ElvisContext?) {
        iLog("KotlinParser", msg = "enterElvis")
    }

    override fun exitElvis(ctx: ElvisContext?) {
        iLog("KotlinParser", msg = "exitElvis")
    }

    override fun enterInfixFunctionCall(ctx: InfixFunctionCallContext?) {
        iLog("KotlinParser", msg = "enterInfixFunctionCall")
    }

    override fun exitInfixFunctionCall(ctx: InfixFunctionCallContext?) {
        iLog("KotlinParser", msg = "exitInfixFunctionCall")
    }

    override fun enterRangeExpression(ctx: RangeExpressionContext?) {
        iLog("KotlinParser", msg = "enterRangeExpression")
    }

    override fun exitRangeExpression(ctx: RangeExpressionContext?) {
        iLog("KotlinParser", msg = "exitRangeExpression")
    }

    override fun enterAdditiveExpression(ctx: AdditiveExpressionContext?) {
        iLog("KotlinParser", msg = "enterAdditiveExpression")
    }

    override fun exitAdditiveExpression(ctx: AdditiveExpressionContext?) {
        iLog("KotlinParser", msg = "exitAdditiveExpression")
    }

    override fun enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext?) {
        iLog("KotlinParser", msg = "enterMultiplicativeExpression")
    }

    override fun exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext?) {
        iLog("KotlinParser", msg = "exitMultiplicativeExpression")
    }

    override fun enterAsExpression(ctx: AsExpressionContext?) {
        iLog("KotlinParser", msg = "enterAsExpression")
    }

    override fun exitAsExpression(ctx: AsExpressionContext?) {
        iLog("KotlinParser", msg = "exitAsExpression")
    }

    override fun enterPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext?) {
        iLog("KotlinParser", msg = "enterPrefixUnaryExpression")
    }

    override fun exitPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext?) {
        iLog("KotlinParser", msg = "exitPrefixUnaryExpression")
    }

    override fun enterUnaryPrefix(ctx: UnaryPrefixContext?) {
        iLog("KotlinParser", msg = "enterUnaryPrefix")
    }

    override fun exitUnaryPrefix(ctx: UnaryPrefixContext?) {
        iLog("KotlinParser", msg = "exitUnaryPrefix")
    }

    override fun enterPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext?) {
        iLog("KotlinParser", msg = "enterPostfixUnaryExpression")
    }

    override fun exitPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext?) {
        iLog("KotlinParser", msg = "exitPostfixUnaryExpression")
    }

    override fun enterPostfixUnarySuffix(ctx: PostfixUnarySuffixContext?) {
        iLog("KotlinParser", msg = "enterPostfixUnarySuffix")
    }

    override fun exitPostfixUnarySuffix(ctx: PostfixUnarySuffixContext?) {
        iLog("KotlinParser", msg = "exitPostfixUnarySuffix")
    }

    override fun enterDirectlyAssignableExpression(ctx: DirectlyAssignableExpressionContext?) {
        iLog("KotlinParser", msg = "enterDirectlyAssignableExpression")
    }

    override fun exitDirectlyAssignableExpression(ctx: DirectlyAssignableExpressionContext?) {
        iLog("KotlinParser", msg = "exitDirectlyAssignableExpression")
    }

    override fun enterParenthesizedDirectlyAssignableExpression(ctx: ParenthesizedDirectlyAssignableExpressionContext?) {
        iLog("KotlinParser", msg = "enterParenthesizedDirectlyAssignableExpression")
    }

    override fun exitParenthesizedDirectlyAssignableExpression(ctx: ParenthesizedDirectlyAssignableExpressionContext?) {
        iLog("KotlinParser", msg = "exitParenthesizedDirectlyAssignableExpression")
    }

    override fun enterAssignableExpression(ctx: AssignableExpressionContext?) {
        iLog("KotlinParser", msg = "enterAssignableExpression")
    }

    override fun exitAssignableExpression(ctx: AssignableExpressionContext?) {
        iLog("KotlinParser", msg = "exitAssignableExpression")
    }

    override fun enterParenthesizedAssignableExpression(ctx: ParenthesizedAssignableExpressionContext?) {
        iLog("KotlinParser", msg = "enterParenthesizedAssignableExpression")
    }

    override fun exitParenthesizedAssignableExpression(ctx: ParenthesizedAssignableExpressionContext?) {
        iLog("KotlinParser", msg = "exitParenthesizedAssignableExpression")
    }

    override fun enterAssignableSuffix(ctx: AssignableSuffixContext?) {
        iLog("KotlinParser", msg = "enterAssignableSuffix")
    }

    override fun exitAssignableSuffix(ctx: AssignableSuffixContext?) {
        iLog("KotlinParser", msg = "exitAssignableSuffix")
    }

    override fun enterIndexingSuffix(ctx: IndexingSuffixContext?) {
        iLog("KotlinParser", msg = "enterIndexingSuffix")
    }

    override fun exitIndexingSuffix(ctx: IndexingSuffixContext?) {
        iLog("KotlinParser", msg = "exitIndexingSuffix")
    }

    override fun enterNavigationSuffix(ctx: NavigationSuffixContext?) {
        iLog("KotlinParser", msg = "enterNavigationSuffix")
    }

    override fun exitNavigationSuffix(ctx: NavigationSuffixContext?) {
        iLog("KotlinParser", msg = "exitNavigationSuffix")
    }

    override fun enterCallSuffix(ctx: CallSuffixContext?) {
        iLog("KotlinParser", msg = "enterCallSuffix")
    }

    override fun exitCallSuffix(ctx: CallSuffixContext?) {
        iLog("KotlinParser", msg = "exitCallSuffix")
    }

    override fun enterAnnotatedLambda(ctx: AnnotatedLambdaContext?) {
        iLog("KotlinParser", msg = "enterAnnotatedLambda")
    }

    override fun exitAnnotatedLambda(ctx: AnnotatedLambdaContext?) {
        iLog("KotlinParser", msg = "exitAnnotatedLambda")
    }

    override fun enterTypeArguments(ctx: TypeArgumentsContext?) {
        iLog("KotlinParser", msg = "enterTypeArguments")
    }

    override fun exitTypeArguments(ctx: TypeArgumentsContext?) {
        iLog("KotlinParser", msg = "exitTypeArguments")
    }

    override fun enterValueArguments(ctx: ValueArgumentsContext?) {
        iLog("KotlinParser", msg = "enterValueArguments")
    }

    override fun exitValueArguments(ctx: ValueArgumentsContext?) {
        iLog("KotlinParser", msg = "exitValueArguments")
    }

    override fun enterValueArgument(ctx: ValueArgumentContext?) {
        iLog("KotlinParser", msg = "enterValueArgument")
    }

    override fun exitValueArgument(ctx: ValueArgumentContext?) {
        iLog("KotlinParser", msg = "exitValueArgument")
    }

    override fun enterPrimaryExpression(ctx: PrimaryExpressionContext?) {
        iLog("KotlinParser", msg = "enterPrimaryExpression")
    }

    override fun exitPrimaryExpression(ctx: PrimaryExpressionContext?) {
        iLog("KotlinParser", msg = "exitPrimaryExpression")
    }

    override fun enterParenthesizedExpression(ctx: ParenthesizedExpressionContext?) {
        iLog("KotlinParser", msg = "enterParenthesizedExpression")
    }

    override fun exitParenthesizedExpression(ctx: ParenthesizedExpressionContext?) {
        iLog("KotlinParser", msg = "exitParenthesizedExpression")
    }

    override fun enterCollectionLiteral(ctx: CollectionLiteralContext?) {
        iLog("KotlinParser", msg = "enterCollectionLiteral")
    }

    override fun exitCollectionLiteral(ctx: CollectionLiteralContext?) {
        iLog("KotlinParser", msg = "exitCollectionLiteral")
    }

    override fun enterLiteralConstant(ctx: LiteralConstantContext?) {
        iLog("KotlinParser", msg = "enterLiteralConstant")
    }

    override fun exitLiteralConstant(ctx: LiteralConstantContext?) {
        iLog("KotlinParser", msg = "exitLiteralConstant")
    }

    override fun enterStringLiteral(ctx: StringLiteralContext?) {
        iLog("KotlinParser", msg = "enterStringLiteral")
    }

    override fun exitStringLiteral(ctx: StringLiteralContext?) {
        iLog("KotlinParser", msg = "exitStringLiteral")
    }

    override fun enterLineStringLiteral(ctx: LineStringLiteralContext?) {
        iLog("KotlinParser", msg = "enterLineStringLiteral")
    }

    override fun exitLineStringLiteral(ctx: LineStringLiteralContext?) {
        iLog("KotlinParser", msg = "exitLineStringLiteral")
    }

    override fun enterMultiLineStringLiteral(ctx: MultiLineStringLiteralContext?) {
        iLog("KotlinParser", msg = "enterMultiLineStringLiteral")
    }

    override fun exitMultiLineStringLiteral(ctx: MultiLineStringLiteralContext?) {
        iLog("KotlinParser", msg = "exitMultiLineStringLiteral")
    }

    override fun enterLineStringContent(ctx: LineStringContentContext?) {
        iLog("KotlinParser", msg = "enterLineStringContent")
    }

    override fun exitLineStringContent(ctx: LineStringContentContext?) {
        iLog("KotlinParser", msg = "exitLineStringContent")
    }

    override fun enterLineStringExpression(ctx: LineStringExpressionContext?) {
        iLog("KotlinParser", msg = "enterLineStringExpression")
    }

    override fun exitLineStringExpression(ctx: LineStringExpressionContext?) {
        iLog("KotlinParser", msg = "exitLineStringExpression")
    }

    override fun enterMultiLineStringContent(ctx: MultiLineStringContentContext?) {
        iLog("KotlinParser", msg = "enterMultiLineStringContent")
    }

    override fun exitMultiLineStringContent(ctx: MultiLineStringContentContext?) {
        iLog("KotlinParser", msg = "exitMultiLineStringContent")
    }

    override fun enterMultiLineStringExpression(ctx: MultiLineStringExpressionContext?) {
        iLog("KotlinParser", msg = "enterMultiLineStringExpression")
    }

    override fun exitMultiLineStringExpression(ctx: MultiLineStringExpressionContext?) {
        iLog("KotlinParser", msg = "exitMultiLineStringExpression")
    }

    override fun enterLambdaLiteral(ctx: LambdaLiteralContext?) {
        iLog("KotlinParser", msg = "enterLambdaLiteral")
    }

    override fun exitLambdaLiteral(ctx: LambdaLiteralContext?) {
        iLog("KotlinParser", msg = "exitLambdaLiteral")
    }

    override fun enterLambdaParameters(ctx: LambdaParametersContext?) {
        iLog("KotlinParser", msg = "enterLambdaParameters")
    }

    override fun exitLambdaParameters(ctx: LambdaParametersContext?) {
        iLog("KotlinParser", msg = "exitLambdaParameters")
    }

    override fun enterLambdaParameter(ctx: LambdaParameterContext?) {
        iLog("KotlinParser", msg = "enterLambdaParameter")
    }

    override fun exitLambdaParameter(ctx: LambdaParameterContext?) {
        iLog("KotlinParser", msg = "exitLambdaParameter")
    }

    override fun enterAnonymousFunction(ctx: AnonymousFunctionContext?) {
        iLog("KotlinParser", msg = "enterAnonymousFunction")
    }

    override fun exitAnonymousFunction(ctx: AnonymousFunctionContext?) {
        iLog("KotlinParser", msg = "exitAnonymousFunction")
    }

    override fun enterFunctionLiteral(ctx: FunctionLiteralContext?) {
        iLog("KotlinParser", msg = "enterFunctionLiteral")
    }

    override fun exitFunctionLiteral(ctx: FunctionLiteralContext?) {
        iLog("KotlinParser", msg = "exitFunctionLiteral")
    }

    override fun enterObjectLiteral(ctx: ObjectLiteralContext?) {
        iLog("KotlinParser", msg = "enterObjectLiteral")
    }

    override fun exitObjectLiteral(ctx: ObjectLiteralContext?) {
        iLog("KotlinParser", msg = "exitObjectLiteral")
    }

    override fun enterThisExpression(ctx: ThisExpressionContext?) {
        iLog("KotlinParser", msg = "enterThisExpression")
    }

    override fun exitThisExpression(ctx: ThisExpressionContext?) {
        iLog("KotlinParser", msg = "exitThisExpression")
    }

    override fun enterSuperExpression(ctx: SuperExpressionContext?) {
        iLog("KotlinParser", msg = "enterSuperExpression")
    }

    override fun exitSuperExpression(ctx: SuperExpressionContext?) {
        iLog("KotlinParser", msg = "exitSuperExpression")
    }

    override fun enterIfExpression(ctx: IfExpressionContext?) {
        iLog("KotlinParser", msg = "enterIfExpression")
    }

    override fun exitIfExpression(ctx: IfExpressionContext?) {
        iLog("KotlinParser", msg = "exitIfExpression")
    }

    override fun enterWhenSubject(ctx: WhenSubjectContext?) {
        iLog("KotlinParser", msg = "enterWhenSubject")
    }

    override fun exitWhenSubject(ctx: WhenSubjectContext?) {
        iLog("KotlinParser", msg = "exitWhenSubject")
    }

    override fun enterWhenExpression(ctx: WhenExpressionContext?) {
        iLog("KotlinParser", msg = "enterWhenExpression")
    }

    override fun exitWhenExpression(ctx: WhenExpressionContext?) {
        iLog("KotlinParser", msg = "exitWhenExpression")
    }

    override fun enterWhenEntry(ctx: WhenEntryContext?) {
        iLog("KotlinParser", msg = "enterWhenEntry")
    }

    override fun exitWhenEntry(ctx: WhenEntryContext?) {
        iLog("KotlinParser", msg = "exitWhenEntry")
    }

    override fun enterWhenCondition(ctx: WhenConditionContext?) {
        iLog("KotlinParser", msg = "enterWhenCondition")
    }

    override fun exitWhenCondition(ctx: WhenConditionContext?) {
        iLog("KotlinParser", msg = "exitWhenCondition")
    }

    override fun enterRangeTest(ctx: RangeTestContext?) {
        iLog("KotlinParser", msg = "enterRangeTest")
    }

    override fun exitRangeTest(ctx: RangeTestContext?) {
        iLog("KotlinParser", msg = "exitRangeTest")
    }

    override fun enterTypeTest(ctx: TypeTestContext?) {
        iLog("KotlinParser", msg = "enterTypeTest")
    }

    override fun exitTypeTest(ctx: TypeTestContext?) {
        iLog("KotlinParser", msg = "exitTypeTest")
    }

    override fun enterTryExpression(ctx: TryExpressionContext?) {
        iLog("KotlinParser", msg = "enterTryExpression")
    }

    override fun exitTryExpression(ctx: TryExpressionContext?) {
        iLog("KotlinParser", msg = "exitTryExpression")
    }

    override fun enterCatchBlock(ctx: CatchBlockContext?) {
        iLog("KotlinParser", msg = "enterCatchBlock")
    }

    override fun exitCatchBlock(ctx: CatchBlockContext?) {
        iLog("KotlinParser", msg = "exitCatchBlock")
    }

    override fun enterFinallyBlock(ctx: FinallyBlockContext?) {
        iLog("KotlinParser", msg = "enterFinallyBlock")
    }

    override fun exitFinallyBlock(ctx: FinallyBlockContext?) {
        iLog("KotlinParser", msg = "exitFinallyBlock")
    }

    override fun enterJumpExpression(ctx: JumpExpressionContext?) {
        iLog("KotlinParser", msg = "enterJumpExpression")
    }

    override fun exitJumpExpression(ctx: JumpExpressionContext?) {
        iLog("KotlinParser", msg = "exitJumpExpression")
    }

    override fun enterCallableReference(ctx: CallableReferenceContext?) {
        iLog("KotlinParser", msg = "enterCallableReference")
    }

    override fun exitCallableReference(ctx: CallableReferenceContext?) {
        iLog("KotlinParser", msg = "exitCallableReference")
    }

    override fun enterAssignmentAndOperator(ctx: AssignmentAndOperatorContext?) {
        iLog("KotlinParser", msg = "enterAssignmentAndOperator")
    }

    override fun exitAssignmentAndOperator(ctx: AssignmentAndOperatorContext?) {
        iLog("KotlinParser", msg = "exitAssignmentAndOperator")
    }

    override fun enterEqualityOperator(ctx: EqualityOperatorContext?) {
        iLog("KotlinParser", msg = "enterEqualityOperator")
    }

    override fun exitEqualityOperator(ctx: EqualityOperatorContext?) {
        iLog("KotlinParser", msg = "exitEqualityOperator")
    }

    override fun enterComparisonOperator(ctx: ComparisonOperatorContext?) {
        iLog("KotlinParser", msg = "enterComparisonOperator")
    }

    override fun exitComparisonOperator(ctx: ComparisonOperatorContext?) {
        iLog("KotlinParser", msg = "exitComparisonOperator")
    }

    override fun enterInOperator(ctx: InOperatorContext?) {
        iLog("KotlinParser", msg = "enterInOperator")
    }

    override fun exitInOperator(ctx: InOperatorContext?) {
        iLog("KotlinParser", msg = "exitInOperator")
    }

    override fun enterIsOperator(ctx: IsOperatorContext?) {
        iLog("KotlinParser", msg = "enterIsOperator")
    }

    override fun exitIsOperator(ctx: IsOperatorContext?) {
        iLog("KotlinParser", msg = "exitIsOperator")
    }

    override fun enterAdditiveOperator(ctx: AdditiveOperatorContext?) {
        iLog("KotlinParser", msg = "enterAdditiveOperator")
    }

    override fun exitAdditiveOperator(ctx: AdditiveOperatorContext?) {
        iLog("KotlinParser", msg = "exitAdditiveOperator")
    }

    override fun enterMultiplicativeOperator(ctx: MultiplicativeOperatorContext?) {
        iLog("KotlinParser", msg = "enterMultiplicativeOperator")
    }

    override fun exitMultiplicativeOperator(ctx: MultiplicativeOperatorContext?) {
        iLog("KotlinParser", msg = "exitMultiplicativeOperator")
    }

    override fun enterAsOperator(ctx: AsOperatorContext?) {
        iLog("KotlinParser", msg = "enterAsOperator")
    }

    override fun exitAsOperator(ctx: AsOperatorContext?) {
        iLog("KotlinParser", msg = "exitAsOperator")
    }

    override fun enterPrefixUnaryOperator(ctx: PrefixUnaryOperatorContext?) {
        iLog("KotlinParser", msg = "enterPrefixUnaryOperator")
    }

    override fun exitPrefixUnaryOperator(ctx: PrefixUnaryOperatorContext?) {
        iLog("KotlinParser", msg = "exitPrefixUnaryOperator")
    }

    override fun enterPostfixUnaryOperator(ctx: PostfixUnaryOperatorContext?) {
        iLog("KotlinParser", msg = "enterPostfixUnaryOperator")
    }

    override fun exitPostfixUnaryOperator(ctx: PostfixUnaryOperatorContext?) {
        iLog("KotlinParser", msg = "exitPostfixUnaryOperator")
    }

    override fun enterExcl(ctx: ExclContext?) {
        iLog("KotlinParser", msg = "enterExcl")
    }

    override fun exitExcl(ctx: ExclContext?) {
        iLog("KotlinParser", msg = "exitExcl")
    }

    override fun enterMemberAccessOperator(ctx: MemberAccessOperatorContext?) {
        iLog("KotlinParser", msg = "enterMemberAccessOperator")
    }

    override fun exitMemberAccessOperator(ctx: MemberAccessOperatorContext?) {
        iLog("KotlinParser", msg = "exitMemberAccessOperator")
    }

    override fun enterSafeNav(ctx: SafeNavContext?) {
        iLog("KotlinParser", msg = "enterSafeNav")
    }

    override fun exitSafeNav(ctx: SafeNavContext?) {
        iLog("KotlinParser", msg = "exitSafeNav")
    }

    override fun enterModifiers(ctx: ModifiersContext?) {
        iLog("KotlinParser", msg = "enterModifiers")
    }

    override fun exitModifiers(ctx: ModifiersContext?) {
        iLog("KotlinParser", msg = "exitModifiers")
    }

    override fun enterParameterModifiers(ctx: ParameterModifiersContext?) {
        iLog("KotlinParser", msg = "enterParameterModifiers")
    }

    override fun exitParameterModifiers(ctx: ParameterModifiersContext?) {
        iLog("KotlinParser", msg = "exitParameterModifiers")
    }

    override fun enterModifier(ctx: ModifierContext?) {
        iLog("KotlinParser", msg = "enterModifier")
    }

    override fun exitModifier(ctx: ModifierContext?) {
        iLog("KotlinParser", msg = "exitModifier")
    }

    override fun enterTypeModifiers(ctx: TypeModifiersContext?) {
        iLog("KotlinParser", msg = "enterTypeModifiers")
    }

    override fun exitTypeModifiers(ctx: TypeModifiersContext?) {
        iLog("KotlinParser", msg = "exitTypeModifiers")
    }

    override fun enterTypeModifier(ctx: TypeModifierContext?) {
        iLog("KotlinParser", msg = "enterTypeModifier")
    }

    override fun exitTypeModifier(ctx: TypeModifierContext?) {
        iLog("KotlinParser", msg = "exitTypeModifier")
    }

    override fun enterClassModifier(ctx: ClassModifierContext?) {
        iLog("KotlinParser", msg = "enterClassModifier")
    }

    override fun exitClassModifier(ctx: ClassModifierContext?) {
        iLog("KotlinParser", msg = "exitClassModifier")
    }

    override fun enterMemberModifier(ctx: MemberModifierContext?) {
        iLog("KotlinParser", msg = "enterMemberModifier")
    }

    override fun exitMemberModifier(ctx: MemberModifierContext?) {
        iLog("KotlinParser", msg = "exitMemberModifier")
    }

    override fun enterVisibilityModifier(ctx: VisibilityModifierContext?) {
        iLog("KotlinParser", msg = "enterVisibilityModifier")
    }

    override fun exitVisibilityModifier(ctx: VisibilityModifierContext?) {
        iLog("KotlinParser", msg = "exitVisibilityModifier")
    }

    override fun enterVarianceModifier(ctx: VarianceModifierContext?) {
        iLog("KotlinParser", msg = "enterVarianceModifier")
    }

    override fun exitVarianceModifier(ctx: VarianceModifierContext?) {
        iLog("KotlinParser", msg = "exitVarianceModifier")
    }

    override fun enterTypeParameterModifiers(ctx: TypeParameterModifiersContext?) {
        iLog("KotlinParser", msg = "enterTypeParameterModifiers")
    }

    override fun exitTypeParameterModifiers(ctx: TypeParameterModifiersContext?) {
        iLog("KotlinParser", msg = "exitTypeParameterModifiers")
    }

    override fun enterTypeParameterModifier(ctx: TypeParameterModifierContext?) {
        iLog("KotlinParser", msg = "enterTypeParameterModifier")
    }

    override fun exitTypeParameterModifier(ctx: TypeParameterModifierContext?) {
        iLog("KotlinParser", msg = "exitTypeParameterModifier")
    }

    override fun enterFunctionModifier(ctx: FunctionModifierContext?) {
        iLog("KotlinParser", msg = "enterFunctionModifier")
    }

    override fun exitFunctionModifier(ctx: FunctionModifierContext?) {
        iLog("KotlinParser", msg = "exitFunctionModifier")
    }

    override fun enterPropertyModifier(ctx: PropertyModifierContext?) {
        iLog("KotlinParser", msg = "enterPropertyModifier")
    }

    override fun exitPropertyModifier(ctx: PropertyModifierContext?) {
        iLog("KotlinParser", msg = "exitPropertyModifier")
    }

    override fun enterInheritanceModifier(ctx: InheritanceModifierContext?) {
        iLog("KotlinParser", msg = "enterInheritanceModifier")
    }

    override fun exitInheritanceModifier(ctx: InheritanceModifierContext?) {
        iLog("KotlinParser", msg = "exitInheritanceModifier")
    }

    override fun enterParameterModifier(ctx: ParameterModifierContext?) {
        iLog("KotlinParser", msg = "enterParameterModifier")
    }

    override fun exitParameterModifier(ctx: ParameterModifierContext?) {
        iLog("KotlinParser", msg = "exitParameterModifier")
    }

    override fun enterReificationModifier(ctx: ReificationModifierContext?) {
        iLog("KotlinParser", msg = "enterReificationModifier")
    }

    override fun exitReificationModifier(ctx: ReificationModifierContext?) {
        iLog("KotlinParser", msg = "exitReificationModifier")
    }

    override fun enterPlatformModifier(ctx: PlatformModifierContext?) {
        iLog("KotlinParser", msg = "enterPlatformModifier")
    }

    override fun exitPlatformModifier(ctx: PlatformModifierContext?) {
        iLog("KotlinParser", msg = "exitPlatformModifier")
    }

    override fun enterAnnotation(ctx: AnnotationContext?) {
        iLog("KotlinParser", msg = "enterAnnotation")
    }

    override fun exitAnnotation(ctx: AnnotationContext?) {
        iLog("KotlinParser", msg = "exitAnnotation")
    }

    override fun enterSingleAnnotation(ctx: SingleAnnotationContext?) {
        iLog("KotlinParser", msg = "enterSingleAnnotation")
    }

    override fun exitSingleAnnotation(ctx: SingleAnnotationContext?) {
        iLog("KotlinParser", msg = "exitSingleAnnotation")
    }

    override fun enterMultiAnnotation(ctx: MultiAnnotationContext?) {
        iLog("KotlinParser", msg = "enterMultiAnnotation")
    }

    override fun exitMultiAnnotation(ctx: MultiAnnotationContext?) {
        iLog("KotlinParser", msg = "exitMultiAnnotation")
    }

    override fun enterAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext?) {
        iLog("KotlinParser", msg = "enterAnnotationUseSiteTarget")
    }

    override fun exitAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext?) {
        iLog("KotlinParser", msg = "exitAnnotationUseSiteTarget")
    }

    override fun enterUnescapedAnnotation(ctx: UnescapedAnnotationContext?) {
        iLog("KotlinParser", msg = "enterUnescapedAnnotation")
    }

    override fun exitUnescapedAnnotation(ctx: UnescapedAnnotationContext?) {
        iLog("KotlinParser", msg = "exitUnescapedAnnotation")
    }

    override fun enterSimpleIdentifier(ctx: SimpleIdentifierContext?) {
        iLog("KotlinParser", msg = "enterSimpleIdentifier")
    }

    override fun exitSimpleIdentifier(ctx: SimpleIdentifierContext?) {
        iLog("KotlinParser", msg = "exitSimpleIdentifier")
    }

    override fun enterIdentifier(ctx: IdentifierContext?) {
        iLog("KotlinParser", msg = "enterIdentifier")
    }

    override fun exitIdentifier(ctx: IdentifierContext?) {
        iLog("KotlinParser", msg = "exitIdentifier")
    }

    override fun enterEveryRule(ctx: ParserRuleContext?) {
        iLog("KotlinParser", msg = "enterEveryRule")
    }

    override fun exitEveryRule(ctx: ParserRuleContext?) {
        iLog("KotlinParser", msg = "exitEveryRule")
    }

    override fun visitTerminal(node: TerminalNode?) {
        iLog("KotlinParser", msg = "visitTerminal")
    }

    override fun visitErrorNode(node: ErrorNode?) {
        iLog("KotlinParser", msg = "visitErrorNode")
    }
}