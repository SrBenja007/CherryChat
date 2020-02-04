package me.yushust.cherrychat.storage;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.RequiredArgsConstructor;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

import java.util.Collections;

@RequiredArgsConstructor
public class MongoConnectionManager {

    private final String hostName;
    private final int port;
    private final String userName;
    private final String password;
    private final String database;

    private MongoClient mongoClient;
    private Morphia morphia;

    private Datastore datastore;

    public void connect() {
        ServerAddress address = new ServerAddress(hostName, port);
        if(userName == null || userName.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            mongoClient = new MongoClient(address);
        } else {
            mongoClient = new MongoClient(address, Collections.singletonList(MongoCredential.createCredential(userName, database, password.toCharArray())));
        }

        this.morphia = new Morphia();
        morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {

            @Override
            protected ClassLoader getClassLoaderForClass() {
                return MongoConnectionManager.this.getClass().getClassLoader();
            }
        });
    }

    public Datastore getDatastore() {
        if(datastore == null) {
            datastore = this.morphia.createDatastore(mongoClient, database);
        }
        return datastore;
    }

}
