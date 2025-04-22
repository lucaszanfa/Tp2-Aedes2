# Classe Show - Implementação em Java

Esta classe representa um show (série ou filme) com seus atributos básicos e métodos necessários para manipulação.

## 📋 Atributos da Classe

| Atributo       | Tipo       | Descrição                          |
|----------------|------------|------------------------------------|
| `show_id`      | String     | Identificador único do show        |
| `type`         | String     | Tipo (Série ou Filme)              |
| `title`        | String     | Título                             |
| `director`     | String     | Diretor(es)                        |
| `cast`         | String[]   | Elenco principal                   |
| `country`      | String     | País de origem                     |
| `date_added`   | Date       | Data de adição à plataforma        |
| `release_year` | int        | Ano de lançamento                  |
| `rating`       | String     | Classificação etária               |
| `duration`     | String     | Duração                            |
| `listed_in`    | String[]   | Gêneros/Categorias                 |

### Construtores
- `Show()` - Construtor padrão
- `Show(String show_id, String type, ...)` - Construtor com parâmetros

### Métodos de Acesso
- Getters e setters para todos os atributos

### Funcionalidades
- `clone()`: Retorna uma cópia do objeto
- `imprimir()`: Exibe os atributos formatados
- `ler()`: Carrega dados de um arquivo

## ⚠️ Tratamento de Dados
- Valores faltantes são substituídos por `"NaN"`
- Arrays vazios são inicializados corretamente
- Datas são validadas

