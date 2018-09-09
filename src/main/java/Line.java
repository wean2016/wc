import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @version V1.0.0
 * @Description
 * @Author liuyuequn weanyq@gmail.com
 * @Date 2018/9/9 21:39
 */
public class Line {
    public static void line(String text){
        System.out.println(String.format("行数为: %d", countLine(text)));
    }

    private static int countLine(String text){
        int line = 0;
        if (text.length() <= 1000){
            // 如果少于 1000 个字符，则直接遍历
            for (int i = 0; i <= text.length() - 3; i++) {
                if (text.substring(i,i+3).equals("\r\n")){
                    line++;
                }
            }
            return line;
        }else {
            // 大于 1000 个字符，递归并发处理
            CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> countLine(text.substring(0, 1000)));
            CompletableFuture<Integer> last = CompletableFuture.supplyAsync(() -> countLine(text.substring(1000)));
            // 计算分割中间部分是否有换行符
            int end = 1002 >= text.length() ? text.length() : 1002;
            int midLine = text.substring(997, end).contains("\r\n") ? 1 : 0;
            try {
                return first.get() + last.get() + midLine;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
