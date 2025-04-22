import java.io.*;
import java.text.*;
import java.util.*;

class Show {
    // Atributos privados conforme especificado
    private String id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    // Construtor padrão
    public Show() {
        this.id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[0];
        this.country = "NaN";

        // Define a data padrão como 1 de março de 1900
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            this.dateAdded = sdf.parse("March 1, 1900");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.releaseYear = 0;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listedIn = new String[0];
    }

    // Construtor com parâmetros
    public Show(String id, String type, String title, String director, String[] cast, 
                String country, Date dateAdded, int releaseYear, String rating, 
                String duration, String[] listedIn) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = listedIn;
    }

    // Métodos getters
    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDirector() {
        return this.director;
    }

    public String[] getCast() {
        return this.cast;
    }

    public String getCountry() {
        return this.country;
    }

    public Date getDateAdded() {
        return this.dateAdded;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public String getRating() {
        return this.rating;
    }

    public String getDuration() {
        return this.duration;
    }

    public String[] getListedIn() {
        return this.listedIn;
    }

    // Métodos setters
    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setListedIn(String[] listedIn) {
        this.listedIn = listedIn;
    }

    // Método clone
    public Show clone() {
        Show clone = new Show();
        clone.id = this.id;
        clone.type = this.type;
        clone.title = this.title;
        clone.director = this.director;
        clone.cast = this.cast.clone();
        clone.country = this.country;
        clone.dateAdded = (Date) this.dateAdded.clone();
        clone.releaseYear = this.releaseYear;
        clone.rating = this.rating;
        clone.duration = this.duration;
        clone.listedIn = this.listedIn.clone();
        return clone;
    }

    // Método para ler um show do arquivo CSV pelo ID
    public void ler(String showId) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));

        String line;
        boolean found = false;
        // Pular o cabeçalho
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] fields = parseCSVLine(line);

            if (fields[0].equals(showId)) {
                found = true;
                this.id = fields[0];
                this.type = fields[1].equals("") ? "NaN" : fields[1];
                this.title = fields[2].equals("") ? "NaN" : fields[2];
                this.director = fields[3].equals("") ? "NaN" : fields[3];

                // Tratamento do cast
                if (fields[4].equals("")) {
                    this.cast = new String[0];
                } else {
                    // Removendo colchetes e dividindo por vírgulas
                    String castString = fields[4].trim();
                    if (castString.startsWith("[") && castString.endsWith("]")) {
                        castString = castString.substring(1, castString.length() - 1);
                    }

                    if (castString.isEmpty()) {
                        this.cast = new String[0];
                    } else {
                        this.cast = castString.split(", ");
                        Arrays.sort(this.cast); // Ordenar o cast conforme solicitado
                    }
                }

                this.country = fields[5].equals("") ? "NaN" : fields[5];

                // Tratamento da data
                if (fields[6].equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                    this.dateAdded = sdf.parse("March 1, 1900");
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                    this.dateAdded = sdf.parse(fields[6]);
                }

                this.releaseYear = fields[7].equals("") ? 0 : Integer.parseInt(fields[7]);
                this.rating = fields[8].equals("") ? "NaN" : fields[8];
                this.duration = fields[9].equals("") ? "NaN" : fields[9];

                // Tratamento do listedIn
                if (fields[10].equals("")) {
                    this.listedIn = new String[0];
                } else {
                    // Removendo colchetes e dividindo por vírgulas
                    String listedInString = fields[10].trim();
                    if (listedInString.startsWith("[") && listedInString.endsWith("]")) {
                        listedInString = listedInString.substring(1, listedInString.length() - 1);
                    }

                    if (listedInString.isEmpty()) {
                        this.listedIn = new String[0];
                    } else {
                        this.listedIn = listedInString.split(", ");
                        Arrays.sort(this.listedIn); // Ordenar as categorias conforme solicitado
                    }
                }

                break;
            }
        }

        br.close();

        if (!found) {
            throw new Exception("Show com ID " + showId + " não encontrado!");
        }
    }

    // Método auxiliar para lidar com as aspas e vírgulas nos campos CSV
    private String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }

    // Método para imprimir os dados do show
    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dateStr = this.dateAdded.equals(new Date(0)) ? "NaN" : sdf.format(this.dateAdded);
        
        // Formatação do array de cast
        StringBuilder castStr = new StringBuilder("[");
        for (int i = 0; i < this.cast.length; i++) {
            if (i > 0) castStr.append(", ");
            castStr.append(this.cast[i]);
        }
        castStr.append("]");
        
        // Formatação do array de listedIn
        StringBuilder listedInStr = new StringBuilder("[");
        for (int i = 0; i < this.listedIn.length; i++) {
            if (i > 0) listedInStr.append(", ");
            listedInStr.append(this.listedIn[i]);
        }
        listedInStr.append("]");
        
        System.out.println("=> " + this.id + " ## " + 
                        this.title + " ## " + 
                        this.type + " ## " + 
                        this.director + " ## " + 
                        castStr.toString() + " ## " + 
                        this.country + " ## " + 
                        dateStr + " ## " + 
                        this.releaseYear + " ## " + 
                        this.rating + " ## " + 
                        this.duration + " ## " + 
                        listedInStr.toString());
    }
}

public class Showapp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            String line;
            while (!(line = sc.nextLine()).equals("FIM")) {
                Show show = new Show();
                try {
                    show.ler(line);
                    show.imprimir();
                } catch (Exception e) {
                    System.out.println("Erro ao ler o show: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
