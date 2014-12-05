package ar.edu.itba.geb.compiler.java.core;

import java_cup.runtime.Symbol;

%%

%public
%class AnalizadorLexico
%cup
%line
%column
%ignorecase
%eofclose

%{
	private void error(String message)
	{
		System.err.println("Error at line " + (yyline+1) + ", column " + (yycolumn+1) + " : " + message);
	}
%}

LETTER = ({CAPITAL}|{SMALL})
CAPITAL = [A-Z\xC0-\xD6\xD8-\xDE]
SMALL = [a-z\xDF-\xF6\xF8-\xFF]
DIGIT = [0-9]
IDENT = ({LETTER}|{DIGIT}|['_])
%state COMMENT
%state CHAR
%state CHARESC
%state CHAREND
%state STRING
%state ESCAPED

letter          = [A-Za-z]
digit           = [0-9]
alphanumeric    = {letter}|{digit}
other_id_char   = [_]
identifier      = {letter}({alphanumeric}|{other_id_char})*
integer			= 0 | [1-9]([0-9])*
whiteSpace = [\r\n\z\t\f ]
string = "\"" [^\"]* "\""
char            = [^\r\n]
comment_body    = {char}*\n
comment         = "//" {comment_body}
%%

//keywords

"public"		{return new Symbol(sym.PUBLIC,yyline+1,yycolumn+1);}
"class"		{return new Symbol(sym.CLASS,yyline+1,yycolumn+1);}
"void"		{return new Symbol(sym.VOID,yyline+1,yycolumn+1);}
"main"		{return new Symbol(sym.MAIN,yyline+1,yycolumn+1);}
"String"		{return new Symbol(sym.STRING,yyline+1,yycolumn+1);}
"return"	{return new Symbol(sym.RETURN,yyline+1,yycolumn+1);}
"int"		{return new Symbol(sym.INT,yyline+1,yycolumn+1);}
"boolean"	{return new Symbol(sym.BOOL,yyline+1,yycolumn+1);}
"si"		{return new Symbol(sym.IF,yyline+1,yycolumn+1);}
"sino"		{return new Symbol(sym.ELSE,yyline+1,yycolumn+1);}
"mientras"	{return new Symbol(sym.WHILE,yyline+1,yycolumn+1);}
"printInt"	{return new Symbol(sym.PRINT_INT,yyline+1,yycolumn+1);}
"printStr"	{return new Symbol(sym.PRINT_STRING,yyline+1,yycolumn+1);}
"true"		{return new Symbol(sym.TRUE,yyline+1,yycolumn+1);}
"false"		{return new Symbol(sym.FALSE,yyline+1,yycolumn+1);}
"this"		{return new Symbol(sym.THIS,yyline+1,yycolumn+1);}
"new"		{return new Symbol(sym.NEW,yyline+1,yycolumn+1);}

"!"		{return new Symbol(sym.NOT, yyline+1, yycolumn+1);}
"&&"		{return new Symbol(sym.AND, yyline+1, yycolumn+1);}
"=="		{return new Symbol(sym.EQUAL, yyline+1, yycolumn+1);}
"<="		{return new Symbol(sym.LESS_OR_EQUAL, yyline+1, yycolumn+1);}
">="		{return new Symbol(sym.GREATER_OR_EQUAL, yyline+1, yycolumn+1);}
"!="		{return new Symbol(sym.NOT_EQUAL, yyline+1, yycolumn+1);}
"+="		{return new Symbol(sym.ADD_ASSIGN, yyline+1, yycolumn+1);}
"-="		{return new Symbol(sym.SUB_ASSIGN, yyline+1, yycolumn+1);}
"*="		{return new Symbol(sym.MULT_ASSIGN, yyline+1, yycolumn+1);}
"||"		{return new Symbol(sym.OR, yyline+1, yycolumn+1);}
"="			{return new Symbol(sym.ASSIGN, yyline+1, yycolumn+1);}
"<"			{return new Symbol(sym.LESS, yyline+1, yycolumn+1);}
">"			{return new Symbol(sym.GREATER, yyline+1, yycolumn+1);}
"+"			{return new Symbol(sym.ADD,yyline+1,yycolumn+1);}
"-"			{return new Symbol(sym.SUB,yyline+1,yycolumn+1);}
"*"			{return new Symbol(sym.MULT,yyline+1,yycolumn+1);}
"/"			{return new Symbol(sym.DIV,yyline+1,yycolumn+1);}
"."			{return new Symbol(sym.DOT,yyline+1,yycolumn+1);}


","			{return new Symbol(sym.COMMA,yyline+1,yycolumn+1);}
";"			{return new Symbol(sym.SEMI,yyline+1,yycolumn+1);}
"."			{return new Symbol(sym.DOT,yyline+1,yycolumn+1);}
"{"			{return new Symbol(sym.LBRACE,yyline+1,yycolumn+1);}
"}"			{return new Symbol(sym.RBRACE,yyline+1,yycolumn+1);}
"("			{return new Symbol(sym.LPARENS,yyline+1,yycolumn+1);}
")"			{return new Symbol(sym.RPARENS,yyline+1,yycolumn+1);}
"["			{return new Symbol(sym.LBRACKET,yyline+1,yycolumn+1);}
"]"			{return new Symbol(sym.RBRACKET,yyline+1,yycolumn+1);}

{string}		{return new Symbol(sym.STRING_LITERAL, yyline+1, yycolumn+1, yytext());}
{DIGIT}+ { return new Symbol(sym._INTEGER_, new Integer(yytext())); }
{LETTER}{IDENT}* { return new Symbol(sym.ID, yytext().intern()); }
[ \t\r\n\f] { /* ignore white space. */ }

{comment} 		{ /* Ignore comment. */ }

" "		{}