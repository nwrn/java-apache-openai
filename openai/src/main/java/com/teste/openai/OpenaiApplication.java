package com.teste.openai;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class OpenaiApplication {

    private static String KEY = "sk-";
    private static String PROMPT = "O que e o chat GPT?";
    private static long MAX_TOKENS = 100;
    private static float TEMPERATURE = 1;
    private static String MODEL = "text-davinci-003";


    public static void main(String[] args){

        try{
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost
                    ("https://api.openai.com/v1/completions");

            StringEntity entity = new StringEntity
                    ("{" +
                            "\"model\" : " +
                            "\"" +
                            MODEL +
                            "\"," +
                            "\"prompt\" : \"" +
                            PROMPT +
                            "\"," +
                            "\"max_tokens\" : " +
                            MAX_TOKENS +
                            "," +
                            "\"temperature\" : " +
                            TEMPERATURE +
                            "}");

            entity.setContentType("application/json");
            post.setHeader("Content-Type","application/json");
            post.setHeader("Authorization","Bearer "+KEY);
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            if(response.getStatusLine().getStatusCode() != 201)
                System.out.println("HTTP Error: "+response.getStatusLine().getStatusCode());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String output;
            System.out.println("\n\nGPT Resposta: \n");
            while ( (output = reader.readLine()) != null)
                System.out.println(output);

            client.getConnectionManager().shutdown();

        }catch (Exception exception){System.out.println( exception.getMessage() );}



    }

}
