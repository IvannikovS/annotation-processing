package com.ivannikov;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnection {
    private static Driver driver;

    public static Driver getDriver() {
        if (driver == null) {
            driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic
                    ("neo4j", "12345678"));
        }
        return driver;
    }

    public static void close() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }
}
