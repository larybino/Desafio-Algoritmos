public class Aluno {
    private int id;
    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;
    private int ano;

    public Aluno(int id, double nota1, double nota2, double nota3, double nota4, int ano) {
        this.id = id;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public double getNota4() {
        return nota4;
    }

    public int getAno() {
        return ano;
    }
}