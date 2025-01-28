package main;

import database.*;

public class Main {
  public static void main(String[] args) {
    Database.connect();
    Database.createTables();
    MainStorage storage = new MainStorage();
    storage.loadUsers();
    MainController.launch(MainController.class, args);
  }
}
