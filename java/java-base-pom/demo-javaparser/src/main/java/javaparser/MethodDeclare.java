package javaparser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MethodDeclare {

    public static JSONArray declareParseCode(String code) {
        JSONArray declareJsonArray = new JSONArray();
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);
        //解析Java源代码并创建抽象语法树。
        //解析源代码。它从提供程序获取源代码。开头指示可以在源代码中找到的内容（编译单元，块，导入...）
        try {
            TypeDeclaration declaration = compilationUnit.getType(0);
            //返回在此编译单元中声明的类型的列表。
            List<BodyDeclaration> list = declaration.getMembers();
            //获取这个类里面的成员
            for (BodyDeclaration bodyDeclaration : list) {
                //枚举成员
                Map<String, String> declareMap = new HashMap<>();
                if (bodyDeclaration.isMethodDeclaration()) {
                    //判断是否为方法
                    MethodDeclaration declareParse = (MethodDeclaration) bodyDeclaration;
                    declareMap.put("name", declareParse.getDeclarationAsString());
                    //获取方法名
                    declareMap.put("body", declareParse.getBody().toString());
                    //获取方法body
                }
                JSONObject declareJson = JSONObject.parseObject(JSON.toJSONString(declareMap));
                //解析成字符串
                declareJsonArray.add(declareJson);
            }
            compilationUnit.accept(new VoidVisitorAdapter<Void>() {
                //不返回任何内容的访问者，其所有访问方法都有一个默认实现，该实现只是以未指定的顺序访问其子方法
                @Override
                public void visit(MethodDeclaration n, Void arg) {
                    super.visit(n, arg);
                }
            }, null);
        } catch (Exception e) {
            System.out.println(e);
        }
        return declareJsonArray;
    }

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("/Users/albert/IdeaProjects/java-world");
        final Path filePath = Files.walk(path).filter(p -> p.endsWith("JsonHelper.java")).findFirst().get();
        final String context = Files.lines(filePath).collect(Collectors.joining("\n"));
        final JSONArray json = MethodDeclare.declareParseCode(context);
        System.out.println(json.toString(SerializerFeature.PrettyFormat));
    }

}
