/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voracious;
import java.util.Arrays;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

/**
 *
 * @author ivan
 */
public class Voracious {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String c;
        String v;
        String c_winner;
        double cost_votant;
        double voting_intention;
        double person_percent = 0.7;


        // int num = 0;

        System.out.println("Ingrese la cantidad de candidatos\n ");
        c = reader.nextLine();

        System.out.println("Ingrese la cantidad de votantes\n ");
        v = reader.nextLine();

        System.out.println("Ingrese el candidato corrupto \n ");
        c_winner = reader.nextLine();
        // armar arreglo antes de escoger ganador

        // String[] array = new String[] {"John", "Mary", "Bob"};

        try{
            Integer.parseInt(c);
            Integer.parseInt(v);
            Integer.parseInt(c_winner);
        } catch (NumberFormatException e) {
           System.out.println("No es un entero");
           return;
        }


        double[] result = new double[(int) Math.ceil(Integer.parseInt(v) * person_percent)];
        int new_c = Integer.parseInt(c);
        double total_result = 0;
        double[][] multi = new double[new_c + 3][Integer.parseInt(v)];
        int[] vote_list = new int[Integer.parseInt(v)];
        double vote;

        for (int i = 0; i < new_c; i++) {
            double voting_intention_total = 0;

            for (int j = 0; j < multi[0].length; j++) {
                vote_list[j] = j;
                System.out.println("Ingrese la intención de voto del votante " + j + " Por el candidato " + i + " \n ");

                try{
                    vote = Double.parseDouble(reader.nextLine());
                } catch (NumberFormatException e) {
                   System.out.println("No es un número");
                   return;
                }


                if (i <= 0) {
                    multi[new_c][j] = vote;
                    multi[i][j] = vote;
                } else {
                    if ((multi[new_c][j] + vote) <= 1) {
                        multi[new_c][j] = multi[new_c][j] + vote;
                        multi[i][j] = vote;
                    } else {
                       System.out.println("La sumatoria no puede exceder 1 ");
                       return;
                    }
                }

            }
        }


        for (int j = 0; j < multi[0].length; j++) {
            System.out.println("Ingrese cantidad disponible para sobornar el votante\n ");
            try{
                cost_votant = Double.parseDouble(reader.nextLine());
            } catch (NumberFormatException e) {
               System.out.println("No es un número");
               return;
            }
            multi[new_c + 1][j] = cost_votant;
            multi[new_c + 2][j] = cost_votant * (1 - multi[Integer.parseInt(c_winner)][j]);
        }

        System.out.print("\n Votantes         " + Arrays.toString(vote_list));
        for (int i = 0; i < multi.length; i++) {
            if (i < new_c) {
                if (Integer.parseInt(c_winner) == i) {
                    System.out.print("\n*Candidato " + i + ":         ");
                }else {
                    System.out.print("\n Candidato " + i + ":         ");
                }
            } else if (new_c == i) {
                System.out.print("\n Sumatoria:           ");
            } else if (new_c + 1 == i) {
                System.out.print("\n Costo:               ");
            } else {
                System.out.print("\n Costo x Prob:        ");
            }
            for (int j = 0; j < multi[0].length; j++) {
                System.out.print(round(multi[i][j], 2) + " | ");
            }
        }
        for (int j = 0; j < ((int) Math.ceil(Integer.parseInt(v) * person_percent)); j++) {
            result[j] = round(multi[new_c + 2][j], 1);
            total_result = total_result + round(multi[new_c + 2][j], 1);
        }

        Arrays.sort(result);

        // for (int j = 0; j < multi[new_c + 2].length; j++) {
        //     System.out.println("m******************* ");
        //     System.out.print(multi[new_c + 2][j]);
        // }

        for (int j = 0; j < result.length; j++) {
            System.out.print("\n" + " | " +result[j] + " | ");
        }

        System.out.print("\n Sumatoria total" + round(total_result, 1) + "\n");

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
