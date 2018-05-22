//import java.util.Map;
//
//import org.eclipse.jdt.core.JavaCore;
//import org.eclipse.jdt.core.dom.*;
//public class ASTTEST {
//
//    public static void main(String[] args) {
//        ASTParser parser = ASTParser.newParser(AST.JLS3); //设置Java语言规范版本
//        parser.setKind(ASTParser.K_COMPILATION_UNIT);
//
//        Map<String, String> compilerOptions = JavaCore.getOptions();
//        compilerOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_7); //设置Java语言版本
//        compilerOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_7);
//        compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
//        parser.setCompilerOptions(compilerOptions); //设置编译选项
//
//        char[] src = "class A { void method1(int b){;} }".toCharArray();
//        parser.setSource(src);
//
//
//        CompilationUnit cu = (CompilationUnit) parser.createAST(null); //这个参数是IProgessMonitor,用于GUI的进度显示,我们不需要，填个null. 返回值是AST的根结点
//
//        System.out.println(cu); //把AST直接输出看看啥样
//    }
//}
