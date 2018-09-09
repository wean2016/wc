import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toMap;

/**
 * @version V1.0.0
 * @Description
 * @Author liuyuequn weanyq@gmail.com
 * @Date 2018/9/9 20:21
 */
public class WC {
    public static void main(String[] args) {
        if (args.length <= 0){
            System.err.println("请输入参数");
            return;
        }
        // 拿到文件
        Map<String, String> texts = null;
        // 1. 处理包含 -s 参数的情况
        boolean hasSArg = Arrays.asList(args).stream().anyMatch(s -> s.equals("-s"));
        if (hasSArg){
            String originFileName = args[args.length - 1];
            String temp = originFileName.replaceAll("\\.", "\\.");
            int splitPoint = temp.lastIndexOf("\\");
            String path = temp.substring(0, splitPoint);
            String regexfileName = temp.substring(splitPoint + 1).replaceAll("\\*", ".*");
            Pattern pattern = Pattern.compile(regexfileName.isEmpty() ? ".*" : regexfileName);
            File file = new File(path);
            texts = getTexts(file, pattern);
        }else {
            String text = ReadToString.readToString(args[args.length - 1]);
            texts = new HashMap<>();
            texts.put(args[args.length - 1], text);
        }

        if (texts == null || texts.isEmpty()){
            System.err.println("文件不存在!");
            return;
        }


        // 处理各个文件，并行处理各个参数(忽略最后一个参数， 即忽略文件名)
        texts.forEach((name, text) -> {
            System.out.println(name);
            Arrays.asList(args).subList(0, args.length-1).parallelStream().forEach(s -> {
                switch (s){
                    case "-c" : Count.count(text); break;
                    case "-w" : Word.word(text); break;
                    case "-l" : Line.line(text); break;
                    default: break;
                }
            });
        });

    }


    private static Map<String, String> getTexts(File file, Pattern pattern) {
        Map<String, String> texts = new HashMap<>();
        if (file.isDirectory()){
            texts = Arrays.stream(file.list())
                    .filter(s -> !new File(file.getAbsolutePath() + "\\" + s).isDirectory())
                    .filter(s -> pattern.matcher(s).matches())
                    .collect(toMap(s -> file.getAbsolutePath() + "\\" + s, s -> {
                        String text = ReadToString.readToString(file.getAbsolutePath() + "\\" + s);
                        return text == null ? "" : text;
                    }));
            Arrays.stream(file.list())
                    .filter(s -> new File(file.getAbsolutePath() + "\\" + s).isDirectory())
                    .map(s -> new File(file.getAbsolutePath() + "\\" + s))
                    .map(file1 -> getTexts(file1, pattern))
                    .forEach(texts::putAll);
        }
        return texts;
    }
}