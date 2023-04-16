

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class App {
    public static void main(String[] args) throws Exception {
        
           
        List<Hello> arr = new ArrayList<Hello>();
        List<String> lines = new ArrayList<String>();
        String Name = null;
        String FileName = null;
        String line = null;

        String fff = ".arxml";
        String m = "_mod.arxml";
        Scanner k = new Scanner(System.in);
        FileName = k.next();
        try {
            k.close();
            if( !(FileName.contains(fff)) )
            {
                throw new NotVaildAutosarFileException(FileName);
            }

            File f1 = new File(FileName);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);

            if (br.readLine() == null) {
                fr.close();
                br.close();
                throw new EmptyAutosarFileException(FileName);
            }
            fr.close();
            br.close();
            fr = new FileReader(f1);
            br = new BufferedReader(fr);

            String N1 = null;
            String N2 = null;
            String N3 = null;
            int x = 0,i=0;
            while ((line = br.readLine()) != null) {

                if( line.contains("UUID") ) 
                {
                    N1 = line.substring(line.indexOf('=')+2, line.indexOf('>')-1);
                    ++x;
                }
                else if( line.contains("SHORT-NAME") ) 
                {
                    N2 = line.substring(line.indexOf('>')+1, line.indexOf('/')-1);
                    ++x;
                }
                else if( line.contains("LONG-NAME") ) 
                {
                    N3 = line.substring(line.indexOf('>')+1, line.indexOf('/')-1);
                    ++x;
                }
                else if( x%3 == 0  &&  x != 0) 
                {
                    arr.add( new Hello(N1, N2, N3) );
                    x = 0;
                }
                
            }
            fr.close();
            br.close();
            Collections.sort(arr);


            x = 0;
            fr = new FileReader(f1);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {

                if( line.contains("UUID") ) 
                {
                    Name = line.substring(line.indexOf('=')+2, line.indexOf('>')-1);
                    line = line.replace(Name, arr.get(i).getUuid());
                    ++x;
                }
                else if( line.contains("SHORT-NAME") ) 
                {
                    Name = line.substring(line.indexOf('>')+1, line.indexOf('/')-1);
                    line = line.replace(Name, arr.get(i).getShortName());
                    ++x;
                }
                else if( line.contains("LONG-NAME") ) 
                {
                    Name = line.substring(line.indexOf('>')+1, line.indexOf('/')-1);
                    line = line.replace(Name, arr.get(i).getLongName());
                    ++x;
                }
                else if( x%3 == 0 &&  x != 0) 
                {
                    x = 0;
                    ++i;
                }
                lines.add(line);
                lines.add("\n");
            }
            fr.close();
            br.close();

            FileName =  FileName.substring( 0, FileName.indexOf(".") )  + m;

            File f = new File(FileName);
            FileWriter fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                 out.write(s);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}


class Hello implements Comparable<Hello> {
    private String Uuid;
    private String Short_name;
    private String Long_name;

    public Hello (String U,String S,String L) 
    {
        Uuid = U;
        Short_name = S;
        Long_name = L;
    }

    @Override
    public int compareTo(Hello a)
    {
        return this.Short_name.compareTo(a.Short_name);
    }

    public String getUuid()
    {
        return Uuid;
    }

    public String getShortName()
    {
        return Short_name;
    }

    public String getLongName()
    {
        return Long_name;
    }

}

class NotVaildAutosarFileException extends Exception {

    public NotVaildAutosarFileException(String n)
    {
        super("Invalid File Name " + n);
    }


}



class EmptyAutosarFileException extends Exception {

    public EmptyAutosarFileException(String n)
    {
        super("Empty Autosar File " + n);
    }


}