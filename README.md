### Build and Run

```
cd ~/Documents/Social-Media && javac -d bin -cp "lib/*" $(find src -name "*.java") && java --module-path lib --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "bin:res:lib/*" main/Main
```
#### Project Structure

#### Social-Media

├── bin

├── lib

├── res

└── src
