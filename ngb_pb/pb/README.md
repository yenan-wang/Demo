本工程中使用的protobuf版本是2.5.0
使用步骤：
1、在pb文件夹中编写pb文件
2、修改generate_single_pb_java.bat中，要生成的pb文件名，然后运行该bat命令，即可生成新的对应的java文件
3、generate_pb_java.bat是批量生成java文件，直接运行，会生成所有pb文件夹中的pb文件对应的java文件
4、如果你使用的protobuf版本不是2.5.0的，请到该网址下载对应版本的protoc.exe程序，并替换该目录下的protoc.exe
protoc.exe下载地址：https://repo1.maven.org/maven2/com/google/protobuf/protoc/