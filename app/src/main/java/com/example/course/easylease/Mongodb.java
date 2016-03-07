package com.example.course.easylease;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import java.util.*;
import android.util.Log;
/**
 * Created by yubin on 3/7/16.
 */
public class Mongodb {
    private MongoClient connection;
    private MongoDatabase db;
    public Mongodb(){
        MongoClientURI uri = new MongoClientURI("mongodb://jjshao:jj5914@ds045531.mlab.com:45531/ybhe");
        connection= new MongoClient(uri);
        db=connection.getDatabase("ybhe");
        Log.d("Messgae by YB","Connection success");
    }
    public boolean queryPassword(String username, String password){
        FindIterable<Document> query=db.getCollection("androidUser").find(new Document("username",username).
                append("password",password));
        if(query==null){
            return false;
        }else{
            return true;
        }
    }
    public List<String> createNewUser(final String username, final String password,String email,String phoneNum){
        List<String> message = new ArrayList<String>();
        FindIterable<Document> iterable= db.getCollection("androidUser").find();
        boolean test=true;
        Iterator<Document> it=iterable.iterator();
        while(it.hasNext()){
            Document single = it.next();
            if(single.containsValue(password)||single.containsValue(username)){
                test=false;
            }
        }
        //Document registeredUser=db.getCollection("androidUser").find(eq("username",username)).first();
        //Document registeredEmail=db.getCollection("androidUser").find(eq("password",password)).first();
        //Log.d("Messaage by YBHE: ",registeredEmail+"");
        if(test){
            db.getCollection("androidUser").insertOne(new Document("username",username).append("password",password)
                        .append("email",email).append("phoneNum",phoneNum));
            message.add("Successfully Created a new user");
            Log.d("message::","success");
        }else{

        }
        return message;
    }
    public void closeConn(){
        this.connection.close();
    }
}
