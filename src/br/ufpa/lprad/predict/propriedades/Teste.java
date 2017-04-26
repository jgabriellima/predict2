/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.lprad.predict.propriedades;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LPRAD
 */
public class Teste {

    public static Locale locale = new Locale("pt", "BR");
    public static NumberFormat nf = NumberFormat.getNumberInstance(locale);

    public static void main(String[] args) {
//        try {
////        System.out.println("" + Double.parseDouble("54856,289"));
////            System.out.println("" + nf.parse("54856,289").doubleValue());
//
//            double t = nf.parse("54856,289").doubleValue();
//
//            String aux = String.valueOf(t);
//
//            double f = 99154.643 - 93074;
//            System.out.println("f: "+f);
//            System.out.println(aux.replace(".", ","));
//
//        } catch (ParseException ex) {
//            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
//        }
System.out.println(""+System.getenv("ProgramFiles"));
    }
}
