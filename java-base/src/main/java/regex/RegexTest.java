package regex;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 正则表达式
 *
 * Pattern 类：
 * pattern 对象是一个正则表达式的编译表示。
 * Pattern 类没有公共构造方法。
 * 要创建一个 Pattern 对象，你必须首先调用其公共静态编译方法，它返回一个 Pattern 对象。
 * 该方法接受一个正则表达式作为它的第一个参数。
 *
 * Matcher 类：
 * Matcher 对象是对输入字符串进行解释和匹配操作的引擎。
 * 与Pattern 类一样，Matcher 也没有公共构造方法。
 * 你需要调用 Pattern 对象的 matcher 方法来获得一个 Matcher 对象
 *
 *
 * PatternSyntaxException：
 * PatternSyntaxException 是一个非强制异常类，它表示一个正则表达式模式中的语法错误。
 *
 *
 * @author booty
 * @date 2021/5/24 09:03
 */
public class RegexTest {


    /**
     * 示例
     */
    @org.junit.jupiter.api.Test
    void testMatch(){
        String content ="are you ok";
        String pattern = ".*ok.*";
        boolean isMatch= Pattern.matches(pattern,content);
        System.out.println("字符串中是否包含了 'ok' 子字符串? " + isMatch);
    }


    /**
     * 捕获组是把多个字符当一个单独单元进行处理的方法，它通过对括号内的字符分组来创建。
     * 例如，正则表达式 (dog) 创建了单一分组，组里包含"d"，"o"，和"g"。
     * 捕获组是通过从左至右计算其开括号来编号。例如，在表达式（（A）（B（C））），有四个这样的组
     * ((A)(B(C)))
     * (A)
     * (B(C))
     * (C)
     * 可以通过调用 matcher 对象的 groupCount 方法来查看表达式有多少个分组。groupCount 方法返回一个 int 值，表示matcher对象当前有多个捕获组。
     * 还有一个特殊的组（group(0)），它总是代表整个表达式。该组不包括在 groupCount 的返回值中。
     */
    @org.junit.jupiter.api.Test
    void testGroup(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 创建 matcher 对象
        Matcher m = r.matcher(line);

        //查询分组数（总分组group（0）不计入其中）
        int i = m.groupCount();
        System.out.println("groupCount:"+i);

        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
            /*
            group（0）为特殊分组，不计入分组数中，代表总分组，指全部内容
            Found value: This order was placed for QT3000! OK?

            以下为3个分组分别的值
            Found value: This order was placed for QT
            Found value: 3000
            Found value: ! OK?
             */
        } else {
            System.out.println("NO MATCH");
        }
    }


    /**
     * 语法
     *
     * 在其他语言中，\\ 表示：我想要在正则表达式中插入一个普通的（字面上的）反斜杠，请不要给它任何特殊的意义。
     * 在 Java 中，\\ 表示：我要插入一个正则表达式的反斜线，所以其后的字符具有特殊的意义。
     * 所以，在其他的语言中（如Perl），一个反斜杠 \ 就足以具有转义的作用，而在 Java 中正则表达式中则需要有两个反斜杠才能被解析为其他语言中的转义作用。
     * 也可以简单的理解在 Java 的正则表达式中，两个 \\ 代表其他语言中的一个 \
     * 这也就是为什么表示一位数字的正则表达式是 \\d，而表示一个普通的反斜杠是 \\\\。
     *
     * \
     *
     * 将下一字符标记为特殊字符、文本、反向引用或八进制转义符。例如，"n"匹配字符"n"。"\n"匹配换行符。序列"\\\\"匹配"\\"，"\\("匹配"("。
     *
     * ^
     *
     * 匹配输入字符串开始的位置。如果设置了 RegExp 对象的 Multiline 属性，^ 还会与"\n"或"\r"之后的位置匹配。
     *
     * $
     *
     * 匹配输入字符串结尾的位置。如果设置了 RegExp 对象的 Multiline 属性，$ 还会与"\n"或"\r"之前的位置匹配。
     *
     * *
     *
     * 零次或多次匹配前面的字符或子表达式。例如，zo* 匹配"z"和"zoo"。* 等效于 {0,}。
     *
     * +
     *
     * 一次或多次匹配前面的字符或子表达式。例如，"zo+"与"zo"和"zoo"匹配，但与"z"不匹配。+ 等效于 {1,}。
     *
     * ?
     *
     * 零次或一次匹配前面的字符或子表达式。例如，"do(es)?"匹配"do"或"does"中的"do"。? 等效于 {0,1}。
     *
     * {n}
     *
     * n 是非负整数。正好匹配 n 次。例如，"o{2}"与"Bob"中的"o"不匹配，但与"food"中的两个"o"匹配。
     *
     * {n,}
     *
     * n 是非负整数。至少匹配 n 次。例如，"o{2,}"不匹配"Bob"中的"o"，而匹配"foooood"中的所有 o。"o{1,}"等效于"o+"。"o{0,}"等效于"o*"。
     *
     * {n,m}
     *
     * m 和 n 是非负整数，其中 n <= m。匹配至少 n 次，至多 m 次。例如，"o{1,3}"匹配"fooooood"中的头三个 o。'o{0,1}' 等效于 'o?'。注意：您不能将空格插入逗号和数字之间。
     *
     * ?
     *
     * 当此字符紧随任何其他限定符（*、+、?、{n}、{n,}、{n,m}）之后时，匹配模式是"非贪心的"。"非贪心的"模式匹配搜索到的、尽可能短的字符串，而默认的"贪心的"模式匹配搜索到的、尽可能长的字符串。例如，在字符串"oooo"中，"o+?"只匹配单个"o"，而"o+"匹配所有"o"。
     *
     * .
     *
     * 匹配除"\r\n"之外的任何单个字符。若要匹配包括"\r\n"在内的任意字符，请使用诸如"[\s\S]"之类的模式。
     *
     * (pattern)
     *
     * 匹配 pattern 并捕获该匹配的子表达式。可以使用 $0…$9 属性从结果"匹配"集合中检索捕获的匹配。若要匹配括号字符 ( )，请使用"\("或者"\)"。
     *
     * (?:pattern)
     *
     * 匹配 pattern 但不捕获该匹配的子表达式，即它是一个非捕获匹配，不存储供以后使用的匹配。这对于用"or"字符 (|) 组合模式部件的情况很有用。例如，'industr(?:y|ies) 是比 'industry|industries' 更经济的表达式。
     *
     * (?=pattern)
     *
     * 执行正向预测先行搜索的子表达式，该表达式匹配处于匹配 pattern 的字符串的起始点的字符串。它是一个非捕获匹配，即不能捕获供以后使用的匹配。例如，'Windows (?=95|98|NT|2000)' 匹配"Windows 2000"中的"Windows"，但不匹配"Windows 3.1"中的"Windows"。预测先行不占用字符，即发生匹配后，下一匹配的搜索紧随上一匹配之后，而不是在组成预测先行的字符后。
     *
     * (?!pattern)
     *
     * 执行反向预测先行搜索的子表达式，该表达式匹配不处于匹配 pattern 的字符串的起始点的搜索字符串。它是一个非捕获匹配，即不能捕获供以后使用的匹配。例如，'Windows (?!95|98|NT|2000)' 匹配"Windows 3.1"中的 "Windows"，但不匹配"Windows 2000"中的"Windows"。预测先行不占用字符，即发生匹配后，下一匹配的搜索紧随上一匹配之后，而不是在组成预测先行的字符后。
     *
     * x|y
     *
     * 匹配 x 或 y。例如，'z|food' 匹配"z"或"food"。'(z|f)ood' 匹配"zood"或"food"。
     *
     * [xyz]
     *
     * 字符集。匹配包含的任一字符。例如，"[abc]"匹配"plain"中的"a"。
     *
     * [^xyz]
     *
     * 反向字符集。匹配未包含的任何字符。例如，"[^abc]"匹配"plain"中"p"，"l"，"i"，"n"。
     *
     * [a-z]
     *
     * 字符范围。匹配指定范围内的任何字符。例如，"[a-z]"匹配"a"到"z"范围内的任何小写字母。
     *
     * [^a-z]
     *
     * 反向范围字符。匹配不在指定的范围内的任何字符。例如，"[^a-z]"匹配任何不在"a"到"z"范围内的任何字符。
     *
     * \\b
     *
     * 匹配一个字边界，即字与空格间的位置。例如，"er\b"匹配"never"中的"er"，但不匹配"verb"中的"er"。
     *
     * \\B
     *
     * 非字边界匹配。"er\B"匹配"verb"中的"er"，但不匹配"never"中的"er"。
     *
     * \\cx
     *
     * 匹配 x 指示的控制字符。例如，\cM 匹配 Control-M 或回车符。x 的值必须在 A-Z 或 a-z 之间。如果不是这样，则假定 c 就是"c"字符本身。
     *
     * \\d
     *
     * 数字字符匹配。等效于 [0-9]。
     *
     * \\D
     *
     * 非数字字符匹配。等效于 [^0-9]。
     *
     * \\f
     *
     * 换页符匹配。等效于 \x0c 和 \cL。
     *
     * \\n
     *
     * 换行符匹配。等效于 \x0a 和 \cJ。
     *
     * \\r
     *
     * 匹配一个回车符。等效于 \x0d 和 \cM。
     *
     * \\s
     *
     * 匹配任何空白字符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效。
     *
     * \\S
     *
     * 匹配任何非空白字符。与 [^ \f\n\r\t\v] 等效。
     *
     * \\t
     *
     * 制表符匹配。与 \x09 和 \cI 等效。
     *
     * \\v
     *
     * 垂直制表符匹配。与 \x0b 和 \cK 等效。
     *
     * \\w
     *
     * 匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。
     *
     * \\W
     *
     * 与任何非单词字符匹配。与"[^A-Za-z0-9_]"等效。
     *
     * \\xn
     *
     * 匹配 n，此处的 n 是一个十六进制转义码。十六进制转义码必须正好是两位数长。例如，"\x41"匹配"A"。"\x041"与"\x04"&"1"等效。允许在正则表达式中使用 ASCII 代码。
     *
     * \\num
     *
     * 匹配 num，此处的 num 是一个正整数。到捕获匹配的反向引用。例如，"(.)\1"匹配两个连续的相同字符。
     *
     * \\n
     *
     * 标识一个八进制转义码或反向引用。如果 \n 前面至少有 n 个捕获子表达式，那么 n 是反向引用。否则，如果 n 是八进制数 (0-7)，那么 n 是八进制转义码。
     *
     * \\nm
     *
     * 标识一个八进制转义码或反向引用。如果 \nm 前面至少有 nm 个捕获子表达式，那么 nm 是反向引用。如果 \nm 前面至少有 n 个捕获，则 n 是反向引用，后面跟有字符 m。如果两种前面的情况都不存在，则 \nm 匹配八进制值 nm，其中 n 和 m 是八进制数字 (0-7)。
     *
     * \\nml
     *
     * 当 n 是八进制数 (0-3)，m 和 l 是八进制数 (0-7) 时，匹配八进制转义码 nml。
     *
     * \\un
     *
     * 匹配 n，其中 n 是以四位十六进制数表示的 Unicode 字符。例如，\u00A9 匹配版权符号 (©)。
     *
     *
     *
     *
     * Matcher 类的方法
     * 索引方法
     * 索引方法提供了有用的索引值，精确表明输入字符串中在哪能找到匹配：
     *
     * 序号	方法及说明
     * 1	public int start()
     * 返回以前匹配的初始索引。
     * 2	public int start(int group)
     *  返回在以前的匹配操作期间，由给定组所捕获的子序列的初始索引
     * 3	public int end()
     * 返回最后匹配字符之后的偏移量。
     * 4	public int end(int group)
     * 返回在以前的匹配操作期间，由给定组所捕获子序列的最后字符之后的偏移量。
     *
     * 查找方法
     * 查找方法用来检查输入字符串并返回一个布尔值，表示是否找到该模式：
     *
     * 序号	方法及说明
     * 1	public boolean lookingAt()
     *  尝试将从区域开头开始的输入序列与该模式匹配。
     * 2	public boolean find()
     * 尝试查找与该模式匹配的输入序列的下一个子序列。
     * 3	public boolean find(int start）
     * 重置此匹配器，然后尝试查找匹配该模式、从指定索引开始的输入序列的下一个子序列。
     * 4	public boolean matches()
     * 尝试将整个区域与模式匹配。
     *
     * 替换方法
     * 替换方法是替换输入字符串里文本的方法：
     *
     * 序号	方法及说明
     * 1	public Matcher appendReplacement(StringBuffer sb, String replacement)
     * 实现非终端添加和替换步骤。
     * 2	public StringBuffer appendTail(StringBuffer sb)
     * 实现终端添加和替换步骤。
     * 3	public String replaceAll(String replacement)
     *  替换模式与给定替换字符串相匹配的输入序列的每个子序列。
     * 4	public String replaceFirst(String replacement)
     *  替换模式与给定替换字符串匹配的输入序列的第一个子序列。
     * 5	public static String quoteReplacement(String s)
     * 返回指定字符串的字面替换字符串。这个方法返回一个字符串，就像传递给Matcher类的appendReplacement 方法一个字面字符串一样工作。
     *
     *
     *
     */
    @org.junit.jupiter.api.Test
    void testApi(){

    }


    //matcher类的方法====================

    /**
     * 索引方法
     * 索引方法提供了有用的索引值，精确表明输入字符串中在哪能找到匹配：
     *
     * 序号	方法及说明
     * 1	public int start()
     * 返回以前匹配的初始索引。
     * 2	public int start(int group)
     *  返回在以前的匹配操作期间，由给定组所捕获的子序列的初始索引
     * 3	public int end()
     * 返回最后匹配字符之后的偏移量。
     * 4	public int end(int group)
     * 返回在以前的匹配操作期间，由给定组所捕获子序列的最后字符之后的偏移量。
     */
    @org.junit.jupiter.api.Test
    void testIndexCount(){
        /*
        对单词 "cat" 出现在输入字符串中出现次数进行计数
        其中 \\b 标识字符边界，如下，仅 cat 会被匹配， 若其他单词中夹杂了cat则不会被匹配
         */
        String regex = "\\bcat\\b";
        String input = "cat cat cat cattie cat";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher= pattern.matcher(input);

        int count=0;
        while (matcher.find()){
            count++;
            System.out.println("Match number "+count);
            System.out.println("start(): "+matcher.start());
            System.out.println("end(): "+matcher.end());
            /*
            运行结果：
            Match number 1
            start(): 0
            end(): 3
            Match number 2
            start(): 4
            end(): 7
            Match number 3
            start(): 8
            end(): 11
            Match number 4
            start(): 19
            end(): 22
             */
        }
    }


    /**
     * 查找方法
     * 查找方法用来检查输入字符串并返回一个布尔值，表示是否找到该模式：
     *
     * 序号	方法及说明
     * 1	public boolean lookingAt()
     *  尝试将从区域开头开始的输入序列与该模式匹配。
     * 2	public boolean find()
     * 尝试查找与该模式匹配的输入序列的下一个子序列。
     * 3	public boolean find(int start）
     * 重置此匹配器，然后尝试查找匹配该模式、从指定索引开始的输入序列的下一个子序列。
     * 4	public boolean matches()
     * 尝试将整个区域与模式匹配。
     */
    @org.junit.jupiter.api.Test
    void testLookingAt(){
        /*
        matches 和 lookingAt 方法
        matches 和 lookingAt 方法都用来尝试匹配一个输入序列模式。它们的不同是 matches 要求整个序列都匹配，而lookingAt 不要求。
        lookingAt 方法虽然不需要整句都匹配，但是需要从第一个字符开始匹配。
        这两个方法经常在输入字符串的开始使用。
         */
        String regex = "foo";
        String input = "fooooooooooooooooo";
        String input2 = "ooooofoooooooooooo";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(input);
        Matcher matcher2 = pattern.matcher(input2);

        System.out.println("Current REGEX is: "+regex);
        System.out.println("Current INPUT is: "+input);
        System.out.println("Current INPUT2 is: "+input2);

        System.out.println("lookingAt(): "+matcher1.lookingAt());
        System.out.println("matches(): "+matcher1.matches());
        System.out.println("lookingAt(): "+matcher2.lookingAt());
        /*
        Current REGEX is: foo
        Current INPUT is: fooooooooooooooooo
        Current INPUT2 is: ooooofoooooooooooo
        lookingAt(): true
        matches(): false
        lookingAt(): false
         */

    }

    /**
     * 替换方法
     * 替换方法是替换输入字符串里文本的方法：
     *
     * 序号	方法及说明
     * 1	public Matcher appendReplacement(StringBuffer sb, String replacement)
     * 实现非终端添加和替换步骤。
     * 2	public StringBuffer appendTail(StringBuffer sb)
     * 实现终端添加和替换步骤。
     * 3	public String replaceAll(String replacement)
     *  替换模式与给定替换字符串相匹配的输入序列的每个子序列。
     * 4	public String replaceFirst(String replacement)
     *  替换模式与给定替换字符串匹配的输入序列的第一个子序列。
     * 5	public static String quoteReplacement(String s)
     * 返回指定字符串的字面替换字符串。这个方法返回一个字符串，就像传递给Matcher类的appendReplacement 方法一个字面字符串一样工作。
     */
    @org.junit.jupiter.api.Test
    void testReplace(){
        /*
        replaceFirst 和 replaceAll 方法
        replaceFirst 和 replaceAll 方法用来替换匹配正则表达式的文本。
        不同的是，replaceFirst 替换首次匹配，replaceAll 替换所有匹配
         */
        String regex="dog";
        String input="The dog says meow. " + "All dogs say meow.";
        String replace="cat";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String s = matcher.replaceAll(replace);
        System.out.println(s);
        //结果：The cat says meow. All cats say meow.


        /*
        Matcher 类也提供了appendReplacement 和 appendTail 方法用于文本替换：
        其中* 代表0不匹配或多次匹配*前方的字符，即该表达式匹配以a开头b结尾的字符或b
        */
        String regex1 = "a*b";
        String input1 = "aabfooaabfooabfoobkkk";
        String replace1 = "-";
        Pattern pattern1 =Pattern.compile(regex1);
        Matcher matcher1=pattern1.matcher(input1);
        StringBuffer sb=new StringBuffer();
        while (matcher1.find()){
            matcher1.appendReplacement(sb,replace1);
        }
        matcher1.appendTail(sb);
        System.out.println(sb.toString());
        //结果 -foo-foo-foo-kkk，其中变化：aab,aab,ab，b都替换为-
    }

    /**
     * PatternSyntaxException 类的方法
     * PatternSyntaxException 是一个非强制异常类，它指示一个正则表达式模式中的语法错误。
     *
     * PatternSyntaxException 类提供了下面的方法来帮助我们查看发生了什么错误。
     *
     * 序号	方法及说明
     * 1	public String getDescription()
     * 获取错误的描述。
     * 2	public int getIndex()
     *  获取错误的索引。
     * 3	public String getPattern()
     * 获取错误的正则表达式模式。
     * 4	public String getMessage()
     * 返回多行字符串，包含语法错误及其索引的描述、错误的正则表达式模式和模式中错误索引的可视化指示。
     */
    @org.junit.jupiter.api.Test
    void testPatternSyntaxException(){
        // 个参数分别为错误参数，正则表达式，错误下标
        PatternSyntaxException patternSyntaxException = new PatternSyntaxException("msg","reg",0);
        throw patternSyntaxException;
    }


    /**
     * 匹配QQ号(长度为5-10位，纯数字组成，且不能以0开头)
     */
    @org.junit.jupiter.api.Test
    void testQQ(){
        String qq = "1093723829";
        //[1-9]表示第一位为1-9，\\d{4,9}表示有4-9个纯数字
        String reg = "[1-9]\\d{4,9}";
        System.out.println(qq.matches(reg));
        /*
        [1-9]规定第一位只能是1~9即不为0的纯数字，
        而[0-9]{4,9}则规定可输入4~9个纯数字，
        加起来刚好是：首位不为0的长度为5~10的纯数字
         */
    }


    /**
     * 对输入的电话号码进行匹配
     * （匹配要求：匹配成功的电话号码位数为11位的纯数字，且以1开头，第二位必须是：3、7、8中的一位，
     *  即只匹配13*********、17*********、18*********的电话号码）
     */
    @org.junit.jupiter.api.Test
    void testTelphone(){
        String tel="13699452222";
        String reg="1[3|7|8]\\d{9}";
        System.out.println(tel.matches(reg));
        /*
        一位一位地匹配，所以，开头必须是数字1，那么我们可以写[1]来表示（不过对于只有一个字符的描述，可省略[]）；
        接下来描述第二个字符：只能是3、7、8，所以我们使用[378]来表示。
        然后后9位号码只要是数字就可以了，所以我们可以用[0-9]{9}来表示。
        连起来就是：regex = "1[378][0-9]{9}"
         */
    }

    /**
     * 对字符串“张三@@@李四@@王五@茅台”进行切割，去掉@符号。
     */
    @org.junit.jupiter.api.Test
    void testSplit(){
        /*

         */
        String str="张三@@@李四@@王五@茅台";
        String reg="@+";
        String[] split = str.split(reg);
        System.out.println(Arrays.toString(split));
        /*
        首先我们要去掉字符串中的若干个@符号，
        如果只有一个@符号我们可以用直接用@来匹配，
        但这里的@是不确定的，所以我们要用到规则中的：

        次数的正则：
         X?     一次或无
         X*     零次或多次
         X+     一次或多次
         X{n}   n次
         X{n,m} 至少n次，但不超过m次

　       所以我们用@+来表示：@这个符号至少出现一次这种情况
         */
    }

    /**
     * 【以叠词切割】
     * 如字符串"abccsasahhhz"按“叠词”来切割就变成了“ab”，“sasa”，“z”。
     * 因为“cc”、“hhh”都是叠词，需要切割掉。现在请将字符串“张三@@@李四￥￥王五ssssssss江流儿”按照叠词切割
     */
    @org.junit.jupiter.api.Test
    void testSplit2(){

        String str="张三@@@李四￥￥王五ssssssss江流儿";
        String reg="(.)\\1+";
        String[] split = str.split(reg);
        System.out.println(Arrays.toString(split));
        /*
        我们使用“.”来表示任意字符，接着我们需要表示两个这样的字符：这里我们需要使用到“组”的概念：
        用括号：（）来表示组，那么组是干嘛的？
        我们就可以 对组中的数据进行引用：
        那么regex = "(.)\\1"就表示：某一字符出现了两次


        转义字符的使用
　　     对于“haha.lisi.nihao”这样的字符串如果要用"."来切割，要怎么办呢？
        可能你会说定义regex="."不就哦了吗？
        但是如果你代码真这样写的话，你的输出结果就会像你的脑海一样“一片空白”。
        注意：“.”这个符号在正则表达式中是有特殊意义的：
　　     这个小点可以代表任何字符，所以我们需要用转义字符\来将“.”转义为普通的点，所以只要把regex = "\\."即可
         */
    }

    /**
     * 将字符串“张三@@@李四YYY王五*****王尼玛”中的叠词替换为：“、”。
     */
    @org.junit.jupiter.api.Test
    void testSplitReplace(){
        String str="张三@@@李四YYY王五*****王尼玛";
        String reg="(.)\\1+";
        String s = str.replaceAll(reg, "、");
        System.out.println(s);
        /*
         (注意首先我们用(.)来表示任意字符，而\\1是对组(.)中的字符进行复用，合起来就是：两个相同的字符)
        现在我们不只是需要出现两次的字符，所以使用+号来表示出现多次，最终叠词就表示为：regex = "(.)\\1+"
         */
    }

    /**
     * 将“张三@@@李四YYY王五*****王尼玛”中的叠词替换为单字符，即结果为：“张三@李四Y王五*王尼玛”
     */
    @org.junit.jupiter.api.Test
    void testReplaceAll(){

        String str="张三@@@李四YYY王五*****王尼玛";
        String reg="(.)\\1+";
        String s = str.replaceAll(reg, "$1");
        System.out.println(s);
         /*
        首先我们都需要匹配到叠词，但是替换的内容却不是固定的“、”了，
        我们需要将叠词替换为它本身的字符，所以我们需要引用组的内容，
        我们可以使用$1来复用组中第1组的值(即叠词的字符)
         */
    }

    /**
     * 获取字符串“Hi ! Don't be moved by yourself Fzz”中为两个字母的单词。即Hi、be、by。
     *
     */
    @org.junit.jupiter.api.Test
    void testLength(){
        String str="Hi ! Don't be moved by yourself Fzz";
        String reg="\\b[a-zA-Z]{2}\\b";
        Pattern pattern=Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group());
        }

        /*
        对子串进行匹配，即两个字母的单词，字母可以用[a-zA-Z]来表示，范围是两个，所以regex = "[a-zA-Z]{2}"。
        但这样不够准确，我们需要的是单词，而不是三个字母，
        所以要用到“边界匹配器”，单词边界：\\b，所以regex = "\\b[a-zA-Z]{2}\\b"

        正则边界匹配器：
        ^       行的开头
        $       行的结尾
        \\b     单词边界
        \\B     非单词边界
        \\A     输入的开头
        \\G     上一个匹配的结尾
        \\Z     输入的结尾，仅用于最后的结束符（如果有的话）
        \\z     输入的结尾

         */
    }

    /**
     * 请将下面的字符串“我我我......我我......爱...爱爱...学...学......学编程”改为：“我爱学编程”
     */
    @org.junit.jupiter.api.Test
    void testDistinct(){
        String str="我我我......我我......爱...爱爱...学...学......学编程";
        //去掉.
        String reg1= "\\.";
        String str1 = str.replaceAll(reg1, "");
        System.out.println(str1);
        //去掉叠词
        String reg2="(.)\\1+";
        String str2 = str1.replaceAll(reg2,"$1");
        System.out.println(str2);
    }


    /**
     * 获取html网页中的的QQ邮箱
     */
    @org.junit.jupiter.api.Test
    void testEmail() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file= new File(path+File.separator+"email.html");
        String regex="\\d{5,10}@qq.com";
        Pattern pattern=Pattern.compile(regex);


        BufferedReader bf=new BufferedReader(new FileReader(file));
        String line=null;
        //读取行
        while ((line=bf.readLine())!=null){

            //打印找到的qq邮箱
            Matcher matcher=pattern.matcher(line);
            while (matcher.find()){
                System.out.println(matcher.group());
            }
        }
    }




}
