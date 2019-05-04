

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//===================================== 
// 父親是一個執行緒類別. 
// 改寫父親的run()方法.
//=====================================  
public class MyThread extends Thread{ 
    private String address;
    private String filename;
    
    //------------------------------
    // 建構元(1)
    //------------------------------ 
    public MyThread(String address, String filename){
        super();
        this.address = address;
        this.filename = filename;
    }

    //------------------------------
    // 建構元(2)
    //------------------------------ 
    public MyThread(){
        super();
    }
    
    //==============================  
    // 改寫父親的run()方法
    //==============================      
    @Override
    public void run(){
        try{ 
            //建立一個檔案輸出連線
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)));
            
            //建立一個http連線
            HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
            
            //取得連線狀態
            int code = connection.getResponseCode();
            
            //如果連線狀態OK
            if (code == HttpURLConnection.HTTP_OK){ 
                //建立一個連線的輸入串流
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
                //逐行讀入資料
                String data;
                boolean firstLine = true; 
                
                while((data = br.readLine()) != null){ 
                    System.out.println(data);
                    
                    // 將內容寫出檔案
                    if(firstLine){
                        bw.write(data);
                        firstLine=false;
                    }else{
                        bw.write(("\n"));
                        bw.write(data);                
                    } 
                }
                
                //關閉輸出入串流
                br.close();
                bw.close();
            }else{
                System.out.println("無法連結網站");
            }
        }catch(MalformedURLException e ){  
            System.out.println("網址錯誤");
        }catch(IOException e){
            System.out.println("讀寫錯誤");
        }
    }
    //==============================       
}