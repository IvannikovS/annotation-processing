package com.ivannikov.demo;

public class Test {

    //    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (Session session = driver.session()) {
//            session.writeTransaction(tx -> {
//                // Убедитесь, что основной компонент существует или создан
//                tx.run("MERGE (c:Component {name: $name, type: $type})",
//                        Values.parameters("name", componentInfo.getComponentName(), "type", componentInfo.getComponentType()));
//
//                for (String dependency : componentInfo.getDependencies()) {
//                    // Создание или подтверждение существования зависимого компонента
//                    tx.run("MERGE (d:Component {name: $dependency})",
//                            Values.parameters("dependency", dependency));
//                    // Создание связи между основным компонентом и его зависимостями
//                    tx.run("MATCH (c:Component {name: $mainName}), (d:Component {name: $dependencyName}) " +
//                                    "MERGE (c)-[:DEPENDS_ON]->(d)",
//                            Values.parameters("mainName", componentInfo.getComponentName(), "dependencyName", dependency));
//                }
//                return null;
//            });
//        }
//    }

//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (Session session = driver.session()) {
//            session.writeTransaction(tx -> {
//                // Сначала обеспечиваем, что узел компонента существует
//                tx.run("MERGE (c:Component {name: $name}) ON CREATE SET c.type = $type",
//                        Values.parameters("name", componentInfo.getComponentName(), "type", componentInfo.getComponentType()));
//
//                // Теперь для каждой зависимости
//                for (String dependency : componentInfo.getDependencies()) {
//                    // Убеждаемся, что каждая зависимость существует как узел
//                    tx.run("MERGE (d:Component {name: $dependency})",
//                            Values.parameters("dependency", dependency));
//                    // Создаем связь от зависимости к компоненту
//                    tx.run("MATCH (c:Component {name: $componentName}), (d:Component {name: $dependencyName}) " +
//                                    "MERGE (d)-[:DEPENDS_ON]->(c)",
//                            Values.parameters("componentName", componentInfo.getComponentName(), "dependencyName", dependency));
//                }
//                return null;
//            });
//        }
//    }

//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (Session session = driver.session()) {
//            session.writeTransaction(tx -> {
//                // Сначала обеспечиваем, что узел компонента существует, и обновляем его тип
//                tx.run("MERGE (c:Component {name: $name}) SET c.type = $type",
//                        Values.parameters("name", componentInfo.getComponentName(), "type", componentInfo.getComponentType()));
//
//                // Теперь для каждой зависимости
//                for (String dependency : componentInfo.getDependencies()) {
//                    // Убеждаемся, что каждая зависимость существует как узел
//                    tx.run("MERGE (d:Component {name: $dependency})",
//                            Values.parameters("dependency", dependency));
//                    // Создаем связь от зависимости к компоненту
//                    tx.run("MATCH (c:Component {name: $componentName}), (d:Component {name: $dependencyName}) " +
//                                    "MERGE (d)-[:DEPENDS_ON]->(c)",
//                            Values.parameters("componentName", componentInfo.getComponentName(), "dependencyName", dependency));
//                }
//                return null;
//            });
//        }
//    }

    // Метод для сохранения информации о компоненте в файл
//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (FileWriter fileWriter = new FileWriter("components.txt", true);
//             PrintWriter printWriter = new PrintWriter(fileWriter)) {
//            // Форматируем строку с информацией о компоненте
//            String componentInfoLine = String.format("Component Name: %s, Component Type: %s",
//                    componentInfo.getComponentName(),
//                    componentInfo.getComponentType());
//            // Записываем информацию о компоненте в файл
//            printWriter.println(componentInfoLine);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (FileWriter fileWriter = new FileWriter("components.txt", true);
//             PrintWriter printWriter = new PrintWriter(fileWriter)) {
//            printWriter.println("Component: " + componentInfo.getComponentName() + ", Type: " + componentInfo.getComponentType());
//            for (String dependency : componentInfo.getDependencies()) {
//                printWriter.println("  Dependency: " + dependency);
//            }
//            printWriter.println();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (FileWriter fileWriter = new FileWriter("components.txt", true);
//             PrintWriter printWriter = new PrintWriter(fileWriter)) {
//            printWriter.printf("Component: %s [%s]\n", componentInfo.getComponentName(), componentInfo.getComponentType());
//            if (!componentInfo.getDependencies().isEmpty()) {
//                printWriter.println("  Dependencies:");
//                for (String dependency : componentInfo.getDependencies()) {
//                    printWriter.printf("    - %s\n", dependency);
//                }
//            } else {
//                printWriter.println("  No dependencies");
//            }
//            printWriter.println();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (Session session = Neo4jConnection.getDriver().session()) {
//            String createComponentQuery = "MERGE (c:Component {name: $name, type: $type}) RETURN c";
//            session.run(createComponentQuery, Values.parameters("name", componentInfo.getComponentName(), "type", componentInfo.getComponentType()));
//
//            for (String dependency : componentInfo.getDependencies()) {
//                String linkDependencyQuery = "MATCH (c:Component {name: $name}), (d:Component {name: $dependency}) MERGE (c)-[:DEPENDS_ON]->(d)";
//                session.run(linkDependencyQuery, Values.parameters("name", componentInfo.getComponentName(), "dependency", dependency));
//            }
//        }
//    }

//    @Override
//    public void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        Runtime.getRuntime().addShutdownHook(new Thread(Neo4jConnection::close));
//    }


//    public void saveComponentInfo(ComponentInfo componentInfo) {
//        try (Session session = driver.session()) {
//            session.writeTransaction(tx -> {
//                String componentName = componentInfo.getComponentName();
//                String componentType = componentInfo.getComponentType();
//
//                // Динамически формируем запрос на основе типа компонента
//                String label = componentType; // Здесь можно реализовать логику проверки названия лейбла
//                String query = String.format(
//                        "MERGE (c:Component {name: $name}) " +
//                                "ON CREATE SET c.type = $type " +
//                                "ON MATCH SET c.type = $type " +
//                                "SET c:%1$s " + // Добавляем лейбл без использования REMOVE для избежания удаления других возможных лейблов
//                                "RETURN c", label);
//
//                tx.run(query, Values.parameters("name", componentName, "type", componentType));
//
//                // Обработка зависимостей
//                for (String dependency : componentInfo.getDependencies()) {
//                    tx.run("MERGE (d:Component {name: $dependency})",
//                            Values.parameters("dependency", dependency));
//                    tx.run("MATCH (c:Component {name: $componentName}), (d:Component {name: $dependencyName}) " +
//                                    "MERGE (d)-[:DEPENDS_ON]->(c)",
//                            Values.parameters("componentName", componentName, "dependencyName", dependency));
//                }
//                return null;
//            });
//        }
//    }

    //    private ComponentInfo createComponentInfo(Element element, String componentType) {
//        ComponentInfo component = new ComponentInfo(element.asType().toString(), componentType);
//        for (Element enclosedElement : element.getEnclosedElements()) {
//            analyzeDependencies(enclosedElement, component);
//        }
//        return component;
//    }
}
