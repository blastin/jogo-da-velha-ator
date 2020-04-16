package dominio;

import java.util.Optional;

@FunctionalInterface
public interface Colecao<T> {

    Optional<T> obter(final int x, final int y);

}
