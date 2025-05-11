package com.mycompany.aula5;

import java.io.*;
import java.util.*;

/**
 *
 * @author T-GAMER
 */
public class MergeSort {
    private long qntdComparacoes;
    private long qntdTrocas;

    public MergeSort() {
        
    }

    public void mergeSort(int[] arr) {
        qntdComparacoes = 0;
        qntdTrocas      = 0;
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) >>> 1;
            mergeSort(arr, inicio, meio);
            mergeSort(arr, meio + 1, fim);
            merge(arr, inicio, meio, fim);
        }
    }

    private void merge(int[] arr, int inicio, int meio, int fim) {
        int n1 = meio - inicio + 1;
        int n2 = fim - meio;
        int[] left  = new int[n1];
        int[] right = new int[n2];
        System.arraycopy(arr, inicio, left,  0, n1);
        System.arraycopy(arr, meio+1, right, 0, n2);

        int i = 0, j = 0, k = inicio;
        while (i < n1 && j < n2) {
            qntdComparacoes++;
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
            qntdTrocas++;
        }
        while (i < n1) {
            arr[k++] = left[i++];
            qntdTrocas++;
        }
        while (j < n2) {
            arr[k++] = right[j++];
            qntdTrocas++;
        }
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
            bw.write("\nAlgoritmo Utilizado: Merge Sort");
            bw.write(String.format("\nTempo de Execução: %02dh:%02dm:%02ds:%03dms", h, m, s, ms));
            bw.write("\nQuantidade de Comparações: " + qntdComparacoes);
            bw.write("\nQuantidade de Movimentações: " + qntdTrocas);
            bw.write("\n\n[Dados Ordenados]\n");
            for (int num : numeros) bw.write(num + " ");
        }
    }
}
