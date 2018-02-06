package HTTPClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class StartClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		while(true)
		{
		Socket socket = new Socket("127.0.0.1", 8888);
		PrintStream writer = new PrintStream(socket.getOutputStream());
	    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String fileName = "";
		System.out.println("请输入要访问的文件:");
		Scanner scanner = new Scanner(System.in);
		fileName = scanner.nextLine();
		/*------------------发送请求头------------------*/
		writer.println("GET /"+fileName+" HTTP/1.1");
		writer.println("Host:localhost");
		writer.println("connection:keep-alive");
		writer.println();
		writer.flush();
		/*------------------发送请求体-------------------*/
		//get请求
		if(true){
			;
		}
		writer.flush();
		/*------------------接收响应状态------------------*/
		String firstLineOfResponse = reader.readLine();//HTTP/1.1 200 OK
		String secondLineOfResponse = reader.readLine();//Content-Type:text/html
		String threeLineOfResponse = reader.readLine();//Content-Length:
		String fourLineOfResponse = reader.readLine();//blank line
		//读取响应数据，保存文件
		String statusCode = firstLineOfResponse.split(" ")[1];//利用分隔符得到响应码
	   
		
		/*------------------接收响应状态（响应成功）------------------*/
		if(statusCode.equals("200"))
		{ 
			String saveLocation = "D:/program files/JAVA/eclipse/workspace/HTTPClient/File";
		    System.out.println("是否修改文件名？   是：1   否：2");
		    String index = scanner.nextLine();
		    if(index.equals("1"))
		    {
			   System.out.println("新文件名为：");
			   fileName = scanner.nextLine();
		    }
		    else
		    {
			  ;
		    }
		    File file  = new File(saveLocation+"/"+fileName);
		    FileOutputStream out = new FileOutputStream(file);
		    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
			System.out.println(" 响应成功");
			byte[] b = new byte[1024];
			int len = in.read(b);
		    while(len!=-1)
		    {		    	   
			  out.write(b, 0, len);
			  len = in.read(b);
			 }
		    in.close();
		    out.close();
		}
		/*------------接收响应状态（响应失败）包括400,403,404,500,503----*/
		else if(statusCode.equals("400"))
		{
			StringBuffer result = new StringBuffer();
			String line = null ;
			while ((line = reader.readLine()) != null) {
			        result.append(line);
			}
			reader.close();
			System.out.println(result);
			System.out.println("客户端请求有语法错误");

		}
		else if(statusCode.equals("403"))
		{
			StringBuffer result = new StringBuffer();
			String line = null ;
			while ((line = reader.readLine()) != null) {
				    result.append(line);
			}
			reader.close();
			System.out.println(result);
			System.out.println("服务器收到请求，但拒绝提供服务");

		}
		else if(statusCode.equals("404"))
		{
			System.out.println(firstLineOfResponse);
			System.out.println(secondLineOfResponse);
			System.out.println(threeLineOfResponse);
			System.out.println(fourLineOfResponse);
			StringBuffer result = new StringBuffer();
			String line = null ;
			while ((line = reader.readLine()) != null) {
				   result.append(line);
			}
			reader.close();
			System.out.println(result);
			

		}
		else if(statusCode.equals("500"))
		{
			StringBuffer result = new StringBuffer();
			String line = null ;
			while ((line = reader.readLine()) != null) {
					result.append(line);
			}
			reader.close();
			System.out.println(result);
			System.out.println("服务器发生不可预期错误");

		}
		else if(statusCode.equals("503"))
		{
			StringBuffer result = new StringBuffer();
			String line = null ;
			while ((line = reader.readLine()) != null) {
					result.append(line);
			}
			reader.close();
			System.out.println(result);
			System.out.println("服务器当前不能处理客户端请求，但一段时间之后，服务器可能恢复正常");

		}
		
		socket.close();
		}



	}

}
