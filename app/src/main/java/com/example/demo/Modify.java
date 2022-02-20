
import com.example.demo.DemoClassVisitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Modify {

    public static void main(String[] args) {
        deal();
    }

    private static void deal() {
        String classFilePath = "D:/Hello.class";
        File file = new File(classFilePath);
        if (file.exists()) {
            try {
                System.out.println("begin visit class!");
                //读取class文件
                ClassReader classReader = new ClassReader(new FileInputStream(file));
                //创建ClassWriter
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                //自定义ClassVisitor
                ClassVisitor demoVisitor = new DemoClassVisitor(Opcodes.ASM9, classWriter);
                //关联ClassReader和visitor
                classReader.accept(demoVisitor, ClassReader.EXPAND_FRAMES);
                //写回修改后的class文件
                FileOutputStream fileOutputStream = new FileOutputStream(classFilePath);
                fileOutputStream.write(classWriter.toByteArray());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
