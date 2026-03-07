package org.unibl.etf.services.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.models.exceptions.NotFoundException;
import org.unibl.etf.models.dto.requests.UserOAuth2Request;
import org.unibl.etf.models.dto.responses.UserOAuth2Response;
import org.unibl.etf.models.dto.responses.UserResponse;
import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.repositories.UserRepository;
import org.unibl.etf.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @PersistenceContext
    private final EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }


    @Transactional
    @Override
    public UserOAuth2Response getUserOAuth2(UserOAuth2Request request) {
        Optional<UserEntity> userEntity = userRepository.findByGoogleId(request.getGoogleId());
        if(userEntity.isPresent()){
            return mapper.map(userEntity.get(), UserOAuth2Response.class);
        }
        UserEntity user = mapper.map(request, UserEntity.class);
        user.setRole(UserRole.USER);
        user = userRepository.saveAndFlush(user);
        entityManager.refresh(user);
        return mapper.map(user, UserOAuth2Response.class);
    }

    @Override
    public UserOAuth2Response getUserOAuth2ByGoogleId(String googleId) {
        UserEntity entity = userRepository.findByGoogleId(googleId).orElseThrow(NotFoundException::new);
        return mapper.map(entity, UserOAuth2Response.class);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findByRoleNot(UserRole.ADMIN).stream().map(user -> mapper.map(user, UserResponse.class)).collect(Collectors.toList());
//        return userRepository.findAll().stream().map(user -> mapper.map(user, UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponse changeRole(Long id, UserRole role) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);
        userEntity.setRole(role);
        userRepository.saveAndFlush(userEntity);
        return mapper.map(userEntity, UserResponse.class);
    }

    @Override
    public UserResponse changeRole(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);
        UserRole role = userEntity.getRole();
        if(UserRole.USER.equals(role))
            role = UserRole.MODERATOR;
        else if(UserRole.MODERATOR.equals(role))
            role = UserRole.USER;
        userEntity.setRole(role);
        userRepository.saveAndFlush(userEntity);
        return mapper.map(userEntity, UserResponse.class);
    }
}
