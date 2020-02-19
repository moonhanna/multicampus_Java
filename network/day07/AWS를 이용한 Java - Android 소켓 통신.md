# AWS를 이용한 Java - Android 소켓 통신



### 1. jar 파일 생성



<img src=".\img\1.png" style="zoom: 67%;" />



<img src=".\img\2.PNG" style="zoom:80%;" />



<img src=".\img\3.png" style="zoom:80%;" />





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



<img src=".\img\111.jpg" style="zoom:50%;" />