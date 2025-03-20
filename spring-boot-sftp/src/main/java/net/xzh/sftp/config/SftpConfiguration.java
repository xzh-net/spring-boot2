package net.xzh.sftp.config;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.jcraft.jsch.ChannelSftp;

@Configuration
@EnableConfigurationProperties(SftpProperties.class)
public class SftpConfiguration {

	@Resource
	private SftpProperties properties;

	@Bean
	public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
		factory.setHost(properties.getHost());
		factory.setPort(properties.getPort());
		factory.setUser(properties.getUsername());
		if (StringUtils.isNotEmpty(properties.getPrivateKey())) {
			factory.setPrivateKey(new ClassPathResource(properties.getPrivateKey()));
			factory.setPrivateKeyPassphrase(properties.getPassphrase());
		} else {
			factory.setPassword(properties.getPassword());
		}
		factory.setAllowUnknownKeys(true);
		CachingSessionFactory<ChannelSftp.LsEntry> cachingSessionFactory = new CachingSessionFactory<>(factory);
		cachingSessionFactory.setPoolSize(10);
		cachingSessionFactory.setSessionWaitTimeout(10000);
		return cachingSessionFactory;
	}

	@Bean
	public SftpRemoteFileTemplate remoteFileTemplate(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		return new SftpRemoteFileTemplate(sftpSessionFactory);
	}

	/**
	 * 从服务器同步文件到本地
	 * 
	 * @param sftpSessionFactory
	 * @return
	 */
	@Bean
	public SftpInboundFileSynchronizer sftpInboundFileSynchronizer(
			SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		SftpInboundFileSynchronizer fileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory);
		fileSynchronizer.setDeleteRemoteFiles(false);
		fileSynchronizer.setRemoteDirectory(properties.getRemoteDir());
		fileSynchronizer.setFilter(new SftpSimplePatternFileListFilter("*.*"));
		return fileSynchronizer;
	}

	/**
	 * 每5s将远程文件与本地文件比较同步 入站通道适配器。可同步远程目录到本地，且可监听远程文件的操作
	 * 
	 * @param sftpInboundFileSynchronizer
	 * @return
	 */
	@Bean
	@InboundChannelAdapter(channel = "fileSynchronizerChannel", poller = @Poller(cron = "0/5 * * * * *", maxMessagesPerPoll = "1"))
	public MessageSource<File> sftpMessageSource(SftpInboundFileSynchronizer sftpInboundFileSynchronizer) {
		SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(
				sftpInboundFileSynchronizer);
		source.setLocalDirectory(new File(properties.getLocalDir()));
		source.setAutoCreateLocalDirectory(true);
		source.setLocalFilter(new AcceptOnceFileListFilter<>());
		source.setMaxFetchSize(-1);
		return source;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	@ServiceActivator(inputChannel = "fileSynchronizerChannel")
	public MessageHandler handler() {
		// 同步时打印文件信息
		return (m) -> {
			System.out.println(m.getPayload());
			m.getHeaders().forEach((key, value) -> System.out.println("\t\t|---" + key + "=" + value));
		};
	}

	@Bean
	@ServiceActivator(inputChannel = "lsChannel")
	public MessageHandler lsHandler() {
		SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory(), "ls", "payload");
		sftpOutboundGateway.setOptions("-dirs"); // 配置项
		return sftpOutboundGateway;
	}

	@Bean
	@ServiceActivator(inputChannel = "nlstChannel")
	public MessageHandler listFileNamesHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "nlst", "payload");
		return sftpOutboundGateway;
	}

	@Bean
	@ServiceActivator(inputChannel = "getChannel")
	public MessageHandler getFileHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "get", "payload");
		sftpOutboundGateway.setOptions("-R");
		sftpOutboundGateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
		sftpOutboundGateway.setLocalDirectory(new File(properties.getLocalDir()));
		sftpOutboundGateway.setAutoCreateLocalDirectory(true);
		return sftpOutboundGateway;
	}

	@Bean
	@ServiceActivator(inputChannel = "toPathChannel")
	public MessageHandler pathHandler() {
		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
		// automatically create the remote directory
		handler.setAutoCreateDirectory(true);
		handler.setRemoteDirectoryExpression(new SpelExpressionParser().parseExpression("headers[path]"));
		handler.setFileNameGenerator(new FileNameGenerator() {
			@Override
			public String generateFileName(Message<?> message) {
				return (String) message.getHeaders().get("filename");
			}
		});
		return handler;
	}

	@Bean
	@ServiceActivator(inputChannel = "downloadChannel")
	public MessageHandler downloadHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "mget", "payload");
		sftpOutboundGateway.setOptions("-R");
		sftpOutboundGateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
		sftpOutboundGateway.setLocalDirectory(new File(properties.getLocalDir()));
		sftpOutboundGateway.setAutoCreateLocalDirectory(true);
		return sftpOutboundGateway;
	}

	@Bean
	@ServiceActivator(inputChannel = "uploadChannel")
	public MessageHandler uploadHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
		handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getRemoteDir()));
		handler.setFileNameGenerator(message -> {
			if (message.getPayload() instanceof File) {
				return ((File) message.getPayload()).getName();
			} else {
				throw new IllegalArgumentException("File expected as payload.");
			}
		});
		return handler;
	}

	@Bean
	@ServiceActivator(inputChannel = "uploadByteChannel")
	public MessageHandler multiTypeHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
		handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getRemoteDir()));
		handler.setFileNameGenerator(message -> {
			if (message.getPayload() instanceof byte[]) {
				return (String) message.getHeaders().get("name");
			} else {
				throw new IllegalArgumentException("byte[] expected as payload.");
			}
		});
		return handler;
	}

	@MessagingGateway
	public interface SftpGateway {
		@SuppressWarnings("rawtypes")
		@Gateway(requestChannel = "lsChannel")
		List<FileInfo> listFile(String dir);

		@Gateway(requestChannel = "nlstChannel")
		String nlstFile(String dir);

		@Gateway(requestChannel = "getChannel")
		File getFile(String dir);

		@Gateway(requestChannel = "uploadChannel")
		void upload(File file);

		@Gateway(requestChannel = "uploadByteChannel")
		void upload(byte[] inputStream, String name);

		@Gateway(requestChannel = "toPathChannel")
		void upload(@Payload byte[] file, @Header("filename") String filename, @Header("path") String path);

		@Gateway(requestChannel = "downloadChannel")
		List<File> downloadFiles(String dir);
	}
}
