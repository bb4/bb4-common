/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression

abstract sealed class Token(val symbol: Char)

case object PLUS extends Token('+')
case object MINUS extends Token('-')
case object LEFT_PAREN extends Token('(')
case object RIGHT_PAREN extends Token('+')
