package sistema;

import memoria.MenRam;
import memoria.MenVirtual;
import memoria.MenHD;

public class Main {
    final static int QUANTIDADE_THREADS = 3;
    final static int TAMANHO_VIRTUAL = 16;
    final static int CLOCK_SPEED = 1000; // Velocidade do clock em milissegundos (1 segundo)

    public static void main(String[] args) {
        MenRam MenFisica = new MenRam(TAMANHO_VIRTUAL / 2);
        MenVirtual MV = new MenVirtual(TAMANHO_VIRTUAL);
        MenHD HD = new MenHD();
        Status status = new Status(MenFisica, MV, HD);
        MMU mmu = new MMU(MV, MenFisica, HD);

        try {
            for (int clock = 1; clock <= QUANTIDADE_THREADS; clock++) {
                System.out.println("Clock: " + clock);

                // Simular execução de processos
                for (int i = 1; i <= QUANTIDADE_THREADS; i++) {
                    String SUA_ENTRADA = new FabricaDeEntradas(TAMANHO_VIRTUAL).getNewEntrada();
                    String comandos[] = SUA_ENTRADA.split(",");
                    Processo processo = new Processo(i, mmu, comandos, status);
                    processo.start();
                    Thread.sleep(1000); // Espera 100 milissegundos entre os processos
                }

                // Atualizar status no final de cada ciclo de clock
                status.status_fim();

                // Esperar um tempo entre os ciclos (clock speed)
                Thread.sleep(CLOCK_SPEED);
            }
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        }
    }
}