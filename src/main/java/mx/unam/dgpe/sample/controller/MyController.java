package mx.unam.dgpe.sample.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MyController extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(MyController.class);
    private static String pba;
    
    public void start(Future<Void> fut) {
        logger.info("Inicializando Vertical");
        Router router = Router.router(vertx);
        //router.route("/*").handler(StaticHandler.create("assets")); // para invocar asi: http://localhost:8080/index.html
        // el directorio "upload-folder" será creado en la misma ubicación que el jar fue ejecutado
        router.route().handler(BodyHandler.create().setUploadsDirectory("upload-folder"));
        router.get("/api/primero").handler(this::primero);
router.get("/api/calculadora").handler(this::calculadora);
        router.post("/api/segundo").handler(this::segundo);
        
        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer().requestHandler(router::accept).listen(
                config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });        
	pba = System.getenv("PBA");        
        logger.info("Vertical iniciada !!!");
    }  
    private void primero(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String mode = request.getParam("mode");
        String jsonResponse = procesa(mode);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }
   private void calculadora(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String valor1    = request.getParam("valor1");
        String valor2    = request.getParam("valor2");
        String operacion = request.getParam("operacion");
        String jsonResponse = procesaCalc(operacion,valor1,valor2);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }
    
    private void segundo(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String decoded = routingContext.getBodyAsString();
        String jsonResponse = procesa(decoded);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }

    private String procesa(String decoded) {
        Map<String, String> autos = new HashMap<>();
        autos.put("primero", "Ferrari");
        autos.put("segundo", "Lamborgini");
        autos.put("tercero", "Bugatti");
        
        Map<Object, Object> info = new HashMap<>();
        info.put("decoded", decoded);
        info.put("nombre", "gustavo");
        info.put("edad", "21");
        info.put("autos", autos);
	info.put("variable",pba);
        return Json.encodePrettily(info);
    }

private String procesaCalc(String operacion,String valor1, String valor2) {
        int Resultado = 0;
        int xValor1 = Integer.parseInt(valor1);
        int xValor2 = Integer.parseInt(valor2);

        switch (operacion) {

         case "sumar":
           Resultado = xValor1 + xValor2;
	   break;
         case "restar":
           Resultado = xValor1 - xValor2;
	   break;
         case "multiplicar":
           Resultado = xValor1 * xValor2;
	   break;
         case "dividir":
           Resultado = xValor1 / xValor2;
           break;
         default :
           Resultado = 0;

        }
        
        String xResultado =  String.valueOf(Resultado);

        Map<Object, Object> info = new HashMap<>();
       
        info.put("Operacion",operacion);
        info.put("valor1", valor1);
        info.put("valor2", valor2);
        info.put("Resultado", xResultado);

        info.put("variable", pba);
      
        return Json.encodePrettily(info);
    }


}
