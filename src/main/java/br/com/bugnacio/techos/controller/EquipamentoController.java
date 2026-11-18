
package br.com.bugnacio.techos.controller;

import java.util.List;
import org.springframework.data.domain.Sort;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import br.com.bugnacio.techos.model.TipoEquipamento;
import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.repository.ClienteRepository;
import br.com.bugnacio.techos.repository.EquipamentoRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller: avisa ao Spring que é uma classe controladora.
//O Spring cria um objeto dessa classe ao iniciar.
//Nos não escrevemos "new EquipamentoController()", o Spring faz isso sozinho.

@Controller

//Prefixo de URL de todos os metodos da classe:"/equipamentos".
//"Ex:@GetMapping("/novo") -> URL: "/euqipamentos/novo"
@RequestMapping("/equipamentos")
public class EquipamentoController {

    // --- ATRIBUTOS(as ferramentas do controller)
    // --repo é variavel do tipo EquipamentoRepository, que acessa o banco de dados.
    // o objeto real que fica nela é criado pelo Spring(ele gera a implementação sozinho).
    //private: só recebe esta classe acessa. Final: depois de criado não muda mais(não pode receber outro objeto).
    // Por enquanto o repo está vazio, quem precisa preencher é o contrutor logo abaixo:
    private final EquipamentoRepository repo;
    //Construtor: recebeo repo do Spring e guardaem uma variavel a cima
    private final ClienteRepository Clienterepo;


    public EquipamentoController(EquipamentoRepository repo, ClienteRepository Clienterepo) {
        this.repo = repo;
        this.Clienterepo = Clienterepo;
    }

    @GetMapping
    public String listar(
            @RequestParam(value = "busca", required = false) String busca,
            Model model) {

        List<Equipamento> equipamentos;

        if (busca != null && !busca.isBlank()) {

            equipamentos = repo.findByMarcaContainingIgnoreCaseOrModeloContainingIgnoreCaseOrderByMarcaAscModeloAsc(
                    busca.trim(), busca.trim());

            if (equipamentos.isEmpty()) {
                model.addAttribute("mensagemBusca", "Nenhum equipamento encontrado.");
            } else {
                model.addAttribute("mensagemBusca", "Pesquisa realizada! Equipamentos encontrados.");
            }

        } else {

            equipamentos = repo.findAll(
            Sort.by("marca").ascending().and(Sort.by("modelo").ascending()));
        }

        model.addAttribute("equipamentos", equipamentos);
        model.addAttribute("busca", busca);

        return "equipamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        Equipamento equipamento = new Equipamento();

        model.addAttribute("equipamento", equipamento);
        // Envia os clientes cadastrados.
        model.addAttribute("clientes", Clienterepo.findAll());
        // Envia os valores do Enum.
        model.addAttribute("tipos", TipoEquipamento.values());
        return "equipamentos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Equipamento equipamento, BindingResult result,
            Model model, RedirectAttributes redirect) {

        // Verifica se é um cadastro novo.
        boolean novoCadastro = equipamento.getId() == null;

        // Verifica se o número de série já está cadastrado.
        boolean jaExiste = novoCadastro? repo.existsByNumeroSerie(equipamento.getNumeroSerie()): repo.existsByNumeroSerieAndIdNot(equipamento.getNumeroSerie(), equipamento.getId());

        // Impede o cadastro de números de série repetidos.
        if (jaExiste) {result.rejectValue("numeroSerie", "duplicado","Número de série já cadastrado.");
        }

        // Se houver erros, retorna ao formulário.
        if (result.hasErrors()) {
            model.addAttribute("clientes", Clienterepo.findAll());
            model.addAttribute("tipos", TipoEquipamento.values());
            return "equipamentos/form";
        }

        // Recupera o cliente cadastrado no banco.
        Long clienteId = equipamento.getCliente().getId();
        Cliente cliente = Clienterepo.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        equipamento.setCliente(cliente);

        // Salva o equipamento.
        repo.save(equipamento);

        redirect.addFlashAttribute("mensagem", novoCadastro ? "Equipamento cadastrado com sucesso!": "Equipamento atualizado com sucesso!");

        return "redirect:/equipamentos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Optional<Equipamento> equipamento = repo.findById(id);

        if (equipamento.isEmpty()) {
            redirect.addFlashAttribute("erro", "Equipamento não encontrado.");
            return "redirect:/equipamentos";
        }

        model.addAttribute("equipamento", equipamento.get());
        model.addAttribute("tipos", TipoEquipamento.values());
        model.addAttribute("clientes", Clienterepo.findAll());

        return "equipamentos/form";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable("id") Long id,
            RedirectAttributes redirect) {

        repo.deleteById(id);

        redirect.addFlashAttribute(
                "mensagem", "Equipamento excluído com sucesso!");

        return "redirect:/equipamentos";
    }

}
