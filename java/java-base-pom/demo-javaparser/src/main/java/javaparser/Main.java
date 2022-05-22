package javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("/Users/albert/IdeaProjects/java-world");
        final Path filePath = Files.walk(path).filter(p -> p.endsWith("TimePrinter.java")).findFirst().get();
        final String context = Files.lines(filePath).collect(Collectors.joining("\n"));
        final CompilationUnit unit = StaticJavaParser.parse(context);
        System.out.println(unit);
    }
}
