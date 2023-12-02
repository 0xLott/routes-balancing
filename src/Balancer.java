import java.util.*;

public class Balancer {

    public static void distribute(int[] routes, int[][] trucks) {
        int target = (Arrays.stream(routes).sum()) / trucks.length;
        System.out.println("TARGET: " + target);

//        for (int[] truck : trucks) {
//            balance(routes, target);
//        }

        balance(routes, target);
    }

    public static int[] balance(int[] routes, int target) {
        System.out.println("ROTAS:" + Arrays.toString(routes));
        int[][] splitted = split(routes);
        int[] remaining;

        // Base: quando a metade da direita tiver apenas um elemento
        if (splitted[1].length == 1) {

            int[] esq = splitted[0];
            int[] dir = splitted[1];

            System.out.println("\t esq: " + Arrays.toString(esq));
            System.out.println("\t dir: " + Arrays.toString(dir));

            int[] selected = findClosestCombination(esq, dir, target);
            System.out.println("Selected: " + Arrays.toString(selected));

            remaining = removeRoutes(esq, selected);
        } else {
            int[] remainingLeft = balance(splitted[0], target);
            int[] remainingRight = balance(splitted[1], target);

            // Combine remaining routes from left and right subtrees
            remaining = mergeArrays(remainingLeft, remainingRight);
            System.out.println("R"+ Arrays.toString(remaining));
        }
        return remaining;
    }

    public static int[][] split(int[] array) {
        int mid = array.length / 2;

        /*
         * Se o array inicial tiver um número ímpar de posições, `arrayEsq` terá tamanho `mid + 1`. Caso contário, terá
         * tamanho `mid + 0`. Portanto, o elemento extra de um array ímpar ficará sempre em `arrayEsq`.
         */
        int[] arrayEsq = new int[mid + array.length % 2];
        int[] arrayDir = new int[mid];

        System.arraycopy(array, 0, arrayEsq, 0, arrayEsq.length);
        System.arraycopy(array, arrayEsq.length, arrayDir, 0, arrayDir.length);

        return new int[][]{arrayEsq, arrayDir};
    }

    public static int[] findClosestCombination(int[] esq, int[] dir, int target) {
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
     * @param originalArray     Conjunto original de rotas
     * @param selectedRoutes    Rotas selecionadas
     * @return Array com os elementos originais que não estão presentes no array de rotas selecionadas, ou seja, as
     * rotas que não foram selecionadas.
     */
    public static int[] removeRoutes(int[] originalArray, int[] selectedRoutes) {
        List<Integer> remainingRoutes = new ArrayList<>();

        // Convert the original array to a list to facilitate element removal
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

        // Convert the list back to an array
        int[] result = new int[remainingRoutes.size()];
        for (int i = 0; i < remainingRoutes.size(); i++) {
            result[i] = remainingRoutes.get(i);
        }

        return result;
    }

    public static int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            merged[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; i++) {
            merged[arr1.length + i] = arr2[i];
        }

        return merged;
    }
}
