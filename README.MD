# Engenharia de Software - Algoritmos II

**Disciplina:** Algoritmos II  
**Professor(a):** Frank Willian  
**Data:** 03/07/2024  

---

## Desafio - Cálculos Estatísticos com Notas de Alunos

Neste desafio, o objetivo é implementar um programa em Java que realize cálculos estatísticos utilizando as notas finais de alunos da 2ª série do curso de Engenharia de Software da Faculdade ALC. O programa deve ler um arquivo TXT contendo dados formatados como mostrado abaixo, com possibilidade de processar arquivos de vários MBs:

```
ID,Nota1,Nota2,Nota3,Nota4,Ano
101,85,78,92,88,2023
102,70,68,65,72,2023
103,90,85,87,90,2023
104,60,62,58,65,2023
105,75,80,70,78,2023
106,88,85,90,93,2023
107,55,60,58,62,2024
108,77,79,75,80,2024
109,95,92,94,96,2024
110,68,70,72,74,2024
```

### O arquivo de entrada possui as seguintes colunas:

1. **1ª coluna**: Identificação do aluno.
2. **2ª, 3ª, 4ª e 5ª colunas**: Notas finais das disciplinas.
3. **6ª coluna**: Ano.

### Os seguintes requisitos devem ser implementados:

1. **Cálculo de Estatísticas por Disciplina/Ano**: Calcular a média, mediana e desvio padrão das notas finais de cada disciplina em cada ano.
   
2. **Cálculo de Estatísticas por Disciplina**: Calcular a média, mediana e desvio padrão das notas finais de cada disciplina, considerando todos os anos.

3. **Identificação de Desempenho dos Alunos**:
   - Melhor aluno: maior média das notas finais.
   - Pior aluno: menor média das notas finais.

4. **Classificação dos Alunos**:
   - Total de alunos.
   - Quantidade de alunos aprovados e reprovados em cada disciplina (média >= 70).

5. **Criação de Arquivos de Saída**:
   - Resultados dos cálculos estatísticos.
   - Lista de alunos aprovados em todas as disciplinas.
   - Lista de alunos reprovados em uma ou mais disciplinas.
