package jogador;

import jogo.Marca;

// Contrato comum a qualquer tipo de jogador.
public interface Jogador
{
    Marca getMarca ();
    void facaSuaJogada ();
}