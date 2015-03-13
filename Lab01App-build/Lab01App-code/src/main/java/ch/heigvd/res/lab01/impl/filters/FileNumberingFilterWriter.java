package ch.heigvd.res.lab01.impl.filters;

import ch.heigvd.res.lab01.impl.Utils;
import java.io.BufferedReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer. When
 * filter encounters a line separator, it sends it to the decorated writer. It then
 * sends the line number and a tab character, before resuming the write process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }
    private int noline = 1;
    private String ligne;

    @Override
    public void write(String str, int off, int len) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");

        String str2 = str.substring(off, off + len);
        BufferedReader bufReader = new BufferedReader(new StringReader(str2));
        
        String[] line = null;
        String temp = null;
        String result = "";
        
        Boolean contienRetour = false;
        
        line = Utils.getNextLine(str2);
        
        while (!line[0].equals("")) { 
            temp = noline + "\t" + line[0];
            result += temp;
            noline++;
            
            if(line[0].contains("\n") || line[0].contains("\r")){
                contienRetour = true;
            }
            else{
                contienRetour = false;
            }
            line = Utils.getNextLine(line[1]);
            
            // S'il y a un retour à la ligne dans la dernière ligne, il faut faire une nouvelle ligne avec juste un numéro

        }
        if (contienRetour)
            result += noline + "\t" + line[1];
        System.out.println(result);
        
//        if(contienRetour){
//            result += noline + "\t" + line[1];
//        }
        
        super.write(result,0,result.length());
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
        String s = new String(cbuf);
        write(s,off,len);
    }

    @Override
    public void write(int c) throws IOException {
        throw new UnsupportedOperationException("The student has not implemented this method yet.");
    }

}
