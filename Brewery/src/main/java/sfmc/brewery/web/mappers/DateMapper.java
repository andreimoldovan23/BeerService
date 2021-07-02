package sfmc.brewery.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {
    public OffsetDateTime asOffset(Timestamp ts) {
        if (ts != null) {
            LocalDateTime local = ts.toLocalDateTime();
            return OffsetDateTime.of(local.getYear(), local.getMonthValue(), local.getDayOfMonth(),
                    local.getHour(), local.getMinute(), local.getSecond(), local.getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    public Timestamp asTimestamp(OffsetDateTime of) {
        if (of != null) {
            return Timestamp.valueOf(of.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        } else {
            return null;
        }
    }
}
