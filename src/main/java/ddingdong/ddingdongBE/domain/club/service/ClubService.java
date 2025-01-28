package ddingdong.ddingdongBE.domain.club.service;

import ddingdong.ddingdongBE.domain.club.entity.Club;
import java.util.List;

public interface ClubService {

    Long save(Club club);

    Club getById(Long clubId);

    Club getByUserId(Long userId);

    List<Club> findAll();

    void update(Club club, Club updatedClub);

    void delete(Long clubId);

    Club getByUserIdWithFetch(Long userId);
}
