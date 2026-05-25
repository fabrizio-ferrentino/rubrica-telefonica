-- ============================================================================
--  Script di Inizializzazione Database: rubrica_db
--  Versione: 1.1.0
--  Autore: Fabrizio Ferrentino
--  Descrizione: Crea lo schema relazionale e popola le tabelle di sistema.
-- ============================================================================

CREATE DATABASE IF NOT EXISTS rubrica_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE rubrica_db;

-- ----------------------------------------------------------------------------
--  Tabella: persone
--  Descrizione: Memorizza i contatti della rubrica telefonica.
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS persone (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(50)  NOT NULL,
    cognome   VARCHAR(50),
    indirizzo VARCHAR(150),
    telefono  VARCHAR(30)  NOT NULL,
    eta       INT
);

-- ----------------------------------------------------------------------------
--  Tabella: utenti
--  Descrizione: Gestione credenziali per l'autenticazione all'applicazione.
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS utenti (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- ------------------------------------------------------------
--  Utente di default (admin / admin)
-- ------------------------------------------------------------
INSERT INTO utenti (username, password)
VALUES ('admin', 'admin')
ON DUPLICATE KEY UPDATE username = username;
