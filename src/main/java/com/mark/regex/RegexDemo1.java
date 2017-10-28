package com.mark.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mark
 * Date  : 2017/10/28
 */
public class RegexDemo1 {

    public static void main(String[] args) {
        // 去除单词与 , 和 . 之间的空格
        String Str = "Hello , World .";
        String pattern = "(\\w)(\\s+)([.,])";
        // $0 匹配 `(\w)(\s+)([.,])` 结果为 `o空格,` 和 `d空格.`
        // $1 匹配 `(\w)` 结果为 `o` 和 `d`
        // $2 匹配 `(\s+)` 结果为 `空格` 和 `空格`
        // $3 匹配 `([.,])` 结果为 `,` 和 `.`
        System.out.println(Str.replaceAll(pattern, "$1$3")); // Hello, World.
    }

    private static void matcher() {
        String input = "one backslash \\ and two backslash \\\\";
        final Pattern pattern = Pattern.compile("\\\\");
        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.start() + " " + matcher.end() + matcher.group());
        }
    }

    private static void group() {
        String input = "Here is a WikiWord followed by AnotherWikiWord, then SomeWikiWord.";
        Pattern pattern = Pattern.compile("[A-Z][a-z]*([A-Z][a-z]*)+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println("Found this wiki word: " + matcher.group());
        }
    }


}
