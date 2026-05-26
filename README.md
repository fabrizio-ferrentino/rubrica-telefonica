# 📒 Rubrica Telefonica

A Java desktop application for managing personal contacts, built with a **Swing** GUI and **MySQL** persistence via JDBC.

## ✨ Features

- **View** contacts in a table
- **Add** new contacts via a dedicated form
- **Edit** existing contacts
- **Delete** contacts with confirmation dialog
- **User login** with username and password
- **MySQL database** persistence via JDBC
- **JToolBar** with icons on buttons

## 🛠 Tech Stack

- Java (Swing, JDBC)
- MySQL

## 🚀 Getting Started

### Prerequisites

- Java JDK 11 or higher
- MySQL installed and running

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/fabrizio-ferrentino/rubrica-telefonica.git
   ```

2. Open `credenziali_database.properties` and fill in your MySQL credentials:
   ```properties
   db.host=localhost
   db.port=3306
   db.name=rubrica_db
   db.username=your_username
   db.password=your_password
   ```

3. Run the SQL script to create the database:
   ```bash
   mysql -u username -p < schema_database.sql
   ```

4. Launch the application:
   ```bash
   java -jar Rubrica.jar
   ```

---

## 📁 Project Structure

```
rubrica-telefonica/
├── src/
│   ├── Main.java                  # Entry point
│   ├── Persona.java               # Contact model
│   ├── Utente.java                # User model
│   ├── RubricaManager.java        # Business logic & persistence
│   ├── MainFrame.java             # Main window with JTable
│   └── EditorPersonaDialog.java   # Add/edit contact form
├── schema_database.sql            # Database creation script
├── credenziali_database.properties
└── Rubrica.jar                    # Executable
```

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

## 👤 Author

[Fabrizio Ferrentino](https://fabrizioferrentino.dev)
