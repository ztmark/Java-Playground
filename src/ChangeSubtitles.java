import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mark
 * Date  : 2014/12/17
 * Time  : 22:44
 *
 * 修改字幕文件
 *
 */
public class ChangeSubtitles {
    public static void main(String[] args) {
        String path = "L:\\movie\\老友记";
        String[] d = new String[]{"第六季","第七季","第八季"};
        for (String s : d) {
            File dir = new File(path+File.separator+s);
            File[] videos = listFiles(dir,".mkv");
            File[] srts = listFiles(dir,".srt");
            change(videos,srts);
        }

    }

    private static void change(File[] v, File[] s) {
        for (int i = 0; i < s.length; i++) {
            File dest = new File(s[i].getParent()+File.separator+v[i].getName().substring(0, v[i].getName().length()-3)+"srt");
            changeAndSave(s[i],dest);
        }
    }

    private static File[] listFiles(File dir, String type) {
        File[] all = dir.listFiles();
        List<File> ans = new ArrayList<>();
        for (File file : all) {
            if (file.getName().endsWith(type)) {
                ans.add(file);
            }
        }
        return ans.toArray(new File[ans.size()]);
    }

    private static String replace(String o,String r) {
        Pattern p = Pattern.compile("(\\{.*?\\})");
        Matcher m = p.matcher(o);
        String res = o;
        if (m.find()) {
            res = m.replaceAll(r);
        }
        return res;
    }

    private static void changeAndSave(File srt, File dest) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(srt),"GB2312"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest),"UTF-8"));
            char[] buff = new char[50*1024];
            br.read(buff);
            String tmp = replace(String.copyValueOf(buff),"");
            bw.write(tmp);
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
