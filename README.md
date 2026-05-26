# 📒 Rubrica Telefonica

Applicazione desktop Java per la gestione dei contatti personali, realizzata con interfaccia grafica **Swing** e persistenza su **MySQL** tramite JDBC.


## ✨ Funzionalità

- **Visualizzazione** dei contatti in tabella
- **Aggiunta** di nuovi contatti tramite form dedicato
- **Modifica** di contatti esistenti
- **Eliminazione** con richiesta di conferma
- **Login utente** con username e password
- **Persistenza su database MySQL** tramite JDBC
- **Barra degli strumenti JToolBar** con icone sui bottoni

## 🛠 Tecnologie utilizzate

- Java (Swing, JDBC)
- MySQL

## 🚀 Come eseguire il progetto

### Prerequisiti
- Java JDK 11 o superiore installato
- MySQL installato e in esecuzione

### Installazione

1. Clona il repository:
   ```bash
   git clone https://github.com/fabrizio-ferrentino/rubrica-telefonica.git
   ```

2. Apri il file `credenziali_database.properties` e inserisci le tue credenziali MySQL:
   ```properties
   db.host=localhost
   db.port=3306
   db.name=rubrica_db
   db.username=il_tuo_username
   db.password=la_tua_password
   ```

3. Esegui lo script SQL per creare il database:
   ```bash
   mysql -u username -p < schema_database.sql
   ```

4. Avvia l'applicazione:
   ```bash
   java -jar Rubrica.jar
   ```

---

## 📁 Struttura del progetto

```
rubrica-telefonica/
├── src/
│   ├── Main.java                  # Entry point
│   ├── Persona.java               # Modello dati contatto
│   ├── Utente.java                # Modello dati utente
│   ├── RubricaManager.java        # Logica e persistenza
│   ├── MainFrame.java             # Finestra principale con JTable
│   └── EditorPersonaDialog.java   # Form inserimento/modifica
├── schema_database.sql            # Script creazione database
├── credenziali_database.properties
└── Rubrica.jar                    # Eseguibile
```

## 📄 Licenza
 
Questo progetto è distribuito sotto licenza **MIT**. Consulta il file [LICENSE](LICENSE) per maggiori dettagli.

## 👤 Autore

 [Fabrizio Ferrentino](https://fabrizioferrentino.dev)
