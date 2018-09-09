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
        int line = countLineChar(text) + (text.isEmpty() ? 0 : 1);
        System.out.println(String.format("行数为: %d", line));
    }


    /**
     * 数换行符
     * @param text
     * @return
     */
    private static int countLineChar(String text){
        int line = 0;
        if (text.length() <= 500){
            // 如果少于 500 个字符，则直接遍历
            for (int i = 0; i <= text.length() - 2; i++) {
                if (text.substring(i,i+2).equals("\r\n")){
                    line++;
                }
            }
            return line;
        }else {
            // 大于 500 个字符，递归并发处理
            CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> countLineChar(text.substring(0, 500)));
            CompletableFuture<Integer> last = CompletableFuture.supplyAsync(() -> countLineChar(text.substring(500)));
            // 计算分割中间部分是否有换行符
            int midLine = text.substring(499, 501).equals("\r\n") ? 1 : 0;
            try {
                return first.get() + last.get() + midLine;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
