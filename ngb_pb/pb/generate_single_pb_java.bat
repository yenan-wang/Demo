::使用此脚本命令生成java文件
@echo off
::目标文件夹
set destDir=.
set tarDir=..\src\main\java

::这里修改你要生成java文件的pb名字
::protoc.exe --java_out=..\src\main\java ./pb文件名.proto
protoc.exe --java_out=..\src\main\java ./pb_demo.proto

goto end

:end

echo generate over.
@echo on

pause