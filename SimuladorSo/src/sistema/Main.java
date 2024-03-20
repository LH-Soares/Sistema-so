package sistema;

import memoria.MenRam;
import memoria.MenVirtual;

public class Main {
	final static int QUANTIDADE_THREADS = 3;
	public static void main(String[] args) {
		
		
		final int TAMANHO_VIRTUAL = 16;
		
	
		MenRam MenFisica = new MenRam(TAMANHO_VIRTUAL/2);
		MenVirtual MV = new MenVirtual(TAMANHO_VIRTUAL);
		Status status = new Status(MenFisica, MV);
		MMU mmu = new MMU(MV,MenFisica);
	
		
		try {
			for(int i=1; i <= QUANTIDADE_THREADS; i++) {
				String SUA_ENTRADA = new FabricaDeEntradas(TAMANHO_VIRTUAL).getNewEntrada();	
				String comandos [] = SUA_ENTRADA.split(",");
				new Processo(i, mmu, comandos, status).start();
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}		
	}
}
