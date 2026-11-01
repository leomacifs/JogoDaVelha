package jogador;

import jogo.Marca;
import jogo.Tabuleiro;
import java.util.Objects;

// IA com heurísticas: ganhar → bloquear → futuro → qualquer lugar.
public class JogadorAutomatico implements Jogador, Cloneable
{
    private Marca     marca     = null;
    private Tabuleiro tabDoJogo = null;

    // === Construtores ===

    public JogadorAutomatico (Tabuleiro tab) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        this.tabDoJogo = tab;
        this.marca = ((int)(Math.random()*2) == 0)
            ? new Marca('X') : new Marca('O');
    }

    public JogadorAutomatico (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        if (mrc == null) throw new Exception("Marca não pode ser nula");
        this.tabDoJogo = tab;
        this.marca     = mrc;
    }

    // === Contrato Jogador ===

    public Marca getMarca () { return this.marca; }

    public void facaSuaJogada ()
    {
        try
        {
            if (tentaGanhar())         return;
            if (tentaBloquear())       return;
            if (tentaJogadaDeFuturo()) return;
            jogaEmQualquerLugar();
        }
        catch (Exception e) { /* posições validadas internamente */ }
    }

    // === Heurísticas ===

    private boolean tentaGanhar () throws Exception
    {
        return tentaCompletar(this.marca);
    }

    private boolean tentaBloquear () throws Exception
    {
        Marca oponente = new Marca(this.marca.getSimbolo() == 'X' ? 'O' : 'X');
        return tentaCompletar(oponente);
    }

    // Completa trio com 2 marcas de 'alvo' + 1 vazio.
    private boolean tentaCompletar (Marca alvo) throws Exception
    {
        int[][][] trios = todosOsTrios();
        for (int[][] trio : trios)
        {
            int marcasAlvo = 0, vazios = 0;
            int[] posVazia = null;

            for (int[] p : trio)
            {
                if (tabDoJogo.haMarcaNaPosicao(p[0], p[1]))
                {
                    if (tabDoJogo.getMarcaNaPosicao(p[0], p[1]).equals(alvo))
                        marcasAlvo++;
                    else
                        marcasAlvo = -100;
                }
                else
                {
                    vazios++;
                    if (posVazia == null) posVazia = p;
                }
            }

            if (marcasAlvo == 2 && vazios == 1)
            {
                tabDoJogo.setMarcaNaPosicao(this.marca, posVazia[0], posVazia[1]);
                return true;
            }
        }
        return false;
    }

    // Trio com 1 marca própria, 0 do oponente e 2 vazios.
    private boolean tentaJogadaDeFuturo () throws Exception
    {
        int[][][] trios = todosOsTrios();
        for (int[][] trio : trios)
        {
            int minhas = 0, oponentes = 0;
            int[] primeiroVazio = null;

            for (int[] p : trio)
            {
                if (tabDoJogo.haMarcaNaPosicao(p[0], p[1]))
                {
                    if (tabDoJogo.getMarcaNaPosicao(p[0], p[1]).equals(this.marca))
                        minhas++;
                    else
                        oponentes++;
                }
                else if (primeiroVazio == null)
                {
                    primeiroVazio = p;
                }
            }

            if (minhas == 1 && oponentes == 0 && primeiroVazio != null)
            {
                tabDoJogo.setMarcaNaPosicao(this.marca,
                                            primeiroVazio[0], primeiroVazio[1]);
                return true;
            }
        }
        return false;
    }

    // Fallback: primeira posição livre.
    private void jogaEmQualquerLugar () throws Exception
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!tabDoJogo.haMarcaNaPosicao(i, j))
                {
                    tabDoJogo.setMarcaNaPosicao(this.marca, i, j);
                    return;
                }
    }

    // Lista dos 8 trios vencedores.
    private int[][][] todosOsTrios ()
    {
        return new int[][][] {
            {{0,0},{0,1},{0,2}}, {{1,0},{1,1},{1,2}}, {{2,0},{2,1},{2,2}},
            {{0,0},{1,0},{2,0}}, {{0,1},{1,1},{2,1}}, {{0,2},{1,2},{2,2}},
            {{0,0},{1,1},{2,2}}, {{0,2},{1,1},{2,0}}
        };
    }

    // === Object ===

    @Override
    public String toString ()
    {
        return "JogadorAutomatico[" + marca + "]";
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        JogadorAutomatico outro = (JogadorAutomatico) obj;
        return Objects.equals(this.marca, outro.marca)
            && Objects.equals(this.tabDoJogo, outro.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(this.marca, this.tabDoJogo);
    }

    // === Cópia ===

    public JogadorAutomatico (JogadorAutomatico modelo) throws Exception
    {
        if (modelo == null) throw new Exception("Modelo não pode ser nulo");
        this.marca = (modelo.marca != null)
            ? new Marca(modelo.marca.getSimbolo()) : null;
        this.tabDoJogo = (modelo.tabDoJogo != null)
            ? new Tabuleiro(modelo.tabDoJogo) : null;
    }

    @Override
    public Object clone ()
    {
        try { return new JogadorAutomatico(this); }
        catch (Exception e) { return null; }
    }
}