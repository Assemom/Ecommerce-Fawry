package interfaces;

import java.time.LocalDate;

public interface Expired {
    LocalDate getExpirationDate();
    boolean isExpired();
}
