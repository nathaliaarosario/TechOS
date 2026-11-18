package br.com.bugnacio.techos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


// @Entity: informa ao JPA que esta classe representa uma tabela no banco de dados.
@Entity
public class Equipamento {

    // @Id: identifica a chave primária da tabela.
    // @GeneratedValue: permite que o banco gere o ID automaticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Enumerated: define como o Enum será armazenado no banco.
    // EnumType.STRING: salva o nome do valor, por exemplo, NOTEBOOK.
    // @NotNull: impede que o campo fique sem uma opção selecionada.
    // message: mensagem exibida quando a validação não é atendida.
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Selecione o tipo de equipamento.")
    private TipoEquipamento tipo;

    // @NotBlank: impede que a marca seja nula, vazia ou contenha apenas espaços.
    // message: informa ao usuário o que precisa preencher.
    @NotBlank(message = "Informe a marca.")
    private String marca;

    // @NotBlank: impede que o modelo seja nulo, vazio ou contenha apenas espaços.
    @NotBlank(message = "Informe o modelo.")
    private String modelo;

    // @NotBlank: impede que o número de série fique vazio.
    @NotBlank(message = "Informe o número de série.")
    private String numeroSerie;

    // @ManyToOne: indica que vários equipamentos podem pertencer a um mesmo cliente.
    // @JoinColumn: define a coluna que fará a ligação com a tabela Cliente.
    // name = "cliente_id": nome da chave estrangeira na tabela Equipamento.
    // @NotNull: exige que um cliente seja selecionado.
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "Selecione um cliente.")
    private Cliente cliente;

    // Construtor vazio: permite criar um objeto Equipamento sem informar os atributos.
    public Equipamento() {
    }

    // GET: retorna o ID do equipamento.
    public Long getId() {
        return id;
    }

    // SET: define o ID do equipamento.
    public void setId(Long id) {
        this.id = id;
    }

    // GET: retorna o tipo do equipamento.
    public TipoEquipamento getTipo() {
        return tipo;
    }

    // SET: define o tipo do equipamento.
    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    // GET: retorna a marca do equipamento.
    public String getMarca() {
        return marca;
    }

    // SET: define a marca do equipamento.
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // GET: retorna o modelo do equipamento.
    public String getModelo() {
        return modelo;
    }

    // SET: define o modelo do equipamento.
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    // GET: retorna o número de série.
    public String getNumeroSerie() {
        return numeroSerie;
    }

    // SET: define o número de série.
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    // GET: retorna o cliente relacionado ao equipamento.
    public Cliente getCliente() {
        return cliente;
    }

    // SET: define qual cliente está relacionado ao equipamento.
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
