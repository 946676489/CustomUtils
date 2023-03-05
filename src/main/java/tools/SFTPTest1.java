package tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPTest1 {
	private String sftpPath = "upload";

	public static void main(String[] args) {
		new SFTPTest1().sftpDownload("a.txt");
	}

	Session sshSession = null;
	/**
	 * 连接sftp服务器
	 *
	 */
	public ChannelSftp sftpConnect() {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession("test02", "192.168.2.119", 22);// 用户名，主机，端口号
			sshSession.setPassword("test02");// 密码
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.setTimeout(3000); // 设置timeout时间
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("sftp connected success");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return sftp;
	}

	/**
	 * 下载文件
	 *
	 * @param remoteFile
	 *            文件名称
	 */
	public String sftpDownload(String remoteFile) {
		ChannelSftp sftp = sftpConnect();
		if (null != sftp) {
			try {
				if (StringUtils.isNotBlank(remoteFile)) {
					// sftpPath 文件所在的路径
					remoteFile = sftpPath + "/" + remoteFile;
					InputStream in = sftp.get(remoteFile);
					String lineTxt = null;
					BufferedReader br1 = new BufferedReader(new InputStreamReader(in, "utf-8"));// 考虑到编码格式
					while ((lineTxt = br1.readLine()) != null) {// 读取下一行
						System.out.println(lineTxt);
					}

					if (br1 != null) {
						br1.close();
					}
					if (in != null) {
						in.close();
					}
					return "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭连接
				disconnect(sftp);
			}
		}
		return null;
	}

	private void disconnect(ChannelSftp sftp) {
		if (sftp != null && !sftp.isClosed()) {
			sshSession.disconnect();
			sftp.exit();
		}
	}

}
