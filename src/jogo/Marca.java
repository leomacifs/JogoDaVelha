package jogo;

public class Marca
{
    private char simbolo;

    public Marca (char smb) throws Exception
    {
        if (smb != 'X' && smb != 'O')
            throw new Exception("Símbolo inválido: deve ser 'X' ou 'O'");
        this.simbolo = smb;
    }

    public char getSimbolo ()
    {
        return this.simbolo;
    }

    @Override
    public String toString ()
    {
        return "" + simbolo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Marca outra = (Marca) obj;
        return this.simbolo == outra.simbolo;
    }

    @Override
    public int hashCode ()
    {
        return Character.hashCode(this.simbolo);
    }
}