
To avoid building the applications jar or runnables, you can use the following command in your cli to run the application..
```
mvn clean javafx:run
```

To build the application, simply run the following commands..
```
mvn clean package
```
```
java -jar .
\target\"inventory-management-system-<version#>.jar"
```

* If you have the appropriate JRE installed (JRE 15), you can run it like an ordinary executable !

* Or if you locate the ```"target\inventorymanagementsystem\'inventory management system'"``` directory
    you can run the preferable executable script (*bat* or *bash*).

    * If you don't see the directory, you can run this..

        ```
        mvn clean javafx:jlink
        ```