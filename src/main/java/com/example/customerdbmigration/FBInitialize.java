package com.example.customerdbmigration;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FBInitialize {

	@PostConstruct
	public void initialize() {
		try {

			//FileInputStream serviceAccount = new FileInputStream("/serviceAccountKey.json");
			
			InputStream serviceAccount = FBInitialize.class.getResourceAsStream("/serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://customerdbmigration.firebaseio.com")
					.build();

			FirebaseApp.initializeApp(options);
			
			log.info("Firebase connection established successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}