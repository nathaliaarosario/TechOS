
package br.com.bugnacio.techos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bugnacio.techos.model.Cliente;

// Repository: camada responsável pelo acesso ao banco de dados.
// Permite cadastrar, consultar, atualizar e excluir clientes.
// Ela utiliza a entidade Cliente para realizar essas operações.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Verifica se já existe um cliente cadastrado com o telefone informado.
    // Retorna true se encontrar e false se não encontrar.
    boolean existsByTelefone(String telefone);

    // Verifica se existe outro cliente utilizando o telefone informado.
    // O ID permite desconsiderar o próprio cliente durante uma edição.
    boolean existsByTelefoneAndIdNot(String telefone, Long id);

    // Busca clientes que contenham o nome informado.
    // Containing: procura parte do nome.
    // IgnoreCase: ignora diferenças entre letras maiúsculas e minúsculas.
    // List<Cliente>: retorna uma lista de clientes encontrados.
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
