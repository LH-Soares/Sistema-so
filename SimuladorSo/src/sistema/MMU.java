package sistema;

import memoria.MenRam;
import memoria.MenVirtual;
import memoria.Pagina;

public class MMU {
	private MenRam MemoriaFisica;
	private MenVirtual MemoriaVirtual;
	private Lista LRU = new Lista();
	
	public MMU(MenVirtual MV_,MenRam Menram_) {
		this.MemoriaFisica = Menram_;
		this.MemoriaVirtual = MV_;
		
	}
	
	public synchronized  void  receberComando(String s, int id) {
		System.out.println("Processo: " + id);
		String[] t = s.split("-");
		
		if(t[1].contains("R")) {
			leitura( Integer.parseInt(t[0]));
		}else {
			escrita(Integer.parseInt(t[0]), Integer.parseInt(t[2]));
		}		
	}

	private synchronized void escrita(int indiceVirtual, int escrita) {
		Integer posicaoNaRam = null;
		System.out.println("escrevendo:" + escrita + " na posicao: " + indiceVirtual);
		
		if(MemoriaVirtual.getPagina(indiceVirtual).existe()) {	//CASO A PAGINA JA EXISTA, FAZENDO UMA ATUALIZACAO	//se a pagina existe
			System.out.println(" A PAGINA JA EXISTA, FAZENDO UMA ATUALIZACAO");
			if(MemoriaVirtual.getPagina(indiceVirtual).isPresente()) {		//se a pagina ja esta na RAM
				System.out.println("a variavel ja esta na RAM");
				posicaoNaRam = MemoriaVirtual.getPagina(indiceVirtual).getMolduraPagina();	//pega a posicao q o valor esta na ram
				MemoriaVirtual.getPagina(indiceVirtual).setModificada(true); 				
				MemoriaFisica.setValor(posicaoNaRam, escrita);
				LRU.adicionarRecente(indiceVirtual);		//ALGORITMO, AQUI FOI REFERENCIADO - IR PARA O FINAL DA LISTA
			}else { //CASO O VALOR NAO ESTEJA NA MEMORIA RAM
				System.out.println("O VALOR NAO ESTA NA MEMORIA RAM");
				int posicao_ram = MemoriaVirtual.getPagina(indiceVirtual).getMolduraPagina()  ;
				MemoriaFisica.setValor(posicao_ram, escrita);						//escrevendo valor novo
				LRU.adicionarRecente(indiceVirtual);
			}
		}else { //CASO A PAGINA AINDA NAO EXISTA, FAZENDO UMA NOVA INSERCAO
			System.out.println(" PAGINA AINDA NAO EXISTA, FAZENDO UMA NOVA INSERCAO");
			posicaoNaRam = MemoriaFisica.getIndiceLivre();
			
			if(posicaoNaRam != null) { 		// EXISTE ESPACO LIVRE NA RAM!!!
				MemoriaVirtual.getPagina(indiceVirtual).setMolduraPagina(posicaoNaRam);
				MemoriaVirtual.getPagina(indiceVirtual).setPresente(true);
				MemoriaFisica.setValor(posicaoNaRam, escrita);
				LRU.adicionarRecente(indiceVirtual);	
				
			} else { //CASO NAO EXISTA MEMORIA LIVRE PARA FAZER UMA INSERCAO
				System.out.println("NAO EXISTE MEMORIA LIVRE PARA FAZER UMA INSERCAO");
				posicaoNaRam = MemoriaFisica.getIndiceLivre();
				MemoriaVirtual.getPagina(indiceVirtual).setMolduraPagina(posicaoNaRam);
				MemoriaVirtual.getPagina(indiceVirtual).setPresente(true);
				MemoriaFisica.setValor(posicaoNaRam, escrita);
				
				LRU.adicionarRecente(indiceVirtual);	
				
			}
						
		}
		
		
		
		System.out.println("ESCRITA FEITA COM SUCESSO");
	
	}

	



	private void leitura(int indiceVirtual) {
		System.out.println("Leitura em :" + indiceVirtual );
		Pagina leitura = this.MemoriaVirtual.getPagina(indiceVirtual);
		
		
		if(leitura.getMolduraPagina() == null) {			//tentando ler pagina NULA
			System.out.println("LEITURA SENDO REALIZADA EM UMA PAGINA QUE NAO EXISTE ");
			return;
		}
		if(leitura != null) {
			if(leitura.isPresente()) {						//ela esta na ram
				System.out.println("Indece:" + indiceVirtual + " valor: " + this.MemoriaFisica.getValor(leitura.getMolduraPagina()));
				leitura.setReferenciada(true);
				LRU.adicionarRecente(indiceVirtual);
			}else { 									
				this.leitura(indiceVirtual);
				
				
			}
		}
		
	}

}

