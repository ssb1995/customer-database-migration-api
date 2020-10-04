package com.example.customerdbmigration.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.customerdbmigration.model.Customer;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import lombok.extern.slf4j.Slf4j;

//CRUD operations
@Service
@Slf4j
public class CustomerServiceImpl
//implements CustomerService 
{

    public static final String collectionName="customers";

    public String saveCustomerDetails(List<Customer> customer) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        for(Customer i : customer) {
        	ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(i.getUsername()).set(i);
        	log.info("Saved record with username -> "+i.getUsername());
        }
        //return collectionsApiFuture.get().getUpdateTime().toString();
        return "success";
    }

    public Customer getCustomerDetails(String username) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(collectionName).document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        Customer customer = null;

        if(document.exists()) {
            customer = document.toObject(Customer.class);
            log.info("Retrieved record with username -> "+username);
            return customer;
        }else {
            return null;
        }
    }
    
    public List<Customer> getCustomerDetails() throws InterruptedException, ExecutionException {
    	Firestore dbFirestore = FirestoreClient.getFirestore();
        //DocumentReference documentReference = dbFirestore.collection(collectionName).document();
        CollectionReference collectionReference = dbFirestore.collection(collectionName);
        ApiFuture<QuerySnapshot> future = collectionReference.get();

        QuerySnapshot temp = future.get();

        List<Customer> customer = null;

        if(temp != null) {
            customer = temp.toObjects(Customer.class);
            log.info("Retrieved all user details!");
            return customer;
        }else {
            return null;
        }
	}

    public String updateCustomerDetails(Customer person) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(person.getUsername()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteCustomer(String username) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(collectionName).document(username).delete();
        return "Document with Customer ID "+username+" has been deleted";
    }

}