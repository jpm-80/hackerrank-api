package com.hackerrank.api.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hackerrank/v1")
public class ApiHackerRankController {
	private static final String uriHackerRank = "https://jsonmock.hackerrank.com/api/countries";
	
	@GetMapping
	public String validarPaises(@RequestParam String country, @RequestParam String phone) throws IOException, InterruptedException {
//		CONSUMIR UN API SOLO CON JAVA 17
//		Afghanistan, Albania, Algeria, Andorra, Angola, Antarctica
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uriHackerRank + "?name=" + country))
				.GET()
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
//		OBTENER DATA DEL JSON OBTENIDO
//		String salida = returnDataFromMatcher(response.body());
//		String salida = returnDataFromStringMethods(response.body());
		String salida = returnDataFromScanner(response.body());
		
		return salida.concat(phone);
	}
	
	public String returnDataFromMatcher(String response) {
		String salida = "";
		Matcher m = Pattern.compile("callingCodes.*?\\[(.*?)\\]").matcher(response);
        if(m.find()) {
        	String[] codes = m.group(1).replace("\"", "").split(",");
            for (String code : codes) {
            	salida = "+" + code.trim();
            }
        }
        return salida;
	}
	public String returnDataFromStringMethods(String response) {
		String key = "\"callingCodes\"";
		String salida = "";
		int start = response.indexOf(key);
		if (start != -1) {
		    start = response.indexOf("[", start) + 1;
		    int end = response.indexOf("]", start);
		    String[] codes = response.substring(start, end).replace("\"", "").split(",");
		    for (String code : codes) {
		    	salida = "+" + code.trim();
		    }
		}
		return salida;
	}
	
	public String returnDataFromScanner(String response) {
		String salida = "";
		try (Scanner sc = new Scanner(response)) {
		    sc.useDelimiter("\"callingCodes\"");
		    sc.next(); // saltar hasta el campo
		    String after = sc.next();
		    int start = after.indexOf("[") + 1;
		    int end = after.indexOf("]");
		    String[] codes = after.substring(start, end).replace("\"", "").split(",");
		    for (String code : codes) {
		    	salida = "+" + code.trim();
		    }
		}
		return salida;
	}
}
