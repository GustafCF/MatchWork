package com.br.MatchWork.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.Login;
import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.dtos.EnterpriseRequestDto;
import com.br.MatchWork.entity.dtos.EnterpriseResponseDto;
import com.br.MatchWork.entity.mapper.EnterpriseMapper;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.EnterpriseRepository;
import com.br.MatchWork.repository.RoleRepository;
import com.br.MatchWork.service.EnterpriseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EnterpriseServiceTest {

    @Mock
    private EnterpriseRepository repository;
    
    @Mock
    private RoleRepository roleRepo;

    @Mock
    private EnterpriseMapper mapper;

    @InjectMocks
    private EnterpriseServiceImpl service;

    private Enterprise enterprise;
    private EnterpriseRequestDto request;
    private EnterpriseResponseDto response;
    private Login login;
    private Role role;

    @BeforeEach
    void setup() {
        role = new Role("ENTERPRISE", "Permissões específicas para Empresas");
        role.setId(2L);

        request = new EnterpriseRequestDto(
            "ADM-FOOD",
            "FOOD E-COMMERCE",
            "AV_MARIE",
            "1212121212",
            "CP - 235",
            "adfood@email.com",
            "senha123"
        );

        login = new Login("adfood@email.com", "passwordEncode");
        enterprise = new Enterprise("ADM-FOOD", "FOOD E-COMMERCE", "AV_MARIE", "1212121212", "CP - 235", login);
        enterprise.setId(1L);

        response = new EnterpriseResponseDto(
            "ADM-FOOD",
            "FOOD E-COMMERCE",
            "AV_MARIE",
            "1212121212",
            "CP - 235",
            "adfood@email.com",
            ""
        );
    }

    @Test
    @DisplayName("Success insert Enterprise")
    void findAllEnterprise_Success() {
        Enterprise e2 = new Enterprise(
            "CHIKEN to", "FOOD E-COMMERCE", "ds 372", "1212121212", "CP - 235",new Login("chiken@email.com", "senha123"));
            e2.setId(2L);
            EnterpriseResponseDto response2 = new EnterpriseResponseDto("CHIKEN to", "FOOD E-COMMERCE", "ds 372", "1212121212", "CP - 235", "chiken@email.com", "");
            
            when(repository.findAll()).thenReturn(List.of(enterprise, e2));
            when(mapper.toResponse(enterprise)).thenReturn(response);
            when(mapper.toResponse(e2)).thenReturn(response2);

            List<EnterpriseResponseDto> result = service.findAll();

            assertEquals(2, result.size());
            assertEquals(response, result.get(0));
            assertEquals(response2, result.get(1));
            verify(repository).findAll();
            verify(mapper).toResponse(enterprise);
            verify(mapper).toResponse(e2);
    }

    @Test
    @DisplayName("Not found Role")
    void insertEnterprise_RoleNotFound() {
        when(roleRepo.findByName("ENTERPRISE")).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.createEnterprise(request));
        assertEquals("Resource not found: ENTERPRISE", exception.getMessage());
        verify(roleRepo).findByName("ENTERPRISE");
        verifyNoInteractions(mapper, repository);
    }

    @Test
    @DisplayName("Find By Enterprise iD - Success")
    void findById_Success() {
        when(repository.findById(2L)).thenReturn(Optional.of(enterprise));
        when(mapper.toResponse(enterprise)).thenReturn(response);
        EnterpriseResponseDto result = service.findById(2L);
        assertEquals(response, result);
        verify(repository).findById(2L);
        verify(mapper).toResponse(enterprise);
    }

    @Test
    @DisplayName("Find By Enterprise iD - Not found")
    void findById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
            () -> service.findById(99L));
        assertEquals("Resource not found: 99", exception.getMessage());
        verify(repository).findById(99L);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Find All Users - Success")
    void findAll_Success() {
        Enterprise enterprise2 = new Enterprise("CHIKEN", "FOOD", "DS_RBC", "13-13-131-32", "br_re", new Login("chiken@email.com", "senha123"));
        enterprise2.setId(3L);
        EnterpriseResponseDto response2 = new EnterpriseResponseDto("CHIKEN", "FOOD", "DS_RBC", "13-13-131-32", "br_re", "chiken@email.com", "");

        when(repository.findAll()).thenReturn(List.of(enterprise, enterprise2));
        when(mapper.toResponse(enterprise)).thenReturn(response);
        when(mapper.toResponse(enterprise2)).thenReturn(response2);

        List<EnterpriseResponseDto> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals(response, result.get(0));
        assertEquals(response2, result.get(1));
        verify(repository).findAll();
        verify(mapper).toResponse(enterprise);
        verify(mapper).toResponse(enterprise2);
    }

    @Test
    @DisplayName("Find All Enterprise - Empty List")
    void findAll_Empty() {
        when(repository.findAll()).thenReturn(List.of());
        List<EnterpriseResponseDto> result = service.findAll();
        assertEquals(0, result.size());
        verify(repository).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Update Enterprise - Success")
    void update_Success() {
        EnterpriseRequestDto updateRequest = new EnterpriseRequestDto(
            "ADM-TEC",
            "Tech E-COMMERCE",
            "AV_MARIE",
            "1212121245",
            "CP - 235",
            "adfood@email.com",
            "senha123"
        );

        Enterprise enterpriseUpdate = new Enterprise(
            "ADM-TEC",
            "Tech E-COMMERCE",
            "AV_MARIE",
            "1212121245",
            "CP - 235",
            enterprise.getLogin()
        );
        enterpriseUpdate.setId(2L);

        EnterpriseResponseDto responseUpdate = new EnterpriseResponseDto(
            "ADM-TEC",
            "Tech E-COMMERCE",
            "AV_MARIE",
            "1212121245",
            "CP - 235",
            "adfood@email.com",
            ""
        );

        when(repository.findById(2L)).thenReturn(Optional.of(enterprise));
        when(repository.save(any(Enterprise.class))).thenReturn(enterpriseUpdate);
        when(mapper.toResponse(enterpriseUpdate)).thenReturn(responseUpdate);

        EnterpriseResponseDto result = service.update(2L, updateRequest);

        assertEquals(responseUpdate, result);
        verify(repository).findById(2L);
        verify(mapper).update(enterprise, updateRequest);
        verify(repository).save(enterprise);
        verify(mapper).toResponse(enterpriseUpdate);
    }
}