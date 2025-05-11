/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aula5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author T-GAMER
 */
public class RadixSort {
    private long qntdComparacoes;
    private long qntdTrocas;

    public RadixSort() {
        
    }

  public void radixSort(int[] arr) {
    qntdComparacoes = 0;
    qntdTrocas      = 0;
    int n = arr.length;

    int min = Arrays.stream(arr).min().orElse(0);
    if (min < 0) {
        for (int i = 0; i < n; i++) {
            arr[i] -= min;
        }
    }

    int max = Arrays.stream(arr).max().orElse(0);
    for (int exp = 1; max / exp > 0; exp *= 10) {
        countingSort(arr, exp);
    }

    if (min < 0) {
        for (int i = 0; i < n; i++) {
            arr[i] += min;
        }
    }
}

    private void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count  = new int[10];

        for (int i = 0; i < n; i++) {
            int idx = (arr[i]/exp) % 10;
            count[idx]++;
            qntdComparacoes++;
        }
        for (int i = 1; i < 10; i++) count[i] += count[i-1];
        for (int i = n-1; i >= 0; i--) {
            int idx = (arr[i]/exp) % 10;
            output[count[idx]-1] = arr[i];
            count[idx]--;
            qntdTrocas++;
        }
        System.arraycopy(output, 0, arr, 0, n);
    }

    public int[] lerNumerosDeArquivo(String caminho) throws IOException {
        List<Integer> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.replace("[", "").replace("]", "");
                if (linha.isBlank()) continue;
                for (String p : linha.split(",")) {
                    if (!p.isBlank()) lista.add(Integer.parseInt(p.trim()));
                }
            }
        }
        return lista.stream().mapToInt(i -> i).toArray();
    }

    public void salvarResultados(String caminho, int[] numeros, long tempoMs) throws IOException {
        long h  = tempoMs / 3_600_000;
        long m  = (tempoMs % 3_600_000) / 60_000;
        long s  = (tempoMs % 60_000)     / 1_000;
        long ms = tempoMs % 1_000;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write("Nome do Aluno: Felipe Pereira Arantes");
            bw.write("\nAlgoritmo Utilizado: Radix Sort");
            bw.write(String.format("\nTempo de Execução: %02dh:%02dm:%02ds:%03dms", h, m, s, ms));
            bw.write("\nQuantidade de Comparações: " + qntdComparacoes);
            bw.write("\nQuantidade de Movimentações: " + qntdTrocas);
            bw.write("\n\n[Dados Ordenados]\n");
            for (int num : numeros) bw.write(num + " ");
        }
    }
}
