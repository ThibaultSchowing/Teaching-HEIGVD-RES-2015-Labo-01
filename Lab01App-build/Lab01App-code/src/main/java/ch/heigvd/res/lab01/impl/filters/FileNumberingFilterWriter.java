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
    private boolean charRetour = false;
    private boolean firstLine = true;
    
    private String nextLine(){
        return noline++ + "\t";
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
        if(firstLine){
            ligne = nextLine();
            out.write(ligne,0,ligne.length());
            firstLine = false;
        }
        
        String[] lines = Utils.getNextLine(str.substring(off,off+len));
        if(lines[0].isEmpty()){
            out.write(lines[1],0,lines[1].length());
            return;
        }
        
        while(!lines[0].isEmpty()){
            out.write(lines[0],0,lines[0].length());
            ligne = nextLine();
            out.write(ligne,0,ligne.length());
            lines = Utils.getNextLine(lines[1]);
        }
        if(!lines[1].isEmpty()){
            out.write(lines[1],0,lines[1].length());
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
        String s = new String(cbuf);
        write(s,off,len);
    }

    @Override
    public void write(int c) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
        // Le caractère précédent était un retour, pas besoin d'écrire le no de ligne
        if (firstLine){
            out.write(noline++ + "\t");
            firstLine = false;
        }
        if((char)c == '\n' || (char)c == '\r'){
            charRetour = true;
        } else if (charRetour ){
            out.write(noline++ + "\t");
            charRetour = false;
        } 
        
        out.write(c);
    }

}
