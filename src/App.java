import java.util.List;

public class App {
    public static void main(String[] args) {
        List<int[]> rotas = GeradorDeProblemas.geracaoDeRotas(10, 1, 1.0);

//        int[] rotas = {30, 40, 30, 20, 20, 30, 40, 20, 30, 40};
        int[][] caminhoes = new int[3][rotas.get(0).length/3 + rotas.get(0).length % 2];

        Balancer.distribute(rotas.get(0), caminhoes);

    }

}
