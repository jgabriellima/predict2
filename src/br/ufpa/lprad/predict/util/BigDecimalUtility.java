package br.ufpa.lprad.predict.util;

import java.math.BigDecimal;
import java.util.Scanner;
import org.springframework.util.StringUtils;

/**
 *
 * @author leonardo
 */
public class BigDecimalUtility {

    public static String getFormattedCurrency(BigDecimal toFormat) {
        String stringToFormat = toFormat.toString();
//        System.out.println("StringToFormat: " + stringToFormat);
        stringToFormat = stringToFormat.replace(".", ",");
//        System.out.println("StringToFormat sem ponto: " + stringToFormat);
        Scanner scanner = new Scanner(stringToFormat);
        scanner.useDelimiter(",");
        String string = scanner.next();
        StringBuilder part1 = new StringBuilder(string);
//        System.out.println("part1: " + part1);
        String part2 = "00";
//        System.out.println("part2 padrao: " + part2);
        if (scanner.hasNext()) {
//            System.out.println("entrou no if de part2 ");
            part2 = scanner.next();
//            System.out.println("part2 modificado: " + part2);
        }
        if (part1.length() > 3) {
            part1.insert(part1.length() - 3, ".");
        }
        return part1.append(",").append(part2).toString();
    //        Scanner scanner = new Scanner(stringToFormat);
    //        scanner.useDelimiter(".");
    //        StringBuilder firstPart = new StringBuilder(scanner.next());
    //        StringBuilder secondPart = new StringBuilder(scanner.next());
    //        if (firstPart.length() > 3) {
    //            firstPart.insert(firstPart.length() - 3, ".");
    //        }
    //        return firstPart.append(secondPart).toString();

    }

    public static BigDecimal setFormattedCurrency(String toFormat) {
        if (toFormat.isEmpty()) {
            toFormat = "0,00";
        }
//        System.out.println("toFormat não modificado: " + toFormat);
        String formattedString = StringUtils.delete(toFormat, ".");
//        System.out.println("toFormat modificado sem ponto: " + formattedString);
        formattedString = StringUtils.replace(formattedString, ",", ".");
//        System.out.println("toFormat modificado completamente: " + formattedString);
        return new BigDecimal(formattedString);
    }
//    public static String getFormatMoney(String stringToFormat) {
//        System.out.println("getFormatMoney StringToFormat: "+ stringToFormat);
//        StringBuilder formattedString = new StringBuilder(stringToFormat.replace(".", ","));
//        Scanner scanner = new Scanner(formattedString.toString());
//        scanner.useDelimiter(",");
//        StringBuilder firstPart = new StringBuilder(scanner.next());
//        StringBuilder secondPart = new StringBuilder(",");
//        secondPart.append(scanner.next());
//        if (firstPart.length() > 3) {
//            firstPart.insert(firstPart.length() - 3, ".");
//        }
//        String response = firstPart.append(secondPart).toString();
//        System.out.println("getFormatMoney formattedString: "+ response);
//        return response;
//    }
//
//    public static String setFormatMoney(String stringToFormat) {
//        StringBuilder formattedSB = new StringBuilder(stringToFormat.replace(",", "."));
//        if (formattedSB.length() > 6) {
//            formattedSB.deleteCharAt(formattedSB.length() - 7);
//        }
//        String formattedString = formattedSB.toString();
//        return formattedString;
//    }
}
