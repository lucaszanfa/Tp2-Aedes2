import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.HashSet;

public class Show {
    private String show_id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    // Construtor vazio
    public Show() {
        this.show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[]{"NaN"};
        this.country = "NaN";
        this.date_added = null;
        this.release_year = -1;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listed_in = new String[]{"NaN"};
    }

    // Construtor com parâmetros
    public Show(String show_id, String type, String title, String director, String[] cast, String country,
               String date_added, int release_year, String rating, String duration, String[] listed_in) {
        this.show_id = (show_id != null && !show_id.isEmpty()) ? show_id : "NaN";
        this.type = (type != null && !type.isEmpty()) ? type : "NaN";
        this.title = (title != null && !title.isEmpty()) ? title : "NaN";
        this.director = (director != null && !director.isEmpty()) ? director : "NaN";
        this.cast = (cast != null && cast.length > 0) ? cast : new String[]{"NaN"};
        this.country = (country != null && !country.isEmpty()) ? country : "NaN";
        this.date_added = parseDate(date_added);
        this.release_year = (release_year > 0) ? release_year : -1;
        this.rating = (rating != null && !rating.isEmpty()) ? rating : "NaN";
        this.duration = (duration != null && !duration.isEmpty()) ? duration : "NaN";
        this.listed_in = (listed_in != null && listed_in.length > 0) ? listed_in : new String[]{"NaN"};

        // Ordena as listas
        if (this.cast.length > 1 && !this.cast[0].equals("NaN")) {
            Arrays.sort(this.cast);
        }
        if (this.listed_in.length > 1 && !this.listed_in[0].equals("NaN")) {
            Arrays.sort(this.listed_in);
        }
    }

    // Métodos auxiliares para datas
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty() || dateStr.equals("NaN")) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "NaN";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
        return sdf.format(date);
    }

    // Getters e Setters
    public String getShow_id() { return show_id; }
    public void setShow_id(String show_id) { this.show_id = (show_id != null && !show_id.isEmpty()) ? show_id : "NaN"; }

    public String getType() { return type; }
    public void setType(String type) { this.type = (type != null && !type.isEmpty()) ? type : "NaN"; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = (title != null && !title.isEmpty()) ? title : "NaN"; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = (director != null && !director.isEmpty()) ? director : "NaN"; }

    public String[] getCast() { return cast; }
    public void setCast(String[] cast) {
        this.cast = (cast != null && cast.length > 0) ? cast : new String[]{"NaN"};
        if (this.cast.length > 1 && !this.cast[0].equals("NaN")) {
            Arrays.sort(this.cast);
        }
    }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = (country != null && !country.isEmpty()) ? country : "NaN"; }

    public Date getDate_added() { return date_added; }
    public void setDate_added(String date_added) { this.date_added = parseDate(date_added); }

    public int getRelease_year() { return release_year; }
    public void setRelease_year(int release_year) { this.release_year = (release_year > 0) ? release_year : -1; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = (rating != null && !rating.isEmpty()) ? rating : "NaN"; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = (duration != null && !duration.isEmpty()) ? duration : "NaN"; }

    public String[] getListed_in() { return listed_in; }
    public void setListed_in(String[] listed_in) {
        this.listed_in = (listed_in != null && listed_in.length > 0) ? listed_in : new String[]{"NaN"};
        if (this.listed_in.length > 1 && !this.listed_in[0].equals("NaN")) {
            Arrays.sort(this.listed_in);
        }
    }

    // Método clone
    public Show clone() {
        return new Show(
            this.show_id,
            this.type,
            this.title,
            this.director,
            this.cast.clone(),
            this.country,
            formatDate(this.date_added),
            this.release_year,
            this.rating,
            this.duration,
            this.listed_in.clone()
        );
    }

    // Método imprimir
    public void imprimir() {
        String castStr = (cast[0].equals("NaN")) ? "[NaN]" : Arrays.toString(cast);
        String listedInStr = (listed_in[0].equals("NaN")) ? "[NaN]" : Arrays.toString(listed_in);
        String dateStr = formatDate(date_added);
        String yearStr = (release_year == -1) ? "NaN" : Integer.toString(release_year);
        String cleanTitle = title.replaceAll("^\"|\"$", ""); // remove aspas apenas nas extremidades
        
        System.out.println(
            "=> " + show_id + " ## " + cleanTitle + " ## " + type + " ## " + director + " ## " +
            castStr + " ## " + country + " ## " + dateStr + " ## " + yearStr + " ## " +
            rating + " ## " + duration + " ## " + listedInStr + " ##"
        );
    }

    // Método para ler do arquivo CSV
    public static Show[] lerCSV(String arquivo) {
        Show[] shows = new Show[0];

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean cabecalho = true;

            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }

                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Processar cada campo
                String show_id = dados[0].isEmpty() ? "NaN" : dados[0];
                String type = dados[1].isEmpty() ? "NaN" : dados[1];
                String title = dados[2].isEmpty() ? "NaN" : dados[2];
                String director = dados[3].isEmpty() ? "NaN" : dados[3];
                
                String[] cast = dados[4].replace("\"", "").split("\\s*,\\s*");
                if (cast.length == 0 || (cast.length == 1 && cast[0].isEmpty())) {
                    cast = new String[]{"NaN"};
                }
                
                String country = dados[5].isEmpty() ? "NaN" : dados[5];
                String date_added = dados[6].isEmpty() ? "NaN" : dados[6];
                int release_year = dados[7].isEmpty() ? -1 : Integer.parseInt(dados[7]);
                String rating = dados[8].isEmpty() ? "NaN" : dados[8];
                String duration = dados[9].isEmpty() ? "NaN" : dados[9];
                
                String[] listed_in = dados[10].replace("\"", "").split("\\s*,\\s*");
                if (listed_in.length == 0 || (listed_in.length == 1 && listed_in[0].isEmpty())) {
                    listed_in = new String[]{"NaN"};
                }

                Show novoShow = new Show(show_id, type, title, director, cast, country,
                                       date_added, release_year, rating, duration, listed_in);
                
                shows = Arrays.copyOf(shows, shows.length + 1);
                shows[shows.length - 1] = novoShow;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo.");
        }

        return shows;
    }

// Busca sequencial pelo título
public static void pesquisaSequencial(Show[] catalogo, Scanner scanner) {
    long startTime = System.nanoTime();
    boolean continuar = true;
    int tamanho = catalogo.length;  // Obtendo o tamanho do array
    int comparacoes = 0;  // Variável para contar o número de comparações feitas

    do {
        String pesquisa = scanner.nextLine().trim();
        
        if (pesquisa.equalsIgnoreCase("FIM")) {
            continuar = false;
        } else {
            boolean encontrado = false;
            
            // Loop para percorrer o catálogo e comparar o título
            for (int i = 0; i < tamanho; i++) {
                comparacoes++;  // Incrementa a cada comparação
                if (catalogo[i].getTitle().equalsIgnoreCase(pesquisa)) {
                    System.out.println("SIM");
                    encontrado = true;
                    break;  // Encerra a busca se encontrar o show
                }
            }
            
            if (!encontrado) {
                System.out.println("NAO");
            }
        }
    } while (continuar); // Continua até o usuário digitar "FIM"

    long endTime = System.nanoTime();
    long duration = endTime - startTime;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("matricula_sequencial.txt"))) {
        // Escreve o log no arquivo com o número de comparações e a duração em milissegundos
        writer.write("847522\t" + duration / 1_000_000.0 + "ms\t" + comparacoes);
    } catch (IOException e) {
        System.err.println("Erro ao escrever o arquivo de log.");
    }
}

    // Método main para busca interativa
    public static void main(String[] args) {
// Carrega todos os shows do arquivo CSV
Show[] catalogo = lerCSV("/tmp/disneyplus.csv");
Show[] subconjunto;
int contador = 0;

// HashSet para armazenar os IDs fornecidos pelo usuário
HashSet<String> idsUsuario = new HashSet<>();

// Lê a entrada da primeira parte até FIM
Scanner scanner = new Scanner(System.in);
while (true) {
    String input = scanner.nextLine().trim();
    if (input.equalsIgnoreCase("FIM")) {
        break;  // Sai do loop se o usuário digitar "FIM"
    } else {
        idsUsuario.add(input);  // Adiciona o ID fornecido à lista
    }
}

// Cria o subconjunto com base nos IDs fornecidos
subconjunto = Arrays.stream(catalogo)
                    .filter(show -> idsUsuario.contains(show.getShow_id()))  // Filtra os shows pelo ID
                    .toArray(Show[]::new);  // Converte o resultado para um novo array de shows

// Agora você pode chamar a função de busca sequencial sobre o subconjunto
System.out.println("NAO");
pesquisaSequencial(subconjunto, scanner);

scanner.close();
    }
}
