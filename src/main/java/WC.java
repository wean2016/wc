import java.io.File;
import java.util.Arrays;

/**
 * @version V1.0.0
 * @Description
 * @Author liuyuequn weanyq@gmail.com
 * @Date 2018/9/9 20:21
 */
public class WC {
    public static void main(String[] args) {
        if (args.length <= 0){
            System.out.println("请输入参数");
            return;
        }
        // 拿到文件
        String fileName = args[args.length - 1];
        String text = ReadToString.readToString(fileName);
        if (text == null){
            System.out.println("文件不存在!");
            return;
        }

        // 并行处理各个参数(忽略最后一个参数， 即忽略文件名)
        Arrays.asList(args).subList(0, args.length-1).parallelStream().forEach(s -> {
            switch (s){
                case "-c" : Count.count(text); break;
                case "-w" : Word.word(text); break;
                default: break;
            }
        });

    }


}
