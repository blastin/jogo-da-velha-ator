package dominio;

import java.util.Optional;

public interface Jogadas {

    Optional<Posicao> jogadaAdversario();

    Colecao<Posicao> posicoes();

}
