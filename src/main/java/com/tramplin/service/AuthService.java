package com.tramplin.service;

import com.tramplin.dto.employer.request.RegisterEmployerRequest;
import com.tramplin.dto.request.LoginRequest;
import com.tramplin.dto.request.RefreshTokenRequest;
import com.tramplin.dto.response.AuthResponse;
import com.tramplin.dto.seeker.request.RegisterSeekerRequest;
import com.tramplin.entity.*;
import com.tramplin.enums.Role;
import com.tramplin.enums.UserStatus;
import com.tramplin.enums.VerificationStatus;
import com.tramplin.exception.CompanyAlreadyExistsException;
import com.tramplin.exception.EmailAlreadyExistsException;
import com.tramplin.exception.InvalidTokenException;
import com.tramplin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SeekerRepository seekerRepository;
    private final EmployerRepository employerRepository;
    private final CompanyRepository companyRepository;
    private final CompanyVerificationRepository companyVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse registerSeeker(RegisterSeekerRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SEEKER)
                .status(UserStatus.ACTIVE)
                .build());

        seekerRepository.save(Seeker.builder()
                .user(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isPrivateProfile(false)
                .isApplicationsHistoryPrivate(false)
                .isRecommendationsPrivate(false)
                .build());

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse registerEmployer(RegisterEmployerRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (companyRepository.existsByName(request.getCompanyName())) {
            throw new CompanyAlreadyExistsException(request.getCompanyName());
        }

        User user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EMPLOYER)
                .status(UserStatus.ACTIVE)
                .build());

        Company company = companyRepository.save(Company.builder()
                .name(request.getCompanyName())
                .isVerified(false)
                .build());

        if (request.getWebsiteUrl() != null && !request.getWebsiteUrl().isBlank()) {
            company.getSocials().add(CompanySocial.builder()
                    .company(company)
                    .name("website")
                    .value(request.getWebsiteUrl())
                    .build());
            companyRepository.save(company);
        }

        employerRepository.save(Employer.builder()
                .user(user)
                .company(company)
                .build());

        companyVerificationRepository.save(CompanyVerification.builder()
                .company(company)
                .submittedBy(user)
                .taxNumber(request.getTaxNumber())
                .status(VerificationStatus.PENDING)
                .build());

        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        return buildAuthResponse(user);
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        String email;

        try {
            email = jwtService.extractUsername(token);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidTokenException::new);

        if (!jwtService.isTokenValid(token, user)) {
            throw new InvalidTokenException();
        }

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }
}