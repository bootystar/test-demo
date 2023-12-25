package LocalDate;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @author booty
 * @date 2021/5/26 14:06
 */
public class DateTimeTest {

    /**
     * 本地日期和时间
     * LocalDate
     * LocalDateTime
     */
    @Test
    public void testBase() {
        LocalDate of = LocalDate.of(2000, 1, 1);
        System.out.println(of);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime tomorrow = now.plusDays(1);
        System.out.println(yesterday);
        System.out.println(now);
        System.out.println(tomorrow);
    }

    /**
     * 基准时间
     * 相比本地，以格林威治时间为基准
     * 时间可能与本地时间不同，有时区之分
     */
    @Test
    public void testInstant() {
        Instant now = Instant.now();
        System.out.println(now);
    }


    /**
     * 格式化
     * DateTimeFormatter
     *
     * YYYY 代表 Week Year
     * yyyy 代表year
     *
     * MM 代表 月（Month）
     * mm代表 秒（Min）
     *
     * DD  格式是指当前日期在当年中的天数
     * dd  当月的天
     *
     * HH代表24小时制
     * hh代表12小时制
     */
    @Test
    public void testFormat() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDateTime now = LocalDateTime.now();
        String format = dtf.format(now);
        System.out.println(format);


        DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String format1 = iso.format(now);
        System.out.println(format1);

        LocalDateTime parse1 = LocalDateTime.parse(format1, iso);
        System.out.println(parse1);
    }

    /**
     * 计算差值
     *
     * Period       计算两个日期之间的差值
     * Duration     计算两个时间之间的差值(注意:不可用于日期)
     * 时间大的在后,否则是负数
     */
    @Test
    public void testPeriodDuration() throws InterruptedException {

        Instant start = Instant.now();
        Thread.sleep(1000);
        Instant end = Instant.now();
        //时间大的在后，否则负数
        Duration between = Duration.between(start, end);
        System.out.println(between.toNanos());


        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        //日期大的在后，否则负数
        Period between1 = Period.between(yesterday, now);
        System.out.println(between1.getDays());
    }

    /**
     * TemporalAdjuster
     * 时间校正器接口
     * LocalDate和LocalDateTime类可使用with()方法调整时间
     * with()方法需要一个TemporalAdjuster的实现
     * <p>
     * TemporalAdjusters类
     * 该类提供了大量静态方法用于TemporalAdjuster的实现
     */
    @Test
    public void testTemporalAdjuster() {
        LocalDateTime now = LocalDateTime.now();
        //调整日期到当月的指定天上
        LocalDateTime localDateTime = now.withDayOfMonth(20);
        System.out.println(localDateTime);

        //调整到下周日
        LocalDateTime nextSunday = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(nextSunday);

        //调整到下一个工作日
        LocalDateTime with = now.with((e) -> {
            LocalDateTime time = (LocalDateTime) e;
            DayOfWeek dayOfWeek = time.getDayOfWeek();

            if(dayOfWeek.equals(DayOfWeek.FRIDAY)){
                return time.plusDays(3);
            }else if (dayOfWeek.equals(DayOfWeek.SATURDAY)){
                return time.plusDays(2);
            }else {
                return time.plusDays(1);
            }
        });
        System.out.println(with);
    }


    /**
     * 时区相关
     *
     * ZonedDate
     * ZonedTime
     * ZonedDateTime
     */
    @Test
    public void testZoned(){
        //获取可用时区列表
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(availableZoneIds);
        //获取指定时区的现在时间
        LocalDateTime now = LocalDateTime.now(ZoneId.of(availableZoneIds.iterator().next()));
        System.out.println(now);
    }

}
