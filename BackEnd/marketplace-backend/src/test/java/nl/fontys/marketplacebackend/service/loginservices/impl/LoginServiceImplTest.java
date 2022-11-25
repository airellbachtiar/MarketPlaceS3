package nl.fontys.marketplacebackend.service.loginservices.impl;

import nl.fontys.marketplacebackend.dto.logindtos.LoginRequestDTO;
import nl.fontys.marketplacebackend.dto.logindtos.LoginResponseDTO;
import nl.fontys.marketplacebackend.model.User;
import nl.fontys.marketplacebackend.persistence.UserRepository;
import nl.fontys.marketplacebackend.service.loginservices.AccessTokenEncoder;
import nl.fontys.marketplacebackend.service.loginservices.exception.InvalidLoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @Test
    void login_ReturnResponse() {
        User user = User.builder()
                .id("1")
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .password("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3")
                .build();
        when(userRepositoryMock.findUserByEmail("email")).thenReturn(user);

        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("email")
                .password("123")
                .build();

        LoginResponseDTO actualResponse = loginService.login(request);
        assertNotNull(actualResponse);
        verify(userRepositoryMock).findUserByEmail("email");
    }

    @Test
    void login_UserIsNull_ReturnException() {
        when(userRepositoryMock.findUserByEmail("email")).thenReturn(null);

        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("email")
                .password("password")
                .build();

        assertThrows(InvalidLoginException.class, ()-> loginService.login(request));
        verify(userRepositoryMock).findUserByEmail("email");
    }

    @Test
    void login_PasswordIncorrect_ReturnException() {
        User user = User.builder()
                .id("1")
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .password("password")
                .build();
        when(userRepositoryMock.findUserByEmail("email")).thenReturn(user);

        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("email")
                .password("123")
                .build();

        assertThrows(InvalidLoginException.class, ()-> loginService.login(request));
        verify(userRepositoryMock).findUserByEmail("email");
    }
}
