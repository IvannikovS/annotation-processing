package com.ivannikov;

import com.google.auto.service.AutoService;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_19)
public class SpringComponentProcessor extends AbstractProcessor {
    private final ComponentDataStorage storage = new ComponentDataStorage();
    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = Trees.instance(processingEnv);
        Runtime.getRuntime().addShutdownHook(new Thread(storage::close));
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        handleAnnotationType(roundEnv, Controller.class, "Controller");
        handleAnnotationType(roundEnv, RestController.class, "RestController");
        handleAnnotationType(roundEnv, Service.class, "Service");
        handleAnnotationType(roundEnv, Repository.class, "Repository");
        handleAnnotationType(roundEnv, Component.class, "Component");
        handleAnnotationType(roundEnv, Configuration.class, "Configuration");

        // Дополнительная обработка для конфигураций CORS
        processCorsConfigurations(roundEnv);

        return true;
    }

    private <A extends Annotation> void handleAnnotationType(RoundEnvironment roundEnv, Class<A> annotation, String componentType) {
        for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
            ComponentInfo component = createComponentInfo(element, componentType);
            storage.saveComponentInfo(component);
        }
    }

    private void processCorsConfigurations(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Configuration.class)) {
            boolean isCorsConfig = false;
            String clientUrl = null;
            for (Element enclosedElement : element.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.METHOD) {
                    ExecutableElement method = (ExecutableElement) enclosedElement;
                    if (isCorsMethod(method)) {
                        isCorsConfig = true;
                        clientUrl = extractUrlFromMethod(method);
                        break;
                    }
                }
            }
            if (isCorsConfig) {
                if (clientUrl == null) {
                    clientUrl = "http://default-client-url"; // Используем дефолтное значение URL
                }
                ComponentInfo clientComponent = new ComponentInfo("Client", clientUrl, "Client");

                for (Element controllerElement : roundEnv.getElementsAnnotatedWith(Controller.class)) {
                    ComponentInfo controllerComponent = createComponentInfo(controllerElement, "Controller");
                    clientComponent.addDependentComponent(controllerComponent.getReference());
                    storage.saveComponentInfo(controllerComponent);
                }

                for (Element restControllerElement : roundEnv.getElementsAnnotatedWith(RestController.class)) {
                    ComponentInfo restControllerComponent = createComponentInfo(restControllerElement, "RestController");
                    clientComponent.addDependentComponent(restControllerComponent.getReference());
                    storage.saveComponentInfo(restControllerComponent);
                }

                storage.saveComponentInfo(clientComponent);
            }
        }
    }

    private boolean isCorsMethod(ExecutableElement method) {
        for (VariableElement parameter : method.getParameters()) {
            if (parameter.asType().toString().contains("CorsRegistry")) {
                return true;
            }
        }
        return false;
    }

    private String extractUrlFromMethod(ExecutableElement method) {
        MethodTree methodTree = trees.getTree(method);
        if (methodTree != null && methodTree.getBody() != null) {
            String body = methodTree.getBody().toString();
            Pattern pattern = Pattern.compile("allowedOrigins\\(\"([^\"]+)\"\\)");
            Matcher matcher = pattern.matcher(body);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    private ComponentInfo createComponentInfo(Element element, String componentType) {
        String fullName = element.asType().toString();
        String componentName = getInitials(fullName);
        ComponentInfo component = new ComponentInfo(componentName, fullName, componentType);
        for (Element enclosedElement : element.getEnclosedElements()) {
            analyzeDependencies(enclosedElement, component);
        }
        return component;
    }

    private void analyzeDependencies(Element enclosedElement, ComponentInfo component) {
        if (enclosedElement.getKind().isField() && enclosedElement.getAnnotation(Autowired.class) != null) {
            VariableElement variableElement = (VariableElement) enclosedElement;
            component.addDependency(variableElement.asType().toString());
        }

        if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR && enclosedElement.getAnnotation(Autowired.class) != null) {
            ExecutableElement executableElement = (ExecutableElement) enclosedElement;
            for (VariableElement parameter : executableElement.getParameters()) {
                component.addDependency(parameter.asType().toString());
            }
        }
    }

    private String getInitials(String fullName) {
        String[] parts = fullName.split("\\.");
        String className = parts[parts.length - 1];
        StringBuilder initials = new StringBuilder();
        for (char c : className.toCharArray()) {
            if (Character.isUpperCase(c)) {
                initials.append(c);
            }
        }
        return initials.toString();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                Controller.class.getName(),
                RestController.class.getName(),
                Repository.class.getName(),
                Service.class.getName(),
                Component.class.getName(),
                Configuration.class.getName());
    }
}
