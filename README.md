# crypto-wallet-java
CRYPTO WALLET PERFORMANCE

### About 

   Java program that given a collection of crypto assets with their positions, it must retrieve, concurrently, their latest prices from the Coincap API and return the updated total financial value of the wallet with performance data.
1. Input
   CSV file representing the wallet with columns symbol, quantity, price

  You can pass the full path of your csv file or run it without passing a parameter

2. Output
   Print a line with total={},best_asset={},best_performance={},worst_asset={},worst_performance= {}

### Install

You'll need:

- Java 17
- Maven 3.0.5 or later

Run Maven, specifying a location into which the completed Maven distro should be installed:

```
mvn clean install
```

### Usage
Locate jar file and execute

Execution without parameter
```
java -jar target/cripto-wallet-java-0.0.1-SNAPSHOT.jar 
```

Execution with parameter
```
java -jar target/cripto-wallet-java-0.0.1-SNAPSHOT.jar FULL_PATH_CSV
```
