package com.br.MatchWork.service;

import org.springframework.stereotype.Service;

import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.dtos.CurriculumRequestDto;
import com.br.MatchWork.entity.dtos.CurriculumResponseDto;
import com.br.MatchWork.entity.mapper.CurriculumMapper;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.CurriculumRepository;
import com.br.MatchWork.repository.UserRepository;

@Service
public class CurriculumServiceImpl implements CurriculumService {

    private final CurriculumRepository curriculumRepo;
    private final CurriculumMapper mapper;
    private final UserRepository userRepo;

    public CurriculumServiceImpl(CurriculumRepository curriculumRepo, UserRepository userRepo, CurriculumMapper mapper) {
        this.curriculumRepo = curriculumRepo;
        this.mapper = mapper;
        this.userRepo = userRepo;
    }

    @Override
    public CurriculumResponseDto createCurriculum(String email, CurriculumRequestDto dto) {
        User user = userRepo.findByLogin_Email(email).orElseThrow(() -> new ResourceNotFoundException(email));
        mapper.create(user.getCurriculum(), dto);
        userRepo.save(user);
        return mapper.toResponse(user.getCurriculum());
    }
    

}
