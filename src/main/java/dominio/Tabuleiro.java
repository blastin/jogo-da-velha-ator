package dominio;

import java.util.Optional;

public interface Tabuleiro extends Colecao<Posicao>, Jogadas {

    /**
     * @return Resultado do dominio.jogo caso tenha finalizado
     */
    Optional<Resultado> resultadoJogo();

}
