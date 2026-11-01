package jogo;

import java.util.Objects;

public class AnalistaSituacional
{
    private Tabuleiro tabDoJogo;

    public AnalistaSituacional (Tabuleiro tab) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        this.tabDoJogo = tab;
    }

    public Marca getMarcaDoVencedor ()
    {
        try
        {
            // linhas
            for (int i = 0; i < 3; i++)
            {
                Marca a = tabDoJogo.getMarcaNaPosicao(i, 0);
                Marca b = tabDoJogo.getMarcaNaPosicao(i, 1);
                Marca c = tabDoJogo.getMarcaNaPosicao(i, 2);
                if (a != null && a.equals(b) && b.equals(c)) return a;
            }
            // colunas
            for (int j = 0; j < 3; j++)
            {
                Marca a = tabDoJogo.getMarcaNaPosicao(0, j);
                Marca b = tabDoJogo.getMarcaNaPosicao(1, j);
                Marca c = tabDoJogo.getMarcaNaPosicao(2, j);
                if (a != null && a.equals(b) && b.equals(c)) return a;
            }
            // diagonal principal
            Marca d0 = tabDoJogo.getMarcaNaPosicao(0, 0);
            Marca d1 = tabDoJogo.getMarcaNaPosicao(1, 1);
            Marca d2 = tabDoJogo.getMarcaNaPosicao(2, 2);
            if (d0 != null && d0.equals(d1) && d1.equals(d2)) return d0;

            // diagonal secundária
            Marca s0 = tabDoJogo.getMarcaNaPosicao(0, 2);
            Marca s1 = tabDoJogo.getMarcaNaPosicao(1, 1);
            Marca s2 = tabDoJogo.getMarcaNaPosicao(2, 0);
            if (s0 != null && s0.equals(s1) && s1.equals(s2)) return s0;
        }
        catch (Exception e) { /* posições são sempre válidas aqui */ }
        return null;
    }

    private boolean tabuleiroCheio ()
    {
        try {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (!tabDoJogo.haMarcaNaPosicao(i, j)) return false;
            return true;
        } catch (Exception e) { return true; }
    }

    public boolean fimDeJogo ()
    {
        return getMarcaDoVencedor() != null || tabuleiroCheio();
    }

    @Override
    public String toString ()
    {
        return "AnalistaSituacional sobre tabuleiro:\n" + tabDoJogo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        AnalistaSituacional outro = (AnalistaSituacional) obj;
        return Objects.equals(this.tabDoJogo, outro.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(this.tabDoJogo);
    }
}