public class Persona {
    private int id;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private int eta;

    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    // Getter
    public int    getId()        { return id; }
    public String getNome()      { return nome; }
    public String getCognome()   { return cognome; }
    public String getIndirizzo() { return indirizzo; }
    public String getTelefono()  { return telefono; }
    public int    getEta()       { return eta; }

    // Setter
    public void setId(int id)                  { this.id = id; }
    public void setNome(String nome)           { this.nome = nome; }
    public void setCognome(String cognome)     { this.cognome = cognome; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public void setTelefono(String telefono)   { this.telefono = telefono; }
    public void setEta(int eta)                { this.eta = eta; }

    @Override
    public String toString() {
        return nome + " " + cognome;
    }
}