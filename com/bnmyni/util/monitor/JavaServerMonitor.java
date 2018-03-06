package com.bnmyni.util.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 * 通过java api 获取主机的内存，cpu，磁盘，网络情况进行监控
 * 注意：OperatingSystemMXBean 需要使用 com.sun.management.OperatingSystemMXBean，不能使用java.lang.OperatingSystemMXBean
 *     如果eclipse中引用的是jdk\jre\lib\rt.jar但无法引用到 com.sun.management.OperatingSystemMXBean
 *     需要修改eclipse配置，将deprecated and restricted api / forbidden reference(access rule) 从error修改为warning
 */
public class JavaServerMonitor {

	static final int KB = 1024;
	static final int MB = 1024 * 1024;
	static final int GB = KB * MB;

	public static void main(String[] args) throws Exception {
		// 简易调用获取主机的cpu信息
		String cmd = "C:\\windows"  + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
				+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
		Process proc = Runtime.getRuntime().exec(cmd);
		proc.getOutputStream().close();
		InputStream is = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "GBK");
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			System.out.println(line);
			sb.append("\r\n");
		}
		is.close();
		isr.close();
		br.close();
		
	}

	/**
	 * 获取主机的内存总量，使用量，计算使用率
	 */
	public static void getMonitorInfoBean() {
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// // 内存总大小
		long totalMemory = osmxb.getTotalPhysicalMemorySize() / MB;
		System.out.println(totalMemory);
		// 剩余内存
		long freeMemory = osmxb.getFreePhysicalMemorySize() / MB;
		System.out.println(freeMemory);
		// 已使用内存
		long usedMemory = (totalMemory - freeMemory) / MB;
		System.out.println(usedMemory);

	}
	
	/**
	 * 获取cpu使用情况
	 */
	public static long getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
					+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(30);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return 100 * (busytime) / (busytime + idletime);
			} else {
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * cpu使用情况计算
	 */
	public static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < 10) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0)
						idletime += Long.valueOf(s1).longValue();
					if (s2.length() > 0)
						idletime += Long.valueOf(s2).longValue();
					continue;
				}
				if (s1.length() > 0)
					kneltime += Long.valueOf(s1).longValue();
				if (s2.length() > 0)
					usertime += Long.valueOf(s2).longValue();
			}

			retn[0] = idletime;
			retn[1] = kneltime + usertime;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retn;
	}
	
	public static String substring(String src, int start_idx, int end_idx){   
        byte[] b = src.getBytes();   
        String tgt = "";   
        for(int i=start_idx; i<=end_idx; i++){   
            tgt +=(char)b[i];   
        }   
        return tgt;   
    }

	/**
	 * 获取主机的内存磁盘总量，使用量
	 */
	public static void checkDiskSpace() {
		File[] roots = File.listRoots();

		for (File _file : roots) {
			System.out.println(_file.getPath());
			System.out.println(_file.getTotalSpace() / GB);
			System.out.println(_file.getFreeSpace() / GB);
		}
	}

	/**
	 * 主机网关测试
	 */
	public static int getNetState(String gateway) {
		Runtime runtime = Runtime.getRuntime();
		Process process;
		int netState = 0;// 异常
		try {
			process = runtime.exec("ping " + gateway);
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println(line);
				sb.append("\r\n");
			}
			is.close();
			isr.close();
			br.close();

			if (null != sb && !sb.toString().equals("")) {
				if (sb.toString().indexOf("TTL") > 0) {
					// 网络畅通
					netState = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return netState;
	}
}
