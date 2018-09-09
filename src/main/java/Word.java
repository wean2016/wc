/**
 * @version V1.0.0
 * @Description
 * @Author liuyuequn weanyq@gmail.com
 * @Date 2018/9/9 21:14
 */
public class Word {
    public static void word(String text){
        boolean blank = true;   // 非单词标记
        int count = 0;
        for (char c : text.toCharArray()) {
            // 如果是单词开头
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
                if (blank){
                    blank = false;
                    count++;
                }
            }else if (c == '-' && !blank){
                // 如果是单词间隔
            }else {
                // 如果是单词结尾
                blank = true;
            }
        }
        System.out.println(String.format("单词数: %d", count));
    }
}
