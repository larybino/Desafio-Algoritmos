import java.io.BufferedReader; //usada para leitura de arquivos txt
import java.io.BufferedWriter; //usada para escrita de arquivos txt
import java.io.FileReader; //usado para ler dados de um arquivo como caracteres
import java.io.FileWriter; //usado para escrever dados em um arquivo como caracteres
import java.io.IOException; //exceção lançada quando ocorre algum erro de I/O durante a leitura ou escrita de arquivos
import java.util.ArrayList; //usada para armazenar listas de notas de alunos
import java.util.Arrays; //manipular arrays, como ordenação e conversão entre arrays e coleções
import java.util.Collections; //ordenação, busca e sincronização de coleções
import java.util.Comparator; //usada para comparar objetos e fornecer uma lógica de ordenação personalizada
import java.util.List; //acessar, adicionar, remover e manipular elementos em uma lista
import java.util.stream.Collectors; //usada em operações de streaming, como filtrar e coletar dados de fluxos

public class App {
    public static void main(String[] args) {
        // Lê os dados dos alunos do arquivo.txt
        List<Aluno> alunos = lerArquivo("src/arquivo.txt");

         // número máximo de anos presentes nos dados dos alunos
        int maxAno = alunos.stream().mapToInt(Aluno::getAno).max().orElse(2023) + 1;
        // usa 2023 como valor padrão 


        //listas para armazenar as notas finais por disciplina e ano
        List<List<Double>> notasDisciplina1 = new ArrayList<>(maxAno);
        List<List<Double>> notasDisciplina2 = new ArrayList<>(maxAno);
        List<List<Double>> notasDisciplina3 = new ArrayList<>(maxAno);
        List<List<Double>> notasDisciplina4 = new ArrayList<>(maxAno);

        //listas de notas para cada ano
        for (int i = 0; i < maxAno; i++) {
            notasDisciplina1.add(new ArrayList<>());
            notasDisciplina2.add(new ArrayList<>());
            notasDisciplina3.add(new ArrayList<>());
            notasDisciplina4.add(new ArrayList<>());
        }

        // Preenche as listas com as notas dos alunos
        for (Aluno aluno : alunos) {
            int anoIndex = aluno.getAno(); 
            notasDisciplina1.get(anoIndex).add(aluno.getNota1());
            notasDisciplina2.get(anoIndex).add(aluno.getNota2());
            notasDisciplina3.get(anoIndex).add(aluno.getNota3());
            notasDisciplina4.get(anoIndex).add(aluno.getNota4());
        }
        

        // Cálculo de estatísticas por disciplina e ano
        calcularEstatisticas(notasDisciplina1, "Disciplina 1");
        calcularEstatisticas(notasDisciplina2, "Disciplina 2");
        calcularEstatisticas(notasDisciplina3, "Disciplina 3");
        calcularEstatisticas(notasDisciplina4, "Disciplina 4");

        // Cálculo de estatísticas por disciplina (todos os anos)
        calcularEstatisticasTotal(notasDisciplina1, "Disciplina 1");
        calcularEstatisticasTotal(notasDisciplina2, "Disciplina 2");
        calcularEstatisticasTotal(notasDisciplina3, "Disciplina 3");
        calcularEstatisticasTotal(notasDisciplina4, "Disciplina 4");

        // melhor e pior aluno
        Aluno melhorAluno = Collections.max(alunos, Comparator.comparingDouble(App::calcularMediaAluno));
        Aluno piorAluno = Collections.min(alunos, Comparator.comparingDouble(App::calcularMediaAluno));
        System.out.printf("Melhor Aluno: %s, Média: %.2f%n", melhorAluno.getId(), calcularMediaAluno(melhorAluno));
        System.out.printf("Pior Aluno: %s, Média: %.2f%n", piorAluno.getId(), calcularMediaAluno(piorAluno));

        // aprovados e reprovados
        long totalAlunos = alunos.size();
        long aprovadosDisciplina1 = alunos.stream().filter(a -> a.getNota1() >= 70).count();
        long aprovadosDisciplina2 = alunos.stream().filter(a -> a.getNota2() >= 70).count();
        long aprovadosDisciplina3 = alunos.stream().filter(a -> a.getNota3() >= 70).count();
        long aprovadosDisciplina4 = alunos.stream().filter(a -> a.getNota4() >= 70).count();

        long reprovadosDisciplina1 = totalAlunos - aprovadosDisciplina1;
        long reprovadosDisciplina2 = totalAlunos - aprovadosDisciplina2;
        long reprovadosDisciplina3 = totalAlunos - aprovadosDisciplina3;
        long reprovadosDisciplina4 = totalAlunos - aprovadosDisciplina4;

        System.out.printf("Total de Alunos: %d%n", totalAlunos);
        System.out.printf("Aprovados Disciplina 1: %d, Reprovados: %d%n", aprovadosDisciplina1, reprovadosDisciplina1);
        System.out.printf("Aprovados Disciplina 2: %d, Reprovados: %d%n", aprovadosDisciplina2, reprovadosDisciplina2);
        System.out.printf("Aprovados Disciplina 3: %d, Reprovados: %d%n", aprovadosDisciplina3, reprovadosDisciplina3);
        System.out.printf("Aprovados Disciplina 4: %d, Reprovados: %d%n", aprovadosDisciplina4, reprovadosDisciplina4);


        // criação de arquivos 
        criarArquivoDeSaidaResultados("src/resultados.txt", notasDisciplina1, notasDisciplina2, notasDisciplina3, notasDisciplina4);
        criarArquivoDeSaida("src/aprovados.txt", alunos.stream().filter(a -> Arrays.asList(a.getNota1(), a.getNota2(), a.getNota3(), a.getNota4()).stream().allMatch(n -> n >= 70)).collect(Collectors.toList()));
        criarArquivoDeSaida("src/reprovados.txt", alunos.stream().filter(a -> Arrays.asList(a.getNota1(), a.getNota2(), a.getNota3(), a.getNota4()).stream().anyMatch(n -> n < 70)).collect(Collectors.toList()));
    }

    // lê dados do arquivo.txt
    private static List<Aluno> lerArquivo(String caminho) {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("ID")) continue;
                String[] partes = linha.split(",");
                String idStr = partes[0];
                int id = Integer.parseInt(idStr);                
                double nota1 = Double.parseDouble(partes[1]);
                double nota2 = Double.parseDouble(partes[2]);
                double nota3 = Double.parseDouble(partes[3]);
                double nota4 = Double.parseDouble(partes[4]);
                int ano = Integer.parseInt(partes[5]);
                alunos.add(new Aluno(id, nota1, nota2, nota3, nota4, ano));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    // calculando média, mediana e desvio padrao por disciplina e ano
    // calcula separadamente as notas de 2023 e 2024
    private static void calcularEstatisticas(List<List<Double>> notasPorAno, String disciplina) {
        for (int i = 0; i < notasPorAno.size(); i++) {
            List<Double> notasDoAno = notasPorAno.get(i);
            if (!notasDoAno.isEmpty()) {
                double media = calcularMedia(notasDoAno); // calcula os números de cada coluna de acordo com o ano
                double mediana = calcularMediana(notasDoAno);
                double desvioPadrao = calcularDesvioPadrao(notasDoAno, media);
                System.out.printf("Disciplina: %s, Ano: %d, Média: %.2f, Mediana: %.2f, Desvio Padrão: %.2f%n",
                        disciplina, i, media, mediana, desvioPadrao);
            }
        }
    }

    // calculando média, mediana e desvio padrão por disciplina
    // calcula as notas de 2023 e 2024 juntas
    private static void calcularEstatisticasTotal(List<List<Double>> notasPorAno, String disciplina) {
        List<Double> todasNotas = new ArrayList<>();
        for (List<Double> notas : notasPorAno) {
            todasNotas.addAll(notas);
        }
        if (!todasNotas.isEmpty()) {
            double media = calcularMedia(todasNotas); // calcula todos os números de cada coluna
            double mediana = calcularMediana(todasNotas);
            double desvioPadrao = calcularDesvioPadrao(todasNotas, media);
            System.out.printf("Disciplina: %s, Média: %.2f, Mediana: %.2f, Desvio Padrão: %.2f%n",
                    disciplina, media, mediana, desvioPadrao);
        }
    }

    private static double calcularMedia(List<Double> notas) {
        return notas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private static double calcularMediana(List<Double> notas) {
        int size = notas.size();
        List<Double> sorted = notas.stream().sorted().collect(Collectors.toList());
        return size % 2 == 0 ? (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0 : sorted.get(size / 2);
    }

    private static double calcularDesvioPadrao(List<Double> notas, double media) {
        double variancia = notas.stream().mapToDouble(n -> Math.pow(n - media, 2)).average().orElse(0.0);
        return Math.sqrt(variancia);
    }

    //calcula a média por linha
    private static double calcularMediaAluno(Aluno aluno) {
        return Arrays.asList(aluno.getNota1(), aluno.getNota2(), aluno.getNota3(), aluno.getNota4()).stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private static void criarArquivoDeSaidaResultados(String caminho, List<List<Double>> notasDisciplina1, List<List<Double>> notasDisciplina2, List<List<Double>> notasDisciplina3, List<List<Double>> notasDisciplina4) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("Resultados:\n");
            // percorre sobre cada ano e escreve as estatísticas para cada disciplina
            for (int i = 0; i < notasDisciplina1.size(); i++) {
                if (!notasDisciplina1.get(i).isEmpty()) {
                    writer.write(String.format("Ano: %d, Disciplina 1, Média: %.2f, Mediana: %.2f, Desvio Padrão: %.2f\n",
                            i, calcularMedia(notasDisciplina1.get(i)), calcularMediana(notasDisciplina1.get(i)), calcularDesvioPadrao(notasDisciplina1.get(i), calcularMedia(notasDisciplina1.get(i)))));
                }
                if (!notasDisciplina2.get(i).isEmpty()) {
                    writer.write(String.format("Ano: %d, Disciplina 2, Média: %.2f, Mediana: %.2f, Desvio Padrão: %.2f\n",
                            i, calcularMedia(notasDisciplina2.get(i)), calcularMediana(notasDisciplina2.get(i)), calcularDesvioPadrao(notasDisciplina2.get(i), calcularMedia(notasDisciplina2.get(i)))));
                }
                if (!notasDisciplina3.get(i).isEmpty()) {
                    writer.write(String.format("Ano: %d, Disciplina 3, Média: %.2f, Mediana: %.2f, Desvio Padrão: %.2f\n",
                            i, calcularMedia(notasDisciplina3.get(i)), calcularMediana(notasDisciplina3.get(i)), calcularDesvioPadrao(notasDisciplina3.get(i), calcularMedia(notasDisciplina3.get(i)))));
                }
                if (!notasDisciplina4.get(i).isEmpty()) {
                    writer.write(String.format("Ano: %d, Disciplina 4, Média: %.2f, Mediana: %.2f, Desvio Padrão: %.2f\n",
                            i, calcularMedia(notasDisciplina4.get(i)), calcularMediana(notasDisciplina4.get(i)), calcularDesvioPadrao(notasDisciplina4.get(i), calcularMedia(notasDisciplina4.get(i)))));
                }
            }
            writer.flush(); // Força a escrita de todos os dados para o arquivo
        } catch (IOException e) {
            e.printStackTrace();//imprime a exceção ocorrida
        }
    }

    private static void criarArquivoDeSaida(String caminho, List<Aluno> alunos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("ID, Nota 1, Nota 2, Nota 3, Nota 4, Ano\n");
            // percorre sobre cada aluno na lista
            for (Aluno aluno : alunos) {
                writer.write(String.format("%s, %.2f, %.2f, %.2f, %.2f, %d\n",
                        aluno.getId(), aluno.getNota1(), aluno.getNota2(), aluno.getNota3(), aluno.getNota4(), aluno.getAno()));
            }
            writer.flush(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
