package com.nbai.utils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import com.nbai.common.entity.dto.AiPower;
import com.nbai.common.entity.dto.AiPowerTask;

/**
 * N卡算力管理，用于查询服务器上N卡的算力使用情况
 * 2021-1-18 zz：目前这个helper是个独立执行的项目，未来肯定是要把算力管理整合到训练、测试模块中，对算力池进行统一监控管理。
 *
 * @author zz
 * @version 1.0
 * @date 2021-1-18
 */
public class NvidaUtil {

    protected static final Pattern SPACE_SPLITER = Pattern.compile("\\s+");
    protected static final Pattern SPACE2_SPLITER = Pattern.compile("\\s{2,100}");

    private static final boolean debug = false;

    /**
     * 通过命令行，查询当前显卡使用情况
     *
     * @return 显卡使用情况
     */
//    public static AiPower getCurrentPower() throws IOException, ParseException {
//        ArrayList<String> nvdLines = new ArrayList<>(32);
//        if (! debug) {
//            String cmd = "nvidia-smi";
//            // 执行cmd，回收返回结果。
//            Process process = Runtime.getRuntime().exec(cmd);
//            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
//            LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
//            String line;
//            while ((line = lineNumberReader.readLine()) != null) {
//                // 读入一行命令行执行结果
//                nvdLines.add(line);
//            }
//        } else {
//            // debug
//            nvdLines.add("Mon Jan 18 15:33:13 2021       ");
//            nvdLines.add("+-----------------------------------------------------------------------------+");
//            nvdLines.add("| NVIDIA-SMI 418.67       Driver Version: 418.67       CUDA Version: 10.1     |");
//            nvdLines.add("|-------------------------------+----------------------+----------------------+");
//            nvdLines.add("| GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |");
//            nvdLines.add("| Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |");
//            nvdLines.add("|===============================+======================+======================|");
//            nvdLines.add("|   0  Tesla M40           On   | 00000000:00:07.0 Off |                  Off |");
//            nvdLines.add("| N/A   53C    P0   215W / 250W |   4711MiB / 12215MiB |     52%      Default |");
//            nvdLines.add("+-------------------------------+----------------------+----------------------+");
//            nvdLines.add("                                                                               ");
//            nvdLines.add("+-----------------------------------------------------------------------------+");
//            nvdLines.add("| Processes:                                                       GPU Memory |");
//            nvdLines.add("|  GPU       PID   Type   Process name                             Usage      |");
//            nvdLines.add("|=============================================================================|");
//            nvdLines.add("|    0     31782      C   python                                      2349MiB |");
//            nvdLines.add("|    0     32034      C   python                                      2351MiB |");
//            nvdLines.add("+-----------------------------------------------------------------------------+");
//
//            nvdLines.clear();
//            nvdLines.add("Sat Mar  6 17:33:06 2021       ");
//            nvdLines.add("+-----------------------------------------------------------------------------+");
//            nvdLines.add("| NVIDIA-SMI 450.102.04   Driver Version: 450.102.04   CUDA Version: 11.0     |");
//            nvdLines.add("|-------------------------------+----------------------+----------------------+");
//            nvdLines.add("| GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |");
//            nvdLines.add("| Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |");
//            nvdLines.add("|                               |                      |               MIG M. |");
//            nvdLines.add("|===============================+======================+======================|");
//            nvdLines.add("|   0  Tesla T4            Off  | 00000000:AF:00.0 Off |                    0 |");
//            nvdLines.add("| N/A   61C    P0    25W /  70W |      0MiB / 15109MiB |      5%      Default |");
//            nvdLines.add("|                               |                      |                  N/A |");
//            nvdLines.add("+-------------------------------+----------------------+----------------------+");
//            nvdLines.add("                                                                               ");
//            nvdLines.add("+-----------------------------------------------------------------------------+");
//            nvdLines.add("| Processes:                                                                  |");
//            nvdLines.add("|  GPU   GI   CI        PID   Type   Process name                  GPU Memory |");
//            nvdLines.add("|        ID   ID                                                   Usage      |");
//            nvdLines.add("|=============================================================================|");
//            nvdLines.add("|    0   N/A  N/A     31782      C   python                           2349MiB |");
//            nvdLines.add("|  No running processes found                                                 |");
//            nvdLines.add("+-----------------------------------------------------------------------------+");
//
//        }
//        // 解析回收的结果
//        return parseCmdOutput(nvdLines.toArray(new String[nvdLines.size()]));
//    }

//        样例数据
//0       Mon Jan 18 15:33:13 2021
//1       +-----------------------------------------------------------------------------+
//2       | NVIDIA-SMI 418.67       Driver Version: 418.67       CUDA Version: 10.1     |
//3       |-------------------------------+----------------------+----------------------+
//4       | GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
//5       | Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
//6       |===============================+======================+======================|
//7       |   0  Tesla M40           On   | 00000000:00:07.0 Off |                  Off |
//8       | N/A   53C    P0   215W / 250W |   4711MiB / 12215MiB |     52%      Default |
//9       +-------------------------------+----------------------+----------------------+
//10
//11      +-----------------------------------------------------------------------------+
//12      | Processes:                                                       GPU Memory |
//13      |  GPU       PID   Type   Process name                             Usage      |
//14      |=============================================================================|
//15      |    0     31782      C   python                                      2349MiB |
//16      |    0     32034      C   python                                      2351MiB |
//17      +-----------------------------------------------------------------------------+

    /**
     * 解析nvidia-smi的命令行输出
     * @param cmdOutput 输出结果
     * @return 解析结果
     * @throws ParseException 解析异常
     */
    public static AiPower parseCmdOutput(String[] cmdOutput) throws ParseException {
        // 第一次遍历，确定两个表格的数据段行号
        int timeLineNo = 0;
        int versionLineNo = 2;
        int gpuLineNo = 7;
        int processHeaderLineNo = 12;
        int processLineNo = 14;
        String lineStatus = "";
        for (int lineNo=0; lineNo < cmdOutput.length; lineNo++) {
            String line = cmdOutput[lineNo];
            if ("".equals(lineStatus) && line.contains("--------")) {
                versionLineNo = lineNo + 1;
                lineStatus = "version";
            }
            if ("version".equals(lineStatus) && line.contains("========")) {
                gpuLineNo = lineNo + 2;
                lineStatus = "gpu";
            }
            if ("gpu".equals(lineStatus) && line.trim().isEmpty()) {
                lineStatus = "blank";
            }
            if ("blank".equals(lineStatus) && line.contains("--------")) {
                processHeaderLineNo = lineNo + 2;
                lineStatus = "processHeader";
            }
            if ("processHeader".equals(lineStatus) && line.contains("========")) {
                processLineNo = lineNo + 1;
                break;
            }
        }

        // 解析输出结果
        AiPower ret = new AiPower();
        // 分析过程说明：
        // 第一段表格，gpu相关参数
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.US);
        ret.setTime(sdf.parse(cmdOutput[timeLineNo].trim()));
        // 驱动版本
        String[] sections;
        sections = SPACE_SPLITER.split(cmdOutput[versionLineNo]);
        ret.setDriverVersion(sections[5]);
        // 显卡名称
        sections = SPACE_SPLITER.split(cmdOutput[gpuLineNo - 1]);
        ret.setName(sections[2]);
        // 运行资源状态
        sections = SPACE_SPLITER.split(cmdOutput[gpuLineNo]);
        ret.setTemperature(Integer.parseInt(sections[2].replace("C", "")));
        ret.setPower(Integer.parseInt(sections[4].replace("W", "")));
        ret.setMaxPower(Integer.parseInt(sections[6].replace("W", "")));
        ret.setMemory(Integer.parseInt(sections[8].replace("MiB", "")));
        ret.setTotalMemory(Integer.parseInt(sections[10].replace("MiB", "")));
        ret.setUtilization(Integer.parseInt(sections[12].replace("%", "")) / 100.0F);
        // 当前运行任务
        // 表头
        sections = SPACE2_SPLITER.split(cmdOutput[processHeaderLineNo]);
        HashMap<String, Integer> processHeaderMap = new HashMap<>(sections.length);
        int position = 0;
        for (String name : sections) {
            position = cmdOutput[processHeaderLineNo].indexOf(name, position);
            String usedName = name;
            if ("Process name".equals(usedName)) {
                usedName = "Process";
            }
            if ("GPU Memory |".equals(usedName) || "Usage".equals(usedName)) {
                usedName = "Memory";
            }
            processHeaderMap.put(usedName, processHeaderMap.size());
        }

        ArrayList<AiPowerTask> tasks = new ArrayList<>(cmdOutput.length - gpuLineNo);
        for (int iLine = processLineNo; iLine < cmdOutput.length - 1; iLine++) {
            if (cmdOutput[iLine].contains("No running processes found")) {
                break;
            }
            sections = SPACE_SPLITER.split(cmdOutput[iLine]);
            AiPowerTask task = new AiPowerTask();
            task.setGpu(Integer.parseInt(sections[processHeaderMap.get("GPU")]));
            task.setPid(Integer.parseInt(sections[processHeaderMap.get("PID")]));
            task.setType(sections[processHeaderMap.get("Type")]);
            task.setProcess(sections[processHeaderMap.get("Process")]);
            task.setMemory(Integer.parseInt(sections[processHeaderMap.get("Memory")].replace("MiB", "")));
            tasks.add(task);
        }
        ret.setRunning(tasks.toArray(tasks.toArray(new AiPowerTask[tasks.size()])));

        return ret;
    }
}
