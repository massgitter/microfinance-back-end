package et.com.act.microfinance.security;

import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Query(value = "SELECT u FROM Users u WHERE (:id is null OR u.id=:id) " +
            "AND (:userName is null OR u.username=:userName) "+
            "AND (:fullName is null OR lower(u.fullName) like lower(concat('%', :fullName,'%')))")
    Page<Users> searchUsers(@Param("id") Long id,
                           @Param("userName") String userName,
                           @Param("fullName") TypedParameterValue fullName,
                           Pageable pageable);
}
