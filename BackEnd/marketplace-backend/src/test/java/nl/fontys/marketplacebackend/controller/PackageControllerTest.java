package nl.fontys.marketplacebackend.controller;

import nl.fontys.marketplacebackend.dto.CreatePackageDTO;
import nl.fontys.marketplacebackend.dto.CreateUpdatePackageDto;
import nl.fontys.marketplacebackend.dto.PackageDto;
import nl.fontys.marketplacebackend.model.Package;
import nl.fontys.marketplacebackend.service.PackageService;
import nl.fontys.marketplacebackend.service.impl.PackageServiceImpl;
import nl.fontys.marketplacebackend.service.impl.RecommendedAlgorithmImpl;
import nl.fontys.marketplacebackend.service.impl.TopRatedAlgorithmImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;



@WebMvcTest(PackageController.class)
@ExtendWith(SpringExtension.class)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageServiceImpl packageService;
    @MockBean
    private TopRatedAlgorithmImpl topRatedAlgorithm;
    @MockBean
    private RecommendedAlgorithmImpl recommendedAlgorithm;

    @Test
    void getAllPackages_ShouldReturn200WithPackages() throws Exception
    {
        List<PackageDto> dtoList = new ArrayList<>();

        dtoList.add(PackageDto.builder().id("id1").build());
        dtoList.add(PackageDto.builder().id("id2").build());
        dtoList.add(PackageDto.builder().id("id3").build());

        when(packageService.getAllPackages()).thenReturn(dtoList);

        mockMvc.perform(get("/packages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [
                                {
                                    "id": "id1"
                                } ,
                                {
                                    "id": "id2"
                                } ,
                                {
                                    "id": "id3"
                                }                 
                            ]
                            """));
        verify(packageService).getAllPackages();
    }

    @Test
    void getPackageById_ShouldReturn200WithPackage() throws Exception
    {
        String id = "id";
        PackageDto dto = PackageDto.builder().id(id).build();

        when(packageService.getPackageById(id)).thenReturn(dto);

        mockMvc.perform(get("/packages/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "id" : "id"
                                }
                                """));
        verify(packageService).getPackageById(id);
    }

    @Test
    @WithMockUser(username = "Fendamear")
    void addPackage_ShouldReturn201WithPackageID() throws Exception
    {
        String image = "image";
        String title = "title";
        String description = "description";
        String categoryId = "categoryId";
        String creatorId = "creatorId";

        CreatePackageDTO dto = CreatePackageDTO.builder().image(image).title(title).description(description).categoryId(categoryId).creatorId(creatorId).build();

        Package p = Package.builder().id("id").image(image).title(title).description(description).build();

        when(packageService.addPackage(dto)).thenReturn(p);

        mockMvc.perform(post("/packages").contentType(APPLICATION_JSON_VALUE).content("""
                                            {
                                                "image" : "image",
                                                "title" : "title",
                                                "description" : "description",
                                                "categoryId" : "categoryId",
                                                "creatorId" : "creatorId"                           
                                            }
                """)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("id"));

        verify(packageService).addPackage(dto);
    }

    @Test
    @WithMockUser(username = "Fendamear")
    void updatePackage_ShouldReturn200WithPackageDTO() throws Exception
    {
        String id = "id";
        String image = "image";
        String title = "title";
        String description = "description";
        String categoryId = "categoryId";
        String creatorId = "creatorId";

        CreateUpdatePackageDto dto = CreateUpdatePackageDto.builder().image(image).title(title).description(description).categoryId(categoryId).creatorId(creatorId).build();

        PackageDto packageDto = PackageDto.builder().id("id").build();

        when(packageService.updatePackage(id, dto)).thenReturn(packageDto);

        mockMvc.perform(put("/packages/" + id).contentType(APPLICATION_JSON_VALUE).content("""
                                            {
                                                "image" : "image",
                                                "title" : "title",
                                                "description" : "description",
                                                "categoryId" : "categoryId",
                                                "creatorId" : "creatorId"                           
                                            }
                """)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "id": "id"
                            }
                             """));

        verify(packageService).updatePackage(id, dto);
    }


    @Test
    void activatePackage_ShouldReturn200WithMessage() throws Exception
    {
        String id = "id";

        when(packageService.activatePackage(id)).thenReturn(true);

        mockMvc.perform(put("/packages/activate/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully activated package: " + id));

        verify(packageService).activatePackage(id);

    }

    @Test
    void activatePackage_ShouldReturn409WithMessage() throws Exception
    {
        String id = "id";

        when(packageService.activatePackage(id)).thenReturn(false);

        mockMvc.perform(put("/packages/activate/" + id))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Failed to activate package"));

        verify(packageService).activatePackage(id);
    }

    @Test
    @WithMockUser(username = "Fendamear")
    void deletePackage_ShouldReturn204() throws Exception
    {
        String id = "id";

        when(packageService.deletePackage(id)).thenReturn(true);

        mockMvc.perform(delete("/packages/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(packageService).deletePackage(id);

    }

    @Test
    @WithMockUser(username = "Fendamear")
    void deletePackage_ShouldReturn500() throws Exception
    {
        String id = "id";

        when(packageService.deletePackage(id)).thenReturn(false);

        mockMvc.perform(delete("/packages/" + id))
                .andDo(print())
                .andExpect(status().isInternalServerError());


        verify(packageService).deletePackage(id);
    }
}
