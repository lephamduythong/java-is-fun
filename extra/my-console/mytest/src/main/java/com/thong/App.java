package com.thong;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.AbstractDelta;

public class App 
{
    public static void main( String[] args )
    {
        var input1 = "[A,B,C,D]";
        var input2 = "[A,B,C,E]";

        ArrayList<String> list1 = parseList(input1);
        ArrayList<String> list2 = parseList(input2);

        System.out.println("List 1: " + list1);
        System.out.println("List 2: " + list2);

        String result = compareLists(list1, list2);
        System.out.println("Comparison result: " + result);

        // Example usage of diffStrings
        String str1 = "Hello World";
        String str2 = "Hello World2";
        diffStrings(str1, str2);
    }

    /**
     * Convert a string like "[A,B,C,D]" to ArrayList<String>
     */
    public static ArrayList<String> parseList(String input) {
        // Remove [ and ]
        String trimmed = input.trim();
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        }
        // If the string is empty after removing brackets, return an empty list
        if (trimmed.isEmpty()) {
            return new ArrayList<>();
        }
        // Split elements, trim extra spaces
        String[] parts = trimmed.split(",");
        ArrayList<String> result = new ArrayList<>();
        for (String part : parts) {
            result.add(part.trim());
        }
        return result;
    }

    /**
     * Compare two ArrayList<String>, return missing and redundant elements
     * @param list1 First list
     * @param list2 Second list
     * @return String describing missing and redundant elements
     */
    public static String compareLists(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> missing = new ArrayList<>(list2);
        missing.removeAll(list1);

        ArrayList<String> extra = new ArrayList<>(list1);
        extra.removeAll(list2);

        return "Missing: " + missing + ", Redundant: " + extra;
    }

    /**
     * Compare two strings and print the differences using java-diff-utils
     */
    public static void diffStrings(String str1, String str2) {
        List<String> original = Arrays.asList(str1.split("\\R"));
        List<String> revised = Arrays.asList(str2.split("\\R"));
        
        var patch = DiffUtils.diff(original, revised);

        for (AbstractDelta<String> delta : patch.getDeltas()) {
            System.out.println(delta);
        }
    }
}
