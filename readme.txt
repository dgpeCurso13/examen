  GNU nano 2.8.6                   File: readme.txt                   Modified  


// INSTRUCCIONES PARA CORRER SRVIDOR VERTX DESDE UN DOCKERFILE

a) desde la carpeta vertx-sample/target

b) crear archivo Dockerfile que contenga:
    FROM gustavoarellano/jdk18
    COPY sample-1.0-SNAPSHOT-fat.jar

c) docker build -t curso13/examen


//INSTRUCCIONES PARA BALANCEAR 6 CONTENEDORES IGUALES USANDO HAPROXY

PRIMERO LVANTO MIS 6 PUERTOS CON LA APLICACION

docker run -d -p 8081:8080 -e PBA=hola1 /home/gustavo/curso-dgp/vertx-sample/:/$
docker run -d -p 8082:8080 -e PBA=hola2 /home/gustavo/curso-dgp/vertx-sample/:/$
docker run -d -p 8083:8080 -e PBA=hola3 ..

^G Get Help  ^O Write Out ^W Where Is  ^K Cut Text  ^J Justify   ^C Cur Pos
^X Exit      ^R Read File ^\ Replace   ^U Uncut Text^T To Spell  ^_ Go To Line

