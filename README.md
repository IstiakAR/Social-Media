### Build and Run
Navigate to Social-Media Directory and ...
```
javac -d bin -cp "lib/*" $(find src -name "*.java") && java --module-path lib --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "bin:res:lib/*" main/Main
```
#### Project Structure

#### Social-Media

├── bin

├── lib

├── res

└── src
