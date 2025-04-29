package br.com.nearx.ms_objectstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsObjectstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsObjectstoreApplication.class, args);
	}

}
