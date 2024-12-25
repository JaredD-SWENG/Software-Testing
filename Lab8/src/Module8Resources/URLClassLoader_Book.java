package Module8Resources;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.JFileChooser;

/**
 *
 * @author Owner
 */
public class URLClassLoader_Book {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        File f = null;
        JFileChooser jfc = new JFileChooser("c:/Temp/Book/build/classes/p1"); 
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
        }
        String fileName = f.getName().split("\\.")[0]; // removing the ".class" suffix
        String parentFileName = f.getParentFile().getName();
        URL url = f.getParentFile().getParentFile().toURI().toURL();
        URL[] urla = {url};
        URLClassLoader ucl = new URLClassLoader(urla);
        Class c = Class.forName(parentFileName + "." + fileName, true, ucl);
        System.out.println("First time: " + c.newInstance());

        c = Class.forName(fileName, true, ucl);
        System.out.println("Second time: " + c.newInstance());
    }

}
