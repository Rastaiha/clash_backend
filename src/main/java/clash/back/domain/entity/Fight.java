package clash.back.domain.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Fight {
    Player host, guest, winner;
    long startTime;
}
