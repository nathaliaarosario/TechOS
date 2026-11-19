package br.com.bugnacio.techos.service;

import br.com.bugnacio.techos.exception.RegraDeNegocioException;
import br.com.bugnacio.techos.repository.ClienteRepository;
import br.com.bugnacio.techos.repository.EquipamentoRepository;

//@Service informa ao Spring que esta classe contém regras de negocio.
// o spring cria a gerencia automaticamente um objeto  desta classe
public class ClienteService {

    // Declara os repositorios que serão usados neta classe
    // final impede que essa referencia seja alterada depois de iniciada

    private final ClienteRepository clienteRepo;
    private final EquipamentoRepository equipRepo;

    // construtor utilizado pelo Spring para fornecer os repositorios necessarios
    // Esse processo é chamado injeção de dependencia

    public ClienteService(ClienteRepository clienteRepo, EquipamentoRepository equipRepo) {

        // Guarda o repositorio de clientes no atributo da classe
        this.clienteRepo = clienteRepo;

        // Guarda o repositorio de equipamento no atributo da classe
        this.equipRepo = equipRepo;
    }

    // Método responsável por excluir o cliente pelo seu ID
    public void excluir(Long id) {
        // Antes de excluir o cliente, verificar se exixtem equipamentos cadastrados
        // relacionados a ele
        if (equipRepo.existsByClienteId(id)) {

            // Se existir algum equipamento vinculado ao cliente
            // Lançarr uma exceção e impede que a exclusão seja realizada
            throw new RegraDeNegocioException("Este cliente tem equipamento");
        }

        // Se o cliente não possui equipamento vinculado
        // permite realizar a exclusão peloN ID informado
        clienteRepo.deleteById(id);
    }

}
