package top.abosen.toys.arthasdemo.ognl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiubaisen
 * @date 2020/7/15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Statistics {
    private int total;
    private int male;
    private int female;
    private int mostMaleBirthMonth = -1;
    private int mostFemaleBirthMonth = -1;

    @JsonIgnore
    private final Map<Boolean, Map<Integer, Integer>> birthMonths = new HashMap<>();

    public static StatisticsHolder start() {
        return new StatisticsHolder(new Statistics());
    }

    static class StatisticsHolder {
        private final Statistics statistics;

        private StatisticsHolder(Statistics statistics) {
            this.statistics = statistics;
        }

        public StatisticsHolder add(User user) {
            if (user == null) return this;
            statistics.total++;
            if (user.isMale()) statistics.male++;
            else statistics.female++;

            statistics.birthMonths.compute(user.isMale(), (k, v) -> v == null ? new HashMap<>() : v)
                    .compute(user.getBirthday().getMonthValue(), (k, v) -> v == null ? 1 : v + 1);
            return this;
        }

        public Statistics stop() {
            statistics.mostFemaleBirthMonth = statistics.birthMonths.getOrDefault(Boolean.FALSE, Collections.emptyMap())
                    .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(-1);
            statistics.mostMaleBirthMonth = statistics.birthMonths.getOrDefault(Boolean.TRUE, Collections.emptyMap())
                    .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(-1);
            return statistics;
        }
    }
}