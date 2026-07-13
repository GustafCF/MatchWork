package com.br.MatchWork.service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.User;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.EnterpriseRepository;
import com.br.MatchWork.repository.UserRepository;

@Service
public class SecurityService {

    private static final Logger logger = Logger.getLogger(SecurityService.class.getName());

    private UserRepository userRepo;
    private EnterpriseRepository enterpriseRepo;
    
    public SecurityService(UserRepository userRepo, EnterpriseRepository enterpriseRepo) {
        this.userRepo = userRepo;
        this.enterpriseRepo = enterpriseRepo;
    }

    public boolean isOwnerUser(Long userId) {
        String currentName = getAuthentication().get().getName();
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
        return user.equals(currentName);
    }

    public boolean isOwnerEnterprise(Long enterpriseId) {
        String currentName = getAuthentication().get().getName();
        Enterprise enterprise = enterpriseRepo.findById(enterpriseId).orElseThrow(()-> new ResourceNotFoundException(enterpriseId));
        return enterprise.equals(currentName);
    }

    private Optional<Authentication> getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated() || "anymousUser".equals(auth.getPrincipal())) {
            logger.log(Level.INFO, "Authenticated is null");
            return Optional.empty();
        }
        return Optional.of(auth);
    }
}