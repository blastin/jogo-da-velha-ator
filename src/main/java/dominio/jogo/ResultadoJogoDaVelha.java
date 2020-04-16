package dominio.jogo;

import dominio.Peca;
import dominio.Relatorio;
import dominio.Resultado;

final class ResultadoJogoDaVelha implements Resultado {

    private final Peca pecaResultado;

    private final Relatorio relatorio;

    ResultadoJogoDaVelha(final Peca pecaResultado, final Relatorio relatorio) {
        this.pecaResultado = pecaResultado;
        this.relatorio = relatorio;
    }

    @Override
    public Peca peca() {
        return pecaResultado;
    }

    @Override
    public Relatorio relatorio() {
        return relatorio;
    }

}
