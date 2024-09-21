grammar SCIMFilter;

NOT: 'not';
OPEN_PARA: '(';
CLOSE_PARA: ')';
OPEN_BRACKET: '[';
CLOSE_BRACKET: ']';
PR: 'pr';
AND: 'and';
OR: 'or';
EQ: 'eq';
NE: 'ne';
CO: 'co';
SW: 'sw';
EW: 'ew';
GT: 'gt';
LT: 'lt';
GE: 'ge';
LE: 'le';
QUOTE: '"';
DOUBLE_QUOTE: '""';
HYPHEN: '-';
UNDERSCORE: '_';
DOT: '.';
FALSE: 'false';
NULL: 'null';
TRUE: 'true';
NEW_RETURN: '\r';
NEW_LINE: '\n';

ALPHA : [a-zA-Z];
DIGIT : [0-9];
SP    : [ \t]+;
URI: [a-zA-Z]+([:][a-zA-Z0-9_\-]+)+;
ATTR_NAME: [a-zA-Z]+[a-zA-Z0-9_\-]*;
DOUBLE_SLASH: '\\';
QUOTE_SLASH: ["\\];
ANY: .;

expression
    : filter
    ;

filter
    : filterExpression
    | moreFilters
    ;

moreFilters
    : filterExpression SP filterOperator SP filter
    ;

filterExpression
    : attrExp
    | closedFilter
    | notFilter
    | valuePath
    ;

closedFilter
    : OPEN_PARA filter CLOSE_PARA
    ;

notFilter
    : NOT closedFilter
    ;

valuePath
    : attrPath OPEN_BRACKET filter CLOSE_BRACKET
    ;

attrExp
    : attrPath SP valuePresent
    | attrPath SP compareOp SP compValue
    ;

filterOperator
    : AND | OR
    ;

valuePresent: PR;

compValue
    : FALSE
    | NULL
    | TRUE
    | number
    | stringValue
    ;

stringValue
    : QUOTE ( ~QUOTE | DOUBLE_SLASH QUOTE )* QUOTE
    ;

number: DIGIT+;

compareOp
    : EQ
    | NE
    | CO
    | SW
    | EW
    | GT
    | LT
    | GE
    | LE
    ;

attrPath
    : URI HYPHEN ATTR_NAME subAttr*
    | ATTR_NAME subAttr*
    | ATTR_NAME
    ;

subAttr
    : DOT ATTR_NAME
    ;

