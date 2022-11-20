::使用此脚本命令生成java文件
@echo off
::目标文件夹
set destDir=.
set tarDir=..\src\main\java

if "%1"=="" (
    :: 要过滤的文件后缀
    set destExt=*.proto
    set targetFile=%destDir%\%destExt%
    goto work
)

set targetFile=%1

:work
for /f "delims=" %%i   in ('dir /b/a-d %targetFile%')  do (
    protoc.exe --java_out=%tarDir% %%i
)
goto end

:end

echo generate over.
@echo on