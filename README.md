### Build and Run
Navigate to Social-Media Directory and ...
```
javac -d bin -cp "lib/*" $(find src -name "*.java") && java --module-path lib --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "bin:res:lib/*" main/Main
```
This command line should work on linux & mac. For Windows try with WSL. 
If WSL isn't available then, replace ':' with ';' and .so files in lib folder with .dll files.

Or, download the zip file containing jar from [here](https://github.com/IstiakAR/Social-Media/releases/tag/Release), extract it and run it using...

```
java -jar SocialMediaApp.jar
```

#### Project Structure

#### Social-Media

├── bin/

├── lib/

├── res/

├── src/

└── social-media.db
