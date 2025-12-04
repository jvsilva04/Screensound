package br.com.alura.screensound.principal;

import java.util.Scanner;

public class Principal {
	
	private Scanner leitura = new Scanner(System.in);
	
	public void exibeMenu() {
		var opcao = -1;
        while(opcao != 0) {
            var menu = """
            		------ ScreenSound Music ------
                    1 - Cadastrar Artistas
                    2 - Cadastrar Musicas
                    3 - Listar Músicas
                    4 - Buscar Músicas por Artistas
                    5 - Pesquisar dados sobre um Artista
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            
            switch(opcao) {
            	case 1:
            		cadastrarArtista();
            		break;
            	case 2:
            		cadastrarMusica();
            		break;
            	case 3:
            		listarMusica();
            		break;
            	case 4:
            		buscarMusicasPorArtistas();
            		break;
            	case 5:
            		pesquisarDadosDoArtista();
            		break;
            	case 0:
            		System.out.println("Finalizando Aplicação!!!");
            		break;
        		default:
        			System.out.println("Opção Inválida!");
        			break;
            		
            }
        }
	}

	private void pesquisarDadosDoArtista() {
		// TODO Auto-generated method stub
		
	}

	private void buscarMusicasPorArtistas() {
		// TODO Auto-generated method stub
		
	}

	private void listarMusica() {
		// TODO Auto-generated method stub
		
	}

	private void cadastrarMusica() {
		// TODO Auto-generated method stub
		
	}

	private void cadastrarArtista() {
		// TODO Auto-generated method stub
		
	}

}
