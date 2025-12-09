package br.com.alura.screensound.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;

public interface ArtistaRepository extends JpaRepository<Artista, Long>{

	Optional<Artista> findByNomeContainingIgnoreCase(String nome);
	
	@Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:nome%")
	List<Musica> buscaMusicaPorArtista(String nome);

}
