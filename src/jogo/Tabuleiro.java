package jogo;

import java.util.Arrays;

// Matriz 3x3 que guarda as Marcas (null = vazio).
public class Tabuleiro implements Cloneable
{
    private Marca matriz[][];

    // === Construtor ===

    public Tabuleiro ()
    {
        this.matriz = new Marca[3][3];
    }

    // === Validação ===

    private void validaPosicao (int lin, int col) throws Exception
    {
        if (lin < 0 || lin > 2 || col < 0 || col > 2)
            throw new Exception("Posição inválida: [" + lin + "][" + col + "]");
    }

    // === Leitura / escrita ===

    public boolean haMarcaNaPosicao (int lin, int col) throws Exception
    {
        validaPosicao(lin, col);
        return this.matriz[lin][col] != null;
    }

    public Marca getMarcaNaPosicao (int lin, int col) throws Exception
    {
        validaPosicao(lin, col);
        return this.matriz[lin][col];
    }

    public void setMarcaNaPosicao (Marca mrc, int lin, int col) throws Exception
    {
        validaPosicao(lin, col);
        if (mrc == null)
            throw new Exception("Marca não pode ser nula");
        if (this.matriz[lin][col] != null)
            throw new Exception("Posição já ocupada");
        this.matriz[lin][col] = mrc;
    }

    // === Object ===

    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sb.append(this.matriz[i][j] == null ? " " : this.matriz[i][j].toString());
                if (j < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2) sb.append("---------\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Tabuleiro outro = (Tabuleiro) obj;
        return Arrays.deepEquals(this.matriz, outro.matriz);
    }

    @Override
    public int hashCode ()
    {
        return Arrays.deepHashCode(this.matriz);
    }

    // === Cópia ===

    public Tabuleiro (Tabuleiro modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception("Modelo não pode ser nulo");
        this.matriz = new Marca[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (modelo.matriz[i][j] != null)
                    this.matriz[i][j] = new Marca(modelo.matriz[i][j].getSimbolo());
    }

    @Override
    public Object clone ()
    {
        try { return new Tabuleiro(this); }
        catch (Exception e) { return null; }
    }
}