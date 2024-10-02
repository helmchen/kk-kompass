#! /bin/python
import datetime
import subprocess
import re
from abc import ABC, abstractmethod

class SourceFileHandler(ABC):
    """
    Abstrakte Basisklasse für Klassen, die mit Quellcode-Dateien bearbeiten können. Pro Dateityp
    sollte eine eigene Implementierung erstellt werden.
    """

    def __init__(self, file_path: str, initial_commit_date: datetime.date , initial_commiter: str, inital_ticket_number:str):
        """
        Konstruktor der Klasse. Initialisiert die Attribute der Klasse, welche allen Subklassen zur
        Verfügung stehen.
        :param file_path: Pfad zur Datei
        :param initial_commit_date: Datum des ersten Commits
        :param initial_commiter: Autor des ersten Commits
        :param inital_ticket_number: Ticketnummer des ersten Commits
        """
        self.file_path = file_path
        self.initial_commit_date = initial_commit_date
        self.initial_commit_year = initial_commit_date.year
        self.current_year = datetime.date.now().year
        self.initial_commiter = initial_commiter
        self.inital_ticket_number = inital_ticket_number
        self.author_svn_variable_pattern = re.compile(r'\$Author[^\n]*\$')
        self.id_svn_variable_pattern = re.compile(r'\$Id[^\n]*\$')
        self.date_svn_variable_pattern = re.compile(r'\$Date[^\n]*\$')
        self.head_url_svn_variable_pattern = re.compile(r'\$HeadURL[^\n]*\$')

    @staticmethod
    @abstractmethod
    def can_handle_file(self, file_path: str) -> bool:
        """
        Prüft, ob die Klasse die übergebene Datei verarbeiten kann.
        :param file_path: Pfad zur Datei
        :return: True, wenn die Datei verarbeitet werden kann, ansonsten False
        """
        pass

    def handle_file(self):
        """
        Verarbeitet die Datei. Diese Methode ruft die spezifischen Methoden der Subklassen auf.
        """
        with open(self.file_path, 'r+') as source_file:
            content = source_file.read()
            self.handle_legal_information()
            self.replace_dynamic_author_reference()
            source_file.write(content)


    @abstractmethod
    def handle_legal_information(self, content: str) -> str:
        """
        Verarbeitet die rechtlichen Informationen der Datei. Dies ist. z.B der Lizenztext im Header
        einer Java-Datei.
        :param content: Inhalt der Datei
        :return: Verarbeiteter Inhalt der Datei
        """
        pass

    @abstractmethod
    def replace_dynamic_author_reference(self, content: str) -> str:
        """
        Ersetzt dynamische Referenzen auf den Autor im Quellcode durch den tatsächlichen Autor.
        :param content: Inhalt der Datei
        :return: Verarbeiteter Inhalt der Datei
        """
        pass

class JavaFile(SourceFileHandler):
    """
    Implementierung des SourceFileHandlers für Java-Dateien.
    """

    def __init__(self, file_path: str, initial_commit_date: datetime.date , initial_commiter: str, inital_ticket_number:str):
        # todo: Werte überschreiben, falls fix gesetzt und z.B. Datum < als Input-Datum
        super().__init__(file_path, initial_commit_date, initial_commiter, inital_ticket_number)
        self.license_text = """/*
                  * Licencsed blahblahblah
                  * Copyright (c) ${self.initial_commit_year} - ${self.current_year}
                  */
                  package"""
        self.purgable_license_text_pattern = '^.*package' # alles bis und mit package-Statement

    @staticmethod
    def can_handle_file(file_path: str) -> bool:
        return file_path.endswith('.java')

    def handle_legal_information(self, content):
        return re.sub(f'{self.purgable_license_text_pattern}' , self.license_text, content, flags=re.DOTALL)

    def replace_dynamic_author_reference(self, content):
        pass




def get_first_commit(file_path):
    """
    Diese Funktion ermittelt aus einem Git-Repository den ersten Commit für die übergebene Datei.
    Aus diesen Informationen wird das Datum des Commits, der Autor und die Ticketnummer ermittelt.
    :param file_path: Pfad zur Datei
    :return: Datum des ersten Commits, Autor des ersten Commits, Ticketnummer des ersten Commits
    """
    result = subprocess.run(
        ['git', 'log', '--diff-filter=A', '--format=%as|%ae|%s', '--', file_path],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )

    if result.returncode == 0 and result.stdout:
        first_commit = result.stdout.splitlines()[0]
        commit_date_value, commiter, commit_comment = first_commit.split('|', 1)
        if (len(commit_date_value) == 10):
            commit_date = datetime.date.fromisoformat(commit_date_value)
        else:
            commit_date = None
        match_ticket_number = re.search(r'WEB-\d{2-4}', commit_comment.upper())
        if match_ticket_number:
            initial_ticket_number = match_ticket_number.group(0)
        else:
            initial_ticket_number = None
        return commit_date, commiter, initial_ticket_number
    else:
        return None, None, None


# Beispielaufruf
file_path = 'pfad/zur/datei.txt'
commit_date, commiter, inital_ticket_number = get_first_commit(file_path)




if commit_hash:
    print(f"Erster Commit für {file_path}:")
    print(f"Commit-Hash: {commit_hash}")
    print(f"Datum: {commit_date}")
else:
    print(f"Keine Commits für {file_path} gefunden.")
# Instanziiere die Klasse
my_instance = MyClass()
my_instance.do_something()  # Output: Doing something!
my_instance.another_method(42)  # Output: Handling value: 42

# Versuch, eine Instanz der abstrakten Klasse zu erstellen (führt zu einem Fehler)
# my_interface_instance = MyInterface()  # Fehler: TypeError: Can't instantiate abstract class
