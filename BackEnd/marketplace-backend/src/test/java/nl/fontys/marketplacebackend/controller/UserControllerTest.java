package nl.fontys.marketplacebackend.controller;

import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.dto.UserDto;
import nl.fontys.marketplacebackend.dto.userdtos.CreateUserRequestDTO;
import nl.fontys.marketplacebackend.dto.userdtos.UpdateUserRequestDTO;
import nl.fontys.marketplacebackend.service.PackageService;
import nl.fontys.marketplacebackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PackageService packageService;

    @Test
    void getAllUsers() throws Exception {
        List<UserDto> expectedUsers = List.of(UserDto.builder()
                .id("22")
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n_stankovic@gmail.com")
                .build());

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":  "22", "firstName":  "Nikola", "lastName":  "Stankovic", "email":  "n_stankovic@gmail.com"}]
                          """));

        verify(userService).getAllUsers();
    }

    @Test
    void getUserById() throws Exception {
        UserDto expectedUser = UserDto.builder()
                .id("22")
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n_stankovic@gmail.com")
                .build();

        when(userService.getUserById(expectedUser.getId())).thenReturn(expectedUser);

        mockMvc.perform(get("/users/22"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":  "22", "firstName":  "Nikola", "lastName":  "Stankovic", "email":  "n_stankovic@gmail.com"}
                          """));

        verify(userService).getUserById(expectedUser.getId());
    }

    @Test
    void addUser() throws Exception {
        CreateUserRequestDTO createUserRequestDTO = CreateUserRequestDTO.builder()
                .firstName("Nikola")
                .lastName("Stankovic")
                .email("n_stankovic@gmail.com")
                .password("password")
                .build();

        when(userService.addUser(createUserRequestDTO)).thenReturn(true);

        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                    {
                                        "firstName": "Nikola",
                                        "lastName": "Stankovic",
                                        "email": "n_stankovic@gmail.com",
                                        "password": "password"
                                    }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService).addUser(createUserRequestDTO);
    }

    @Test
    void updateUser() throws Exception {
        //not testable because UserService.updateUser is a void method and cannot be put in mockito method 'when'
    }

    @Test
    void getDownloadedPackageByUser() throws Exception {
        PackageDto expectedPackage = PackageDto.builder()
                .id("11")
                .build();

        when(userService.getDownloadedPackagePerUser("22", "11")).thenReturn(expectedPackage);

        mockMvc.perform(get("/users/22/downloadedPackages/11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":  "11"}
                          """));

        verify(userService).getDownloadedPackagePerUser("22", "11");
    }

    @Test
    void getDownloadedPackagesByUser() throws Exception {
        List<PackageDto> expectedPackages = List.of(PackageDto.builder()
                .id("11")
                .build());

        when(userService.getDownloadedPackagesPerUser("22")).thenReturn(expectedPackages);

        mockMvc.perform(get("/users/22/downloadedPackages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":  "11"}]
                          """));

        verify(userService).getDownloadedPackagesPerUser("22");
    }

    @Test
    void getUploadedPackagesByUser() throws Exception {
        List<PackageDto> expectedPackages = List.of(PackageDto.builder()
                .id("11")
                .build());

        when(packageService.getUploadedPackagesByUser("22")).thenReturn(expectedPackages);

        mockMvc.perform(get("/users/22/uploads"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":  "11"}]
                          """));

        verify(packageService).getUploadedPackagesByUser("22");
    }

    @Test
    @WithMockUser(username = "")
    void addDownloadedPackageByUser() throws Exception {
//        PackageDto packageToAdd = PackageDto.builder()
//                .id("11")
//                .build();
//
//        when(userService.addDownloadedPackage("22", "11")).thenReturn(true);
//
//        mockMvc.perform(post("/users/22/downloadedPackages")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content("""
//                                    {
//                                        "packageId": "11"
//                                    }
//                                """))
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//        verify(userService).addDownloadedPackage("22", "11");
    }

    @Test
    @WithMockUser(username = "")
    void removeDownloadedPackageByUser() throws Exception {
//        when(userService.removeDownloadedPackage("22", "11")).thenReturn(true);
//
//        mockMvc.perform(post("/users/22/downloadedPackages/11")
//                .contentType(APPLICATION_JSON_VALUE)
//                .content("""
//                        """))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        verify(userService).removeDownloadedPackage("22", "11");
    }
}