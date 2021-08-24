package cn.baizhi;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class TestRabbitMQ {
    @Test
    public void testp() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.12.133");
        factory.setPort(5672);
        factory.setUsername("user");
        factory.setPassword("user");
        factory.setVirtualHost("user");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("user", false, false, false, null);
        channel.basicPublish("", "user", null, ("你是大傻逼！！！  ===> " + new Date()).getBytes());

//        channel.basicConsume("user", true, new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                java.lang.String message = new String(body);
//                System.out.println("消费者获取消息： " + message + new Date());
//            }
//        });

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws Exception {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置rabbitmq服务器IP地址
        factory.setHost("192.168.12.133");
        //设置rabbitmq服务器连接端口
        factory.setPort(5672);
        //设置rabbitmq服务器虚拟主机
        factory.setVirtualHost("user");
        //设置rabbitmq服务器用户名
        factory.setUsername("user");
        //设置rabbitmq服务器密码
        factory.setPassword("user");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("user", false, false, false, null);
        channel.basicConsume("user", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                java.lang.String message = new String(body);
                System.out.println("消费者获取消息： " + message);
            }
        });
    }

}

class TT {
    public static void main(String[] args) throws Exception {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置rabbitmq服务器IP地址
        factory.setHost("192.168.12.133");
        //设置rabbitmq服务器连接端口
        factory.setPort(5672);
        //设置rabbitmq服务器虚拟主机
        factory.setVirtualHost("user");
        //设置rabbitmq服务器用户名
        factory.setUsername("user");
        //设置rabbitmq服务器密码
        factory.setPassword("user");

        //获取连接
        Connection connection = factory.newConnection();

        //获取通道
        Channel channel = connection.createChannel();

        /**设置队列参数
         * @param queue 队列名称  如果这个队列不存在，将会被创建
         * @param durable 持久性：用来定义队列是否要持久化  true:持久化  false:不持久化
         * @param exclusive 是否只能由创建者使用，其他连接不能使用。 true:独占队列  false:不独占队列
         * @param autoDelete 是否自动删除（没有连接自动删除） true:自动删除   false:不自动删除
         * @param arguments 队列的其他属性(构造参数)
         */
        channel.queueDeclare("user", false, false, true, null);

        /**消费者消费消息
         * @param queue 队列名称
         * @param autoAck 是否自动应答。false表示consumer在成功消费过后必须要手动回复一下服务器，如果不回复，服务器就将认为此条消息消费失败，继续分发给其他consumer。
         * @param callback 回调方法类，一般为自己的Consumer类
         */
        channel.basicConsume("user", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                java.lang.String message = new String(body);
                System.out.println("消费者获取消息： " + message);
            }
        });
    }
}