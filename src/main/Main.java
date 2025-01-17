
package main;

import java.util.HashMap;
import java.util.Map;

import database.Database;
import model.*;

public class Main {
  public static Map<String, User> users= new HashMap<>();

  public static void main(String[] args) {
    Database.connect();
    Database.createTables();
    MainStorage storage = new MainStorage();
    storage.loadUsers();
    MainController.launch(MainController.class, args);
  }
}
