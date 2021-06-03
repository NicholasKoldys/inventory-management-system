
If you are just interested in running the project quickly, you can use the following command in your cli to run the application..
```
cd "inventory-management-system"
```
```
mvn clean javafx:run
```

To build the application, simply run the following commands..
```
cd "inventory-management-system"
```
```
mvn clean package
```
> *replacing <version#> with the appropriate project version*
```
java -jar .
\target\"inventory-management-system-<version#>.jar"
```

* If you have the appropriate JRE installed (JRE 15), you can run it like an ordinary executable !

* Or if you locate the ```"target\inventorymanagementsystem\'inventory management system'"``` directory
    you can run the preferable executable script (*bat* or *bash*).

    * If you don't see the directory, you can run the following command..

        ```
        mvn clean javafx:jlink
        ```