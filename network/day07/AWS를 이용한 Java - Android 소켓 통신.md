# AWS를 이용한 Java - Android 소켓 통신



### 1. jar 파일 생성



<img width="331" alt="1" src="https://user-images.githubusercontent.com/36683607/74794472-a4eb1380-5306-11ea-93de-6e75d80e0c31.png">



<img width="410" alt="2" src="https://user-images.githubusercontent.com/36683607/74794478-a61c4080-5306-11ea-9039-e40071144651.PNG">


<img width="410" alt="3" src="https://user-images.githubusercontent.com/36683607/74794480-a6b4d700-5306-11ea-865b-5c3869c70c09.png">





### 2. 인스턴스 연결

```bash
$ ssh -i "AWS_KEY.pem" 퍼블릭 DNS(IPv4) //연결
$ scp -i "AWS_KEY.pem" Server.jar 퍼블릭 DNS(IPv4):~/ //jar 파일 복사
$ java -jar Server.jar //실행
```



### 3. 확인

```bash
[ec2-user@ip ~]$ java -jar Server.jar
Start Server
Server Ready..
접속자수:1
/ip
Server Ready..
--
id0101:hi
--
id0101:hi
--
id0101:hi
--
/ip:Exit ..
접속자수:0
```



![111](https://user-images.githubusercontent.com/36683607/74794483-a6b4d700-5306-11ea-9962-dcd00dae4fb5.jpg)
