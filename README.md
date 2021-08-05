# MovieRecSys

MovieRecSys is a movie recommendation system, which applies online and offline storage technology to display an industrial data streaming demo.

# Technology
Java, Jetty, Redis, Spark, JavaScript

# Get started
Go to the root folder and execute the following commands using your favourite command line.
```
mvn package
docker build -t movie .
docker run -it --name movie -p 6010:6010 movie
```
Open your favourite browser and go to `127.0.0.1:6010`.
