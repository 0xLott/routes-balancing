import java.util.List;

public class App {
    public static void main(String[] args) {
        int NUM_CAMINHOES = 6;

        List<int[]> rotas = GeradorDeProblemas.geracaoDeRotas(10, 1, 1.0);
        int[][] caminhoes = new int[NUM_CAMINHOES][rotas.get(0).length/NUM_CAMINHOES + rotas.get(0).length % 2 + 1];
        Balancer.distribute(rotas.get(0), caminhoes);

//        int[] rotas = {30, 40, 30, 20, 20, 30, 40, 20, 40};
//        int[][] caminhoes = new int[NUM_CAMINHOES][rotas.length/NUM_CAMINHOES + rotas.length % 2];
//        Balancer.distribute(rotas, caminhoes);

    }

}
