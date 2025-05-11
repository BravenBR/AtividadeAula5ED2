/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.aula5;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author T-GAMER
 */
public class Aula5 {

    public static void main(String[] args) {
        String entrada = "C:\\Users\\T-GAMER\\Downloads\\ordenaçoes\\dados500_mil.txt";

        executarCaso(new RadixSort(),entrada,"C:\\Users\\T-GAMER\\Downloads\\ordenaçoes\\Radix_medio.txt",TipoCaso.MEDIO);

        executarCaso(new RadixSort(),entrada,"C:\\Users\\T-GAMER\\Downloads\\ordenaçoes\\Radix_melhor.txt",TipoCaso.MELHOR);

        executarCaso(new RadixSort(),entrada,"C:\\Users\\T-GAMER\\Downloads\\ordenaçoes\\Radix_pior.txt",TipoCaso.PIOR);
    }

    private enum TipoCaso { MEDIO, MELHOR, PIOR }

    private static void executarCaso(RadixSort sorter,String arquivoEntrada,String arquivoSaida, TipoCaso caso) {
        try {
            int[] base = sorter.lerNumerosDeArquivo(arquivoEntrada);

            int[] dados;
            switch (caso) {
                case MELHOR:
                    dados = base.clone();
                    Arrays.sort(dados);
                    break;
                case PIOR:
                    dados = base.clone();
                    Arrays.sort(dados);
                    inverter(dados);
                    break;
                default:
                    dados = base.clone();
                    break;
            }

            long inicio = System.currentTimeMillis();
            sorter.radixSort(dados);
            long fim = System.currentTimeMillis();

            sorter.salvarResultados(arquivoSaida, dados, fim - inicio);
            System.out.printf("%s gerado em %s%n", caso, arquivoSaida);

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void inverter(int[] v) {
        for (int i = 0, j = v.length - 1; i < j; i++, j--) {
            int tmp = v[i];
            v[i] = v[j];
            v[j] = tmp;
        }
    }
}
