package com.example.customerdbmigration.service.impl;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.customerdbmigration.model.Customer;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

//CRUD operations
@Service
public class CustomerServiceImpl
//implements CustomerService 
{

    public static final String collectionName="customers";

    public String saveCustomerDetails(Customer customer) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(customer.getUsername()).set(customer);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Customer getCustomerDetails(String username) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(collectionName).document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        Customer customer = null;

        if(document.exists()) {
            customer = document.toObject(Customer.class);
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