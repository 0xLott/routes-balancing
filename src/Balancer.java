import java.util.*;

public class Balancer {
    public static void distribute(int[] routes, int[][] trucks) {
        int target = (Arrays.stream(routes).sum()) / trucks.length;
//        System.out.println("TARGET: " + target);

        int[] remainingRoutes = balance(routes, target, trucks);

        // Se o balanceamento retornar rotas ainda não distribuídas, as redistribui
        for (int i = 0; i < remainingRoutes.length; i++) {
            int[] temp = new int[]{remainingRoutes[i]};
            System.out.println("R: " + Arrays.toString(temp) + "INDEX: " + findNextTruck(trucks));
            assignToTruck(temp, trucks, findNextTruck(trucks));
        }

        for (int index = 0; index < trucks.length; index++) {
            System.out.println("Caminhão " + index + ": " + Arrays.toString(trucks[index])
                    + " - Total: " + Arrays.stream(trucks[index]).sum() + "km");
        }
    }

    /**
     * RECUSIVIDADE: "Quebra" o array em duas metades — a da direita e a da esquerda BASE: Quando a metade da direita
     * tiver apenas um único elemento
     *
     * Realiza operações para encontrar a combinação, entre os elementos de ambas à metade, mais próxima ao valor alvo,
     * atribuindo-a ao caminhão disponível mais adequado e removendo suas respectivas rotas do array original. Em
     * seguida, combina os restantes das rotas e prossegue com a recursão até que o processamento seja concluído.
     *
     * @param routes Array de rotas a serem processadas, geradas a partir da classe `GeradorDeProblemas`
     * @param target Valor alvo (ou "ideal") a ser aproximado na combinação de rotas
     * @param trucks A matriz representando os caminhões e suas respectivas rotas
     * @return Array contendo as rotas restantes após a atribuição aos caminhões
     */
    private static int[] balance(int[] routes, int target, int[][] trucks) {
//        System.out.println("ROTAS: " + Arrays.toString(routes));
        int[][] splitted = split(routes);
        int[] remaining;

        if (splitted[1].length == 1) {

            int[] esq = splitted[0];
            int[] dir = splitted[1];

//            System.out.println("\t esq: " + Arrays.toString(esq));
//            System.out.println("\t dir: " + Arrays.toString(dir));

            int[] selected = findClosestCombination(esq, dir, target);
//            System.out.println("Selected: " + Arrays.toString(selected));

            int selectedTruckIndex = findNextTruck(trucks);
            assignToTruck(selected, trucks, selectedTruckIndex);

            remaining = removeRoutes(esq, selected);

        } else {
            int[] remainingLeft = balance(splitted[0], target, trucks);
            int[] remainingRight = balance(splitted[1], target, trucks);

            remaining = mergeArrays(remainingLeft, remainingRight);
        }

        return remaining;
    }

    /**
     * Divide um array de inteiros em duas partes, dividindo-o ao meio. Se o array inicial tiver um número ímpar de
     * posições, `arrayEsq` terá tamanho `mid + 1`. Caso contário, terá tamanho `mid + 0`. Portanto, o elemento extra de
     * um array ímpar ficará sempre em `arrayEsq`.
     *
     * @return Uma matriz 2D contendo duas partes do array original dividido: a metade da esquerda e a metade da direita
     */
    private static int[][] split(int[] array) {
        int mid = array.length / 2;

        int[] arrayEsq = new int[mid];
        int[] arrayDir = new int[mid];

        System.arraycopy(array, 0, arrayEsq, 0, mid);
        System.arraycopy(array, mid, arrayDir, 0, mid);

        // For odd lengths, add the remaining element to arrayEsq
        if (array.length % 2 != 0) {
            arrayEsq = Arrays.copyOf(arrayEsq, arrayEsq.length + 1);
            arrayEsq[arrayEsq.length - 1] = array[array.length - 1];
        }

        return new int[][]{arrayEsq, arrayDir};
    }

    /**
     * Encontra todas as possíveis combinações entre os elementos dos arrays recebidos, calculando a soma de cada
     * combinação. Em seguida, verifica qual combinação tem a menor diferença absoluta em relação ao valor alvo. Essa
     * combinação é considerada a mais próxima.
     *
     * @param esq    Lista de inteiros representando a metade esquerda
     * @param dir    Lista de inteiros representando a metade direita
     * @param target Valor alvo (ou "ideal") a ser aproximado na combinação de rotas
     * @return Array contendo os dois números cuja soma mais se aproxima do valor alvo
     */
    private static int[] findClosestCombination(int[] esq, int[] dir, int target) {
        List<Integer> closestCombination = new ArrayList<>();
        int closestSum = Integer.MAX_VALUE;

        for (int i = 0; i < esq.length; i++) {
            for (int j = 0; j < dir.length; j++) {
                int sum = esq[i] + dir[j];
                int absDifference = Math.abs(sum - target);

                if (absDifference < Math.abs(closestSum - target)) {
                    closestSum = sum;
                    closestCombination.clear();
                    closestCombination.add(esq[i]);
                    closestCombination.add(dir[j]);
                }
            }
        }

        int[] result = new int[closestCombination.size()];
        for (int i = 0; i < closestCombination.size(); i++) {
            result[i] = closestCombination.get(i);
        }

        return result;
    }

    /**
     * @param originalArray  Conjunto original de rotas
     * @param selectedRoutes Rotas selecionadas
     * @return Array com os elementos originais que não estão presentes no array de rotas selecionadas, ou seja, as
     * rotas que não foram selecionadas.
     */
    private static int[] removeRoutes(int[] originalArray, int[] selectedRoutes) {
        List<Integer> remainingRoutes = new ArrayList<>();

        // Converte o array original em uma lista para facilitar remoção de elemento
        List<Integer> originalArrayList = new ArrayList<>();
        for (int value : originalArray) {
            originalArrayList.add(value);
        }

        for (int route : originalArrayList) {
            boolean isSelected = false;
            for (int selectedRoute : selectedRoutes) {
                if (route == selectedRoute) {
                    isSelected = true;
                    break;
                }
            }
            if (!isSelected) {
                remainingRoutes.add(route);
            }
        }

        // Converte a lista de volta a um array
        int[] result = new int[remainingRoutes.size()];
        for (int i = 0; i < remainingRoutes.size(); i++) {
            result[i] = remainingRoutes.get(i);
        }

        return result;
    }

    private static int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            merged[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; i++) {
            merged[arr1.length + i] = arr2[i];
        }

        return merged;
    }

    private static int findNextTruck(int[][] trucks) {
        int minSum = Integer.MAX_VALUE;
        int selectedTruckIndex = -1;

        for (int i = 0; i < trucks.length; i++) {
            int sum = 0;
            for (int j = 0; j < trucks[i].length; j++) {
                sum += trucks[i][j];
            }

            if (sum < minSum) {
                minSum = sum;
                selectedTruckIndex = i;
            }
        }

        return selectedTruckIndex;
    }

    private static void assignToTruck(int[] selectedRoutes, int[][] trucks, int selectedTruckIndex) {
        int[] truckRoutes = trucks[selectedTruckIndex];

        // Copia as rotas diferentes de zero do array de rotas do caminhão para uma Lista
        List<Integer> rotasNaoZero = new ArrayList<>();
        for (int rota : truckRoutes) {
            if (rota != 0) {
                rotasNaoZero.add(rota);
            }
        }

        // Adiciona as rotas selecionadas à lista
        for (int rotaSelecionada : selectedRoutes) {
            rotasNaoZero.add(rotaSelecionada);
        }

        // Garante que o array de rotas do caminhão é atualizado com as rotas da Lista
        int indice = 0;
        for (int rota : rotasNaoZero) {
            if (indice < truckRoutes.length) { // Verifica os limites do array antes da atribuição
                truckRoutes[indice++] = rota;
            } else {
                break; // Sai do loop se o comprimento do array for excedido
            }
        }

        // Preenche as posições restantes, se houver, com 0
        for (; indice < truckRoutes.length; indice++) {
            truckRoutes[indice] = 0;
        }
    }
}
