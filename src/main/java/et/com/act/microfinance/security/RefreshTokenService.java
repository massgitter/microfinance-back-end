package et.com.act.microfinance.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${microfinance.app.jwtRefreshExpirationSec}")
    private Long refreshTokenDurationSec;

    private final RefreshTokenRepo refreshTokenRepo;

    private final UsersRepo usersRepo;


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            refreshToken.setUser(user.get());
            refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationSec));
            refreshToken.setToken(UUID.randomUUID().toString());

            refreshToken = refreshTokenRepo.save(refreshToken);
            return refreshToken;
        }
        return null;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepo.deleteByUser(usersRepo.findById(userId).get());
    }
}
