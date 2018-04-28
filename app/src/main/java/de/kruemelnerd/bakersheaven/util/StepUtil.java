package de.kruemelnerd.bakersheaven.util;

public class StepUtil {

    public static String removeFirstNumber(String instruction){
        return instruction.replaceFirst("(^\\d+\\. )", "");
    }
}
