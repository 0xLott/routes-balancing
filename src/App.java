import java.util.List;

public class App {
    public static void main(String[] args) {
        int NUM_CAMINHOES = 3;
        int TAM_CONJUNTO = 10;

        for (int QTD_ROTAS = 6; QTD_ROTAS < 100; QTD_ROTAS++) {
            System.out.println("\n----- QTD ROTAS: " + QTD_ROTAS + "-----");
            List<int[]> rotas = GeradorDeProblemas.geracaoDeRotas(QTD_ROTAS, TAM_CONJUNTO, 0.75);

            for (int i = 0; i < TAM_CONJUNTO; i++) {
                int rotasPorCaminhao = (rotas.get(i).length/NUM_CAMINHOES) + (rotas.get(i).length % 2);
                int[][] caminhoes = new int[NUM_CAMINHOES][rotasPorCaminhao];

                Balancer.distribute(rotas.get(i), caminhoes);
            }
        }
    }
}
