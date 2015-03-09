package ch.heigvd.res.lab01.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with the
     * line separator, the second element is the remaining text. If the argument does
     * not contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
        String[] tab = new String[2];
        String endl;
        
        if(lines.contains("\n")){
            endl = "\n";
        }
        else if (lines.contains("\r")){
            endl = "\r";
        }
        else{
            tab = new String[] {"", lines};
            return tab;
        }

        tab[0] = lines.substring(0, lines.indexOf(endl));
        tab[1] = lines.substring(lines.indexOf(endl)+1);
        
        
        tab[0] = tab[0].concat(endl);
        
        return tab;
    }

}
