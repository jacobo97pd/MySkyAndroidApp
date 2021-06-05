package com.example.myskyapp;

import android.util.Log;
import android.widget.Toast;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.util.Set;

public class ConexionMongoDB {
    /**public void main(String[] args){
        /**System.out.println("Probando conexion");
        MongoClient mongo = null;
        try{
            mongo = new MongoClient("localhost", 27017);
        }catch (UnknownError e){
            Log.i("MyApp","I am here");
            e.printStackTrace();
        }

        if(mongo != null){
            DB db = mongo.getDB("basedatos");

            System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        MongoClientURI mongoUri  = new MongoClientURI("mongodb://localhost:27017/mysky");
        MongoClient mongoClient = new MongoClient(mongoUri);
        DB db = mongoClient.getDB("mysky");
        Set<String> collectionNames = db.getCollectionNames();
    }**/
}
