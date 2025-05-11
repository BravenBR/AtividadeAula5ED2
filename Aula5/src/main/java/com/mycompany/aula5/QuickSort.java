package com.mycompany.aula5;

import java.io.*;
import java.util.*;

/**
 *
 * @author T-GAMER
 */
public class QuickSort {

    private long qntdComparacoes;
    private long qntdTrocas;

    public QuickSort() { }

    public void quickSort(int[] arr) {
        qntdComparacoes = 0;
        qntdTrocas      = 0;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int p = partition(arr, low, high);
            if (p - low < high - p) {
                quickSort(arr, low, p - 1);
                low = p + 1;
            } else {
                quickSort(arr, p + 1, high);
                high = p - 1;
            }
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            qntdComparacoes++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int tmp = arr[i];
        arr[i]  = arr[j];
        arr[j]  = tmp;
        qntdTrocas++;
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

    public void salvarResultados(String caminho,
        int[] numeros,
        long tempoMs) throws IOException {

        long h  = tempoMs / 3_600_000;
        long m  = (tempoMs % 3_600_000) / 60_000;
        long s  = (tempoMs % 60_000)     / 1_000;
        long ms = tempoMs % 1_000;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write("Nome do Aluno: Felipe Pereira Arantes");
            bw.write("\nAlgoritmo Utilizado: Quick Sort");
            bw.write(String.format("\nTempo de Execução: %02dh:%02dm:%02ds:%03dms",
                                   h, m, s, ms));
            bw.write("\nQuantidade de Comparações: " + qntdComparacoes);
            bw.write("\nQuantidade de Movimentações: " + qntdTrocas);
            bw.write("\n\n[Dados Ordenados]\n");
            for (int num : numeros) bw.write(num + " ");
        }
    }
}
