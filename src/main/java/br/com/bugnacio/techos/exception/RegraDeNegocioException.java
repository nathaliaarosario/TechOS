package br.com.bugnacio.techos.exception;

//classe responsavel por criar uma exceção personalizada
//para representar erros relacionados as regras de negocio do sistema

//Construtor que recebe uma mensagem explicando o motivo do erro
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {

        // super() chama o construtor da classe pai(RunTimeException)
        // enviando a mensagem recebida
        // Assim, a exceção passa a carregar a descrição do problema

        super(mensagem);
    }

}
