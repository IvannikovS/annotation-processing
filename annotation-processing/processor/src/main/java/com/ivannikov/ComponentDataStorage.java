package com.ivannikov;

import org.neo4j.driver.*;

public class ComponentDataStorage {
    private Driver driver;

    public ComponentDataStorage() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
    }

    public void saveComponentInfo(ComponentInfo componentInfo) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                String componentName = componentInfo.getComponentName();
                String componentType = componentInfo.getComponentType();
                String reference = componentInfo.getReference();

                // Динамическое формирование запроса на основе типа компонента
                String label = componentType != null && !componentType.isEmpty() ? componentType : "Component";
                String query = String.format(
                        "MERGE (c:Component {reference: $reference}) " +
                                "ON CREATE SET c.name = $name, c.type = $type " +
                                "ON MATCH SET c.name = $name, c.type = $type " +
                                "SET c:%1$s " + // Добавляем лейбл без удаления других возможных лейблов
                                "RETURN c", label);

                tx.run(query, Values.parameters("name", componentName, "type", componentType, "reference", reference));

                // Обработка зависимостей
                if (!"Client".equals(componentType)) {
                    for (String dependency : componentInfo.getDependencies()) {
                        tx.run("MERGE (d:Component {reference: $dependency})",
                                Values.parameters("dependency", dependency));
                        tx.run("MATCH (c:Component {reference: $componentReference}), (d:Component {reference: $dependencyReference}) " +
                                        "MERGE (c)-[:DEPENDS_ON]->(d)",
                                Values.parameters("componentReference", reference, "dependencyReference", dependency));
                    }
                }

                // Обработка взаимодействий с клиентом
                if ("Client".equals(componentType)) {
                    for (String dependentComponent : componentInfo.getDependentComponents()) {
                        tx.run("MERGE (d:Component {reference: $dependentComponent})",
                                Values.parameters("dependentComponent", dependentComponent));
                        tx.run("MATCH (c:Component {reference: $componentReference}), (d:Component {reference: $dependentComponentReference}) " +
                                        "MERGE (d)-[:INTERACTS_WITH]->(c)",
                                Values.parameters("componentReference", reference, "dependentComponentReference", dependentComponent));
                    }
                }

                return null;
            });
        }
    }

    public void close() {
        if (driver != null) {
            driver.close();
        }
    }
}
