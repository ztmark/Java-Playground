package com.mark.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mark
 * Date  : 2017/10/28
 */
public class RegexDemo1 {

    public static void main(String[] args) {
//        group();
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
