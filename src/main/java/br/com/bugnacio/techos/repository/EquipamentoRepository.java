package br.com.bugnacio.techos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bugnacio.techos.model.Equipamento;

// Cria uma interface responsável pelo acesso aos dados dos equipamentos.
// extends: herda os métodos disponibilizados pelo JpaRepository.
// Equipamento: informa qual entidade será gerenciada.
// Long: informa o tipo de dado do ID da entidade.
// findAll(): busca todos os registros no banco de dados.
// findById(): busca um registro pelo seu ID.
// save(): salva um novo registro ou atualiza um registro existente.
// delete(): exclui um registro do banco de dados.
// count(): conta quantos registros existem no banco de dados.
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    // Busca equipamentos pela marca OU pelo modelo.
    // Containing: procura partes do texto.
    // IgnoreCase: ignora maiúsculas e minúsculas.
    // OrderBy: organiza por marca e depois por modelo.
    List<Equipamento> findByMarcaContainingIgnoreCaseOrModeloContainingIgnoreCaseOrderByMarcaAscModeloAsc(String marca, String modelo);

    // Verifica se já existe um equipamento com o número de série informado.
    boolean existsByNumeroSerie(String numeroSerie);

    // Verifica se outro equipamento utiliza esse número de série.
    // O ID permite desconsiderar o próprio equipamento durante uma edição.
    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);

     boolean existsByClienteId(Long clienteid);
}

