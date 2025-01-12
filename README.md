### Build and Run

```
cd ~/Documents/JAVA_PROJECT &&\njavac -d bin -cp "lib/*" $(find src -name "*.java") &&\njava --module-path lib --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "bin:res:lib/*" main/Main
```
#### CHECK THE DIRECTORY AND MAKE SURE JAVAFX LIB FOLDER IS IN CORRECT PLACE 

#### JAVA_PROJECT

├── bin

├── lib

├── res

└── src
