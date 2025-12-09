package br.com.alura.screensound.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;
import br.com.alura.screensound.model.TipoArtista;
import br.com.alura.screensound.repository.ArtistaRepository;
import br.com.alura.screensound.service.ConsultaGemini;

public class Principal {
	
	private Scanner leitura = new Scanner(System.in);
	private final ArtistaRepository repositorio;
	
	public Principal(ArtistaRepository repositorio) {
		this.repositorio = repositorio;
	}

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
		System.out.println("Sobre qual artista você deseja saber os dados?");
		var nome = leitura.nextLine();
		ConsultaGemini consulta = new ConsultaGemini();
		var resposta = consulta.obterInformacao(nome);
		System.out.println(resposta.trim());
		
	}

	private void buscarMusicasPorArtistas() {
		System.out.println("De qual artista deseja buscar as musicas?");
		var nome = leitura.nextLine();
		List<Musica> musicas = repositorio.buscaMusicaPorArtista(nome);
		musicas.forEach(System.out::println);
		
	}

	private void listarMusica() {
		List<Artista> artistas = repositorio.findAll();
		artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
		
	}

	private void cadastrarMusica() {
		System.out.println("Cadastrar musica de qual artista?");
		var nome = leitura.nextLine();
		Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
		
		if(artista.isPresent()) {
			System.out.println("Qual o nome da musica?");
			var nomeMusica = leitura.nextLine();
			Musica musica = new Musica(nomeMusica);
			artista.get().getMusicas().add(musica);
			musica.setArtista(artista.get());
			repositorio.save(artista.get());
		}else {
			System.out.println("Artista não encontrado");
		}
	}

	private void cadastrarArtista() {
		var cadastrarNovo = "S";
		
		while(cadastrarNovo.equalsIgnoreCase("s")) {
			System.out.println("Informe o nome do artista:");
			var nome = leitura.nextLine();
			System.out.println("Informe o tipo do artista: (solo, dupla ou banda)");
			var tipo = leitura.nextLine();
			TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
			Artista artista = new Artista(nome,tipoArtista);
			repositorio.save(artista);
			System.out.println("Cadastrar novo artista? (S/N)");
			cadastrarNovo = leitura.nextLine();
		}
		
	}

}
