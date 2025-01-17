### Build and Run

```
cd ~/Documents/Social_Media && javac -d bin -cp "lib/*" $(find src -name "*.java") && java --module-path lib --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "bin:res:lib/*" main/Main
```
#### CHECK THE DIRECTORY AND MAKE SURE JAVAFX LIB FOLDER IS IN CORRECT PLACE 

#### JAVA_PROJECT

├── bin

├── lib

├── res

└── src
