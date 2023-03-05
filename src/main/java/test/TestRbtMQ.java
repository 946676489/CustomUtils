package test;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TestRbtMQ {
	public final static String QUEUE_NAME = "hello";
	public static void main(String[] argv) throws Exception {
		new Sent().start();
		new Recv().start();
	}
}
class Sent extends Thread {
	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		String hello = "Hello World!";
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(TestRbtMQ.QUEUE_NAME, false, false, false, null);
			while (true) {
				String msg = hello + System.currentTimeMillis();
				channel.basicPublish("", TestRbtMQ.QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));
				System.out.println(" [x] Sent '" + msg + "'");

				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class Recv extends Thread {
	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(TestRbtMQ.QUEUE_NAME, false, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				System.out.println(" [x] Received '" + message + "'");
			};
			channel.basicConsume(TestRbtMQ.QUEUE_NAME, true, deliverCallback, consumerTag -> {
			});
			while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}