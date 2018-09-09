import java.util.Arrays;

/**
 * @version V1.0.0
 * @Description
 * @Author liuyuequn weanyq@gmail.com
 * @Date 2018/9/10 0:49
 */
public class Complex {
    public static void complex(String text){
        System.out.printf("复杂数据:\n%s\n%s\n%s\n", blank(text), code(text), note(text));
    }

    private static String blank(String text){
        long blankLine = 0;
        if (!text.isEmpty()){
            blankLine = Arrays.stream(text.split("\r\n"))
                    .map(String::trim)
                    .map(s -> {
                        if (s.contains("//")){
                            // 如果本行包含注释，则去掉注释后的内容
                            return s.substring(0, s.indexOf("//"));
                        }
                        return s;
                    })
                    .filter(s -> s.length() <= 1)
                    .count();

        }
        return String.format("有 %d 个空白行", blankLine);
    }

    private static String code(String text){
        long codeLine = 0;
        if (!text.isEmpty()){
            codeLine = Arrays.stream(text.split("\r\n"))
                    .map(String::trim)
                    .map(s -> {
                        if (s.contains("//")){
                            // 如果本行包含注释，则去掉注释后的内容
                            return s.substring(0, s.indexOf("//"));
                        }
                        return s;
                    })
                    .filter(s -> s.length() > 1)
                    .count();

        }
        return String.format("有 %d 个代码行", codeLine);
    }

    private static String note(String text){
        long noteLine = 0;
        if (!text.isEmpty()) {
            noteLine = Arrays.stream(text.split("\r\n"))
                    .filter(s -> s.contains("//"))
                    .map(s -> s.substring(0, s.indexOf("//")))
                    .map(String::trim)
                    .filter(s -> s.length() <= 1)
                    .count();
        }
        return String.format("有 %d 个注释行", noteLine);
    }

}
