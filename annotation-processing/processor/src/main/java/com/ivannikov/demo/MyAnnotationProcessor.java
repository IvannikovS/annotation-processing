package com.ivannikov.demo;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_19)
@SupportedAnnotationTypes("com.ivannikov.demo.MyAnnotation")
public class MyAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        System.out.println("Процессор запущен");
//        for (TypeElement annotation : annotations) {
//            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
//                String className = element.getSimpleName().toString();
//                System.out.println("Обработка класса: " + className);
//                try (BufferedWriter writer = new BufferedWriter(
//                        new FileWriter("annotated_classes.txt", true))) {
//                    writer.write(className);
//                    writer.newLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return true;
//    }
}
