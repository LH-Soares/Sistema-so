package sistema;

import memoria.MenRam;
import memoria.MenVirtual;

public class Status {
	private MenRam MenFisica;
	private MenVirtual MV;

	private int contador = 0;
	
	public Status(MenRam MenFisica_,MenVirtual MV_ ) {
		this.MenFisica = MenFisica_;
		this.MV = MV_;
	}
	
	public  void status_fim() {
		this.contador++;
		if(contador == Main.QUANTIDADE_THREADS) {
			System.out.println("Status Final:");
			MV.mostrarTudoMenVirutal();
			MenFisica.mostrarTudoRam();
		}
	}
}
