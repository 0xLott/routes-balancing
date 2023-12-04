import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        int NUM_CAMINHOES = 3;
        int TAM_CONJUNTO = 10;
        long TEMPO_LIMITE = 30000000000L;

        while (true) {
            long inicioTeste = System.nanoTime();

            // Quantidade crescente de rotas
            for (int QTD_ROTAS = 6; QTD_ROTAS < 11; QTD_ROTAS++) {
                System.out.println("\n--------- QTD ROTAS: " + QTD_ROTAS + " ---------");
                List<int[]> rotas = GeradorDeProblemas.geracaoDeRotas(QTD_ROTAS, TAM_CONJUNTO, 0.65);

                // Teste com 10 conjuntos para a QTD_ROTAS atual:
                for (int i = 0; i < TAM_CONJUNTO; i++) {
                    System.out.println("\nCONJUNTO: " + i);
                    int rotasPorCaminhao = (rotas.get(i).length / NUM_CAMINHOES) + (rotas.get(i).length % 2);
                    int[][] caminhoes = new int[NUM_CAMINHOES][rotasPorCaminhao];

                    System.out.println("ROTAS: " + Arrays.toString(rotas.get(i)));
                    Balancer.distribute(rotas.get(i), caminhoes);
                }

                long fimTeste = System.nanoTime();
                long tempoExecucao = fimTeste - inicioTeste;

                System.out.println("\nTempo de execução do teste: " + tempoExecucao / 1_000_000_000.0 + " s");
                System.out.println("----------------------------------");

                if (tempoExecucao >= TEMPO_LIMITE) {
                    System.out.println("Limite de tempo excedido.");
                    return;
                }
            }
        }
    }
}
