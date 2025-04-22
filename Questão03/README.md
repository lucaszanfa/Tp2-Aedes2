# Pesquisa Sequencial em Java

Implementação de um sistema de pesquisa sequencial em vetor de objetos `Show`, utilizando o título como chave primária de pesquisa.

## 📋 Descrição do Projeto

Este projeto consiste em:
1. Carregar registros de shows em um vetor
2. Realizar pesquisas sequenciais pelos títulos
3. Gerar um arquivo de log com métricas de desempenho

## 📝 Especificações

### Entrada
- **Primeira parte**: Igual à entrada da primeira questão (registros de shows)
- **Segunda parte**: 
  - Linhas com títulos a pesquisar
  - Última linha contém "FIM"

### Saída
- Linhas com "SIM" ou "NAO" indicando se cada título foi encontrado
- Arquivo de log `matricula_sequencial.txt` contendo:
  - Matrícula do aluno
  - Tempo de execução (em milissegundos)
  - Número de comparações realizadas
